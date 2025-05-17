package com.schnozz.identitiesmod.entities;

import com.schnozz.identitiesmod.items.ItemRegistry;
import com.schnozz.identitiesmod.items.MobHolder;
import com.schnozz.identitiesmod.leveldata.UUIDSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import java.util.Stack;

public class ThrownMobHolder extends ThrowableItemProjectile {

    private static UUIDSavedData command_list;

    public ThrownMobHolder(Level level, LivingEntity shooter) {
        super(EntityType.ENDER_PEARL, shooter, level);
    }

    public ThrownMobHolder(EntityType<ThrownMobHolder> thrownMobHolderEntityType, Level level) {
        super(thrownMobHolderEntityType, level);
    }

    @Override
    protected void onHit(HitResult result)
    {
        if(this.getOwner().level().isClientSide) return;

        BlockPos pos = new BlockPos((int)result.getLocation().x, (int)result.getLocation().y, (int)result.getLocation().z);
        if(this.getOwner() instanceof Player player)
        {
            if(player.getMainHandItem().getItem() instanceof MobHolder mobHolder)
            {
                command_list = UUIDSavedData.get(player.level().getServer());
                Stack<CompoundTag> heldEntities = mobHolder.getHeldEntities();
                if(!heldEntities.isEmpty()) {
                    CompoundTag tag = heldEntities.peek();
                    mobHolder.popHeldEntities();
                    ResourceLocation entityId = ResourceLocation.tryParse(tag.getString("id"));
                    EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(entityId);
                    Entity entity = type.create(player.level());
                    entity.load(tag);
                    entity.moveTo(pos, player.getXRot(), player.getYRot());
                    if (!command_list.getUUIDList().contains(entity.getUUID())) {
                        command_list.addUUID(entity.getUUID());
                    }
                    player.level().addFreshEntity(entity);
                    this.discard();
                }

            }
        }
        this.discard();

    }

    @Override
    protected Item getDefaultItem() {
        return Items.SHULKER_SHELL;
    }
}

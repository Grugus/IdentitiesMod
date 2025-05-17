package com.schnozz.identitiesmod.items;

import com.schnozz.identitiesmod.entities.ThrownMobHolder;
import com.schnozz.identitiesmod.leveldata.UUIDSavedData;
import com.schnozz.identitiesmod.register_attachments.ModDataAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.extensions.IItemExtension;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Stack;

public class MobHolder extends Item  implements IItemExtension {

    private Stack<CompoundTag> heldEntities;
    private static UUIDSavedData command_list;
    private int charges = 0;


    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        tooltipComponents.add(Component.literal("Charges: " + charges));
    }

    public MobHolder(Properties properties) {
        super(properties);
        heldEntities = new Stack<>();
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand){
        if(!player.level().isClientSide && charges>0)
        {
            command_list = UUIDSavedData.get(player.level().getServer());
            if(command_list.getUUIDList().contains(target.getUUID()))
            {
                command_list.removeUUID(target.getUUID());
                CompoundTag tag = new CompoundTag();
                target.save(tag);
                heldEntities.add(tag);
                target.remove(Entity.RemovalReason.DISCARDED);
                command_list.addUUID(tag.getUUID("UUID"));
                charges--;
            }
        }
        return InteractionResult.SUCCESS;


    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        level.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ENDER_PEARL_THROW,
                SoundSource.NEUTRAL,
                0.5F,
                0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
        );
        player.getCooldowns().addCooldown(this, 20);
        if (!level.isClientSide) {
            ThrownMobHolder thrownMobHolder = new ThrownMobHolder(level, player);
            thrownMobHolder.setItem(itemstack);
            thrownMobHolder.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(thrownMobHolder);
        }
        return InteractionResultHolder.success(itemstack);
    }


    public void addCharge()
    {
        charges++;
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        if(!heldEntities.isEmpty())
        {
            addEntity(player, pos);
        }
        return InteractionResult.SUCCESS;
    }



    public Stack<CompoundTag> getHeldEntities()
    {
        return heldEntities;
    }

    public void popHeldEntities()
    {
        heldEntities.pop();
    }


    public void addEntity(Player player, BlockPos pos)
    {
        if(!player.level().isClientSide)
        {
            command_list = UUIDSavedData.get(player.level().getServer());
            CompoundTag tag = heldEntities.pop();
            ResourceLocation entityId = ResourceLocation.tryParse(tag.getString("id"));
            EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(entityId);
            Entity entity = type.create(player.level());
            entity.load(tag);
            entity.moveTo(pos, player.getXRot(), player.getYRot());
            if(!command_list.getUUIDList().contains(entity.getUUID()))
            {
                command_list.addUUID(entity.getUUID());
            }
            player.level().addFreshEntity(entity);


        }
    }

}

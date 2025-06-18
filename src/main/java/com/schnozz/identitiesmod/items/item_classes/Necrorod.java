package com.schnozz.identitiesmod.items.item_classes;

import com.schnozz.identitiesmod.goals.FollowEntityAtDistanceGoal;
import com.schnozz.identitiesmod.leveldata.UUIDSavedData;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.sounds.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Necrorod extends Item {

    private static UUIDSavedData command_list;

    public Necrorod(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide && level instanceof ServerLevel ls && player.getData(ModDataAttachments.POWER_TYPE).equals("Necromancer")) {
            Vec3 eyePosition = player.getEyePosition(1.0F); // Player's eye location
            Vec3 lookVector = player.getLookAngle();        // Direction player is looking
            Vec3 endPosition = eyePosition.add(lookVector.scale(50)); // End of the ray

            // Create AABB for entity search along the line (expanded path)
            AABB searchArea = new AABB(eyePosition, endPosition).inflate(1.0); // Slightly wider search area

            // Filter and find first entity hit
            List<Entity> entities = level.getEntities(player, searchArea, (entity) -> {
                // Optional filters: skip the player and only target living entities
                return entity instanceof LivingEntity && !entity.isSpectator() && entity != player;
            });

            Entity target = null;
            double closestDistance = Double.MAX_VALUE;

            for (Entity entity : entities) {
                AABB entityBox = entity.getBoundingBox().inflate(0.3);
                Optional<Vec3> hit = entityBox.clip(eyePosition, endPosition);

                if (hit.isPresent()) {
                    double distance = eyePosition.distanceTo(hit.get());
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        target = entity;
                    }
                }
            }

            if (target instanceof LivingEntity livingEntity) {
                //sound
                level.playSound(null, player.getOnPos(), ModSounds.ZOMBIES_ARE_COMING_SOUND.get(), SoundSource.PLAYERS);

                command_list = UUIDSavedData.get(level.getServer());
                for(UUID controlled_Entity : command_list.getUUIDList())
                {
                    if(ls.getEntity(controlled_Entity) != null && ls.getEntity(controlled_Entity) instanceof Monster monster)
                    {
                        monster.setTarget(null);
                        monster.goalSelector.removeAllGoals(goal -> goal instanceof FollowEntityAtDistanceGoal);
                        monster.setTarget(livingEntity);
                    }
                }
            } else {
                // do nothing
            }
        }
        super.use(level, player, hand);
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}

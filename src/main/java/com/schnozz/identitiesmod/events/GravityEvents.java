package com.schnozz.identitiesmod.events;

import com.schnozz.identitiesmod.DamageSources.GravityPowerDamageSources;
import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.networking.payloads.GravityPayload;
import com.schnozz.identitiesmod.networking.payloads.EntityDamagePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

import static com.schnozz.identitiesmod.keymapping.ModMappings.*;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class GravityEvents {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        LocalPlayer gravityPlayer = Minecraft.getInstance().player;
        if (gravityPlayer == null) return;
        Level level = gravityPlayer.level();
        if(!level.isClientSide()) return;

        String power = gravityPlayer.getData(ModDataAttachments.POWER_TYPE);
        if (power.equals("Gravity")) {
            float pushDamage = 4.0F; float pullDamage = 4.0F;
                //gravity push
                if (GRAVITY_PUSH_MAPPING.get().consumeClick()) {
                    double xMin = gravityPlayer.getX() - 10.0; double yMin = gravityPlayer.getY() - 10.0; double zMin = gravityPlayer.getZ() - 10.0;
                    double xMax = gravityPlayer.getX() + 10.0; double yMax = gravityPlayer.getY() + 10.0; double zMax = gravityPlayer.getZ() + 10.0;
                    AABB gravityForceBB = new AABB(xMin,yMin,zMin,xMax,yMax,zMax);

                    List<Entity> entitiesInBox = level.getEntities(gravityPlayer, gravityForceBB);
                    for (Entity entity : entitiesInBox) {
                        Vec3 angle = gravityPlayer.getLookAngle();
                        double rx = angle.x; double ry = angle.y; double rz = angle.z;
                        double forceX = 4.0 * rx; double forceY = 4.0 * ry; double forceZ = 4.0 * rz;
                        PacketDistributor.sendToServer(new GravityPayload(entity.getId(),forceX,forceY,forceZ));
                        PacketDistributor.sendToServer(new EntityDamagePayload(entity.getId(),gravityPlayer.getId(),pushDamage));
                    }
                }
                //gravity pull
                else if (GRAVITY_PULL_MAPPING.get().consumeClick()) {
                    double xMin = gravityPlayer.getX() - 20.0; double yMin = gravityPlayer.getY() - 20.0; double zMin = gravityPlayer.getZ() - 20.0;
                    double xMax = gravityPlayer.getX() + 20.0; double yMax = gravityPlayer.getY() + 20.0; double zMax = gravityPlayer.getZ() + 20.0;
                    AABB gravityForceBB = new AABB(xMin,yMin,zMin,xMax,yMax,zMax);

                    List<Entity> entitiesInBox = level.getEntities(gravityPlayer, gravityForceBB);
                    for (Entity entity : entitiesInBox) {
                        double dx = entity.getX() - gravityPlayer.getX(); double dy = entity.getY() - gravityPlayer.getY(); double dz = entity.getZ() - gravityPlayer.getZ();
                        double forceX = -dx/2; double forceY = -dy/2; double forceZ = -dz/2;
                        PacketDistributor.sendToServer(new GravityPayload(entity.getId(),forceX,forceY,forceZ));
                        PacketDistributor.sendToServer(new EntityDamagePayload(entity.getId(),gravityPlayer.getId(),pullDamage));
                    }
                }
        }
    }

}

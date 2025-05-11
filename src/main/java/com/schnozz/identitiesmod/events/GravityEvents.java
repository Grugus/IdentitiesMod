package com.schnozz.identitiesmod.events;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.networking.payloads.GravityPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
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
            double xMin = gravityPlayer.getX() - 8.0; double yMin = gravityPlayer.getY() - 8.0; double zMin = gravityPlayer.getZ() - 8.0;
            double xMax = gravityPlayer.getX() + 8.0; double yMax = gravityPlayer.getY() + 8.0; double zMax = gravityPlayer.getZ() + 8.0;


            AABB gravityForceBB = new AABB(xMin,yMin,zMin,xMax,yMax,zMax);

                //gravity push
                if (GRAVITY_PUSH_MAPPING.get().consumeClick()) {
                    List<Entity> entitiesInBox = level.getEntities(gravityPlayer, gravityForceBB);
                    for (Entity entity : entitiesInBox) {
                        double dx = entity.getX() - gravityPlayer.getX(); double dy = entity.getY() - gravityPlayer.getY(); double dz = entity.getZ() - gravityPlayer.getZ();
                        double forceX = dx > 0 ? -5.0 : 5.0; double forceY = dy > 0 ? -5.0 : 5.0; double forceZ = dz > 0 ? -5.0 : 5.0;
                        PacketDistributor.sendToServer(new GravityPayload(entity.getId(),forceX,forceY,forceZ));
                    }
                }
                //gravity pull
                else if (GRAVITY_PULL_MAPPING.get().consumeClick()) {
                    List<Entity> entitiesInBox = level.getEntities(gravityPlayer, gravityForceBB);
                    for (Entity entity : entitiesInBox) {
                        double dx = entity.getX() - gravityPlayer.getX(); double dy = entity.getY() - gravityPlayer.getY(); double dz = entity.getZ() - gravityPlayer.getZ();
                        double forceX = dx < 0 ? -5.0 : 5.0; double forceY = dy < 0 ? -5.0 : 5.0; double forceZ = dz < 0 ? -5.0 : 5.0;
                        Vec3 gravForce = new Vec3(forceX,forceY,forceZ);
                        PacketDistributor.sendToServer(new GravityPayload(entity.getId(),forceX,forceY,forceZ));
                    }
                }

        }


    }
}

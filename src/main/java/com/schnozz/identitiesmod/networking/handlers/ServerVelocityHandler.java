package com.schnozz.identitiesmod.networking.handlers;

import com.schnozz.identitiesmod.networking.payloads.VelocityPayload;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerVelocityHandler {
    public static void handle(VelocityPayload payload, IPayloadContext context)
    {
        ServerPlayer player = (ServerPlayer) context.player();
        ServerLevel level = player.serverLevel();
        Entity entity = level.getEntity(payload.entityID());

        if (entity == null) return;

        entity.setDeltaMovement(payload.vx(), payload.vy(), payload.vz());
        entity.hurtMarked = true; // mark for sync

        // Optional: Force velocity sync packet to nearby clients
        level.getChunkSource().broadcast(
                entity, new ClientboundSetEntityMotionPacket(entity)
        );
    }
}

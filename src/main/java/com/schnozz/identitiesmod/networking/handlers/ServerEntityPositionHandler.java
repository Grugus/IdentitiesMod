package com.schnozz.identitiesmod.networking.handlers;

import com.schnozz.identitiesmod.networking.payloads.EntityPositionPayload;
import com.schnozz.identitiesmod.networking.payloads.VelocityPayload;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public abstract class ServerEntityPositionHandler {
    public static void handle(EntityPositionPayload payload, IPayloadContext context)
    {
        ServerPlayer player = (ServerPlayer) context.player();
        ServerLevel level = player.serverLevel();
        Entity entity = level.getEntity(payload.entityID());

        if (entity == null) return;

        entity.setPos(payload.x(),payload.y(),payload.z());
        entity.hurtMarked = true; // mark for sync
    }
}

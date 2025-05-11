package com.schnozz.identitiesmod.networking;

import com.schnozz.identitiesmod.networking.payloads.GravityPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Objects;

public class ServerGravityHandler {
    public static void handle(GravityPayload payload, IPayloadContext context)
    {
        ServerPlayer player = (ServerPlayer) context.player();
        Minecraft.getInstance().execute(() -> {
            Objects.requireNonNull(player.level().getEntity(payload.entityID())).push(payload.fx(),payload.fy(),payload.fz());
        });
    }
}

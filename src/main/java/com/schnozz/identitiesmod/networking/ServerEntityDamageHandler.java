package com.schnozz.identitiesmod.networking;

import com.schnozz.identitiesmod.DamageSources.GravityPowerDamageSources;
import com.schnozz.identitiesmod.networking.payloads.PlayerEntityDamagePayload;
import com.schnozz.identitiesmod.networking.payloads.GravityPayload;
import com.schnozz.identitiesmod.networking.payloads.PlayerEntityDamagePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerEntityDamageHandler {
    public static void handle(PlayerEntityDamagePayload payload, IPayloadContext context)
    {
        ServerPlayer player = (ServerPlayer) context.player();
        ServerLevel serverLevel = (ServerLevel) player.level();
        Entity hurtEntity = player.level().getEntity(payload.hurtEntityID());
        DamageSource gravSource = GravityPowerDamageSources.gravityPower(serverLevel);

        Minecraft.getInstance().execute(() -> {
            hurtEntity.hurt(gravSource,payload.damage());
        });
    }
}

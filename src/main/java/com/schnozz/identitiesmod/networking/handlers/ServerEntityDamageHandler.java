package com.schnozz.identitiesmod.networking.handlers;

import com.schnozz.identitiesmod.DamageSources.GravityPowerDamageSources;
import com.schnozz.identitiesmod.networking.payloads.EntityDamagePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerEntityDamageHandler {
    public static void handle(EntityDamagePayload payload, IPayloadContext context)
    {
        ServerPlayer player = (ServerPlayer) context.player();
        ServerLevel serverLevel = (ServerLevel) player.level();
        Entity hurtEntity = player.level().getEntity(payload.hurtEntityID());

        DamageSource dSource = new DamageSource(payload.dType(),serverLevel.getEntity(payload.attackerEntityID()),serverLevel.getEntity(payload.hurtEntityID()));
        Minecraft.getInstance().execute(() -> {
            hurtEntity.hurt(dSource,payload.damage());
        });
    }
}

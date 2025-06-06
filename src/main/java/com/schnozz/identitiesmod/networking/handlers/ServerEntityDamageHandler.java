package com.schnozz.identitiesmod.networking.handlers;

import com.schnozz.identitiesmod.DamageSources.GravityPowerDamageSources;
import com.schnozz.identitiesmod.networking.payloads.EntityDamagePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerEntityDamageHandler {
    public static void handle(EntityDamagePayload payload, IPayloadContext context)
    {
        ServerPlayer player = (ServerPlayer) context.player();
        ServerLevel serverLevel = (ServerLevel) player.level();
        Entity hurtEntity = player.level().getEntity(payload.hurtEntityID());

        DamageSource gravDamageSource = new DamageSource(serverLevel.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(GravityPowerDamageSources.GRAVITY_POWER_DAMAGE),null,player,null);
        Minecraft.getInstance().execute(() -> {
            hurtEntity.hurt(gravDamageSource,payload.damage());
        });
    }
}

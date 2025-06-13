package com.schnozz.identitiesmod.networking.handlers;

import com.schnozz.identitiesmod.mob_effects.ModEffects;
import com.schnozz.identitiesmod.networking.payloads.StunRemovePayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public abstract class ServerStunRemoveHandler {
    public static void handle(StunRemovePayload payload, IPayloadContext context) {
        ServerPlayer player = (ServerPlayer) context.player();
        Entity placeholder = player.level().getEntity(payload.targetId());
        if (placeholder instanceof LivingEntity target) {
            if(target.getActiveEffects().contains(ModEffects.STUN))
            {
                target.removeEffect(ModEffects.STUN);
            }
        }
    }
}

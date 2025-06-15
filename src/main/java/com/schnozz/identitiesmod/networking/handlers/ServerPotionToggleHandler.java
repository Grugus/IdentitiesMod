package com.schnozz.identitiesmod.networking.handlers;

import com.schnozz.identitiesmod.networking.payloads.PotionTogglePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public abstract class ServerPotionToggleHandler {

    public static void handle(PotionTogglePayload payload, IPayloadContext context)
    {
        ServerPlayer player = (ServerPlayer) context.player();

        if(player.hasEffect(payload.effect()))
        {
            player.removeEffect(payload.effect());
        }
        else
        {
            if(payload.level() >= 0) {
                player.addEffect(new MobEffectInstance(payload.effect(), MobEffectInstance.INFINITE_DURATION, payload.level(), false, false, true));
            }
        }
    }
}


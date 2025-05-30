package com.schnozz.identitiesmod.networking;

import com.schnozz.identitiesmod.networking.payloads.PotionLevelPayload;
import com.schnozz.identitiesmod.networking.payloads.PotionTogglePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public abstract class ServerPotionToggleHandler {

    public static void handle(PotionTogglePayload payload, IPayloadContext context)
    {
        Minecraft.getInstance().execute(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            if (player != null) {
                if(player.hasEffect(payload.effect()))
                {
                    player.removeEffect(payload.effect());
                }
                else
                {
                    player.addEffect(new MobEffectInstance(payload.effect(), Integer.MAX_VALUE, payload.level(), false, false));
                }
            }
        });
    }
}


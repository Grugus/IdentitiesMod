package com.schnozz.identitiesmod.networking.handlers;

import com.schnozz.identitiesmod.networking.payloads.PotionLevelPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPotionLevelHandler {

    public static void handle(PotionLevelPayload payload, IPayloadContext context)
    {
        Minecraft.getInstance().execute(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                player.addEffect(new MobEffectInstance(payload.effect(), Integer.MAX_VALUE, payload.level(), false, false));
            }
        });
    }
}

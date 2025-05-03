package com.schnozz.identitiesmod.networking;

import com.schnozz.identitiesmod.networking.payloads.HealthCostPayload;
import com.schnozz.identitiesmod.networking.payloads.PotionLevelPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public abstract class ServerPotionLevelHandler {

    public static void handle(PotionLevelPayload payload, IPayloadContext context)
    {
        Minecraft.getInstance().execute(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            player.addEffect(new MobEffectInstance((Holder<MobEffect>) payload.effect(), Integer.MAX_VALUE, payload.level(), false, false));
        });
    }
}

package com.schnozz.identitiesmod.networking.handlers;

import com.schnozz.identitiesmod.networking.payloads.PotionLevelPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public abstract class ServerPotionLevelHandler {

    public static void handle(PotionLevelPayload payload, IPayloadContext context)
    {

            ServerPlayer player = (ServerPlayer) context.player();
            player.addEffect(new MobEffectInstance((Holder<MobEffect>) payload.effect(), payload.duration(), payload.level(), false, false));

    }
}

package com.schnozz.identitiesmod.networking;

import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.networking.payloads.PotionLevelPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public class ClientPotionLevelHandler {

    public static void handle(PotionLevelPayload payload, IPayloadContext context)
    {
        Minecraft.getInstance().execute(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                player.addEffect(new MobEffectInstance((Holder<MobEffect>) payload.effect(), Integer.MAX_VALUE, payload.level(), false, false));
            }
        });
    }
}

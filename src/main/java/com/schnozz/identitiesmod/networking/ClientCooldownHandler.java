package com.schnozz.identitiesmod.networking;

import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.cooldown.CooldownAttachment;
import com.schnozz.identitiesmod.networking.payloads.CooldownSyncPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientCooldownHandler {
    public static void handle(CooldownSyncPayload payload, IPayloadContext context) {
        LocalPlayer player = Minecraft.getInstance().player;
        CooldownAttachment cdAttachment = player.getData(ModDataAttachments.COOLDOWN);

        if(payload.setRemove())
        {
            cdAttachment.getAllCooldowns().remove(payload.key(), payload.cooldown());

        }
        else
        {
            cdAttachment.getAllCooldowns().put(payload.key(), payload.cooldown());

        }

        player.setData(ModDataAttachments.COOLDOWN, cdAttachment);

    }
}

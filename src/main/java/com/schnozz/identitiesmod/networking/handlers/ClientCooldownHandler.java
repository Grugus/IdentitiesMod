package com.schnozz.identitiesmod.networking.handlers;

import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.cooldown.CooldownAttachment;
import com.schnozz.identitiesmod.networking.payloads.CooldownSyncPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientCooldownHandler {
    public static void handle(CooldownSyncPayload payload, IPayloadContext context) {
        LocalPlayer player = Minecraft.getInstance().player;
        CooldownAttachment newAttachment = new CooldownAttachment();
        newAttachment.getAllCooldowns().putAll(player.getData(ModDataAttachments.COOLDOWN).getAllCooldowns());

        if(payload.setRemove())
        {
            newAttachment.getAllCooldowns().remove(payload.key(), payload.cooldown());

        }
        else
        {
            newAttachment.getAllCooldowns().put(payload.key(), payload.cooldown());

        }

        player.setData(ModDataAttachments.COOLDOWN, newAttachment);

    }
}

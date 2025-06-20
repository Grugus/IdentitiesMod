package com.schnozz.identitiesmod.networking.handlers;

import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.cooldown.CooldownAttachment;
import com.schnozz.identitiesmod.networking.payloads.sync_payloads.CooldownSyncPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerCooldownHandler {
    public static void handle(CooldownSyncPayload payload, IPayloadContext context) {
        Player player = context.player();
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

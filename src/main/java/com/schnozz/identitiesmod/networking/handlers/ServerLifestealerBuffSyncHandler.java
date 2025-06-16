package com.schnozz.identitiesmod.networking.handlers;

import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.networking.payloads.sync_payloads.LifestealerBuffSyncPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerLifestealerBuffSyncHandler {
    public static void handle(LifestealerBuffSyncPayload payload, IPayloadContext context) {
        context.player().setData(ModDataAttachments.LIFESTEALER_BUFFS, payload.attachment());
    }
}

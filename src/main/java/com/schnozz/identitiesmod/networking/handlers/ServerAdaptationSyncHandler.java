package com.schnozz.identitiesmod.networking.handlers;

import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.networking.payloads.sync_payloads.AdaptationSyncPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ServerAdaptationSyncHandler() {
    public static void handle(AdaptationSyncPayload payload, IPayloadContext context) {
        context.player().setData(ModDataAttachments.ADAPTION, payload.attachment());
    }
}

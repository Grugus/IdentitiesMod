package com.schnozz.identitiesmod.networking.handlers;

import com.schnozz.identitiesmod.networking.payloads.sync_payloads.CooldownSyncPayload;
import com.schnozz.identitiesmod.networking.payloads.sync_payloads.GrabSyncPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientGrabSyncPayloadHandler {
    public static void handle(GrabSyncPayload payload, IPayloadContext context) {
        context.player().setPos(payload.position());
    }
}

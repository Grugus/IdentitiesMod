package com.schnozz.identitiesmod.networking.payloads.sync_payloads;

import com.schnozz.identitiesmod.attachments.ViltrumiteAttachment;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ViltrumiteAttachmentSyncPayload(ViltrumiteAttachment attachment) implements CustomPacketPayload {
    public static final Type<ViltrumiteAttachmentSyncPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "viltrumite_attachment_sync"));

    public static final StreamCodec<ByteBuf, ViltrumiteAttachmentSyncPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(ViltrumiteAttachment.CODEC),
            ViltrumiteAttachmentSyncPayload::attachment,
            ViltrumiteAttachmentSyncPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
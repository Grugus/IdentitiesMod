package com.schnozz.identitiesmod.networking.payloads;

import com.schnozz.identitiesmod.attachments.AdaptationAttachment;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public record AdaptationSyncPayload(AdaptationAttachment attachment) implements CustomPacketPayload {
    public static final Type<AdaptationSyncPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "adaptation_sync"));

    public static final StreamCodec<ByteBuf, AdaptationSyncPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(AdaptationAttachment.CODEC),
            AdaptationSyncPayload::attachment,
            AdaptationSyncPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

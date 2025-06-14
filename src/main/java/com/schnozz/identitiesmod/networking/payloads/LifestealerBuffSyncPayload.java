package com.schnozz.identitiesmod.networking.payloads;

import com.schnozz.identitiesmod.attachments.LifestealerBuffsAttachment;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record LifestealerBuffSyncPayload(LifestealerBuffsAttachment attachment) implements CustomPacketPayload {
    public static final Type<LifestealerBuffSyncPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "lifestealer_buff_sync"));

    public static final StreamCodec<ByteBuf, LifestealerBuffSyncPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(LifestealerBuffsAttachment.CODEC),
            LifestealerBuffSyncPayload::attachment,
            LifestealerBuffSyncPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

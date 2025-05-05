package com.schnozz.identitiesmod.networking.payloads;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public record EntityBoxPayload(CompoundTag entity) implements CustomPacketPayload {
    public static final Type<EntityBoxPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "entity_box_payload"));

    public static final StreamCodec<ByteBuf, EntityBoxPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(CompoundTag.CODEC),
            EntityBoxPayload::entity,
            EntityBoxPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

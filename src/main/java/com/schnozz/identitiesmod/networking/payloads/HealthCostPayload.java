package com.schnozz.identitiesmod.networking.payloads;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public record HealthCostPayload(int cost) implements CustomPacketPayload {
    public static final Type<HealthCostPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "health_cost_payload"));

    public static final StreamCodec<ByteBuf, HealthCostPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            HealthCostPayload::cost,
            HealthCostPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}


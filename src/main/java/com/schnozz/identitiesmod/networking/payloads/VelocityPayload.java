package com.schnozz.identitiesmod.networking.payloads;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record VelocityPayload(int entityID, double vx, double vy, double vz) implements CustomPacketPayload {
    public static final Type<VelocityPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "velocity_payload"));




    public static final StreamCodec<ByteBuf, VelocityPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            VelocityPayload::entityID,
            ByteBufCodecs.DOUBLE,
            VelocityPayload::vx,
            ByteBufCodecs.DOUBLE,
            VelocityPayload::vy,
            ByteBufCodecs.DOUBLE,
            VelocityPayload::vz,
            VelocityPayload::new
    );


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}


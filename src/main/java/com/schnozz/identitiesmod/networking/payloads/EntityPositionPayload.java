package com.schnozz.identitiesmod.networking.payloads;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public record EntityPositionPayload(int entityID, double x, double y, double z) implements CustomPacketPayload {
    public static final Type<EntityPositionPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "entity_position_payload"));

    public static final StreamCodec<ByteBuf, EntityPositionPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            EntityPositionPayload::entityID,
            ByteBufCodecs.DOUBLE,
            EntityPositionPayload::x,
            ByteBufCodecs.DOUBLE,
            EntityPositionPayload::y,
            ByteBufCodecs.DOUBLE,
            EntityPositionPayload::z,
            EntityPositionPayload::new
    );


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

package com.schnozz.identitiesmod.networking.payloads;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record PlayerEntityDamagePayload(int hurtEntityID, int attackerEntityID, float damage) implements CustomPacketPayload {
    public static final Type<PlayerEntityDamagePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "entity_box_payload"));

    public static final StreamCodec<ByteBuf, PlayerEntityDamagePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            PlayerEntityDamagePayload::hurtEntityID,
            ByteBufCodecs.INT,
            PlayerEntityDamagePayload::attackerEntityID,
            ByteBufCodecs.FLOAT,
            PlayerEntityDamagePayload::damage,
            PlayerEntityDamagePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

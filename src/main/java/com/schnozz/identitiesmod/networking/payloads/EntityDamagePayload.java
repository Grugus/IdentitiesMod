package com.schnozz.identitiesmod.networking.payloads;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record EntityDamagePayload(int hurtEntityID, int attackerEntityID, float damage) implements CustomPacketPayload {
    public static final Type<EntityDamagePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "entity_damage_payload"));

    public static final StreamCodec<ByteBuf, EntityDamagePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            EntityDamagePayload::hurtEntityID,
            ByteBufCodecs.INT,
            EntityDamagePayload::attackerEntityID,
            ByteBufCodecs.FLOAT,
            EntityDamagePayload::damage,
            EntityDamagePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

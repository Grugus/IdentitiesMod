package com.schnozz.identitiesmod.networking.payloads;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public record EntityDamagePayload(int hurtEntityID, int attackerEntityID, float damage, Holder<DamageType> dType) implements CustomPacketPayload {
    public static final Type<EntityDamagePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "entity_damage_payload"));

    public static final StreamCodec<RegistryFriendlyByteBuf, EntityDamagePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            EntityDamagePayload::hurtEntityID,
            ByteBufCodecs.INT,
            EntityDamagePayload::attackerEntityID,
            ByteBufCodecs.FLOAT,
            EntityDamagePayload::damage,
            ByteBufCodecs.holder(Registries.DAMAGE_TYPE, ByteBufCodecs.registry(Registries.DAMAGE_TYPE)),
            EntityDamagePayload::dType,
            EntityDamagePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

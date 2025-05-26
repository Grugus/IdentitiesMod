package com.schnozz.identitiesmod.networking.payloads;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

public record PotionLevelPayload(Holder<MobEffect> effect, int level, int duration) implements CustomPacketPayload {
    public static final Type<PotionLevelPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "potion_level_payload"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PotionLevelPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.holder(Registries.MOB_EFFECT, ByteBufCodecs.registry(Registries.MOB_EFFECT)),
            PotionLevelPayload::effect,
            ByteBufCodecs.INT,
            PotionLevelPayload::level,
            ByteBufCodecs.INT,
            PotionLevelPayload::duration,
            PotionLevelPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

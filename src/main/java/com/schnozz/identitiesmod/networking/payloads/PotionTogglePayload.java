package com.schnozz.identitiesmod.networking.payloads;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

public record PotionTogglePayload(Holder<MobEffect> effect, int level) implements CustomPacketPayload {
    public static final Type<PotionTogglePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "potion_toggle_payload"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PotionTogglePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.holder(Registries.MOB_EFFECT, ByteBufCodecs.registry(Registries.MOB_EFFECT)),
            PotionTogglePayload::effect,
            ByteBufCodecs.INT,
            PotionTogglePayload::level,
            PotionTogglePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

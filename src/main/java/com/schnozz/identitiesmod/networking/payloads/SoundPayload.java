package com.schnozz.identitiesmod.networking.payloads;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public record SoundPayload(Holder<SoundEvent> sound) implements CustomPacketPayload {
    public static final Type<SoundPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "sound_payload"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SoundPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.holder(Registries.SOUND_EVENT, ByteBufCodecs.registry(Registries.SOUND_EVENT)),
            SoundPayload::sound,
            SoundPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

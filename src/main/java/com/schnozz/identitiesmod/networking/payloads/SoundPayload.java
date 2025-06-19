package com.schnozz.identitiesmod.networking.payloads;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public record SoundPayload(SoundEvent sound, float volume) implements CustomPacketPayload {
    public static final Type<SoundPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "sound_payload"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SoundPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.registry(Registries.SOUND_EVENT),
            SoundPayload::sound,
            ByteBufCodecs.FLOAT,
            SoundPayload::volume,
            SoundPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

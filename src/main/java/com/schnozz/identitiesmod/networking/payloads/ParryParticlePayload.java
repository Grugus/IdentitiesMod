package com.schnozz.identitiesmod.networking.payloads;

import com.schnozz.identitiesmod.cooldown.Cooldown;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ParryParticlePayload(DustColorTransitionOptions particle) implements CustomPacketPayload {

    // Define the Type for the CooldownSyncPayload
    public static final Type<ParryParticlePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "parry_particle_payload"));

    // Create a StreamCodec to serialize and deserialize the payload
    public static final StreamCodec<FriendlyByteBuf, ParryParticlePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(DustColorTransitionOptions.CODEC.codec()),
            ParryParticlePayload::particle,
            ParryParticlePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

package com.schnozz.identitiesmod.networking.payloads;

import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import javax.swing.text.html.parser.Entity;

public record ChaosParticlePayload(DustColorTransitionOptions particle, CompoundTag entity) implements CustomPacketPayload {


    public static final Type<ChaosParticlePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "chaos_particle_payload"));


    public static final StreamCodec<FriendlyByteBuf, ChaosParticlePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(DustColorTransitionOptions.CODEC.codec()),
            ChaosParticlePayload::particle,
            ByteBufCodecs.fromCodec(CompoundTag.CODEC),
            ChaosParticlePayload::entity,
            ChaosParticlePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

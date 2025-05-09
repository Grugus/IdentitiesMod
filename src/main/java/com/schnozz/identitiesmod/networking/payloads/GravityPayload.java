package com.schnozz.identitiesmod.networking.payloads;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public record GravityPayload(int entityID, double fx, double fy, double fz) implements CustomPacketPayload {
    public static final Type<GravityPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "gravity_payload"));


    public static final StreamCodec<ByteBuf, GravityPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            GravityPayload::entityID,
            ByteBufCodecs.DOUBLE,
            GravityPayload::fx,
            ByteBufCodecs.DOUBLE,
            GravityPayload::fy,
            ByteBufCodecs.DOUBLE,
            GravityPayload::fz,
            GravityPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

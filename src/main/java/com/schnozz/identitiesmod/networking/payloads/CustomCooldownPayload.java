package com.schnozz.identitiesmod.networking.payloads;

import com.schnozz.identitiesmod.cooldown.CustomCooldown;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record CustomCooldownPayload(CustomCooldown cooldown, boolean add) implements CustomPacketPayload {
    public static final Type<CustomCooldownPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "cooldown_payload"));

    public static final StreamCodec<ByteBuf, CustomCooldownPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(CustomCooldown.CODEC),
            CustomCooldownPayload::cooldown,
            ByteBufCodecs.BOOL,
            CustomCooldownPayload::add,
            CustomCooldownPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
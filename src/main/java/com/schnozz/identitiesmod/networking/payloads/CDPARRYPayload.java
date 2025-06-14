package com.schnozz.identitiesmod.networking.payloads;

import com.schnozz.identitiesmod.cooldown.Cooldown;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record CDPARRYPayload(Cooldown cooldown) implements CustomPacketPayload {

    // Define the Type for the CooldownSyncPayload
    public static final Type<CDPARRYPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "cdparry_payload"));

    // Create a StreamCodec to serialize and deserialize the payload
    public static final StreamCodec<FriendlyByteBuf, CDPARRYPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(Cooldown.CODEC),
            CDPARRYPayload::cooldown,
            CDPARRYPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

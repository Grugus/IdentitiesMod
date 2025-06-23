package com.schnozz.identitiesmod.networking.payloads.sync_payloads;

import com.schnozz.identitiesmod.codecs.ModCodecs;
import com.schnozz.identitiesmod.cooldown.Cooldown;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public record GrabSyncPayload(Vec3 position) implements CustomPacketPayload {

    // Define the Type for the CooldownSyncPayload
    public static final Type<GrabSyncPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "grab_sync"));

    // Create a StreamCodec to serialize and deserialize the payload
    public static final StreamCodec<FriendlyByteBuf, GrabSyncPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(Vec3.CODEC),
            GrabSyncPayload::position,
            GrabSyncPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

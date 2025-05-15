package com.schnozz.identitiesmod.networking.payloads;


import com.schnozz.identitiesmod.codecs.ModCodecs;
import com.schnozz.identitiesmod.cooldown.Cooldown;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record CooldownSyncPayload(Cooldown cooldown, ResourceLocation key, boolean setRemove) implements CustomPacketPayload {

    // Define the Type for the CooldownSyncPayload
    public static final Type<CooldownSyncPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("identitiesmod", "cooldown_sync"));

    // Create a StreamCodec to serialize and deserialize the payload
    public static final StreamCodec<FriendlyByteBuf, CooldownSyncPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(Cooldown.CODEC),
            CooldownSyncPayload::cooldown,
            ModCodecs.RESOURCE_LOCATION_CODEC,
            CooldownSyncPayload::key,
            ByteBufCodecs.BOOL,
            CooldownSyncPayload::setRemove,
            CooldownSyncPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

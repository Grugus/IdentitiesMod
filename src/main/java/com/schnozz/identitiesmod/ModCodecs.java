package com.schnozz.identitiesmod;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public class ModCodecs {
    public static final StreamCodec<FriendlyByteBuf, ResourceLocation> RESOURCE_LOCATION_CODEC = StreamCodec.of(
            FriendlyByteBuf::writeResourceLocation,
            FriendlyByteBuf::readResourceLocation
    );
}

package com.schnozz.identitiesmod.datacomponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;

import java.util.List;

public record CompoundTagListRecord(List<CompoundTag> entries) {
    public static final Codec<CompoundTagListRecord> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(CompoundTag.CODEC).fieldOf("entries").forGetter(CompoundTagListRecord::entries)
    ).apply(instance, CompoundTagListRecord::new));
}

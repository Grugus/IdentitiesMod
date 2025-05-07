package com.schnozz.identitiesmod.cooldown;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record Cooldown(long startTime, long duration) {

    public static final Codec<Cooldown> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.LONG.fieldOf("start").forGetter(Cooldown::startTime),
            Codec.LONG.fieldOf("duration").forGetter(Cooldown::duration)
    ).apply(instance, Cooldown::new));
}

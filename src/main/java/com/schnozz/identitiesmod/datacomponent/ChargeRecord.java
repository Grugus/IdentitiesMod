package com.schnozz.identitiesmod.datacomponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.schnozz.identitiesmod.cooldown.Cooldown;

public record ChargeRecord(int charge) {
    public static final Codec<ChargeRecord> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("start").forGetter(ChargeRecord::charge)
    ).apply(instance, ChargeRecord::new));
}

package com.schnozz.identitiesmod.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class CombatLoggedAttachment {
    private long hitTime = 0;

    public CombatLoggedAttachment() {}

    public long getHitTime() {
        return hitTime;
    }

    public void setHitTime(long num) {
        hitTime = num;
    }

    public static final Codec<CombatLoggedAttachment> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.LONG.fieldOf("hit_time").forGetter(CombatLoggedAttachment::getHitTime)
            ).apply(instance, unhitTicks -> {
                CombatLoggedAttachment attachment = new CombatLoggedAttachment();
                attachment.setHitTime(unhitTicks);
                return attachment;
            })
    );

}
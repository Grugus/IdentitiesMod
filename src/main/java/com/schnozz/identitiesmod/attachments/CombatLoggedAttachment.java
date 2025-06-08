package com.schnozz.identitiesmod.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class CombatLoggedAttachment {
    private int unhitTicks = 0;

    public CombatLoggedAttachment() {}

    public int getUnhitTicks() {
        return unhitTicks;
    }

    public void setUnhitTicks(int num) {
        unhitTicks = num;
    }

    public static final Codec<CombatLoggedAttachment> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("unhit_ticks").forGetter(CombatLoggedAttachment::getUnhitTicks)
            ).apply(instance, unhitTicks -> {
                CombatLoggedAttachment attachment = new CombatLoggedAttachment();
                attachment.setUnhitTicks(unhitTicks);
                return attachment;
            })
    );

}
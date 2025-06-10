package com.schnozz.identitiesmod.mob_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class StunEffect extends MobEffect {
    protected StunEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public void applyEffect(LivingEntity entity, int amplifier) {
        entity.setDeltaMovement(0,0,0);
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
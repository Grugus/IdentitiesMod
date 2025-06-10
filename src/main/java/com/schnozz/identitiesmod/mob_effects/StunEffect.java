package com.schnozz.identitiesmod.mob_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class StunEffect extends MobEffect {
    public StunEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        entity.setDeltaMovement(0,0,0);
        entity.setPos(entity.xOld,entity.yOld,entity.zOld);
        entity.hurtMarked = true;
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplifier) {
        return true;
    }

    @Override
    public void onEffectAdded(LivingEntity entity, int amplifier) {
        //entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,30,999999999,false,false,false));
        super.onEffectAdded(entity, amplifier);
    }
}
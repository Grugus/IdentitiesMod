package com.schnozz.identitiesmod.mob_effects;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class SuperInvisEffect extends MobEffect {
    private double initialX,initialY,initialZ;

    public SuperInvisEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

}


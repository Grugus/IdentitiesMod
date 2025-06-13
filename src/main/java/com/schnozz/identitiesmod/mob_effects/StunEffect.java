package com.schnozz.identitiesmod.mob_effects;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class StunEffect extends MobEffect {
    private double initialX,initialY,initialZ;
    private boolean onAir;
    public StunEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if(entity.isDeadOrDying())
        {
            return false;
        }
        if(!onAir)
        {
            entity.setDeltaMovement(0,0,0);
            entity.setPos(initialX,initialY,initialZ);
            entity.hurtMarked = true;
            return true;
        }
        else {
            checkAir(entity);
            entity.setDeltaMovement(0,entity.getDeltaMovement().y,0);
            entity.hurtMarked = true;
            return true;
        }
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplifier) {
        return true;
    }

    @Override
    public void onEffectAdded(LivingEntity entity, int amplifier) {
        if(!entity.isDeadOrDying())
        {
            checkAir(entity);
        }
        super.onEffectAdded(entity, amplifier);
    }

    public void checkAir(LivingEntity entity)
    {
        initialX = entity.getX(); initialY = entity.getY() ;initialZ = entity.getZ();

        BlockPos blockBelow = entity.blockPosition().below();
        Block blockAtPos = entity.level().getBlockState(blockBelow).getBlock();
        if(blockAtPos == Blocks.AIR)
        {
            onAir = true;
        }
        else {
            onAir = false;
        }
    }
}
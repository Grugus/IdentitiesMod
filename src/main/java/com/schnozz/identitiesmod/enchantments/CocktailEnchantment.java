package com.schnozz.identitiesmod.enchantments;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public record CocktailEnchantment() implements EnchantmentEntityEffect {
    public static final MapCodec<CocktailEnchantment> CODEC = MapCodec.unit(CocktailEnchantment::new);
    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if(entity instanceof LivingEntity e)
        {
            Random r = new Random();
            //add cooldown instead of checking for effects
            if(r.nextInt(0, 100/(enchantmentLevel*20)) == 0)
            {
                e.addEffect(new MobEffectInstance(MobEffects.WITHER, 80, 1));
                e.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 1));
                e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 1));
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}

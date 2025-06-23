package com.schnozz.identitiesmod.enchantments;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public record WitherEnchantment() implements EnchantmentEntityEffect {
    public static final MapCodec<WitherEnchantment> CODEC = MapCodec.unit(WitherEnchantment::new);
    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if(entity instanceof LivingEntity e)
        {
            Random r = new Random();
            if(r.nextInt(0, 100/(enchantmentLevel*10)) == 0)
            {
                e.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 2));
            }
        }
    }



    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}

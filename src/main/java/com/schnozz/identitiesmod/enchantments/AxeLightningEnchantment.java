package com.schnozz.identitiesmod.enchantments;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public record AxeLightningEnchantment() implements EnchantmentEntityEffect {
    public static final MapCodec<AxeLightningEnchantment> CODEC = MapCodec.unit(AxeLightningEnchantment::new);
    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if(entity instanceof LivingEntity e)
        {
            for(int i = 0; i < enchantmentLevel; i++)
            {
                Random r = new Random();
                if(r.nextInt(0, 5) == 0)
                {
                    EntityType.LIGHTNING_BOLT.spawn(level, e.getOnPos(), MobSpawnType.TRIGGERED);
                }

            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}

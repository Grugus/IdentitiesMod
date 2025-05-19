package com.schnozz.identitiesmod.DamageSources;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public class GravityPowerDamageSources {
    public static final ResourceKey<DamageType> GRAVITY_POWER_DAMAGE =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("identitiesmod", "gravity_power_damage_type"));
}

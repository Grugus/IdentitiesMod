package com.schnozz.identitiesmod.DamageSources;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public class GravityPowerDamageSources {
    public static final ResourceLocation GRAVITY_POWER_ID = ResourceLocation.fromNamespaceAndPath("identitiesmod","gravity_power_damage");
    public static final ResourceKey<DamageType> GRAVITY_POWER_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, GRAVITY_POWER_ID);

    public static DamageSource gravityPower(ServerLevel level) {
        return new DamageSource(level.registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(GRAVITY_POWER_KEY));
    }
}

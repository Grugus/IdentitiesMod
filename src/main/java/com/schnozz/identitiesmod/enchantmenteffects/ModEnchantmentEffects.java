package com.schnozz.identitiesmod.enchantmenteffects;

import com.mojang.serialization.MapCodec;
import com.schnozz.identitiesmod.IdentitiesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEnchantmentEffects {
    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_ENCHANTMENT_EFFECTS =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, IdentitiesMod.MODID);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> WITHER_STRIKE =
            ENTITY_ENCHANTMENT_EFFECTS.register("wither_strike", () -> ApplyWitherEnchantment.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> LIGHTNING_AXE =
            ENTITY_ENCHANTMENT_EFFECTS.register("lightning_axe", () -> AxeLightningEnchantment.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> FIRE_SHIELD =
            ENTITY_ENCHANTMENT_EFFECTS.register("fire_shield", () -> FireShieldEnchantment.CODEC);

    public static void register(IEventBus bus)
    {
        ENTITY_ENCHANTMENT_EFFECTS.register(bus);
    }

}

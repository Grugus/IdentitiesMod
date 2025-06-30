package com.schnozz.identitiesmod.mob_effects;

import com.schnozz.identitiesmod.IdentitiesMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, IdentitiesMod.MODID);

    public static final Holder<MobEffect> STUN = EFFECTS.register("stun", () -> new StunEffect(MobEffectCategory.HARMFUL, 0x9db92c));
    public static final Holder<MobEffect> LIFE_STEALED = EFFECTS.register("life_stealed", () -> new StunEffect(MobEffectCategory.HARMFUL, 0xFF0000));

    public static final Holder<MobEffect> SUPER_INVISIBILITY = EFFECTS.register("super_invisibility", () -> new SuperInvisEffect(MobEffectCategory.HARMFUL, 0xFF69B4));

    public static void register(IEventBus modEventBus) {
        EFFECTS.register(modEventBus);
    }
}

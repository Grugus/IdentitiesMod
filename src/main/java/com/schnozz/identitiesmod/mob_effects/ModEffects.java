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

    public static void register(IEventBus modEventBus) {
        EFFECTS.register(modEventBus);
    }
}

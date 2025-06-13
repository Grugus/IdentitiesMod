package com.schnozz.identitiesmod.mob_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class LifeStealedEffect extends MobEffect {

    public LifeStealedEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onEffectAdded(LivingEntity entity, int amplifier) {
        if(entity instanceof Player p) {
            p.getAttribute(Attributes.MAX_HEALTH).setBaseValue(p.getAttribute(Attributes.MAX_HEALTH).getValue() - 2);
        }
        super.onEffectAdded(entity, amplifier);
    }
}

package com.schnozz.identitiesmod.events.effect;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.mob_effects.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ServerLifeStealedEvents {
    @SubscribeEvent
    public static void onEffectRemoved(MobEffectEvent.Remove event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance effect = event.getEffectInstance();
        if(effect.is(ModEffects.LIFE_STEALED)) {
            if (entity instanceof Player player) {
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20);
            }
        }
    }
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event)
    {
        Entity deadEntity = event.getEntity();
        if (deadEntity instanceof Player deadPlayer) {
            if (event.getSource().getDirectEntity() != null && event.getSource().getDirectEntity().getData(ModDataAttachments.POWER_TYPE).equals("Lifestealer")) {
                deadPlayer.addEffect(new MobEffectInstance(ModEffects.LIFE_STEALED, MobEffectInstance.INFINITE_DURATION, 0, false, false, true));
            }
        }
    }
}

package com.schnozz.identitiesmod.events.effects;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.mob_effects.ModEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ServerStunEffectEvents {
    //prevents item use
    @SubscribeEvent
    public static void onPlayerUseItemStart(LivingEntityUseItemEvent.Start event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player) {
            if (player.hasEffect(ModEffects.STUN)) {
                event.setCanceled(true);
            }
        }
    }
    @SubscribeEvent
    public static void onEntityAttack(AttackEntityEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(ModEffects.STUN)) {
            event.setCanceled(true);
        }
    }

}

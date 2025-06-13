package com.schnozz.identitiesmod.events.effect;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.mob_effects.ModEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ServerStunEffectEvents {

    @SubscribeEvent
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        Entity attacker = event.getSource().getEntity();
        if(attacker instanceof ServerPlayer attackerPlayer)
        {
            if (attackerPlayer.getActiveEffects().contains(ModEffects.STUN)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onItemUse(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();

        if (player.hasEffect(ModEffects.STUN)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onUseTick(LivingEntityUseItemEvent.Tick event) {
        LivingEntity entity = event.getEntity();

        if (entity.hasEffect(ModEffects.STUN)) {
            event.setCanceled(true);
        }
    }
}

package com.schnozz.identitiesmod.events;

import com.schnozz.identitiesmod.DamageSources.GravityPowerDamageSources;
import com.schnozz.identitiesmod.IdentitiesMod;
import net.minecraft.world.damagesource.DamageSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ServerGravityEvents {
    @SubscribeEvent
    public static void onKnockback(LivingKnockBackEvent event) {
        DamageSource source = event.getEntity().getLastDamageSource();
        if(source == null) return;
        if (source.is(GravityPowerDamageSources.GRAVITY_POWER_DAMAGE)) {
            event.setCanceled(true);
        }
    }
}

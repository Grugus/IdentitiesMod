package com.schnozz.identitiesmod.events.gravity;

import com.schnozz.identitiesmod.damage_sources.ModDamageTypes;
import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ServerGravityEvents {
    @SubscribeEvent
    public static void onKnockback(LivingKnockBackEvent event) {
        DamageSource source = event.getEntity().getLastDamageSource();
        if(source == null) return;
        if (source.is(ModDamageTypes.GRAVITY_POWER_DAMAGE)) {
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        Entity entity = event.getEntity();
        if(entity.getData(ModDataAttachments.POWER_TYPE).equals("Gravity")) {
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void onItemUse(PlayerInteractEvent.RightClickItem event) {
        Entity entity = event.getEntity();
        if(entity.getData(ModDataAttachments.POWER_TYPE).equals("Gravity") && event.getItemStack().getItem() == Items.ENDER_PEARL) {
            event.setCanceled(true);
        }
    }
}

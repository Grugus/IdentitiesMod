package com.schnozz.identitiesmod.events.item;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.items.item_classes.FastPowerGauntlet;
import com.schnozz.identitiesmod.items.item_classes.StrongPowerGauntlet;
import com.schnozz.identitiesmod.sounds.ModSounds;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;

import java.util.Random;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ServerPowerGauntletEvents {
    private static Random ran = new Random();
    //disables crit on fast power gauntlets
    @SubscribeEvent
    public static void onLivingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getDirectEntity() instanceof ServerPlayer player)) return;

        ItemStack weapon = player.getMainHandItem();
        if (weapon.getItem() instanceof FastPowerGauntlet gauntlet) {
            float damage = (float)(gauntlet.getTier().getAttackDamageBonus()) + 1;
            event.setAmount(damage);
        }
    }
    //cancel knockback on fast
    @SubscribeEvent
    public static void onKnockback(LivingKnockBackEvent event) {
        DamageSource source = event.getEntity().getLastDamageSource();
        if(source == null) return;
        if(source.getDirectEntity() == null){return;}

        if(source.getDirectEntity() instanceof Player player) {
            ItemStack weapon = player.getMainHandItem();
            if (weapon.getItem() instanceof FastPowerGauntlet gauntlet) {
                event.setCanceled(true);
            }
        }
    }
    //disables use of power gauntelets by non-viltrumites

}

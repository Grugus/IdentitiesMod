package com.schnozz.identitiesmod.events.item;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.items.item_classes.FastPowerGauntlet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class FastPowerGauntletEvents {
    @SubscribeEvent
    public static void onLivingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getDirectEntity() instanceof ServerPlayer player)) return;

        ItemStack weapon = player.getMainHandItem();
        if (weapon.getItem() instanceof FastPowerGauntlet gauntlet) {
            float damage = (float)(gauntlet.getTier().getAttackDamageBonus()) + 1;
            event.setAmount(damage);
        }
    }
}

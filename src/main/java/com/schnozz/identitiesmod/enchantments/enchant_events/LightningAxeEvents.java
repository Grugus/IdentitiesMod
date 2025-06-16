package com.schnozz.identitiesmod.enchantments.enchant_events;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.datagen.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class LightningAxeEvents {
    @SubscribeEvent
    public static void onPlayerAttacked(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide && player.level() instanceof ServerLevel level) {
            if (player.getMainHandItem().getItem() instanceof AxeItem axe) {

                ItemEnchantments enchantments = player.getMainHandItem().getAllEnchantments(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT));
                Holder<Enchantment> lightningAxeHolder = level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(ModEnchantments.LIGHTNING_AXE);

                if(enchantments.getLevel(lightningAxeHolder) <= 0)
                {
                    return;
                }
                System.out.println("Source ID: " + event.getSource().getMsgId());
                if (event.getSource().getMsgId().equals("lightningBolt")) {
                    event.setAmount(0);
                    System.out.println("HIT BY LIGHTNING");
                }
            }
        }
    }
}

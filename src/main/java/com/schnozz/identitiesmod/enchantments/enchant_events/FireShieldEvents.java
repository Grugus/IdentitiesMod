package com.schnozz.identitiesmod.enchantments.enchant_events;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.datagen.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class FireShieldEvents {

    @SubscribeEvent
    public static void onPlayerAttacked(LivingDamageEvent.Pre event) {
        if(event.getEntity() instanceof Player player && !player.level().isClientSide && player.level() instanceof ServerLevel level)
        {
            if (!player.isBlocking()) return;

            ItemStack shield = player.getUseItem();
            if (shield.isEmpty() || !shield.getItem().equals(Items.SHIELD)) return;


            ItemEnchantments enchantments = ((IItemStackExtension) shield).getAllEnchantments(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT));
            Holder<Enchantment> fireShieldHolder = level.registryAccess()
                    .registryOrThrow(Registries.ENCHANTMENT)
                    .getHolderOrThrow(ModEnchantments.FIRE_SHIELD);

            if(enchantments.getLevel(fireShieldHolder) <= 0)
            {
                return;
            }

            if (event.getSource().getDirectEntity() instanceof LivingEntity attacker) {
                attacker.igniteForTicks(40 * enchantments.getLevel(fireShieldHolder));
            }
        }
    }
}

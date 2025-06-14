package com.schnozz.identitiesmod.enchantmenteffects;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.datagen.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class EnchantmentEvents {

    @SubscribeEvent
    public static void onPlayerAttacked(LivingDamageEvent.Pre event) {


        if(event.getEntity() instanceof Player player && !player.level().isClientSide && player.level() instanceof ServerLevel level)
        {
            if(player.getMainHandItem().getItem() instanceof AxeItem a)
            {
                ItemEnchantments enchantments = ((IItemStackExtension) player.getMainHandItem()).getAllEnchantments(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT));
                Holder<Enchantment> fireShieldHolder = level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(ModEnchantments.LIGHTNING_AXE);

                // CHECK LIGHTNING BOLT AND THEN CANCEL DAMAGE

            }

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
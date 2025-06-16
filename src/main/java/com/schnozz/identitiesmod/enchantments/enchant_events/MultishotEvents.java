package com.schnozz.identitiesmod.enchantments.enchant_events;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.datagen.ModEnchantments;
import com.schnozz.identitiesmod.enchantments.enchant_register.ModEnchantmentEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class MultishotEvents {
    @SubscribeEvent
    public static void onArrowLoosed(ArrowLooseEvent event)
    {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide && player.level() instanceof ServerLevel level) {
            ItemStack bow = event.getBow();
            float power = BowItem.getPowerForTime(event.getCharge());

            ItemEnchantments enchantments = bow.getAllEnchantments(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT));
            Holder<Enchantment> multishotHolder = level.registryAccess()
                    .registryOrThrow(Registries.ENCHANTMENT)
                    .getHolderOrThrow(ModEnchantments.MULTISHOT);

            if (enchantments.getLevel(multishotHolder) <= 0) {
                return;
            }
            for(int i = 0; i < enchantments.getLevel(multishotHolder); i++) {
                ItemStack arrowStack = player.getProjectile(bow);
                if (arrowStack.isEmpty()) return;
                Item item = arrowStack.getItem();
                if (!(item instanceof ArrowItem)) return;

                ArrowItem arrowItem = (ArrowItem)item;

                AbstractArrow arrow = arrowItem.createArrow(level, player.getProjectile(bow), player, bow);
                arrow.setOwner(player);
                arrow.setCritArrow(power == 1.0F);
                arrow.setBaseDamage(arrow.getBaseDamage() * power);

                // Calculate spread angle
                float spread = (i - enchantments.getLevel(multishotHolder) / 2.0f) * 10.0f; // in degrees
                Vec3 look = player.getLookAngle();
                Vec3 rotated = look.yRot((float) Math.toRadians(spread));

                arrow.shoot(rotated.x, rotated.y, rotated.z, power * 3.0F, 1.0F); // velocity, inaccuracy

                level.addFreshEntity(arrow);

                //consume arrows from inventory
            }
        }
    }
}

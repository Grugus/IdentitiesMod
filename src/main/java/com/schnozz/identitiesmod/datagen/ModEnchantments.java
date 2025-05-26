package com.schnozz.identitiesmod.datagen;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.enchantmenteffects.ApplyWitherEnchantment;
import com.schnozz.identitiesmod.enchantmenteffects.AxeLightningEnchantment;
import com.schnozz.identitiesmod.enchantmenteffects.FireShieldEnchantment;
import io.netty.bootstrap.BootstrapConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> WITHER_STRIKE = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "wither_strike"));
    public static final ResourceKey<Enchantment> LIGHTNING_AXE = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "lightning_axe"));
    public static final ResourceKey<Enchantment> FIRE_SHIELD = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "fire_shield"));
    public static  final TagKey<Item> shieldTag = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("neoforge", "shields"));

    public static void bootstrap(BootstrapContext<Enchantment> context){
        var enchantments = context.lookup(Registries.ENCHANTMENT);
        var items = context.lookup(Registries.ITEM);

        register(context, WITHER_STRIKE, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                5,
                2,
                Enchantment.dynamicCost(5, 7),
                Enchantment.dynamicCost(25, 7),
                2,
                EquipmentSlotGroup.MAINHAND))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new ApplyWitherEnchantment())
        );

        register(context, LIGHTNING_AXE, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ItemTags.AXES),
                        5,
                        2,
                        Enchantment.dynamicCost(5, 7),
                        Enchantment.dynamicCost(25, 7),
                        2,
                        EquipmentSlotGroup.MAINHAND))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new AxeLightningEnchantment())
        );

        register(context, FIRE_SHIELD, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(shieldTag),
                        1,
                        2,
                        Enchantment.dynamicCost(5, 7),
                        Enchantment.dynamicCost(25, 7),
                        2,
                        EquipmentSlotGroup.MAINHAND))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new FireShieldEnchantment())
        );
    }

    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key,  builder.build(key.location()));
    }
}

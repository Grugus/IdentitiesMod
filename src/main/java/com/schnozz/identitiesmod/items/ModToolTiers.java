package com.schnozz.identitiesmod.items;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

public class ModToolTiers {
    public static final Tier SCYTHE = new SimpleTier(BlockTags.INCORRECT_FOR_NETHERITE_TOOL,5000, 9, 0, 3, () -> Ingredient.of(Items.DIAMOND) );// tag for mineable blocks)
    public static final Tier POWER_GAUNTLET = new SimpleTier(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, Integer.MAX_VALUE, 1.3F, 3.5F, 3, null);
}

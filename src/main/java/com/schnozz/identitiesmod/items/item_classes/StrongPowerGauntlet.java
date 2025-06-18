package com.schnozz.identitiesmod.items.item_classes;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

import javax.management.Attribute;

public class StrongPowerGauntlet extends SwordItem {

    public StrongPowerGauntlet(Tier tier, Properties properties)
    {
        super(tier,properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        super.use(level, player, hand);
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

}



package com.schnozz.identitiesmod.items.item_classes;

import com.schnozz.identitiesmod.register_attachments.ModDataAttachments;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class PowerGauntlet extends SwordItem {
    public static float gauntletDamage = 7.0F;

    public PowerGauntlet(Tier tier, Properties properties) {
        super(tier,properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        super.use(level, player, hand);
        return  InteractionResultHolder.success(player.getItemInHand(hand));
    }


}


package com.schnozz.identitiesmod.items.item_classes;

import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class FastPowerGauntlet extends SwordItem {

    public FastPowerGauntlet(Tier tier, Properties properties) {
        super(tier,properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        super.use(level, player, hand);
        if(!player.getData(ModDataAttachments.POWER_TYPE).equals("Viltrumite"))
        {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        // Prevent breaking blocks with it
        if(!player.getData(ModDataAttachments.POWER_TYPE).equals("Viltrumite"))
        {
            return false;
        }
        return true;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if(!player.getData(ModDataAttachments.POWER_TYPE).equals("Viltrumite")) {
            return true; // true = cancels left click
        }
        return false;
    }
}



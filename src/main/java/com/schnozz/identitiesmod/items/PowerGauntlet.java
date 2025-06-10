package com.schnozz.identitiesmod.items;

import com.schnozz.identitiesmod.goals.FollowEntityAtDistanceGoal;
import com.schnozz.identitiesmod.leveldata.UUIDSavedData;
import com.schnozz.identitiesmod.register_attachments.ModDataAttachments;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class PowerGauntlet extends Item {
    public PowerGauntlet(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        super.use(level, player, hand);
        return  InteractionResultHolder.success(player.getItemInHand(hand));
    }
}


package com.schnozz.identitiesmod.items;

import com.schnozz.identitiesmod.goals.FollowEntityAtDistanceGoal;
import com.schnozz.identitiesmod.leveldata.UUIDSavedData;
import com.schnozz.identitiesmod.register_attachments.ModDataAttachments;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class BoneWhistle extends Item {
    public BoneWhistle(Properties properties) {
        super(properties);
    }

    private static UUIDSavedData command_list;

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide && level instanceof ServerLevel ls && player.getData(ModDataAttachments.POWER_TYPE).equals("Necromancer")) {
            command_list = UUIDSavedData.get(level.getServer());
            for (UUID controlled_Entity : command_list.getUUIDList()) {
                if (ls.getEntity(controlled_Entity) != null && ls.getEntity(controlled_Entity) instanceof Monster monster) {
                    monster.setTarget(null);
                    monster.goalSelector.addGoal(3, new FollowEntityAtDistanceGoal(monster, player,1D, 5f));
                    //set goal follow player
                }
            }
        }

        super.use(level, player, hand);
        return  InteractionResultHolder.success(player.getItemInHand(hand));
    }
}

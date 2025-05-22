package com.schnozz.identitiesmod.items;

import com.schnozz.identitiesmod.leveldata.UUIDSavedData;
import com.schnozz.identitiesmod.register_attachments.ModDataAttachments;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class BoneShears extends Item {
    public BoneShears(Properties properties) {
        super(properties);
    }

    private static UUIDSavedData command_list;

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand)
    {
        if(!player.level().isClientSide && player.getData(ModDataAttachments.POWER_TYPE).equals("Necromancer"))
        {
            command_list = UUIDSavedData.get(((ServerLevel) player.level()).getServer());
            if(command_list.getUUIDList().contains(target.getUUID()))
            {
                target.kill();
            }
        }
        return InteractionResult.SUCCESS;
    }
}

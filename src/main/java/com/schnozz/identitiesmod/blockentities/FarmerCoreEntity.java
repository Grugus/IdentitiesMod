package com.schnozz.identitiesmod.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FarmerCoreEntity extends BlockEntity {

    public FarmerCoreEntity(BlockPos pos, BlockState blockState) {



        super(BlockEntityRegistry.FARMING_BE.get(), pos, blockState);
    }
}

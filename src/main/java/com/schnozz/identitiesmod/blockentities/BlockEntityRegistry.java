package com.schnozz.identitiesmod.blockentities;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.blocks.BlockRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, IdentitiesMod.MODID);

    public static final Supplier<BlockEntityType<FarmerCoreEntity>> FARMING_BE =
            BLOCK_ENTITIES.register( "farming_be", () -> BlockEntityType.Builder.of(FarmerCoreEntity::new, BlockRegistry.FARMING_BLOCK.get()).build(null));

}

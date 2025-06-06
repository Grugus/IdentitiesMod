package com.schnozz.identitiesmod.blocks;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.items.ItemRegistry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(IdentitiesMod.MODID);



    public  static final DeferredBlock<Block> FARMING_BLOCK = registerBlock("farming_block",
            ()-> new FarmerCore(BlockBehaviour.Properties.of()
                    .sound(SoundType.GRASS)

            ));



    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block)
    {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }



    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block)
    {
        ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}

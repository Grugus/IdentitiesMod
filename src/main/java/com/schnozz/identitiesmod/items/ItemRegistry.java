package com.schnozz.identitiesmod.items;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.items.item_classes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ItemRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IdentitiesMod.MODID);

    public static final DeferredItem<Item> NECROROD = ITEMS.registerItem(
            "necrorod",
            Necrorod::new, // The factory that the properties will be passed into.
            new Item.Properties().stacksTo(1) // The properties to use.
    );
    public static final DeferredItem<Item> MOB_HOLDER = ITEMS.registerItem(
            "mobholder",
            MobHolder::new, // The factory that the properties will be passed into.
            new Item.Properties().stacksTo(1) // The properties to use.
    );

    public static final Supplier<SwordItem> SCYTHE = ITEMS.register("scythe", () -> new Scythe(
            // The tier to use.
            ModToolTiers.SCYTHE,
            // The item properties. We don't need to set the durability here because TieredItem handles that for us.
            new Item.Properties().stacksTo(1).attributes(
                    // There are `createAttributes` methods in either the class or subclass of each DiggerItem
                    SwordItem.createAttributes(
                            // The tier to use.
                            ModToolTiers.SCYTHE,
                            // The type-specific attack damage bonus. 3 for swords, 1.5 for shovels, 1 for pickaxes, varying for axes and hoes.
                            1.5f,
                            // The type-specific attack speed modifier. The player has a default attack speed of 4, so to get to the desired
                            // value of 1.6f, we use -2.4f. -2.4f for swords, -3f for shovels, -2.8f for pickaxes, varying for axes and hoes.
                            -2.8f
                            )
            )
    ));
    public static final Supplier<SwordItem> STRONG_POWER_GAUNTLET = ITEMS.register("strong_power_gauntlet", () -> new StrongPowerGauntlet(
            ModToolTiers.STRONG_POWER_GAUNTLET,
            new Item.Properties().stacksTo(1).attributes(
                    SwordItem.createAttributes(
                            ModToolTiers.STRONG_POWER_GAUNTLET,
                            // The type-specific attack damage bonus. 3 for swords, 1.5 for shovels, 1 for pickaxes, varying for axes and hoes.
                            3f,
                            // The type-specific attack speed modifier. The player has a default attack speed of 4, so to get to the desired
                            // value of 1.6f, we use -2.4f. -2.4f for swords, -3f for shovels, -2.8f for pickaxes, varying for axes and hoes.
                            -3f
                    )
            )
    ));
    public static final Supplier<SwordItem> FAST_POWER_GAUNTLET = ITEMS.register("fast_power_gauntlet", () -> new FastPowerGauntlet(
            ModToolTiers.FAST_POWER_GAUNTLET,
            new Item.Properties().stacksTo(1).attributes(
                    SwordItem.createAttributes(
                            ModToolTiers.FAST_POWER_GAUNTLET,
                            // The type-specific attack damage bonus. 3 for swords, 1.5 for shovels, 1 for pickaxes, varying for axes and hoes.
                            0,
                            // The type-specific attack speed modifier. The player has a default attack speed of 4, so to get to the desired
                            // value of 1.6f, we use -2.4f. -2.4f for swords, -3f for shovels, -2.8f for pickaxes, varying for axes and hoes.
                            -1f
                    )
            )
    ));

    public static final DeferredItem<Item> BONE_WHISTLE = ITEMS.registerItem(
            "bone_whistle",
            BoneWhistle::new, // The factory that the properties will be passed into.
            new Item.Properties().stacksTo(1) // The properties to use.
    );
    public static final DeferredItem<Item> BONE_SHEARS = ITEMS.registerItem(
            "bone_shears",
            BoneShears::new, // The factory that the properties will be passed into.
            new Item.Properties().stacksTo(1) // The properties to use.
    );


    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }
}

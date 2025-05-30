package com.schnozz.identitiesmod.items;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.datacomponent.ChargeRecord;
import com.schnozz.identitiesmod.datacomponent.ModDataComponentRegistry;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;

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
    public static final DeferredItem<Item> POWER_GAUNTLET = ITEMS.registerItem(
            "power_gauntlet",
            PowerGauntlet::new, // The factory that the properties will be passed into.
            new Item.Properties().stacksTo(1) // The properties to use.
    );

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }
}

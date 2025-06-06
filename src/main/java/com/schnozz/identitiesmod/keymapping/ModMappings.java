package com.schnozz.identitiesmod.keymapping;

import com.mojang.blaze3d.platform.InputConstants;
import com.schnozz.identitiesmod.IdentitiesMod;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;


@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModMappings {

    //Viltrumite
    public static final Lazy<KeyMapping> VILTRUMITE_GRAB_MAPPING = Lazy.of(() -> new KeyMapping(
            "key.identitiesmod.viltrmuite.grab", // Will be localized using this translation key
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_G, // Default key is G
            "key.categories.misc" // Mapping will be in the misc category
    ));
    public static final Lazy<KeyMapping> VILTRUMITE_CHOKE_MAPPING = Lazy.of(() -> new KeyMapping(
            "key.identitiesmod.viltrumite.choke",
            KeyConflictContext.UNIVERSAL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            "key.categories.misc"
    ));
    //Lifestealer Screen
    public static final Lazy<KeyMapping> LIFESTEALER_MAPPING = Lazy.of(() -> new KeyMapping(
            "key.identitiesmod.lifestealer.screen",
            KeyConflictContext.UNIVERSAL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            "key.categories.misc"
    ));
    //Necromancer
    public static final Lazy<KeyMapping> NECROMANCER_MAPPING = Lazy.of(() -> new KeyMapping(
            "key.identitiesmod.necromancer.remove_target",
            KeyConflictContext.UNIVERSAL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            "key.categories.misc"
    ));
    //Gravity
    public static final Lazy<KeyMapping> GRAVITY_PUSH_MAPPING = Lazy.of(() -> new KeyMapping(
            "key.identitiesmod.gravity.push",
            KeyConflictContext.UNIVERSAL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            "key.categories.misc"
    ));
    public static final Lazy<KeyMapping> GRAVITY_PULL_MAPPING = Lazy.of(() -> new KeyMapping(
            "key.identitiesmod.gravity.pull",
            KeyConflictContext.UNIVERSAL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            "key.categories.misc"
    ));
    public static final Lazy<KeyMapping> GRAVITY_VORTEX_MAPPING = Lazy.of(() -> new KeyMapping(
            "key.identitiesmod.gravity.vortex",
            KeyConflictContext.UNIVERSAL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            "key.categories.misc"
    ));
    public static final Lazy<KeyMapping> GRAVITY_METEOR_MAPPING = Lazy.of(() -> new KeyMapping(
            "key.identitiesmod.gravity.meteor",
            KeyConflictContext.UNIVERSAL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "key.categories.misc"
    ));


    public static final Lazy<KeyMapping> GRAVITY_CHAOS_MAPPING = Lazy.of(() -> new KeyMapping(
            "key.identitiesmod.gravity.chaos",
            KeyConflictContext.UNIVERSAL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_X,
            "key.categories.misc"
    ));
    //Parry
    public static final Lazy<KeyMapping> PARRY_MAPPING = Lazy.of(() -> new KeyMapping(
            "key.identitiesmod.parry",
            KeyConflictContext.UNIVERSAL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "key.categories.misc"
    ));
    //Adaptation
    public static final Lazy<KeyMapping> ADAPTATION_SWITCH_MAPPING = Lazy.of(() -> new KeyMapping(
            "key.identitiesmod.adaptation.switch",
            KeyConflictContext.UNIVERSAL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            "key.categories.misc"
    ));


    // Event is on the mod event bus only on the physical client
    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        //Viltrumite mappings
        event.register(VILTRUMITE_GRAB_MAPPING.get());
        event.register(VILTRUMITE_CHOKE_MAPPING.get()); //in progress


        //Gravity mappings
        event.register(GRAVITY_PUSH_MAPPING.get());
        event.register(GRAVITY_PULL_MAPPING.get());
        event.register(GRAVITY_VORTEX_MAPPING.get());
        event.register(GRAVITY_METEOR_MAPPING.get()); //post v1
        event.register(GRAVITY_CHAOS_MAPPING.get());


        //Adaptation mapping
        event.register(ADAPTATION_SWITCH_MAPPING.get()); //changed to a reflection without adaptation


        //Parry mapping
        event.register(PARRY_MAPPING.get());


        //Lifestealer mapping
        event.register(LIFESTEALER_MAPPING.get());


    }
}


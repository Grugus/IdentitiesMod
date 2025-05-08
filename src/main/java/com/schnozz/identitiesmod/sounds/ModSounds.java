package com.schnozz.identitiesmod.sounds;

import com.schnozz.identitiesmod.IdentitiesMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    // Assuming that your mod id is examplemod
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, IdentitiesMod.MODID);



    public static final Supplier<SoundEvent> PARRY_SOUND = registerSoundEvent("parry_sound");


    private static Supplier<SoundEvent> registerSoundEvent(String name)
    {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }




    public static void register(IEventBus eventBus)
    {
        SOUND_EVENTS.register(eventBus);
    }
}

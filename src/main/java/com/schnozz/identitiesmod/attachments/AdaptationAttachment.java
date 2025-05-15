package com.schnozz.identitiesmod.attachments;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.schnozz.identitiesmod.cooldown.Cooldown;
import com.schnozz.identitiesmod.cooldown.CooldownAttachment;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class AdaptationAttachment {
    private static Map<ResourceLocation, Float> ADAPTATION_VALUES = new HashMap<>();

    public static float getAdaptationValue(ResourceLocation id)
    {
        return ADAPTATION_VALUES.get(id);
    }
    public static void setAdaptationValue(ResourceLocation id, float adaptationValue)
    {
        ADAPTATION_VALUES.put(id,adaptationValue);
    }

}

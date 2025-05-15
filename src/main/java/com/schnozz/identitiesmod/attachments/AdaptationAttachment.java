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
    private Map<ResourceLocation, Float> ADAPTATION_VALUES = new HashMap<>();

    public float getAdaptationValue(ResourceLocation id)
    {
        return ADAPTATION_VALUES.get(id);
    }
    public void setAdaptationValue(ResourceLocation id, float adaptationValue)
    {
        ADAPTATION_VALUES.put(id,adaptationValue);
    }

    public Map<ResourceLocation, Float> getAllAdaptationValues() {
        return ADAPTATION_VALUES;
    }

    public static final Codec<AdaptationAttachment> CODEC = Codec.unboundedMap(
            ResourceLocation.CODEC,
            Codec.FLOAT
    ).xmap(map -> {
        AdaptationAttachment att = new AdaptationAttachment();
        att.ADAPTATION_VALUES.putAll(map);
        return att;
    }, AdaptationAttachment::getAllAdaptationValues);


}

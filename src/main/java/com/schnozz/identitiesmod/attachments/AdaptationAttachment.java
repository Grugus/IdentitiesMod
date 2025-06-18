package com.schnozz.identitiesmod.attachments;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.schnozz.identitiesmod.cooldown.Cooldown;
import com.schnozz.identitiesmod.cooldown.CooldownAttachment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdaptationAttachment {
    private Map<ResourceLocation, Float> ADAPTATION_VALUES = new HashMap<>();
    //public ArrayList<String> damageSourceArrayList = new ArrayList<String>();
    public float adaptationDegree = 0.10F;
    public String[] heatSourceMessageIds = {"onfire","infire","hotfloor"};
    public String[] dotSourceMessageIds = {"magic", "wither", "indirectmagic"};
    public String[] genericMessageIds = {"generic","player"};
    public String[] explosionSourceMessageIds = {"explosion","explosion.player"};
    public String[] arrowMessageId = {"arrow"};
    public String[] fullAdaptIds = {"drown","freeze","cactus"};
    public String[][] importantSourceMessageIdGroups = {heatSourceMessageIds,dotSourceMessageIds,genericMessageIds,explosionSourceMessageIds,arrowMessageId};

    public float getAdaptationValue(ResourceLocation id)
    {
        return ADAPTATION_VALUES.getOrDefault(id, 1.00f);
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

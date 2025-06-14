package com.schnozz.identitiesmod.attachments;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class LifestealerBuffsAttachment {
    private Map<ResourceLocation, Float> LIFESTEALER_BUFFS = new HashMap<>();

    public float getLifestealerBuff(ResourceLocation id)
    {
        return LIFESTEALER_BUFFS.getOrDefault(id, -1F);
    }

    public void setLifestealerBuff(ResourceLocation id, float amplifier)
    {
        LIFESTEALER_BUFFS.put(id,amplifier);
    }

    public Map<ResourceLocation, Float> getAllLifestealerBuffs() {
        return LIFESTEALER_BUFFS;
    }

    public static final Codec<LifestealerBuffsAttachment> CODEC = Codec.unboundedMap(
            ResourceLocation.CODEC,
            Codec.FLOAT
    ).xmap(map -> {
        LifestealerBuffsAttachment att = new LifestealerBuffsAttachment();
        att.LIFESTEALER_BUFFS.putAll(map);
        return att;
    }, LifestealerBuffsAttachment::getAllLifestealerBuffs);
}

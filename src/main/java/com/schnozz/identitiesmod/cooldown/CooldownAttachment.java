package com.schnozz.identitiesmod.cooldown;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class CooldownAttachment {
    private final Map<ResourceLocation, Cooldown> cooldowns = new HashMap<>();

    public void setCooldown(ResourceLocation id, long startTime, long duration) {
        cooldowns.put(id, new Cooldown(startTime, duration));
    }

    public boolean isOnCooldown(ResourceLocation id, long currentTime) {
        Cooldown cd = cooldowns.get(id);
        return cd != null && (currentTime - cd.startTime()) < cd.duration();
    }



    public Map<ResourceLocation, Cooldown> getAllCooldowns() {
        return cooldowns;
    }

    public static final Codec<CooldownAttachment> CODEC = Codec.unboundedMap(
            ResourceLocation.CODEC,
            Cooldown.CODEC
    ).xmap(map -> {
        CooldownAttachment att = new CooldownAttachment();
        att.cooldowns.putAll(map);
        return att;
    }, CooldownAttachment::getAllCooldowns);
}

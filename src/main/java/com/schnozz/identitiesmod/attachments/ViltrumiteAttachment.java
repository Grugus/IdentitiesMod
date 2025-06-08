package com.schnozz.identitiesmod.attachments;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ViltrumiteAttachment {
    private Map<ResourceLocation, Boolean> viltrumiteStates = new HashMap<>();

    public boolean getViltrumiteState(ResourceLocation id) {
        return viltrumiteStates.getOrDefault(id, false);
    }

    public void setViltrumiteState(ResourceLocation id, boolean state) {
        viltrumiteStates.put(id, state);
    }

    public Map<ResourceLocation, Boolean> getAllViltrumiteStates() {
        return viltrumiteStates;
    }

    public static final Codec<ViltrumiteAttachment> CODEC = Codec.unboundedMap(
            ResourceLocation.CODEC,
            Codec.BOOL
    ).xmap(map -> {
        ViltrumiteAttachment att = new ViltrumiteAttachment();
        att.viltrumiteStates.putAll(map);
        return att;
    }, ViltrumiteAttachment::getAllViltrumiteStates);
}




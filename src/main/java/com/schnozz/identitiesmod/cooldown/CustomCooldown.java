package com.schnozz.identitiesmod.cooldown;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class CustomCooldown {

    public static final Codec<CustomCooldown> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("type").forGetter(d -> d.type),
            Codec.INT.fieldOf("length").forGetter(d -> d.length)
    ).apply(instance, CustomCooldown::new));


    private String type;
    private int length;
    public CustomCooldown(String type, int length)
    {
        this.type = type;
        this.length = length;
    }

    public void tickDown(int i)
    {
        length-=i;
    }

    public String getType() {return type;}

    public int getLength() {return length;}
}

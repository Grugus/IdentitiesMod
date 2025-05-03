package com.schnozz.identitiesmod.Buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class StrengthUp extends Button
{
    public StrengthUp(int x, int y, int width, int height, Component message, OnPress onPress, CreateNarration createNarration) {
        super(x, y, width, height, message, onPress, createNarration);
    }
    @Override
    public void onPress()
    {
        Player p = Minecraft.getInstance().player;
        assert p != null;
        if(p.getMaxHealth() >= 22)
        {

        }
        //if player has x hearts over 10 and current strength buff is less than 3,
            //decrease max hearts by x
            //Player gets +1 permanent strength increase
    }

}

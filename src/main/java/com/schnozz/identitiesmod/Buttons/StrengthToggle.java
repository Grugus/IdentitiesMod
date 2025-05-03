package com.schnozz.identitiesmod.Buttons;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class StrengthToggle extends Button
{
    public StrengthToggle(int x, int y, int width, int height, Component message, OnPress onPress, CreateNarration createNarration) {
        super(x, y, width, height, message, onPress, createNarration);
    }
    @Override
    public void onPress()
    {
        //if strength buff is active, turn off
        //else turn on
    }
}

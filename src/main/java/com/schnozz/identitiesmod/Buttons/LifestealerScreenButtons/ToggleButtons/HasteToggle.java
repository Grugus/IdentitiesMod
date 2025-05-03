package com.schnozz.identitiesmod.Buttons.LifestealerScreenButtons.ToggleButtons;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class HasteToggle extends Button {
    public HasteToggle(int x, int y, int width, int height, Component message, OnPress onPress, CreateNarration createNarration) {
        super(x, y, width, height, message, onPress, createNarration);
    }
    @Override
    public void onPress()
    {
        //Player gets permanent strength increase
    }
}

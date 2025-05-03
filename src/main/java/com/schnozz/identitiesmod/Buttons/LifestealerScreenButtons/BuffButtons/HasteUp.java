package com.schnozz.identitiesmod.Buttons.LifestealerScreenButtons.BuffButtons;

import com.schnozz.identitiesmod.networking.payloads.HealthCostPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

public class HasteUp extends Button{
    public HasteUp(int x, int y, int width, int height, Component message, OnPress onPress, CreateNarration createNarration) {
        super(x, y, width, height, message, onPress, createNarration);
    }
    @Override
    public void onPress()
    {
        Player p = Minecraft.getInstance().player;
        assert p != null;
        int permLevel = 0; //set level based on package
        int cost = 4; //perm str level = 0
        if(permLevel == 1) {cost = 6;} //perm str level = 1
        if(p.getMaxHealth() >= 20 + cost && permLevel < 2) //str level is less than 3
        {
            PacketDistributor.sendToServer(new HealthCostPayload(cost));
            permLevel++;
            //send mobeffect package back with buff type and level
        }
    }
}

package com.schnozz.identitiesmod.Buttons.LifestealerScreenButtons.BuffButtons;

import com.schnozz.identitiesmod.networking.payloads.HealthCostPayload;
import com.schnozz.identitiesmod.networking.payloads.PotionLevelPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
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
        boolean hasEffect = p.getActiveEffects().contains(MobEffects.JUMP);
        int cost = 4;
        if(hasEffect) {
            int permLevel = p.getEffect(MobEffects.JUMP).getAmplifier();
            cost = 6;
            if (p.getMaxHealth() >= 20 + cost && permLevel < 1 )
            {
                PacketDistributor.sendToServer(new HealthCostPayload(cost));
                permLevel++;
                PacketDistributor.sendToServer(new PotionLevelPayload(MobEffects.JUMP,permLevel, Integer.MAX_VALUE));
            }
        }
        else {
            if (p.getMaxHealth() >= 20 + cost)
            {
                PacketDistributor.sendToServer(new HealthCostPayload(cost));
                PacketDistributor.sendToServer(new PotionLevelPayload(MobEffects.JUMP,0, Integer.MAX_VALUE));
            }
        }
    }
}

package com.schnozz.identitiesmod.Buttons.LifestealerScreenButtons.BuffButtons;

import com.schnozz.identitiesmod.networking.payloads.HealthCostPayload;
import com.schnozz.identitiesmod.networking.payloads.PotionLevelPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

public class SpeedUp extends Button
{
    public SpeedUp(int x, int y, int width, int height, Component message, OnPress onPress, CreateNarration createNarration) {
        super(x, y, width, height, message, onPress, createNarration);
    }
    @Override
    public void onPress()
    {
        Player p = Minecraft.getInstance().player;
        assert p != null;
        boolean hasEffect = p.getActiveEffects().contains(MobEffects.MOVEMENT_SPEED);
        int cost = 2;
        if(hasEffect) {
            int permLevel = p.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier();
            if (permLevel == 0) {
                cost = 4;
            }
            else {
                cost = 8;
            }
            if (p.getMaxHealth() >= 20 + cost && permLevel < 2 )
            {
                PacketDistributor.sendToServer(new HealthCostPayload(cost));
                permLevel++;
                PacketDistributor.sendToServer(new PotionLevelPayload(MobEffects.MOVEMENT_SPEED,permLevel));
            }
        }
        else {
            if (p.getMaxHealth() >= 20 + cost)
            {
                PacketDistributor.sendToServer(new HealthCostPayload(cost));
                PacketDistributor.sendToServer(new PotionLevelPayload(MobEffects.MOVEMENT_SPEED,0));
            }
        }
    }
}

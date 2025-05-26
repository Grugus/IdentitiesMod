package com.schnozz.identitiesmod.Buttons.LifestealerScreenButtons.BuffButtons;

import com.schnozz.identitiesmod.networking.payloads.HealthCostPayload;
import com.schnozz.identitiesmod.networking.payloads.PotionLevelPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

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
        boolean hasEffect = p.getActiveEffects().contains(MobEffects.DAMAGE_BOOST);
        int cost = 4;
        if(hasEffect) {
            int permLevel = p.getEffect(MobEffects.DAMAGE_BOOST).getAmplifier(); //set level based on package
            if (permLevel == 0) {
                cost = 6;
            } //perm str level = 1
            else {
                cost = 10;
            } //perm str level = 2
            if (p.getMaxHealth() >= 20 + cost && permLevel < 2 ) //str level is less than 3
            {
                PacketDistributor.sendToServer(new HealthCostPayload(cost));
                permLevel++;
                PacketDistributor.sendToServer(new PotionLevelPayload(MobEffects.DAMAGE_BOOST,permLevel, Integer.MAX_VALUE));
            }
        }
        else {
            if (p.getMaxHealth() >= 20 + cost) //str level is less than 3
            {
                PacketDistributor.sendToServer(new HealthCostPayload(cost));
                PacketDistributor.sendToServer(new PotionLevelPayload(MobEffects.DAMAGE_BOOST,0, Integer.MAX_VALUE));
            }
        }
    }

}

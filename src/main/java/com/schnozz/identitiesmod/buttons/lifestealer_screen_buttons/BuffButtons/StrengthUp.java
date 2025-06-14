package com.schnozz.identitiesmod.buttons.lifestealer_screen_buttons.BuffButtons;

import com.schnozz.identitiesmod.networking.payloads.HealthCostPayload;
import com.schnozz.identitiesmod.networking.payloads.PotionLevelPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

public class StrengthUp extends Button
{
    private int permLevel = -1;
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
            permLevel = p.getEffect(MobEffects.DAMAGE_BOOST).getAmplifier();
            p.removeEffect(MobEffects.DAMAGE_BOOST);
            if (permLevel == 0) {
                cost = 6;
            } //perm str level = 1
            else if(permLevel == 1){
                cost = 10;
            } //perm str level = 2
        }

        if (p.getMaxHealth() >= 20 + cost && permLevel < 2) //str level is less than 3
        {
            PacketDistributor.sendToServer(new HealthCostPayload(cost));
            permLevel++;

            PacketDistributor.sendToServer(new PotionLevelPayload(MobEffects.DAMAGE_BOOST, permLevel,MobEffectInstance.INFINITE_DURATION));
            p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,MobEffectInstance.INFINITE_DURATION,permLevel,false,true,true));
        }

    }

}

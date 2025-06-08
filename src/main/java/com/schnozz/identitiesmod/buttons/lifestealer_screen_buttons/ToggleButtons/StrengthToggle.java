package com.schnozz.identitiesmod.buttons.lifestealer_screen_buttons.ToggleButtons;

import com.schnozz.identitiesmod.networking.payloads.PotionTogglePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

public class StrengthToggle extends Button
{
    public StrengthToggle(int x, int y, int width, int height, Component message, OnPress onPress, CreateNarration createNarration) {
        super(x, y, width, height, message, onPress, createNarration);
    }
    @Override
    public void onPress()
    {
        Player p = Minecraft.getInstance().player;
        assert p != null;
        PacketDistributor.sendToServer(new PotionTogglePayload(MobEffects.DAMAGE_BOOST, p.getEffect(MobEffects.DAMAGE_BOOST).getAmplifier()));
    }
}

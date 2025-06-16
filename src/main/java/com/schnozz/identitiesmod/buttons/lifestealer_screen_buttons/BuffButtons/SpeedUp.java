package com.schnozz.identitiesmod.buttons.lifestealer_screen_buttons.BuffButtons;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.networking.payloads.HealthCostPayload;
import com.schnozz.identitiesmod.networking.payloads.PotionLevelPayload;
import com.schnozz.identitiesmod.networking.payloads.sync_payloads.LifestealerBuffSyncPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
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
        if(p == null){return;}

        float permLevel = p.getData(ModDataAttachments.LIFESTEALER_BUFFS).getLifestealerBuff(ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID,"speed"));
        int cost = 2;

        if (permLevel == 0) {
            cost = 4;
        } //perm str level = 1
        else if(permLevel == 1){
            cost = 6;
        } //perm str level = 2

        if (p.getMaxHealth() >= 20 + cost && permLevel < 2) //level is less than 3
        {
            PacketDistributor.sendToServer(new HealthCostPayload(cost));

            permLevel++;
            p.getData(ModDataAttachments.LIFESTEALER_BUFFS).setLifestealerBuff(ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID,"speed"),permLevel);
            PacketDistributor.sendToServer(new LifestealerBuffSyncPayload(p.getData(ModDataAttachments.LIFESTEALER_BUFFS)));

            if(p.getActiveEffects().contains(MobEffects.MOVEMENT_SPEED))
            {
                p.removeEffect(MobEffects.MOVEMENT_SPEED);
            }
            PacketDistributor.sendToServer(new PotionLevelPayload(MobEffects.MOVEMENT_SPEED, (int)permLevel, MobEffectInstance.INFINITE_DURATION));
        }
    }
}

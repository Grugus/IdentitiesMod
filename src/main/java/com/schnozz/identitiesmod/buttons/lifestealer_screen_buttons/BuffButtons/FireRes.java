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

public class FireRes extends Button{
    public FireRes(int x, int y, int width, int height, Component message, OnPress onPress, CreateNarration createNarration) {
        super(x, y, width, height, message, onPress, createNarration);
    }
    @Override
    public void onPress()
    {
        Player p = Minecraft.getInstance().player;
        if(p == null){return;}

        float permLevel = p.getData(ModDataAttachments.LIFESTEALER_BUFFS).getLifestealerBuff(ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID,"fire_res"));
        int cost = 6;

        if (p.getMaxHealth() >= 20 + cost && permLevel < 0)
        {
            PacketDistributor.sendToServer(new HealthCostPayload(cost));
            p.getData(ModDataAttachments.LIFESTEALER_BUFFS).setLifestealerBuff(ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID,"fire_res"),permLevel+1);
            PacketDistributor.sendToServer(new LifestealerBuffSyncPayload(p.getData(ModDataAttachments.LIFESTEALER_BUFFS)));

            PacketDistributor.sendToServer(new PotionLevelPayload(MobEffects.FIRE_RESISTANCE, (int)permLevel, MobEffectInstance.INFINITE_DURATION));
        }
    }
}

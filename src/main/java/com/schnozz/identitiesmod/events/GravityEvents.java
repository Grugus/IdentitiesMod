package com.schnozz.identitiesmod.events;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import static com.schnozz.identitiesmod.keymapping.ModMappings.*;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class GravityEvents {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        LocalPlayer p = Minecraft.getInstance().player;
        if(p == null || !p.level().isClientSide()) return;
        String power = p.getData(ModDataAttachments.POWER_TYPE);
        if (power.equals("Gravity")) {
            if(GRAVITY_PUSH_MAPPING.get().consumeClick())
            {
                 AABB gravityPushBB = new AABB(p.getX(),p.getY(),p.getZ(),p.getX(),p.getY(),p.getZ());
                 gravityPushBB.intersect(p.getBoundingBox());
                 //for()
                 //{

                 //}
            }
            if(GRAVITY_PULL_MAPPING.get().consumeClick())
            {
                AABB gravityPullBB = new AABB(p.getX(),p.getY(),p.getZ(),p.getX(),p.getY(),p.getZ());
            }
        }


    }
}

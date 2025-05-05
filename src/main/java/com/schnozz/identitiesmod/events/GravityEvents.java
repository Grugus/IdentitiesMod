package com.schnozz.identitiesmod.events;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
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
                //check in front of them on the client
                //if theres an entity in the AABB then send a packet with its UUID
                //in the packet handler set the player's data attachment to the UUID
                //create a data attachment for the player called currentGrab
                //check on server side if this has a value then push and clear the entity UUID from the attachment data
            }
            if(GRAVITY_PULL_MAPPING.get().consumeClick())
            {
                //same protocol as if statement above
            }
        }


    }
}

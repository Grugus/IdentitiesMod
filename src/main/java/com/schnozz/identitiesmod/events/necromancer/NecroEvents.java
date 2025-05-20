package com.schnozz.identitiesmod.events.necromancer;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.register_attachments.ModDataAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import static com.schnozz.identitiesmod.keymapping.ModMappings.NECROMANCER_MAPPING;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class NecroEvents {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event)
    {
        LocalPlayer p = Minecraft.getInstance().player;
        if(p == null || !p.level().isClientSide() || !p.hasData(ModDataAttachments.POWER_TYPE)) return;
        String power = p.getData(ModDataAttachments.POWER_TYPE);

        if (power.equals("Necromancer") && NECROMANCER_MAPPING.get().consumeClick()) {
            //set every target under your control to null
        }
    }
}

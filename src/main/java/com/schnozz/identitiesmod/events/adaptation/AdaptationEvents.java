package com.schnozz.identitiesmod.events.adaptation;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.register_attachments.ModDataAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import static com.schnozz.identitiesmod.keymapping.ModMappings.ADAPTATION_SWITCH_MAPPING;
import static com.schnozz.identitiesmod.keymapping.ModMappings.GRAVITY_PUSH_MAPPING;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class AdaptationEvents {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        LocalPlayer adaptationPlayer = Minecraft.getInstance().player;
        if (adaptationPlayer == null) return;
        Level level = adaptationPlayer.level();
        if(!level.isClientSide()) return;

        if(adaptationPlayer.getData(ModDataAttachments.POWER_TYPE).equals("Adaptation"))
        {
            if(ADAPTATION_SWITCH_MAPPING.get().consumeClick())
            {
                switchAdaptation();
            }
        }
    }

    public static void switchAdaptation()
    {

    }
}

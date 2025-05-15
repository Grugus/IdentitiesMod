package com.schnozz.identitiesmod.events;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.register_attachments.ModDataAttachments;
import net.minecraft.advancements.critereon.EntityHurtPlayerTrigger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

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
            if(GRAVITY_PUSH_MAPPING.get().consumeClick())
            {
                switchAdaptation();
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerDamage(LivingIncomingDamageEvent event) {

    }
    public static void switchAdaptation()
    {

    }
}

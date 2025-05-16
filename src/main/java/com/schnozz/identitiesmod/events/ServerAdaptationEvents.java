package com.schnozz.identitiesmod.events;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.register_attachments.ModDataAttachments;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ServerAdaptationEvents {
    //private static list (string or damagesource) that holds every damage source
    //second list that holds important damagesources
    @SubscribeEvent
    public static void onPlayerDamage(LivingIncomingDamageEvent event) {
        Entity entity = event.getEntity();
        DamageSource source = event.getSource();
        if(entity.getData(ModDataAttachments.POWER_TYPE).equals("Adaptation")) {
            Player adapter = (Player) entity;
            adapt(adapter, source);
        }
    }
    public static void adapt(Player adapter, DamageSource source)
    {
        //random number generator to find a location in the important damage source list and decreases it by 1/2 adaptation value
    }
}

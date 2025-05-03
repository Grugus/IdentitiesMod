package com.schnozz.identitiesmod.events;

import com.schnozz.identitiesmod.IdentitiesMod;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.DEDICATED_SERVER)
public class ServerHealthEvents {

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.Clone event)
    {



    }
    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event)
    {
        if(event.getEntity() instanceof Zombie && event.getSource().getDirectEntity() instanceof Player player)
        {

        }
    }
}

package com.schnozz.identitiesmod.events;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ServerHealthEvents {


    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event)
    {
        //change the Zombie to Player

        if(event.getEntity() instanceof Zombie && event.getSource().getDirectEntity() instanceof ServerPlayer player && player.getData(ModDataAttachments.POWER_TYPE).equals("Lifestealer"))
        {
            int h = Math.min(20, player.getData(ModDataAttachments.HEALTH_NEEDED) + 2);
            player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20 + h);
            player.setData(ModDataAttachments.HEALTH_NEEDED, h);


        }

        if(event.getEntity() instanceof Player player && player.getData(ModDataAttachments.POWER_TYPE).equals("Lifestealer") && event.getSource().getDirectEntity() instanceof Player p && !p.equals(player))
        {
            int h = Math.max(0, player.getData(ModDataAttachments.HEALTH_NEEDED) - 2);
            player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20 + h);
            player.setData(ModDataAttachments.HEALTH_NEEDED, h);

        }
    }
}

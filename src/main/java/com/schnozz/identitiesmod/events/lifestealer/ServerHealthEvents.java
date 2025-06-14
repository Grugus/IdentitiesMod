package com.schnozz.identitiesmod.events.lifestealer;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ServerHealthEvents {


    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event)
    {
        if(event.getEntity().level().isClientSide){return;}

        //for when lifestealer kills the player gain 1 heart
        if(event.getEntity() instanceof Zombie && event.getSource().getDirectEntity() instanceof ServerPlayer player && player.getData(ModDataAttachments.POWER_TYPE).equals("Lifestealer")){
                int h = Math.min(20, player.getData(ModDataAttachments.HEALTH_NEEDED) + 2);
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20 + h);
                player.setData(ModDataAttachments.HEALTH_NEEDED, h);
        }

        // for when lifestealer dies to a player lose 1 heart
        if(event.getEntity() instanceof ServerPlayer player && player.getData(ModDataAttachments.POWER_TYPE).equals("Lifestealer")) {

            if (event.getSource().getDirectEntity() instanceof Player p) {
                int h = Math.max(0, player.getData(ModDataAttachments.HEALTH_NEEDED) - 2);
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20 + h);
                player.setData(ModDataAttachments.HEALTH_NEEDED, h);

            }
        }
    }
    @SubscribeEvent
    public static void onLifestealerAttack(LivingIncomingDamageEvent event)
    {
        if(event.getSource().getDirectEntity() instanceof Player player && player.getData(ModDataAttachments.POWER_TYPE).equals("Lifestealer"))
        {
            System.out.println("DAMAGE: " + event.getAmount());
        }
    }
}

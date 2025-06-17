package com.schnozz.identitiesmod.events;


import com.schnozz.identitiesmod.IdentitiesMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.DEDICATED_SERVER)
public class ServerEvents {


    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {

        Scoreboard scoreboard = event.getServer().getScoreboard();
        String objectiveName = "playerKills";

        Objective objective = scoreboard.getObjective(objectiveName);

        if (objective == null) {
            objective = scoreboard.addObjective(
                    objectiveName,
                    ObjectiveCriteria.KILL_COUNT_PLAYERS, // Use built-in kill player criteria
                    Component.literal("Kills"),
                    ObjectiveCriteria.RenderType.INTEGER,
                    false,
                    null
            );
        }

        scoreboard.setDisplayObjective(DisplaySlot.LIST, objective);
        scoreboard.setDisplayObjective(DisplaySlot.BELOW_NAME, objective);
    }

}

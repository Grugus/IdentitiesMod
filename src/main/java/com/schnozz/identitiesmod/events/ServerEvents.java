package com.schnozz.identitiesmod.events;


import com.schnozz.identitiesmod.IdentitiesMod;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.LogicalSidedProvider;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.Random;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.DEDICATED_SERVER)
public class ServerEvents {

    private static long tickCounter = 0;

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


    // Coordinate System
    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event)
    {
        MinecraftServer server = event.getServer();

        tickCounter++;
        if (tickCounter >= 36000) {
            tickCounter = 0;
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                if(!player.getTags().contains("AFK")) {

                    BlockPos pos = player.blockPosition();
                    Random random = new Random();

                    String message = "Player: " + player.getName().getString() + " is in the " + player.level().dimension().location() + " around " + (pos.getX() + random.nextInt(30, 251) + " , " + (pos.getZ() + random.nextInt(30, 251)));
                    sendBroadcast(message, server);
                }
            }
        }

    }

    private static void sendBroadcast(String message, MinecraftServer server) {
        // Loop through all server players and send chat
        if (server != null) {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                if(!player.getTags().contains("AFK"))
                {
                    player.sendSystemMessage(Component.literal("[SERVER BROADCAST] " + message).withColor(0xFFFF00));
                }
            }
        }
    }


}

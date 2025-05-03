package com.schnozz.identitiesmod.networking;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.networking.payloads.HealthCostPayload;
import com.schnozz.identitiesmod.networking.payloads.PowerSyncPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class PowerHandler {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1").executesOn(HandlerThread.MAIN); // Replace "1" with your network protocol version

        registrar.playToClient(
                PowerSyncPayload.TYPE,
                PowerSyncPayload.STREAM_CODEC,
                (payload, context) -> {
                    // Schedule work on the main client thread
                    Minecraft.getInstance().execute(() -> {
                        LocalPlayer player = Minecraft.getInstance().player;
                        if (player != null) {
                            player.setData(ModDataAttachments.POWER_TYPE.get(), payload.power());
                        }
                    });
                }
        );

        registrar.playToServer(
                HealthCostPayload.TYPE,
                HealthCostPayload.STREAM_CODEC,
                (payload, context) -> {
                    Player p = context.player();
                    int currentHealth = p.getData(ModDataAttachments.HEALTH_NEEDED);
                    p.setData(ModDataAttachments.HEALTH_NEEDED, currentHealth - payload.cost());
                    p.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20 + p.getData(ModDataAttachments.HEALTH_NEEDED));
                }
        );
    }
}

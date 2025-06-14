package com.schnozz.identitiesmod.networking;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.events.parry.ClientParryEvents;
import com.schnozz.identitiesmod.events.viltrumite.ClientViltrumiteEvents;
import com.schnozz.identitiesmod.networking.handlers.*;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.networking.payloads.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class PayloadRegister {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1").executesOn(HandlerThread.NETWORK); // Replace "1" with your network protocol version

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

        registrar.playToClient(
                AdaptationSyncPayload.TYPE,
                AdaptationSyncPayload.STREAM_CODEC,
                (payload, context) -> {
                    // Schedule work on the main client thread
                    Minecraft.getInstance().execute(() -> {
                        LocalPlayer player = Minecraft.getInstance().player;
                        if (player != null) {
                            player.setData(ModDataAttachments.ADAPTION.get(), payload.attachment());
                        }
                    });
                }
        );


        registrar.playToClient(
                ViltrumiteAttachmentSyncPayload.TYPE,
                ViltrumiteAttachmentSyncPayload.STREAM_CODEC,
                (payload, context) -> {
                    // Schedule work on the main client thread
                    Minecraft.getInstance().execute(() -> {
                        LocalPlayer player = Minecraft.getInstance().player;
                        if (player != null) {
                            player.setData(ModDataAttachments.VILTRUMITE_STATES.get(), payload.attachment());
                        }
                    });
                }
        );

        registrar.playToClient(
                CombatLoggedAttachmentSyncPayload.TYPE,
                CombatLoggedAttachmentSyncPayload.STREAM_CODEC,
                (payload, context) -> {
                    // Schedule work on the main client thread
                    Minecraft.getInstance().execute(() -> {
                        LocalPlayer player = Minecraft.getInstance().player;
                        if (player != null) {
                            player.setData(ModDataAttachments.COMBAT_LOGGED.get(), payload.attachment());
                        }
                    });
                }
        );

        registrar.playToClient(
                CDPayload.TYPE,
                CDPayload.STREAM_CODEC,
                (payload, context) -> {
                    // Schedule work on the main client thread
                    Minecraft.getInstance().execute(() -> {
                        LocalPlayer player = Minecraft.getInstance().player;
                        if (player != null) {
                            ClientViltrumiteEvents.setIconCooldown(payload.cooldown());
                        }
                    });
                }
        );

        registrar.playToClient(
                CDPayload.TYPE,
                CDPayload.STREAM_CODEC,
                (payload, context) -> {
                    // Schedule work on the main client thread
                    Minecraft.getInstance().execute(() -> {
                        LocalPlayer player = Minecraft.getInstance().player;
                        if (player != null) {
                            ClientParryEvents.setIconCooldown(payload.cooldown());
                        }
                    });
                }
        );

        registrar.playToClient(
                SoundPayload.TYPE,
                SoundPayload.STREAM_CODEC,
                (payload, context) -> {
                    // Schedule work on the main client thread
                    Minecraft.getInstance().execute(() -> {
                        LocalPlayer player = Minecraft.getInstance().player;
                        if (player != null) {
                            player.playSound(payload.sound());
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
                    p.getAttribute(Attributes.MAX_HEALTH).setBaseValue(p.getMaxHealth() + p.getData(ModDataAttachments.HEALTH_NEEDED));
                }
        );

        registrar.playToServer(
                VelocityPayload.TYPE,
                VelocityPayload.STREAM_CODEC,
                ServerVelocityHandler::handle
        );


        registrar.playToServer(
                EntityBoxPayload.TYPE,
                EntityBoxPayload.STREAM_CODEC,
                (payload, context) -> {
                        Player player = context.player();
                        player.setData(ModDataAttachments.ENTITY_HELD.get(), payload.entity());
                }
        );

        registrar.playToServer(
                GravityPayload.TYPE,
                GravityPayload.STREAM_CODEC,
                    ServerGravityHandler::handle
        );

        registrar.playToServer(
                EntityDamagePayload.TYPE,
                EntityDamagePayload.STREAM_CODEC,
                ServerEntityDamageHandler::handle
        );

        registrar.playBidirectional(
                PotionLevelPayload.TYPE,
                PotionLevelPayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ClientPotionLevelHandler::handle,
                        ServerPotionLevelHandler::handle
                )
        );

        registrar.playBidirectional(
                CooldownSyncPayload.TYPE,
                CooldownSyncPayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ClientCooldownHandler::handle,
                        ServerCooldownHandler::handle
                )
        );

        registrar.playToServer(
                PotionTogglePayload.TYPE,
                PotionTogglePayload.STREAM_CODEC,
                ServerPotionToggleHandler::handle
        );

        registrar.playToServer(
                StunPayload.TYPE,
                StunPayload.STREAM_CODEC,
                ServerStunHandler::handle
        );

        registrar.playToServer(
                EntityPositionPayload.TYPE,
                EntityPositionPayload.STREAM_CODEC,
                ServerEntityPositionHandler::handle
        );
    }
}

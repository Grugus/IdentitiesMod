package com.schnozz.identitiesmod.networking;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.events.parry.ClientParryEvents;
import com.schnozz.identitiesmod.events.viltrumite.ClientViltrumiteEvents;
import com.schnozz.identitiesmod.networking.handlers.*;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.networking.payloads.*;
import com.schnozz.identitiesmod.networking.payloads.sync_payloads.*;
import com.schnozz.identitiesmod.networking.payloads.CDPARRYPayload;
import com.schnozz.identitiesmod.networking.payloads.CDPayload;
import com.schnozz.identitiesmod.sounds.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
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

        registrar.playBidirectional(
                AdaptationSyncPayload.TYPE,
                AdaptationSyncPayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ClientAdaptationSyncHandler::handle,
                        ServerAdaptationSyncHandler::handle
                )
        );

        registrar.playBidirectional(
                LifestealerBuffSyncPayload.TYPE,
                LifestealerBuffSyncPayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ClientLifestealerBuffSyncPayload::handle,
                        ServerLifestealerBuffSyncHandler::handle
                )
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
                CDPARRYPayload.TYPE,
                CDPARRYPayload.STREAM_CODEC,
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

        registrar.playToServer(
                SoundPayload.TYPE,
                SoundPayload.STREAM_CODEC,
                (payload, context) -> {
                    // Schedule work on the main client thread
                    Player player = context.player();
                    if (player != null) {
                        player.level().playSound(null, player.getOnPos(), payload.sound(), SoundSource.PLAYERS, payload.volume(),1F);
                    }
                }
        );

        registrar.playToServer(
                HealthCostPayload.TYPE,
                HealthCostPayload.STREAM_CODEC,
                (payload, context) -> {
                    Player p = context.player();
                    int currentHealthNeeded = p.getData(ModDataAttachments.HEALTH_NEEDED);
                    p.setData(ModDataAttachments.HEALTH_NEEDED, currentHealthNeeded - payload.cost());
                    p.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20 + p.getData(ModDataAttachments.HEALTH_NEEDED));
                }
        );

        registrar.playToServer(
                VelocityPayload.TYPE,
                VelocityPayload.STREAM_CODEC,
                ServerVelocityHandler::handle
        );

        registrar.playToServer(
                ParryParticlePayload.TYPE,
                ParryParticlePayload.STREAM_CODEC,
                ServerParryParticleHandler::handle
        );

        registrar.playToServer(
                ChaosParticlePayload.TYPE,
                ChaosParticlePayload.STREAM_CODEC,
                ServerChaosParticleHandler::handle
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

        registrar.playToClient(
                GrabSyncPayload.TYPE,
                GrabSyncPayload.STREAM_CODEC,
                ClientGrabSyncPayloadHandler::handle
        );
    }
}

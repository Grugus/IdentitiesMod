package com.schnozz.identitiesmod.events;


import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.attachments.AdaptationAttachment;
import com.schnozz.identitiesmod.networking.payloads.AdaptationSyncPayload;
import com.schnozz.identitiesmod.register_attachments.ModDataAttachments;
import com.schnozz.identitiesmod.networking.payloads.PowerSyncPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class AttachmentSyncEvents {

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if(event.getEntity() instanceof ServerPlayer player)
        {
            String power = player.getData(ModDataAttachments.POWER_TYPE);
            PacketDistributor.sendToPlayer(player, new PowerSyncPayload(power));
            AdaptationAttachment adaptation = player.getData(ModDataAttachments.ADAPTION);
            PacketDistributor.sendToPlayer(player, new AdaptationSyncPayload(adaptation));

        }
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {

            String power = player.getData(ModDataAttachments.POWER_TYPE);
            PacketDistributor.sendToPlayer(player, new PowerSyncPayload(power));
            AdaptationAttachment adaptation = player.getData(ModDataAttachments.ADAPTION);
            PacketDistributor.sendToPlayer(player, new AdaptationSyncPayload(adaptation));

        }
    }
}

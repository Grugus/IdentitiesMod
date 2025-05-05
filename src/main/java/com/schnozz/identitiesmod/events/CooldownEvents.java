package com.schnozz.identitiesmod.events;


import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.cooldown.CustomCooldown;
import com.schnozz.identitiesmod.networking.payloads.CustomCooldownPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class CooldownEvents {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event)
    {
        if(!event.getEntity().level().isClientSide)
        {
            for(CustomCooldown cd : event.getEntity().getData(ModDataAttachments.COOLDOWN_LIST))
            {
                if(cd.getLength() <= 0)
                {
                    PacketDistributor.sendToPlayer((ServerPlayer) event.getEntity(), new CustomCooldownPayload(cd, false));
                    System.out.println("Cooldown sent for removal");
                }
                else
                {
                    cd.tickDown(1);
                }
            }
            event.getEntity().getData(ModDataAttachments.COOLDOWN_LIST).removeIf(cooldown -> cooldown.getLength() <= 0);
        }
    }
}

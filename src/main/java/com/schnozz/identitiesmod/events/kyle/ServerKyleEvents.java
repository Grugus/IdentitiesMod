package com.schnozz.identitiesmod.events.kyle;


import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.leveldata.FarmValueSavedData;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ServerKyleEvents {
    @SubscribeEvent
    public static void onKyleDeath(PlayerEvent.Clone event)
    {
        if(event.isWasDeath() && !event.getEntity().level().isClientSide && event.getEntity().getData(ModDataAttachments.POWER_TYPE).equals("Kyle"))
        {
            if(event.getEntity().level() instanceof ServerLevel level)
            {
                FarmValueSavedData data = FarmValueSavedData.get(level.getServer());
                long toSubtract = ((long )(0.1 * data.getValue()));
                data.addToValue(-1 * toSubtract);
            }
        }
    }
}

package com.schnozz.identitiesmod.events.effect;

import com.schnozz.identitiesmod.mob_effects.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public class ClientStunEffectEvents {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event)
    {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.level == null || mc.isPaused()) return;

        ClientLevel level = mc.level;

        for (Entity entity : level.entitiesForRendering()) {
            if (entity instanceof Mob mob) {
                if (mob.hasEffect(ModEffects.STUN)) {
                    mob.setNoAi(true);  // Disable AI
                } else if (mob.isNoAi()) {
                    mob.setNoAi(false); // Restore AI
                }
            }
        }

//        LocalPlayer player = Minecraft.getInstance().player;
//        if(player.getActiveEffects().contains(ModEffects.STUN))
//        {
//            player.setDeltaMovement(Vec3.ZERO);
//            player.setPos(player.xOld,player.yOld,player.zOld);
//        }
    }

}

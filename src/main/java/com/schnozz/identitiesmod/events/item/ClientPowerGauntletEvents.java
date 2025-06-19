package com.schnozz.identitiesmod.events.item;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.items.item_classes.FastPowerGauntlet;
import com.schnozz.identitiesmod.items.item_classes.StrongPowerGauntlet;
import com.schnozz.identitiesmod.networking.payloads.SoundPayload;
import com.schnozz.identitiesmod.sounds.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientPowerGauntletEvents {
    //cancels default sound on power gauntlets
    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        Player player = Minecraft.getInstance().player;
        if(player == null){return;}
        System.out.println("EVENT NAME: " + event.getName());
        System.out.println("CHECK NAME: " + SoundEvents.PLAYER_ATTACK_SWEEP.getLocation());

        if (    ("minecraft:"+ event.getName()).equals(SoundEvents.PLAYER_ATTACK_STRONG.getLocation().toString())
                || ("minecraft:"+ event.getName()).equals(SoundEvents.PLAYER_ATTACK_NODAMAGE.getLocation().toString())
                || ("minecraft:"+ event.getName()).equals(SoundEvents.PLAYER_ATTACK_SWEEP.getLocation().toString())    )
        {
            if (player.getMainHandItem().getItem() instanceof StrongPowerGauntlet) {
                event.setSound(null);
                PacketDistributor.sendToServer(new SoundPayload(ModSounds.PUNCH_THUNK_SOUND.get(),3F));
            }
            if (player.getMainHandItem().getItem() instanceof FastPowerGauntlet) {
                event.setSound(null);
                PacketDistributor.sendToServer(new SoundPayload(ModSounds.LIGHT_PUNCH_SOUND.get(),3F));
            }
        }
        else if(("minecraft:"+ event.getName()).equals(SoundEvents.PLAYER_ATTACK_CRIT.getLocation().toString())) {
            if (player.getMainHandItem().getItem() instanceof StrongPowerGauntlet) {
                event.setSound(null);
                PacketDistributor.sendToServer(new SoundPayload(ModSounds.OMNI_MAN_PUNCH_SOUND.get(),3F));
            }
            if (player.getMainHandItem().getItem() instanceof FastPowerGauntlet) {
                event.setSound(null);
                PacketDistributor.sendToServer(new SoundPayload(ModSounds.LIGHT_PUNCH_SOUND.get(),3F));
            }
        }
    }
}

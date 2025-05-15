package com.schnozz.identitiesmod.powers.parry;


import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.register_attachments.ModDataAttachments;
import com.schnozz.identitiesmod.cooldown.Cooldown;
import com.schnozz.identitiesmod.cooldown.CooldownAttachment;
import com.schnozz.identitiesmod.networking.payloads.CooldownSyncPayload;
import com.schnozz.identitiesmod.networking.payloads.SoundPayload;
import com.schnozz.identitiesmod.sounds.ModSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ServerParryEvents {

    @SubscribeEvent
    public static void onPlayerHurt(LivingIncomingDamageEvent event)
    {
        long currentTime = event.getEntity().level().getGameTime();
        if(event.getEntity() instanceof ServerPlayer player && player.hasData(ModDataAttachments.POWER_TYPE) && player.getData(ModDataAttachments.POWER_TYPE).equals("Parry") && player.getData(ModDataAttachments.COOLDOWN).isOnCooldown(ResourceLocation.fromNamespaceAndPath("identitiesmod", "parry_duration"), currentTime))
        {
            if((event.getSource().getDirectEntity() instanceof LivingEntity source))
            {
                source.hurt(event.getSource(), event.getAmount()*.5f);
                player.level().playSound(null, player.getOnPos(), ModSounds.PARRY_SOUND.get(), SoundSource.PLAYERS);
                CooldownAttachment newAtachment = new CooldownAttachment();
                newAtachment.getAllCooldowns().putAll(player.getData(ModDataAttachments.COOLDOWN).getAllCooldowns());
                newAtachment.setCooldown(ResourceLocation.fromNamespaceAndPath("identitiesmod", "parry_cd"), currentTime, 20);
                player.setData(ModDataAttachments.COOLDOWN, newAtachment);
                PacketDistributor.sendToPlayer(player, new CooldownSyncPayload(new Cooldown(currentTime, 20), ResourceLocation.fromNamespaceAndPath("identitiesmod", "parry_cd"), false));
                ParryEvents.setIconCooldown(new Cooldown(currentTime, 20));
                event.setCanceled(true);
                System.out.println("Parry Success");
            }
            else if(event.getSource().getDirectEntity() instanceof AbstractArrow arrow && arrow.getOwner() instanceof LivingEntity source)
            {
                source.hurt(event.getSource(), event.getAmount()*.5f);
                player.level().playSound(null, player.getOnPos(), ModSounds.PARRY_SOUND.get(), SoundSource.PLAYERS);
                CooldownAttachment newAtachment = new CooldownAttachment();
                newAtachment.getAllCooldowns().putAll(player.getData(ModDataAttachments.COOLDOWN).getAllCooldowns());
                newAtachment.setCooldown(ResourceLocation.fromNamespaceAndPath("identitiesmod", "parry_cd"), currentTime, 20);
                player.setData(ModDataAttachments.COOLDOWN, newAtachment);
                ParryEvents.setIconCooldown(new Cooldown(currentTime, 20));
                PacketDistributor.sendToPlayer(player, new CooldownSyncPayload(new Cooldown(currentTime, 20), ResourceLocation.fromNamespaceAndPath("identitiesmod", "parry_cd"), false));
                PacketDistributor.sendToPlayer(player, new SoundPayload(ModSounds.PARRY_SOUND.get()));
                event.setCanceled(true);
                System.out.println("Parry Arrow Success");
            }


        }
    }
}

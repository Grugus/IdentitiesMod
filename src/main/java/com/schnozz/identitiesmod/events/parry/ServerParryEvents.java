package com.schnozz.identitiesmod.events.parry;


import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.cooldown.Cooldown;
import com.schnozz.identitiesmod.cooldown.CooldownAttachment;
import com.schnozz.identitiesmod.mob_effects.ModEffects;
import com.schnozz.identitiesmod.networking.payloads.CDPARRYPayload;
import com.schnozz.identitiesmod.networking.payloads.sync_payloads.CooldownSyncPayload;
import com.schnozz.identitiesmod.networking.payloads.SoundPayload;
import com.schnozz.identitiesmod.sounds.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.DEDICATED_SERVER)
public class ServerParryEvents {
    private static int parryStreak = 0;
    @SubscribeEvent
    public static void onPlayerHurt(LivingIncomingDamageEvent event)
    {
        long currentTime = event.getEntity().level().getGameTime();
        if(event.getEntity() instanceof ServerPlayer player && player.hasData(ModDataAttachments.POWER_TYPE) && player.getData(ModDataAttachments.POWER_TYPE).equals("Parry"))
        {
            if(player.getData(ModDataAttachments.COOLDOWN).isOnCooldown(ResourceLocation.fromNamespaceAndPath("identitiesmod", "parry_duration"), currentTime)) {
                if ((event.getSource().getDirectEntity() instanceof LivingEntity source)) {
                    if(event.getSource().getDirectEntity() instanceof Player) {
                        parryStreak++;
                    }

                    source.hurt(event.getSource(), event.getAmount() * .5f);
                    source.addEffect(new MobEffectInstance(ModEffects.STUN,20));

                    player.level().playSound(null, player.getOnPos(), ModSounds.PARRY_SOUND.get(), SoundSource.PLAYERS);
                    CooldownAttachment newAtachment = new CooldownAttachment();
                    newAtachment.getAllCooldowns().putAll(player.getData(ModDataAttachments.COOLDOWN).getAllCooldowns());
                    newAtachment.setCooldown(ResourceLocation.fromNamespaceAndPath("identitiesmod", "parry_cd"), currentTime, 20);
                    player.setData(ModDataAttachments.COOLDOWN, newAtachment);
                    PacketDistributor.sendToPlayer(player, new CooldownSyncPayload(new Cooldown(currentTime, 20), ResourceLocation.fromNamespaceAndPath("identitiesmod", "parry_cd"), false));
                    PacketDistributor.sendToPlayer(player, new CDPARRYPayload(new Cooldown(currentTime, 20)));
                    event.setCanceled(true);
                    System.out.println("Parry Success");

                    parryBuff(player);
                }else if (event.getSource().getDirectEntity() instanceof AbstractArrow arrow && arrow.getOwner() instanceof LivingEntity source) {
                    if(event.getSource().getDirectEntity() instanceof Player) {
                        parryStreak++;
                    }

                    source.hurt(event.getSource(), event.getAmount() * .5f);
                    player.level().playSound(null, player.getOnPos(), ModSounds.PARRY_SOUND.get(), SoundSource.PLAYERS);
                    CooldownAttachment newAtachment = new CooldownAttachment();
                    newAtachment.getAllCooldowns().putAll(player.getData(ModDataAttachments.COOLDOWN).getAllCooldowns());
                    newAtachment.setCooldown(ResourceLocation.fromNamespaceAndPath("identitiesmod", "parry_cd"), currentTime, 20);
                    player.setData(ModDataAttachments.COOLDOWN, newAtachment);
                    PacketDistributor.sendToPlayer(player, new CDPARRYPayload(new Cooldown(currentTime, 20)));
                    PacketDistributor.sendToPlayer(player, new CooldownSyncPayload(new Cooldown(currentTime, 20), ResourceLocation.fromNamespaceAndPath("identitiesmod", "parry_cd"), false));
                    event.setCanceled(true);
                    System.out.println("Parry Arrow Success");
                    parryBuff(player);
                }
            }
            else
            {
                parryStreak = 0;
            }
        }
    }

    public static void parryBuff(Player parryPlayer)
    {
        if(parryStreak == 3)
        {
            parryPlayer.addEffect(new MobEffectInstance((Holder<MobEffect>) MobEffects.MOVEMENT_SPEED, 600, 1, false, true));
        }
        if(parryStreak == 4)
        {
            parryPlayer.addEffect(new MobEffectInstance((Holder<MobEffect>) MobEffects.DAMAGE_BOOST, 600, 1, false, true));
        }
        if(parryStreak == 5)
        {
            parryPlayer.addEffect(new MobEffectInstance((Holder<MobEffect>) MobEffects.MOVEMENT_SPEED, 600, 2, false, true));
        }
        if(parryStreak == 6)
        {
            parryPlayer.addEffect(new MobEffectInstance((Holder<MobEffect>) MobEffects.REGENERATION, 400, 1, false, true));
        }
        if(parryStreak == 7)
        {
            parryPlayer.addEffect(new MobEffectInstance((Holder<MobEffect>) MobEffects.DAMAGE_BOOST, 600, 2, false, true));
        }
        if(parryStreak == 8)
        {
            parryPlayer.addEffect(new MobEffectInstance((Holder<MobEffect>) MobEffects.REGENERATION, 400, 1, false, true));
        }
        if(parryStreak == 10)
        {
            parryPlayer.addEffect(new MobEffectInstance((Holder<MobEffect>) MobEffects.GLOWING, 300, 3, false, true));
            parryPlayer.addEffect(new MobEffectInstance((Holder<MobEffect>) MobEffects.ABSORPTION, 300, 3, false, true));
            parryStreak = 2;
        }
    }
}

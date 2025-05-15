package com.schnozz.identitiesmod.events;


import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.register_attachments.ModDataAttachments;
import com.schnozz.identitiesmod.cooldown.Cooldown;
import com.schnozz.identitiesmod.cooldown.CooldownAttachment;
import com.schnozz.identitiesmod.networking.payloads.CooldownSyncPayload;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ServerViltruiteEvents {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event)
    {
        if(event.getEntity().level() instanceof ServerLevel level && event.getEntity().hasData(ModDataAttachments.POWER_TYPE) && event.getEntity().getData(ModDataAttachments.POWER_TYPE.get()).equals("Viltruite"))
        {
            if(!event.getEntity().getData(ModDataAttachments.ENTITY_HELD).isEmpty())
            {
                Player p = event.getEntity();
                Entity target = level.getEntity(p.getData(ModDataAttachments.ENTITY_HELD).getUUID("UUID"));
                Vec3 targetPos = p.getEyePosition().add(p.getLookAngle().scale(2));
                assert target != null;
                target.setPos(targetPos);
                target.setNoGravity(true);
            }
        }
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        if(event.getEntity() instanceof ServerPlayer p && !event.getEntity().getData(ModDataAttachments.ENTITY_HELD).isEmpty() && event.getEntity().level() instanceof ServerLevel level && event.getEntity().getData(ModDataAttachments.POWER_TYPE.get()).equals("Viltruite"))
        {
            Entity target = level.getEntity(p.getData(ModDataAttachments.ENTITY_HELD).getUUID("UUID"));
            assert target != null;
            target.setNoGravity(false);
            target.setDeltaMovement(p.getLookAngle().scale(1));
            p.setData(ModDataAttachments.ENTITY_HELD.get(), new CompoundTag());//sends an empty tag
            long startTime = level.getGameTime();
            Cooldown cd = new Cooldown(startTime, 200);
            CooldownAttachment cdAttach = p.getData(ModDataAttachments.COOLDOWN);
            ResourceLocation key = ResourceLocation.fromNamespaceAndPath("identitiesmod", "grab_cd");
            ModClientEvents.setIconCooldown(cd);
            cdAttach.setCooldown(key, startTime, 200);
            PacketDistributor.sendToPlayer(p, new CooldownSyncPayload(cd,key, false  ));


        }

    }


}

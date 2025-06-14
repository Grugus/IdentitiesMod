package com.schnozz.identitiesmod.events.gravity;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.damage_sources.ModDamageTypes;
import com.schnozz.identitiesmod.cooldown.Cooldown;
import com.schnozz.identitiesmod.mob_effects.ModEffects;
import com.schnozz.identitiesmod.networking.payloads.*;
import com.schnozz.identitiesmod.screen.icon.CooldownIcon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;
import java.util.Optional;

import static com.schnozz.identitiesmod.keymapping.ModMappings.*;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientGravityEvents {
    //x and z finals for chaos
    private static final double X_STRENGTH = 1.5;
    private static final double Z_STRENGTH = 1.5;
    //timers
    private static int chaosTimer = 0;
    private static int pullStunTimer = 0;
    //dynamic forces
    private static double vX,vY,vZ;
    //chaos target entity id
    private static int chaosTargetEntityId;
    //chaos damage
    private static float chaosDamage = 2.5F;
    //chaos target find global variables
    private static Entity target;
    private static double closestDistance;
    //entity list for pull
    private static List<Entity> entitiesInBox;
    //stun duration
    private static int stunDuration = 40;
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        LocalPlayer gravityPlayer = Minecraft.getInstance().player;
        if (gravityPlayer == null) return;
        Level level = gravityPlayer.level();
        if(!level.isClientSide()) return;

        String power = gravityPlayer.getData(ModDataAttachments.POWER_TYPE);
        if (power.equals("Gravity")) {
            //gravity push
            if (GRAVITY_PUSH_MAPPING.get().consumeClick() && !gravityPlayer.getData(ModDataAttachments.COOLDOWN).isOnCooldown(ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "gravity.pushcd"), 0)) {
                push(gravityPlayer);
                PUSH_COOLDOWN_ICON.setCooldown(new Cooldown(level.getGameTime(), 240));
                gravityPlayer.getData(ModDataAttachments.COOLDOWN).setCooldown(ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "gravity.pushcd"), level.getGameTime(), 200);
                PacketDistributor.sendToServer(new CooldownSyncPayload(new Cooldown(level.getGameTime(), 200), ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "gravity.pushcd"), false));
            }
            //gravity pull
            else if (GRAVITY_PULL_MAPPING.get().consumeClick() && !gravityPlayer.getData(ModDataAttachments.COOLDOWN).isOnCooldown(ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "gravity.pullcd"), 0)) {
                pull(gravityPlayer);
                PULL_COOLDOWN_ICON.setCooldown(new Cooldown(level.getGameTime(), 200));
                gravityPlayer.getData(ModDataAttachments.COOLDOWN).setCooldown(ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "gravity.pullcd"), level.getGameTime(), 160);
                PacketDistributor.sendToServer(new CooldownSyncPayload(new Cooldown(level.getGameTime(), 160), ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "gravity.pullcd"), false));

                //pullStunTimer = 1;
            }
            //gravity meteor creation and set both position and movement
            else if(GRAVITY_METEOR_MAPPING.get().consumeClick()) //EVAN THIS NEEDS COOLDOWN
            {
                //MeteorEntity newMeteor = new MeteorEntity(,level);
            }
            else if(GRAVITY_CHAOS_MAPPING.get().consumeClick() && !gravityPlayer.getData(ModDataAttachments.COOLDOWN).isOnCooldown(ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "gravity.chaoscd"), 0))  //EVAN THIS NEEDS COOLDOWN
            {
                target = null;
                closestDistance = Integer.MAX_VALUE;

                findChaosTargetAndDistance(gravityPlayer);
                if(target != null) {
                    if (target instanceof LivingEntity livingEntity) {
                        chaosTargetEntityId = livingEntity.getId();
                        chaosTimer = 1; //starts chaos logic loop
                        chaos(gravityPlayer); //intial hit guaranteed
                    }
                    CHAOS_COOLDOWN_ICON.setCooldown(new Cooldown(level.getGameTime(), 250));
                    gravityPlayer.getData(ModDataAttachments.COOLDOWN).setCooldown(ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "gravity.chaoscd"), level.getGameTime(), 250);
                    PacketDistributor.sendToServer(new CooldownSyncPayload(new Cooldown(level.getGameTime(), 250), ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "gravity.chaoscd"), false));
                }
            }


            //chaos logic
            if(chaosTimer > 0 && chaosTimer < 200 && chaosTargetEntityId != 0)
            {
                int ran = (int) (Math.random() * 40);
                if(ran == 0) {
                    chaos(gravityPlayer);
                }
                chaosTimer++;
            }
            if(pullStunTimer > 0 && pullStunTimer < 20 + stunDuration)
            {
                pullStunTimer++;
            }
            if(pullStunTimer == 20)
            {
                for(Entity entity: entitiesInBox)
                {
                    if(entity instanceof LivingEntity livingEntity)
                    {
                        livingEntity.addEffect(new MobEffectInstance(ModEffects.STUN, stunDuration, 0,false,true,true));
                        PacketDistributor.sendToServer(new StunPayload(livingEntity.getId(), stunDuration));
                    }
                }
            }
            if(pullStunTimer >= 20 + stunDuration)
            {
                for(Entity entity: entitiesInBox)
                {
                    if(entity instanceof LivingEntity livingEntity && livingEntity.getActiveEffects().contains(ModEffects.STUN))
                    {
                        livingEntity.removeEffect(ModEffects.STUN);
                    }
                }
                pullStunTimer = 0;
            }
        }
    }
    //cooldown icons
    private static final CooldownIcon PUSH_COOLDOWN_ICON = new CooldownIcon(10, 10, 16, ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "textures/gui/gravitypushcd_icon.png"));
    private static final CooldownIcon PULL_COOLDOWN_ICON = new CooldownIcon(10, 30, 16, ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "textures/gui/gravitypullcd_icon.png"));
    private static final CooldownIcon CHAOS_COOLDOWN_ICON = new CooldownIcon(10, 50, 16, ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "textures/gui/gravitychaoscd_icon.png"));
    //ability methods
    public static void push(Player gravityPlayer)
    {
        Level level = gravityPlayer.level();

        double xMin = gravityPlayer.getX() - 10.0; double yMin = gravityPlayer.getY() - 10.0; double zMin = gravityPlayer.getZ() - 10.0;
        double xMax = gravityPlayer.getX() + 10.0; double yMax = gravityPlayer.getY() + 10.0; double zMax = gravityPlayer.getZ() + 10.0;
        AABB gravityForceBB = new AABB(xMin,yMin,zMin,xMax,yMax,zMax);

        List<Entity> entitiesInBox = level.getEntities(gravityPlayer, gravityForceBB);
        for (Entity entity : entitiesInBox) {
            Vec3 angle = gravityPlayer.getLookAngle();
            double rx = angle.x; double ry = angle.y; double rz = angle.z;
            double forceX = 4.0 * rx; double forceY = 2.0 * ry; double forceZ = 4.0 * rz;
            PacketDistributor.sendToServer(new GravityPayload(entity.getId(),forceX,forceY,forceZ));
        }
    }
    public static void pull(Player gravityPlayer)
    {
        Level level = gravityPlayer.level();

        double xMin = gravityPlayer.getX() - 20.0; double yMin = gravityPlayer.getY() - 20.0; double zMin = gravityPlayer.getZ() - 20.0;
        double xMax = gravityPlayer.getX() + 20.0; double yMax = gravityPlayer.getY() + 20.0; double zMax = gravityPlayer.getZ() + 20.0;
        AABB gravityForceBB = new AABB(xMin,yMin,zMin,xMax,yMax,zMax);

        entitiesInBox = level.getEntities(gravityPlayer, gravityForceBB);
        for (Entity entity : entitiesInBox) {
            double dx = entity.getX() - gravityPlayer.getX(); double dy = entity.getY() - gravityPlayer.getY(); double dz = entity.getZ() - gravityPlayer.getZ();
            double forceX = -dx/2; double forceY = -dy/4; double forceZ = -dz/2;
            PacketDistributor.sendToServer(new GravityPayload(entity.getId(),forceX,forceY,forceZ));
        }
    }

    public static void findChaosTargetAndDistance(Player gravityPlayer)
    {
        Vec3 eyePosition = gravityPlayer.getEyePosition(1.0F); // Player's eye location
        Vec3 lookVector = gravityPlayer.getLookAngle();        // Direction player is looking
        Vec3 endPosition = eyePosition.add(lookVector.scale(25)); // End of the ray

        // Create AABB for entity search along the line (expanded path)
        AABB searchArea = new AABB(eyePosition, endPosition).inflate(1.0); // Slightly wider search area

        // Filter and find first entity hit
        List<Entity> entities = gravityPlayer.level().getEntities(gravityPlayer, searchArea, (entity) -> {
            // Optional filters: skip the player and only target living entities
            return entity instanceof LivingEntity && !entity.isSpectator() && entity != gravityPlayer;
        });

        for (Entity entity : entities) {
            AABB entityBox = entity.getBoundingBox().inflate(0.3);
            Optional<Vec3> hit = entityBox.clip(eyePosition, endPosition);

            if (hit.isPresent()) {
                double distance = eyePosition.distanceTo(hit.get());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    target = entity;
                }
            }
        }
    }
    public static void chaos(Player gravityPlayer)
    {
        double x = (int)(Math.random()*X_STRENGTH)+0.4;
        double z = (int)(Math.random()*Z_STRENGTH)+0.4;
        if((int)(Math.random()*1.8) == 0) {x*=-1;}
        if((int)(Math.random()*1.8) == 0) {z*=-1;}

        Holder<DamageType> gravDamageType = gravityPlayer.level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(ModDamageTypes.GRAVITY_POWER_DAMAGE);

        PacketDistributor.sendToServer(new GravityPayload(chaosTargetEntityId,x,0.0,z));
        PacketDistributor.sendToServer(new EntityDamagePayload(chaosTargetEntityId,gravityPlayer.getId(),chaosDamage,gravDamageType));
    }

    public static void meteor()
    {

    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiEvent.Post event) {
        if(!Minecraft.getInstance().player.getData(ModDataAttachments.POWER_TYPE).equals("Gravity"))
        {
            return;
        }

        long gameTime = Minecraft.getInstance().level.getGameTime();
        GuiGraphics graphics = event.getGuiGraphics();
        PUSH_COOLDOWN_ICON.render(graphics, gameTime);
        PULL_COOLDOWN_ICON.render(graphics, gameTime);
        CHAOS_COOLDOWN_ICON.render(graphics, gameTime);
    }

}

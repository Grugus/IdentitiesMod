package com.schnozz.identitiesmod.events.gravity;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.networking.payloads.VelocityPayload;
import com.schnozz.identitiesmod.register_attachments.ModDataAttachments;
import com.schnozz.identitiesmod.cooldown.Cooldown;
import com.schnozz.identitiesmod.networking.payloads.CooldownSyncPayload;
import com.schnozz.identitiesmod.networking.payloads.GravityPayload;
import com.schnozz.identitiesmod.networking.payloads.EntityDamagePayload;
import com.schnozz.identitiesmod.screen.icon.CooldownIcon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
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
    //AABB finals
    private static final double XZ_SCALE = 4.0;
    private static final double Y_SCALE = 4.0;
    //x and z finals for chaos
    private static final double X_STRENGTH = 1.5;
    private static final double Z_STRENGTH = 1.5;
    //bounding box
    private static AABB vortexBox;
    //timers
    private static int vortexTimer = 0;
    private static int chaosTimer = 0;
    //dynamic forces
    private static double vX,vY,vZ;
    //chaos target entity id
    private static int chaosTargetEntityId;
    //chaos damage
    private static float chaosDamage = 1.5F;

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
            }
            //gravity vortex creation and start of timer
            else if(GRAVITY_VORTEX_MAPPING.get().consumeClick())
            {
                createVortexAABB(gravityPlayer);
                vortexTimer = 1;
            }
            //gravity meteor creation and set both position and movement
            else if(GRAVITY_METEOR_MAPPING.get().consumeClick())
            {
                //MeteorEntity newMeteor = new MeteorEntity(,level);
            }
            else if(GRAVITY_CHAOS_MAPPING.get().consumeClick())//if this errors, could need to be server side
            {
                Vec3 eyePosition = gravityPlayer.getEyePosition(1.0F); // Player's eye location
                Vec3 lookVector = gravityPlayer.getLookAngle();        // Direction player is looking
                Vec3 endPosition = eyePosition.add(lookVector.scale(50)); // End of the ray

                // Create AABB for entity search along the line (expanded path)
                AABB searchArea = new AABB(eyePosition, endPosition).inflate(1.0); // Slightly wider search area

                // Filter and find first entity hit
                List<Entity> entities = level.getEntities(gravityPlayer, searchArea, (entity) -> {
                    // Optional filters: skip the player and only target living entities
                    return entity instanceof LivingEntity && !entity.isSpectator() && entity != gravityPlayer;
                });

                Entity target = null;
                double closestDistance = Double.MAX_VALUE;

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

                if (target instanceof LivingEntity livingEntity) {
                    chaosTargetEntityId = livingEntity.getId();
                    chaosTimer = 1;
                    double x = (int)(Math.random()*X_STRENGTH)+0.2;
                    double z = (int)(Math.random()*Z_STRENGTH)+0.2;
                    if((int)(Math.random()*2) == 0) {x*=-1;}
                    if((int)(Math.random()*2) == 0) {z*=-1;}

                    Holder<DamageType> placeHolderDamageType = level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.CRAMMING);

                    PacketDistributor.sendToServer(new GravityPayload(chaosTargetEntityId,x,0.0,z));
                    PacketDistributor.sendToServer(new EntityDamagePayload(chaosTargetEntityId,gravityPlayer.getId(),chaosDamage,placeHolderDamageType));
                }
            }
            //vortex logic
            if(vortexTimer > 0 && vortexTimer < 240)
            {
                //double vortexBoxSize = vortexBox.getSize();
                List<Entity> entitiesInBox = level.getEntities(gravityPlayer, vortexBox);
                for(Entity entity: entitiesInBox)
                {
                    setVortexForces(entity);;

                    if(entity.getClass().equals(ThrownEnderpearl.class))
                    {
                        entity.setPos(vortexBox.getCenter());
                    }
                    PacketDistributor.sendToServer(new VelocityPayload(entity.getId(),vX,vY,vZ));
                }
                vortexTimer++;
            }
            //chaos logic
            if(chaosTimer > 0 && chaosTimer < 500 && chaosTargetEntityId != 0)
            {
                int ran = (int) (Math.random() * 35);
                if(ran == 0)
                {
                    double x = (int)(Math.random()*X_STRENGTH)+0.2;
                    double z = (int)(Math.random()*Z_STRENGTH)+0.2;
                    if((int)(Math.random()*2) == 0) {x*=-1;}
                    if((int)(Math.random()*2) == 0) {z*=-1;}

                    Holder<DamageType> placeHolderDamageType = level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.CRAMMING);

                    PacketDistributor.sendToServer(new GravityPayload(chaosTargetEntityId,x,0.0,z));
                    PacketDistributor.sendToServer(new EntityDamagePayload(chaosTargetEntityId,gravityPlayer.getId(),chaosDamage,placeHolderDamageType));

                    chaosTimer++;
                }
            }
        }
    }


    private static final CooldownIcon PUSH_COOLDOWN_ICON = new CooldownIcon(10, 10, 16, ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "textures/gui/gravitypushcd_icon.png"));
    private static final CooldownIcon PULL_COOLDOWN_ICON = new CooldownIcon(10, 30, 16, ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "textures/gui/gravitypullcd_icon.png"));

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

        List<Entity> entitiesInBox = level.getEntities(gravityPlayer, gravityForceBB);
        for (Entity entity : entitiesInBox) {
            double dx = entity.getX() - gravityPlayer.getX(); double dy = entity.getY() - gravityPlayer.getY(); double dz = entity.getZ() - gravityPlayer.getZ();
            double forceX = -dx/2; double forceY = -dy/4; double forceZ = -dz/2;
            PacketDistributor.sendToServer(new GravityPayload(entity.getId(),forceX,forceY,forceZ));
        }
    }

    public static void createVortexAABB(Player gravityPlayer)
    {
        Vec3 vortexOrigin = gravityPlayer.getEyePosition().add(gravityPlayer.getLookAngle().scale(4));
        double minX = vortexOrigin.x- XZ_SCALE; double minY = vortexOrigin.y- Y_SCALE; double minZ = vortexOrigin.z- XZ_SCALE;
        double maxX = vortexOrigin.x+ XZ_SCALE; double maxY = vortexOrigin.y+ Y_SCALE; double maxZ = vortexOrigin.z+ XZ_SCALE;
        vortexBox = new AABB(minX,minY,minZ,maxX,maxY,maxZ);
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
    }

    public static void setVortexForces(Entity entity)
    {
        // Position deltas
        double dx = vortexBox.getCenter().x - entity.getX();
        double dy = vortexBox.getCenter().y - entity.getY();
        double dz = vortexBox.getCenter().z - entity.getZ();

        vX = dx/4; vY = dy/4; vZ = dz/4;
    }
}

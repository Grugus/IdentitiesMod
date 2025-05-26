package com.schnozz.identitiesmod.events.gravity;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.register_attachments.ModDataAttachments;
import com.schnozz.identitiesmod.cooldown.Cooldown;
import com.schnozz.identitiesmod.networking.payloads.CooldownSyncPayload;
import com.schnozz.identitiesmod.networking.payloads.GravityPayload;
import com.schnozz.identitiesmod.networking.payloads.EntityDamagePayload;
import com.schnozz.identitiesmod.screen.icon.CooldownIcon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.Items;
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

import static com.schnozz.identitiesmod.keymapping.ModMappings.*;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class GravityEvents {
    private static AABB vortexBox;
    private static double xzScale = 2.5;
    private static double yScale = 2.5;
    private static int vortexTimer = 0;
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        LocalPlayer gravityPlayer = Minecraft.getInstance().player;
        if (gravityPlayer == null) return;
        Level level = gravityPlayer.level();
        if(!level.isClientSide()) return;

        String power = gravityPlayer.getData(ModDataAttachments.POWER_TYPE);
        if (power.equals("Gravity")) {
            float pushDamage = 4.0F; float pullDamage = 4.0F;
            //gravity push
            if (GRAVITY_PUSH_MAPPING.get().consumeClick() && !gravityPlayer.getData(ModDataAttachments.COOLDOWN).isOnCooldown(ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "gravity.pushcd"), 0)) {
                push(gravityPlayer, pushDamage);
                PUSH_COOLDOWN_ICON.setCooldown(new Cooldown(level.getGameTime(), 200));
                gravityPlayer.getData(ModDataAttachments.COOLDOWN).setCooldown(ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "gravity.pushcd"), level.getGameTime(), 200);
                PacketDistributor.sendToServer(new CooldownSyncPayload(new Cooldown(level.getGameTime(), 200), ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "gravity.pushcd"), false));
            }
            //gravity pull
            else if (GRAVITY_PULL_MAPPING.get().consumeClick() && !gravityPlayer.getData(ModDataAttachments.COOLDOWN).isOnCooldown(ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "gravity.pullcd"), 0)) {
                pull(gravityPlayer, pullDamage);
                PULL_COOLDOWN_ICON.setCooldown(new Cooldown(level.getGameTime(), 160));
                gravityPlayer.getData(ModDataAttachments.COOLDOWN).setCooldown(ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "gravity.pullcd"), level.getGameTime(), 160);
                PacketDistributor.sendToServer(new CooldownSyncPayload(new Cooldown(level.getGameTime(), 160), ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "gravity.pullcd"), false));
            }
            //gravity vortex
            else if(GRAVITY_VORTEX_MAPPING.get().consumeClick())
            {
                createVortexAABB(gravityPlayer);
                vortexTimer = 1;
            }
            if(vortexTimer < 120 && vortexTimer > 0)
            {
                //double vortexBoxSize = vortexBox.getSize();
                List<Entity> entitiesInBox = level.getEntities(gravityPlayer, vortexBox);
                for(Entity entity: entitiesInBox)
                {
                    double dx = vortexBox.getCenter().x - entity.getX(); double dy = vortexBox.getCenter().x - entity.getY(); double dz = vortexBox.getCenter().z - entity.getZ();
                    double forceX = (dx)/4; double forceY = (dy)/10; double forceZ = (dz)/4;
                    if(entity.getClass().equals(ThrownEnderpearl.class))
                    {
                        entity.setPos(vortexBox.getCenter());
                    }
                    PacketDistributor.sendToServer(new GravityPayload(entity.getId(),forceX,forceY,forceZ));
                    if(Math.abs(dx)<=1 && Math.abs(dy)<=1 && Math.abs(dz)<=1)
                    {
                        entity.setDeltaMovement(0.0,0.0,0.0);
                    }

                }
                vortexTimer++;
            }
        }
    }


    private static final CooldownIcon PUSH_COOLDOWN_ICON = new CooldownIcon(10, 10, 16, ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "textures/gui/gravitypushcd_icon.png"));
    private static final CooldownIcon PULL_COOLDOWN_ICON = new CooldownIcon(10, 30, 16, ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "textures/gui/gravitypullcd_icon.png"));

    public static void push(Player gravityPlayer, float pushDamage)
    {
        Level level = gravityPlayer.level();

        double xMin = gravityPlayer.getX() - 10.0; double yMin = gravityPlayer.getY() - 10.0; double zMin = gravityPlayer.getZ() - 10.0;
        double xMax = gravityPlayer.getX() + 10.0; double yMax = gravityPlayer.getY() + 10.0; double zMax = gravityPlayer.getZ() + 10.0;
        AABB gravityForceBB = new AABB(xMin,yMin,zMin,xMax,yMax,zMax);

        List<Entity> entitiesInBox = level.getEntities(gravityPlayer, gravityForceBB);
        for (Entity entity : entitiesInBox) {
            Vec3 angle = gravityPlayer.getLookAngle();
            double rx = angle.x; double ry = angle.y; double rz = angle.z;
            double forceX = 5.5 * rx; double forceY = 5.0 * ry; double forceZ = 5.5 * rz;
            PacketDistributor.sendToServer(new GravityPayload(entity.getId(),forceX,forceY,forceZ));
            PacketDistributor.sendToServer(new EntityDamagePayload(entity.getId(),gravityPlayer.getId(),pushDamage));
        }
    }

    public static void pull(Player gravityPlayer, float pullDamage)
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
            PacketDistributor.sendToServer(new EntityDamagePayload(entity.getId(),gravityPlayer.getId(),pullDamage));
        }
    }

    public static void createVortexAABB(Player gravityPlayer)
    {
        Vec3 vortexOrigin = gravityPlayer.getEyePosition().add(gravityPlayer.getLookAngle().scale(6));
        double minX = vortexOrigin.x-xzScale; double minY = vortexOrigin.y-yScale; double minZ = vortexOrigin.z-xzScale;
        double maxX = vortexOrigin.x+xzScale; double maxY = vortexOrigin.y+yScale; double maxZ = vortexOrigin.z+xzScale;
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

}

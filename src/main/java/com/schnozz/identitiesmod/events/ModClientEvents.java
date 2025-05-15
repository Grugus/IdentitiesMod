package com.schnozz.identitiesmod.events;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.cooldown.Cooldown;
import com.schnozz.identitiesmod.cooldown.CooldownAttachment;
import com.schnozz.identitiesmod.networking.payloads.CooldownSyncPayload;
import com.schnozz.identitiesmod.networking.payloads.EntityBoxPayload;
import com.schnozz.identitiesmod.screen.icon.CooldownIcon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.checkerframework.checker.units.qual.C;

import java.util.Comparator;
import java.util.List;

import static com.schnozz.identitiesmod.keymapping.ModMappings.GRAB_MAPPING;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ModClientEvents {


    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        LocalPlayer p = Minecraft.getInstance().player;
        if(p == null || !p.level().isClientSide() || !p.hasData(ModDataAttachments.POWER_TYPE)) return;
        String power = p.getData(ModDataAttachments.POWER_TYPE);

        if (power.equals("Viltruite") && GRAB_MAPPING.get().consumeClick() && !p.getData(ModDataAttachments.COOLDOWN).isOnCooldown(ResourceLocation.fromNamespaceAndPath("identitiesmod", "grab_cd"), 0)) {
            findEntity(Minecraft.getInstance().player);
        }
    }

    private static final CooldownIcon cooldownIcon = new CooldownIcon(10, 10, 16, ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "textures/gui/viltruitegrabcd_icon.png"));

    private static boolean findEntity (Player player)
    {
        Vec3 look = player.getLookAngle();
        Vec3 start = player.position().add(0, player.getEyeHeight(), 0);
        Vec3 frontCenter = start.add(look.scale(2.0)); // 2 blocks in front

        AABB aabb = new AABB(frontCenter, frontCenter).inflate(1.0); // Expand to 2x2x2 area

        List<Entity> entities = player.level().getEntities(player, aabb, e -> !(e instanceof Player));

        if (!entities.isEmpty()) {
            Entity closest = entities.stream()
                    .min(Comparator.comparingDouble(e -> e.distanceToSqr(player)))
                    .orElse(null);

            if (closest != null) {

                PacketDistributor.sendToServer(new EntityBoxPayload(closest.saveWithoutId(new CompoundTag()))); // packet only gets sent if a entity was found
                return true;
            }
        }
        long currentTime = player.level().getGameTime();
        CooldownAttachment newAtachment = new CooldownAttachment();
        newAtachment.getAllCooldowns().putAll(player.getData(ModDataAttachments.COOLDOWN).getAllCooldowns());
        newAtachment.setCooldown(ResourceLocation.fromNamespaceAndPath("identitiesmod", "grab_cd"), currentTime, 200);
        player.setData(ModDataAttachments.COOLDOWN, newAtachment);
        cooldownIcon.setCooldown(new Cooldown(currentTime, 200));
        PacketDistributor.sendToServer(new CooldownSyncPayload(new Cooldown(currentTime, 200), ResourceLocation.fromNamespaceAndPath("identitiesmod", "grab_cd"), false));
        return false;

        // do nothing or anything you want to do if it fails put an else statement -- this wasn't chatgpt btw
    }

    public static void setIconCooldown(Cooldown cod)
    {
        cooldownIcon.setCooldown(cod);
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiEvent.Post event) {
        if(!Minecraft.getInstance().player.getData(ModDataAttachments.POWER_TYPE).equals("Viltruite"))
        {
            return;
        }

        long gameTime = Minecraft.getInstance().level.getGameTime();
        GuiGraphics graphics = event.getGuiGraphics();
        cooldownIcon.render(graphics, gameTime);

    }
}

package com.schnozz.identitiesmod.events.parry;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.cooldown.Cooldown;
import com.schnozz.identitiesmod.cooldown.CooldownAttachment;
import com.schnozz.identitiesmod.networking.payloads.sync_payloads.CooldownSyncPayload;
import com.schnozz.identitiesmod.screen.icon.CooldownIcon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.schnozz.identitiesmod.keymapping.ModMappings.PARRY_MAPPING;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientParryEvents {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event)
    {
        LocalPlayer p = Minecraft.getInstance().player;
        if(p == null || !p.level().isClientSide() || !p.hasData(ModDataAttachments.POWER_TYPE)) return;
        String power = p.getData(ModDataAttachments.POWER_TYPE);

        if (power.equals("Parry") && PARRY_MAPPING.get().consumeClick() && !p.getData(ModDataAttachments.COOLDOWN).isOnCooldown(ResourceLocation.fromNamespaceAndPath("identitiesmod", "parry_cd"), 0)) {
            long currentTime = Minecraft.getInstance().level.getGameTime();
            System.out.println("Parry sent");

            parry(currentTime, p);

        }
    }

    private static final CooldownIcon cooldownIcon = new CooldownIcon(10, 10, 16, ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "textures/gui/parrycd_icon.png"));

    private static void parry(long currentTime, LocalPlayer player) {
        CooldownAttachment newAtachment = new CooldownAttachment();
        newAtachment.getAllCooldowns().putAll(player.getData(ModDataAttachments.COOLDOWN).getAllCooldowns());
        newAtachment.setCooldown(ResourceLocation.fromNamespaceAndPath("identitiesmod", "parry_cd"), currentTime, 70);
        player.setData(ModDataAttachments.COOLDOWN, newAtachment);
        cooldownIcon.setCooldown(new Cooldown(currentTime, 70));
        PacketDistributor.sendToServer(new CooldownSyncPayload(new Cooldown(currentTime, 70), ResourceLocation.fromNamespaceAndPath("identitiesmod", "parry_cd"), false));
        PacketDistributor.sendToServer(new CooldownSyncPayload(new Cooldown(currentTime, 16), ResourceLocation.fromNamespaceAndPath("identitiesmod", "parry_duration"), false));
    }

    public static void setIconCooldown(Cooldown cod)
    {
        cooldownIcon.setCooldown(cod);
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiEvent.Post event) {

        if(!Minecraft.getInstance().player.getData(ModDataAttachments.POWER_TYPE).equals("Parry"))
        {
            return;
        }

        long gameTime = Minecraft.getInstance().level.getGameTime();
        GuiGraphics graphics = event.getGuiGraphics();
        cooldownIcon.render(graphics, gameTime);

    }

}

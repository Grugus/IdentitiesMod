package com.schnozz.identitiesmod.powers.parry;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import com.schnozz.identitiesmod.cooldown.Cooldown;
import com.schnozz.identitiesmod.cooldown.CooldownAttachment;
import com.schnozz.identitiesmod.networking.payloads.CooldownSyncPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.schnozz.identitiesmod.keymapping.ModMappings.GRAB_MAPPING;
import static com.schnozz.identitiesmod.keymapping.ModMappings.PARRY_MAPPING;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ParryEvents {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event)
    {
        LocalPlayer p = Minecraft.getInstance().player;
        if(p == null || !p.level().isClientSide()) return;
        String power = p.getData(ModDataAttachments.POWER_TYPE);

        if (power.equals("Parry") && PARRY_MAPPING.get().consumeClick() && !p.getData(ModDataAttachments.COOLDOWN).isOnCooldown(ResourceLocation.fromNamespaceAndPath("identitiesmod", "parry_cd"), 0)) {
            long currentTime = Minecraft.getInstance().level.getGameTime();
            System.out.println("Parry sent");
            parry(currentTime, p);

        }
    }

    private static void parry(long currentTime, LocalPlayer player) {
        CooldownAttachment newAtachment = new CooldownAttachment();
        newAtachment.getAllCooldowns().putAll(player.getData(ModDataAttachments.COOLDOWN).getAllCooldowns());
        newAtachment.setCooldown(ResourceLocation.fromNamespaceAndPath("identitiesmod", "parry_cd"), currentTime, 120);
        player.setData(ModDataAttachments.COOLDOWN, newAtachment);
        PacketDistributor.sendToServer(new CooldownSyncPayload(new Cooldown(currentTime, 120), ResourceLocation.fromNamespaceAndPath("identitiesmod", "parry_cd"), false));
        PacketDistributor.sendToServer(new CooldownSyncPayload(new Cooldown(currentTime, 20), ResourceLocation.fromNamespaceAndPath("identitiesmod", "parry_duration"), false));
    }

}

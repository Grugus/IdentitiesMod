package com.schnozz.identitiesmod.events.adaptation;

import com.schnozz.identitiesmod.IdentitiesMod;
import com.schnozz.identitiesmod.register_attachments.ModDataAttachments;
import com.schnozz.identitiesmod.screen.icon.AdapterProgressBar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import static com.schnozz.identitiesmod.keymapping.ModMappings.ADAPTATION_SWITCH_MAPPING;

@EventBusSubscriber(modid = IdentitiesMod.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientAdaptationEvents {
    private static final AdapterProgressBar DIAMOND_SWORD_BAR = new AdapterProgressBar(10,10, 18, ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "textures/gui/diamond_sword_icon.png"),ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID,"generic"));
    private static final AdapterProgressBar FLAME_BAR = new AdapterProgressBar(10,33, 18, ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "textures/gui/flame_icon.png"),ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID,"infire"));
    private static final AdapterProgressBar POTION_BAR = new AdapterProgressBar(10,56, 18, ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID, "textures/gui/potion_of_harming_icon.png"),ResourceLocation.fromNamespaceAndPath(IdentitiesMod.MODID,"magic"));
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        LocalPlayer adaptationPlayer = Minecraft.getInstance().player;
        if (adaptationPlayer == null) return;
        Level level = adaptationPlayer.level();
        if(!level.isClientSide()) return;

        if(adaptationPlayer.getData(ModDataAttachments.POWER_TYPE).equals("Adaptation"))
        {
            if(ADAPTATION_SWITCH_MAPPING.get().consumeClick())
            {
                switchAdaptation();
            }
        }
    }
    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiEvent.Post event) {
        if(!Minecraft.getInstance().player.getData(ModDataAttachments.POWER_TYPE).equals("Adaptation"))
        {
            return;
        }

        DIAMOND_SWORD_BAR.render(event.getGuiGraphics());
        FLAME_BAR.render(event.getGuiGraphics());
        POTION_BAR.render(event.getGuiGraphics());
    }

    public static void switchAdaptation()
    {

    }
}

package com.schnozz.identitiesmod.screen.icon;

import com.schnozz.identitiesmod.register_attachments.ModDataAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class AdapterProgressBar {

    private ResourceLocation texture;
    private int x;
    private int y;
    private int size;
    private ResourceLocation source;
    public AdapterProgressBar(int x, int y, int size, ResourceLocation texture, ResourceLocation source) {

        this.x = x;
        this.y = y;
        this.size = size;
        this.texture = texture;
        this.source = source;
    }

    public void render(GuiGraphics guiGraphics) {
        Player adapter = Minecraft.getInstance().player;
        float percentFull = 1.00F-adapter.getData(ModDataAttachments.ADAPTION).getAdaptationValue(source);
        guiGraphics.blit(texture, x, y, 0, (size*percentFull) - size, size, size, size, size*2);
    }
}

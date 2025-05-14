package com.schnozz.identitiesmod.screen.icon;

import com.schnozz.identitiesmod.cooldown.Cooldown;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class CooldownIcon {
    private final ResourceLocation texture;
    private final int x, y, size;
    private Cooldown cd = null; // in ticks

    public CooldownIcon(ResourceLocation texture, int x, int y, int size) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void startCooldown(Cooldown cd) {
        this.cd = cd;
    }

    public boolean isCoolingDown(long currentGameTime) {
        return cd != null && (cd.startTime() >= 0 && currentGameTime < (cd.startTime() + cd.duration()));
    }

    public void render(GuiGraphics graphics, long currentGameTime) {
        graphics.blitSprite(texture, x,y,size,size);
    }
}

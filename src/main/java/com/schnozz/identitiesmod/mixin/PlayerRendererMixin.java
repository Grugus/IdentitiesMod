package com.schnozz.identitiesmod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.schnozz.identitiesmod.mob_effects.ModEffects;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(AbstractClientPlayer entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        if (shouldHidePlayer(entity)) {
            ci.cancel();
        }
    }

    private boolean shouldHidePlayer(Player player) {
        // Example condition: potion effect or tag
        return player.hasEffect(ModEffects.SUPER_INVISIBILITY); // make this the new potion effect
    }
}

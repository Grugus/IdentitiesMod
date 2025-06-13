package com.schnozz.identitiesmod.mixin;

import com.schnozz.identitiesmod.attachments.ModDataAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmBlock.class)
public abstract class FarmBlockMixin {
    @Inject(method = "fallOn", at = @At("HEAD"), cancellable = true)
    private void cancelFarmlandTrample(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo ci) {
        // Your condition: prevent trampling
        if (!level.isClientSide) {
            if(entity instanceof Player p && p.getData(ModDataAttachments.POWER_TYPE).equals("Kyle"))
            {

                ci.cancel();
            }// Cancel the vanilla logic that replaces farmland with dirt
        }
    }
}

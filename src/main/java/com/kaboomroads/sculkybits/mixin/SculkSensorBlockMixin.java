package com.kaboomroads.sculkybits.mixin;

import com.kaboomroads.sculkybits.block.entity.custom.SculkAttacker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkSensorBlock.class)
public abstract class SculkSensorBlockMixin {
    @Inject(method = "stepOn", at = @At("HEAD"), cancellable = true)
    public void canTargetEntity(Level level, BlockPos blockPos, BlockState blockState, Entity entity, CallbackInfo ci) {
        if (!level.isClientSide() && SculkSensorBlock.canActivate(blockState) && !SculkAttacker.testAttackable(entity))
            ci.cancel();
    }
}

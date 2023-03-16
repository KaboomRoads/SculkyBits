package com.kaboomroads.sculkybits.mixin;

import com.kaboomroads.sculkybits.block.entity.custom.SculkAttacker;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.warden.Warden;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Warden.class)
public abstract class WardenMixin {
    @Inject(method = "canTargetEntity(Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    public void canTargetEntity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (!SculkAttacker.testAttackable(entity)) cir.setReturnValue(false);
    }
}

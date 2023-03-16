package com.kaboomroads.sculkybits.effect;

import com.kaboomroads.sculkybits.block.entity.custom.SculkAttacker;
import com.kaboomroads.sculkybits.util.ModDamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class SculkEffect extends MobEffect {
    protected SculkEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.level.isClientSide && SculkAttacker.testAttackable(livingEntity)) {
            float multiplier = amplifier * 0.5f + 1;
            livingEntity.hurt(ModDamageSource.SCULK, 5 * multiplier);
        }
        super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int ticks, int amplifier) {
        return ticks == 1;
    }
}

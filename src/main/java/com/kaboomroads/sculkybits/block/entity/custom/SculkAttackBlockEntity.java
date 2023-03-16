package com.kaboomroads.sculkybits.block.entity.custom;

import com.kaboomroads.sculkybits.util.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class SculkAttackBlockEntity extends BlockEntity implements SculkAttacker {
    public int animationState = 0;

    public SculkAttackBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    @Override
    public double getLevelX() {
        return (double) worldPosition.getX() + 0.5D;
    }

    @Override
    public double getLevelY() {
        return (double) worldPosition.getY() + 0.5D;
    }

    @Override
    public double getLevelZ() {
        return (double) worldPosition.getZ() + 0.5D;
    }

    static void incrementAnimation(SculkAttackBlockEntity sculkAttacker, int amount) {
        sculkAttacker.setAnimationState(MathUtils.loopClamp(sculkAttacker.getAnimationState() + amount, 0, sculkAttacker.getAnimationLength()));
    }

    public abstract int getAnimationLength();

    public abstract int[] getAnimationDamageTicks();

    public int getAnimationState() {
        return animationState;
    }

    public void setAnimationState(int animationState) {
        this.animationState = animationState;
    }

    public abstract void updateState();
}

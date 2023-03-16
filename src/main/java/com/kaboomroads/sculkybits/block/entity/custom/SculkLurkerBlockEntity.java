package com.kaboomroads.sculkybits.block.entity.custom;

import com.kaboomroads.sculkybits.block.custom.SculkLurkerBlock;
import com.kaboomroads.sculkybits.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SculkLurkerBlockEntity extends SculkBiterBlockEntity {
    public boolean hidden = true;

    public SculkLurkerBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SCULK_LURKER.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, SculkLurkerBlockEntity blockEntity) {
        tickAnimation(level, blockPos, blockState, blockEntity);
    }

    public void updateState() {
        BlockState blockState = getBlockState();
        BlockPos blockPos = getBlockPos();
        switch (getAnimationState()) {
            case 0 -> level.setBlock(blockPos, blockState.setValue(SculkLurkerBlock.ANIMATION_STATE, hidden ? 0 : 1), 3);
            case 1, 6 -> level.setBlock(blockPos, blockState.setValue(SculkLurkerBlock.ANIMATION_STATE, 2), 3);
            case 2, 5 -> level.setBlock(blockPos, blockState.setValue(SculkLurkerBlock.ANIMATION_STATE, 3), 3);
            case 3, 4 -> level.setBlock(blockPos, blockState.setValue(SculkLurkerBlock.ANIMATION_STATE, 4), 3);
            default -> level.setBlock(blockPos, blockState.setValue(SculkLurkerBlock.ANIMATION_STATE, 1), 3);
        }
    }

    @Override
    public void tickEnd(Level level, BlockPos blockPos, BlockState blockState, boolean found) {
        hidden = !found;
    }

    @Override
    public int getAnimationLength() {
        return 19;
    }

    @Override
    public int[] getAnimationDamageTicks() {
        return new int[]{0};
    }
}

package com.kaboomroads.sculkybits.block.entity.custom;

import com.kaboomroads.sculkybits.block.custom.SculkAbsorberBlock;
import com.kaboomroads.sculkybits.block.custom.SculkStabberBlock;
import com.kaboomroads.sculkybits.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SculkStabberBlockEntity extends SculkBiterBlockEntity {
    public SculkStabberBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SCULK_STABBER.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, SculkStabberBlockEntity blockEntity) {
        tickAnimation(level, blockPos, blockState, blockEntity);
    }

    @Override
    public void updateState() {
        BlockState blockState = getBlockState();
        BlockPos blockPos = getBlockPos();
        switch (getAnimationState()) {
            case 1, 6 -> level.setBlock(blockPos, blockState.setValue(SculkStabberBlock.ANIMATION_STATE, 1), 3);
            case 2, 5 -> level.setBlock(blockPos, blockState.setValue(SculkStabberBlock.ANIMATION_STATE, 2), 3);
            case 3, 4 -> level.setBlock(blockPos, blockState.setValue(SculkStabberBlock.ANIMATION_STATE, 3), 3);
            case 10, 16 -> level.setBlock(blockPos, blockState.setValue(SculkStabberBlock.ANIMATION_STATE, 4), 3);
            case 12, 15 -> level.setBlock(blockPos, blockState.setValue(SculkStabberBlock.ANIMATION_STATE, 5), 3);
            case 13, 14 -> level.setBlock(blockPos, blockState.setValue(SculkStabberBlock.ANIMATION_STATE, 6), 3);
            case 20, 26 -> level.setBlock(blockPos, blockState.setValue(SculkStabberBlock.ANIMATION_STATE, 7), 3);
            case 22, 25 -> level.setBlock(blockPos, blockState.setValue(SculkStabberBlock.ANIMATION_STATE, 8), 3);
            case 23, 24 -> level.setBlock(blockPos, blockState.setValue(SculkStabberBlock.ANIMATION_STATE, 9), 3);
            case 30, 36 -> level.setBlock(blockPos, blockState.setValue(SculkStabberBlock.ANIMATION_STATE, 10), 3);
            case 32, 35 -> level.setBlock(blockPos, blockState.setValue(SculkStabberBlock.ANIMATION_STATE, 11), 3);
            case 33, 34 -> level.setBlock(blockPos, blockState.setValue(SculkStabberBlock.ANIMATION_STATE, 12), 3);
            default -> level.setBlock(blockPos, blockState.setValue(SculkStabberBlock.ANIMATION_STATE, 0), 3);
        }
    }

    @Override
    public VoxelShape getAttackShape() {
        BlockState blockState = getBlockState();
        Direction direction = blockState.getValue(SculkAbsorberBlock.FACING);
        return switch (direction) {
            case UP -> Block.box(2.0D, 16.0D, 2.0D, 14.0D, 28.0D, 14.0D);
            case DOWN -> Block.box(2.0D, -12.0D, 2.0D, 14.0D, 0.0D, 14.0D);
            case EAST -> Block.box(16.0D, 2.0D, 2.0D, 28.0D, 14.0D, 14.0D);
            case WEST -> Block.box(-12.0D, 2.0D, 2.0D, 0.0D, 14.0D, 14.0D);
            case NORTH -> Block.box(2.0D, 2.0D, -12.0D, 14.0D, 14.0D, 0.0D);
            case SOUTH -> Block.box(2.0D, 2.0D, 16.0D, 14.0D, 14.0D, 28.0D);
        };
    }

    @Override
    public int getAnimationLength() {
        return 39;
    }

    @Override
    public int[] getAnimationDamageTicks() {
        return new int[]{0, 10, 20, 30};
    }
}

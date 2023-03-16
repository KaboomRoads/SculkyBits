package com.kaboomroads.sculkybits.block.entity.custom;

import com.kaboomroads.sculkybits.block.custom.SculkAbsorberBlock;
import com.kaboomroads.sculkybits.block.entity.ModBlockEntities;
import com.kaboomroads.sculkybits.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SculkAbsorberBlockEntity extends SculkBiterBlockEntity {
    public final SculkSpreader sculkSpreader = SculkSpreader.createLevelSpreader();

    public SculkAbsorberBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SCULK_ABSORBER.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, SculkAbsorberBlockEntity blockEntity) {
        tickAnimation(level, blockPos, blockState, blockEntity);
    }

    @Override
    public void tickOnFound(Level level, BlockPos blockPos, BlockState blockState, LivingEntity entity) {
        if (level instanceof ServerLevel serverLevel && level.getBlockEntity(blockPos) instanceof SculkAbsorberBlockEntity)
            Utils.spreadSculk(new BlockPos(entity.getBlockX(), entity.getBlockY(), entity.getBlockZ()), serverLevel, RandomSource.create(), (entity instanceof Player player && player.experienceLevel > 10) ? player.experienceLevel : 5, 1, 1, 3, sculkSpreader, true);
    }

    @Override
    public void updateState() {
        BlockState blockState = getBlockState();
        BlockPos blockPos = getBlockPos();
        switch (getAnimationState()) {
            case 1, 6 -> level.setBlock(blockPos, blockState.setValue(SculkAbsorberBlock.ANIMATION_STATE, 1), 3);
            case 2, 5 -> level.setBlock(blockPos, blockState.setValue(SculkAbsorberBlock.ANIMATION_STATE, 2), 3);
            case 3, 4 -> level.setBlock(blockPos, blockState.setValue(SculkAbsorberBlock.ANIMATION_STATE, 3), 3);
            default -> level.setBlock(blockPos, blockState.setValue(SculkAbsorberBlock.ANIMATION_STATE, 0), 3);
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
        return 19;
    }

    @Override
    public int[] getAnimationDamageTicks() {
        return new int[]{0};
    }
}

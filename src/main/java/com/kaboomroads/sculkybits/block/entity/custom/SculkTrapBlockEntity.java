package com.kaboomroads.sculkybits.block.entity.custom;

import com.kaboomroads.sculkybits.block.ModBlocks;
import com.kaboomroads.sculkybits.block.custom.SculkCageBlock;
import com.kaboomroads.sculkybits.block.custom.SculkTrapBlock;
import com.kaboomroads.sculkybits.block.entity.ModBlockEntities;
import com.kaboomroads.sculkybits.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class SculkTrapBlockEntity extends SculkBiterBlockEntity {
    public SculkTrapBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SCULK_TRAP.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, SculkTrapBlockEntity blockEntity) {
        tickAnimation(level, blockPos, blockState, blockEntity);
    }

    public void updateState() {
        BlockState blockState = getBlockState();
        BlockPos blockPos = getBlockPos();
        switch (getAnimationState()) {
            case 1, 6 -> level.setBlock(blockPos, blockState.setValue(SculkTrapBlock.ANIMATION_STATE, 1), 3);
            case 2, 5 -> level.setBlock(blockPos, blockState.setValue(SculkTrapBlock.ANIMATION_STATE, 2), 3);
            case 3, 4 -> level.setBlock(blockPos, blockState.setValue(SculkTrapBlock.ANIMATION_STATE, 3), 3);
            default -> level.setBlock(blockPos, blockState.setValue(SculkTrapBlock.ANIMATION_STATE, 0), 3);
        }
    }

    @Override
    public void tickStart(Level level, BlockPos blockPos, BlockState blockState, List<? extends Entity> entities) {
        if (level.isClientSide) return;
        if (!entities.isEmpty() && getAnimationState() == 0) {
            BlockPos above = blockPos.above();
            boolean attackable = false;
            for (Entity entity : entities)
                if (entity instanceof LivingEntity livingEntity && Utils.entityIsDamageable(livingEntity) && SculkAttacker.testAttackable(livingEntity)) {
                    attackable = true;
                    livingEntity.teleportTo(getLevelX(), above.getY(), getLevelZ());
                    break;
                }
            BlockState aboveState = level.getBlockState(above);
            if (attackable && (aboveState.is(Blocks.WATER) || aboveState.isAir())) {
                BlockState cageState = ModBlocks.SCULK_CAGE.get().defaultBlockState().setValue(SculkCageBlock.WATERLOGGED, aboveState.is(Blocks.WATER));
                level.setBlock(above, cageState, 3);
            }
        }
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

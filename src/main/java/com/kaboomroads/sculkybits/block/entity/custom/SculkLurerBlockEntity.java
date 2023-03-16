package com.kaboomroads.sculkybits.block.entity.custom;

import com.kaboomroads.sculkybits.block.custom.SculkLurerBlock;
import com.kaboomroads.sculkybits.block.entity.ModBlockEntities;
import com.kaboomroads.sculkybits.gamerule.ModGameRules;
import com.kaboomroads.sculkybits.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SculkLurerBlockEntity extends SculkBiterBlockEntity {
    public static final VoxelShape ATTACK_SHAPE = Block.box(1.0D, 8.0D, 1.0D, 15.0D, 10.0D, 15.0D);

    public SculkLurerBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SCULK_LURER.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, SculkLurerBlockEntity blockEntity) {
        if (level.isClientSide) return;
        tickAnimation(level, blockPos, blockState, blockEntity);
        float range = ModGameRules.RULE_SCULK_LURER_RANGE != null ? level.getGameRules().getRule(ModGameRules.RULE_SCULK_LURER_RANGE).get() : 5;
        if (range > 0 && level instanceof ServerLevel serverLevel)
            for (Entity entity : serverLevel.getEntities().getAll()) {
                Vec3 vecPos = new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
                Vec3 entityLoc = entity.getPosition(1);
                if (Utils.distanceSquared(vecPos, entityLoc) <= range * range) {
                    if (entity instanceof PathfinderMob pathfinderMob && SculkAttacker.testAttackable(entity)) {
                        Vec3 offset = entityLoc.subtract(vecPos);
                        if (offset.lengthSqr() != 0)
                            offset = offset.normalize().multiply(3, 1, 3);
                        pathfinderMob.getNavigation().moveTo(blockPos.getX() - offset.x, blockPos.getY() - offset.y, blockPos.getZ() - offset.z, 1);
                    }
                }
            }
    }

    @Override
    public VoxelShape getAttackShape() {
        return ATTACK_SHAPE;
    }

    @Override
    public void updateState() {
        BlockState blockState = getBlockState();
        BlockPos blockPos = getBlockPos();
        switch (getAnimationState()) {
            case 1, 6 -> level.setBlock(blockPos, blockState.setValue(SculkLurerBlock.ANIMATION_STATE, 1), 3);
            case 2, 5 -> level.setBlock(blockPos, blockState.setValue(SculkLurerBlock.ANIMATION_STATE, 2), 3);
            case 3, 4 -> level.setBlock(blockPos, blockState.setValue(SculkLurerBlock.ANIMATION_STATE, 3), 3);
            case 11, 16 -> level.setBlock(blockPos, blockState.setValue(SculkLurerBlock.ANIMATION_STATE, 4), 3);
            case 12, 15 -> level.setBlock(blockPos, blockState.setValue(SculkLurerBlock.ANIMATION_STATE, 5), 3);
            case 13, 14 -> level.setBlock(blockPos, blockState.setValue(SculkLurerBlock.ANIMATION_STATE, 6), 3);
            default -> level.setBlock(blockPos, blockState.setValue(SculkLurerBlock.ANIMATION_STATE, 0), 3);
        }
    }

    @Override
    public int getAnimationLength() {
        return 19;
    }

    @Override
    public int[] getAnimationDamageTicks() {
        return new int[]{0, 10};
    }
}

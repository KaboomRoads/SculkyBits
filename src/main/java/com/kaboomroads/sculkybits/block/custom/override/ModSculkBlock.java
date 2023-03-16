package com.kaboomroads.sculkybits.block.custom.override;

import com.kaboomroads.sculkybits.block.ModBlocks;
import com.kaboomroads.sculkybits.block.custom.SculkAbsorberBlock;
import com.kaboomroads.sculkybits.block.custom.SculkStabberBlock;
import com.kaboomroads.sculkybits.block.entity.custom.SculkAttacker;
import com.kaboomroads.sculkybits.fluid.ModFluids;
import com.kaboomroads.sculkybits.util.Pair;
import com.kaboomroads.sculkybits.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class ModSculkBlock extends SculkBlock {
    public static final BooleanProperty IS_NATURAL = BooleanProperty.create("natural");

    public ModSculkBlock(Properties properties) {
        super(properties);
    }

    public int chargeMultiplier() {
        return 5;
    }

    public int costOverride(Level level) {
        return costOverride();
    }

    public int costOverride() {
        return 0;
    }

    public boolean overrideCost(Level level) {
        return overrideCost();
    }

    public boolean overrideCost() {
        return false;
    }

    @Override
    public int attemptUseCharge(SculkSpreader.ChargeCursor cursor, @NotNull LevelAccessor levelAccessor, @NotNull BlockPos pos, @NotNull RandomSource source, @NotNull SculkSpreader sculkSpreader, boolean p_222078_) {
        int i = cursor.getCharge();
        Level level = null;
        if (levelAccessor instanceof Level l)
            level = l;
        if (i != 0 && source.nextInt(sculkSpreader.chargeDecayRate()) == 0) {
            BlockPos blockpos = cursor.getPos();
            boolean flag = blockpos.closerThan(pos, sculkSpreader.noGrowthRadius());
            if (!flag && canPlaceGrowth(levelAccessor, blockpos)) {
                int j = sculkSpreader.growthSpawnCost();
                if (level != null) {
                    if (overrideCost(level))
                        j = costOverride(level);
                } else if (overrideCost())
                    j = costOverride();
                if (j <= 0 || source.nextInt(j) < i * chargeMultiplier()) {
                    BlockPos blockpos1 = blockpos.above();
                    Pair<BlockState, BlockPos> pair = this.getRandomGrowthState(levelAccessor, blockpos1, source, sculkSpreader.isWorldGeneration());
                    BlockState blockstate = pair.first;
                    blockpos1 = pair.second;
                    levelAccessor.setBlock(blockpos1, blockstate, 3);
                    levelAccessor.playSound(null, blockpos, blockstate.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                return Math.max(0, i - j);
            } else
                return source.nextInt(sculkSpreader.additionalDecayRate()) != 0 ? i : i - (flag ? 1 : getDecayPenalty(sculkSpreader, blockpos, pos, i));
        } else return i;
    }

    public static boolean canPlaceGrowth(LevelAccessor levelAccessor, BlockPos pos) {
        BlockState blockstate = levelAccessor.getBlockState(pos.above());
        if (blockstate.isAir() || blockstate.is(Blocks.WATER) && blockstate.getFluidState().is(Fluids.WATER)) {
            int i = 0;
            for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 2, 1))) {
                BlockState blockstate1 = levelAccessor.getBlockState(blockpos);
                if (blockstate1.is(Blocks.SCULK_SENSOR) || blockstate1.is(Blocks.SCULK_SHRIEKER) || blockstate1.is(ModBlocks.SCULK_NEST.get()) || blockstate1.getBlock() instanceof SculkAttacker)
                    ++i;
                if (i > 2) return false;
            }
            return true;
        } else return false;
    }

    public static int getDecayPenalty(SculkSpreader sculkSpreader, BlockPos pos, BlockPos pos2, int p_222083_) {
        int i = sculkSpreader.noGrowthRadius();
        float f = Mth.square((float) Math.sqrt(pos.distSqr(pos2)) - (float) i);
        int j = Mth.square(24 - i);
        float f1 = Math.min(1.0F, f / (float) j);
        return Math.max(1, (int) ((float) p_222083_ * f1 * 0.5F));
    }

    public Pair<BlockState, BlockPos> getRandomGrowthState(LevelAccessor levelAccessor, BlockPos pos, RandomSource source, boolean isWorldGen) {
        BlockState blockstate = Blocks.SCULK_SENSOR.defaultBlockState();
        int nextInt = source.nextInt(52);
        if (isWorldGen) nextInt = source.nextInt(58);
        switch (nextInt) {
            case 0, 1, 2, 3, 4, 5 -> {
                blockstate = ModBlocks.SCULK_JAW.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(source)).setValue(ModSculkBlock.IS_NATURAL, true);
                pos = pos.below();
            }
            case 6, 7, 8, 9, 10, 11 -> {
                blockstate = ModBlocks.SCULK_LURKER.get().defaultBlockState().setValue(ModSculkBlock.IS_NATURAL, true);
                pos = pos.below();
            }
            case 12, 13, 14, 15, 16 -> {
                blockstate = ModBlocks.SCULK_TRAP.get().defaultBlockState().setValue(ModSculkBlock.IS_NATURAL, true);
                pos = pos.below();
            }
            case 17, 18, 19, 20 -> blockstate = isWorldGen ? Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON, true) : Blocks.SCULK_SHRIEKER.defaultBlockState();
            case 21 -> blockstate = ModBlocks.SCULK_RIBS.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(source));
            case 22, 23 -> blockstate = ModBlocks.SCULK_BONES.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(source));
            case 24, 25, 26, 27, 28, 29, 30 -> {
                pos = pos.below();
                blockstate = SculkStabberBlock.getStateForGeneration(levelAccessor, ModBlocks.SCULK_STABBER.get().defaultBlockState(), pos).setValue(ModSculkBlock.IS_NATURAL, true);
            }
            case 31, 32, 33, 34 -> {
                pos = pos.below();
                if (Utils.spawnNext(levelAccessor, blockstate, pos)) {
                    blockstate = ModFluids.SCULK_FLUID_BLOCK.get().defaultBlockState();
                } else blockstate = ModBlocks.SCULK_FLESH.get().defaultBlockState();
            }
            case 35 -> blockstate = ModBlocks.SCULK_LURER.get().defaultBlockState().setValue(ModSculkBlock.IS_NATURAL, true);
            case 36, 37, 38, 39, 40, 41 -> {
                pos = pos.below(2);
                BlockState state = levelAccessor.getBlockState(pos);
                if (state.isAir() || state.is(ModBlocks.SCULK_VEIN.get()) || state.is(Blocks.WATER))
                    blockstate = ModBlocks.SCULK_FEELER.get().defaultBlockState().setValue(ModSculkBlock.IS_NATURAL, true);
            }
            case 42, 43 -> {
                blockstate = ModBlocks.SCULK_NEST.get().defaultBlockState().setValue(ModSculkBlock.IS_NATURAL, true);
                pos = pos.below();
            }
            case 44, 45, 46, 47, 48, 49 -> {
                if (isWorldGen) {
                    pos = pos.below();
                    blockstate = SculkAbsorberBlock.getStateForGeneration(levelAccessor, ModBlocks.SCULK_ABSORBER.get().defaultBlockState(), pos).setValue(ModSculkBlock.IS_NATURAL, true);
                }
            }
        }
        return new Pair<>(blockstate.hasProperty(BlockStateProperties.WATERLOGGED) && !levelAccessor.getFluidState(pos).isEmpty() ? blockstate.setValue(BlockStateProperties.WATERLOGGED, true) : blockstate, pos);
    }
}

package com.kaboomroads.sculkybits.block.custom.override;

import com.kaboomroads.sculkybits.block.ModBlocks;
import com.kaboomroads.sculkybits.block.custom.SculkFeelerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class ModSculkVeinBlock extends SculkVeinBlock {
    private final MultifaceSpreader veinSpreader = new MultifaceSpreader(new ModSculkVeinSpreaderConfig(MultifaceSpreader.DEFAULT_SPREAD_ORDER));
    private final MultifaceSpreader sameSpaceSpreader = new MultifaceSpreader(new ModSculkVeinSpreaderConfig(MultifaceSpreader.SpreadType.SAME_POSITION));

    @NotNull
    @Override
    public MultifaceSpreader getSpreader() {
        return this.veinSpreader;
    }

    @NotNull
    @Override
    public MultifaceSpreader getSameSpaceSpreader() {
        return this.sameSpaceSpreader;
    }

    public ModSculkVeinBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull Direction direction) {
        if (!ModSculkVeinSpreaderConfig.checkCanPlace(blockGetter.getBlockState(pos.relative(direction))))
            return Blocks.AIR.defaultBlockState();
        return super.getStateForPlacement(blockState, blockGetter, pos, direction);
    }

    @Nullable
    public BlockState getStateForPlacement(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull Direction direction, @NotNull BlockPlaceContext context) {
        if (context.getPlayer() == null)
            if (!ModSculkVeinSpreaderConfig.checkCanPlace(blockGetter.getBlockState(pos.relative(direction))))
                return Blocks.AIR.defaultBlockState();
        return super.getStateForPlacement(blockState, blockGetter, pos, direction);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        return Arrays.stream(context.getNearestLookingDirections()).map((p_153865_) -> this.getStateForPlacement(blockstate, level, blockpos, p_153865_, context)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    public class ModSculkVeinSpreaderConfig extends MultifaceSpreader.DefaultSpreaderConfig {
        private final MultifaceSpreader.SpreadType[] spreadTypes;

        public ModSculkVeinSpreaderConfig(MultifaceSpreader.SpreadType... spreadTypes) {
            super(ModSculkVeinBlock.this);
            this.spreadTypes = spreadTypes;
        }

        public static boolean checkCanPlace(BlockState state) {
            return !(state.getBlock() instanceof SculkBehaviour);
        }

        public boolean stateCanBeReplaced(BlockGetter blockGetter, @NotNull BlockPos pos1, BlockPos pos2, @NotNull Direction direction, @NotNull BlockState blockState) {
            BlockState blockState2 = blockGetter.getBlockState(pos2.relative(direction));
            if (checkCanPlace(blockGetter.getBlockState(pos2)) && checkCanPlace(blockState2) && !blockState2.is(Blocks.SCULK) && !blockState2.is(Blocks.SCULK_CATALYST) && !blockState2.is(Blocks.MOVING_PISTON)) {
                if (pos1.distManhattan(pos2) == 2) {
                    BlockPos blockpos = pos1.relative(direction.getOpposite());
                    if (blockGetter.getBlockState(blockpos).isFaceSturdy(blockGetter, blockpos, direction))
                        return false;
                }
                FluidState fluidstate = blockState.getFluidState();
                if (!fluidstate.isEmpty() && !fluidstate.is(Fluids.WATER))
                    return false;
                else {
                    Material material = blockState.getMaterial();
                    if (material == Material.FIRE) return false;
                    else
                        return material.isReplaceable() || super.stateCanBeReplaced(blockGetter, pos1, pos2, direction, blockState);
                }
            } else return false;
        }

        public MultifaceSpreader.SpreadType @NotNull [] getSpreadTypes() {
            return this.spreadTypes;
        }

        public boolean isOtherBlockValidAsSource(BlockState blockState) {
            return !blockState.is(Blocks.SCULK_VEIN);
        }
    }

    @Override
    public boolean attemptPlaceSculk(SculkSpreader sculkSpreader, LevelAccessor levelAccessor, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
        BlockState blockstate = levelAccessor.getBlockState(blockPos);
        TagKey<Block> tagkey = sculkSpreader.replaceableBlocks();
        for (Direction direction : Direction.allShuffled(randomSource)) {
            if (hasFace(blockstate, direction)) {
                BlockPos blockpos = blockPos.relative(direction);
                BlockState blockstate1 = levelAccessor.getBlockState(blockpos);
                if (blockstate1.is(tagkey)) {
                    float ran = randomSource.nextFloat();
                    BlockState blockstate2 = Blocks.SCULK.defaultBlockState();
                    BlockState above = levelAccessor.getBlockState(blockpos.above());
                    if (ran <= 0.025 && above.isAir())
                        blockstate2 = randomSource.nextBoolean() ? ModBlocks.SCULK_LURKER.get().defaultBlockState().setValue(ModSculkBlock.IS_NATURAL, true) : ModBlocks.SCULK_JAW.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(randomSource)).setValue(ModSculkBlock.IS_NATURAL, true);
                    else if (ran <= 0.05)
                        blockstate2 = ModBlocks.SCULK_GROWTH.get().defaultBlockState();
                    else if (ran <= 0.2) blockstate2 = ModBlocks.SCULK_FLESH.get().defaultBlockState();
                    levelAccessor.setBlock(blockpos, blockstate2, 3);
                    if (ran <= 0.05) {
                        BlockPos pos = blockpos.below();
                        BlockState state = levelAccessor.getBlockState(pos);
                        boolean water = state.is(Blocks.WATER);
                        if (state.isAir() || state.is(ModBlocks.SCULK_VEIN.get()) || water)
                            levelAccessor.setBlock(pos, ModBlocks.SCULK_FEELER.get().defaultBlockState().setValue(ModSculkBlock.IS_NATURAL, true).setValue(SculkFeelerBlock.WATERLOGGED, water), 3);
                    }
                    Block.pushEntitiesUp(blockstate1, blockstate2, levelAccessor, blockpos);
                    levelAccessor.playSound(null, blockpos, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 1.0F, 1.0F);
                    this.veinSpreader.spreadAll(blockstate2, levelAccessor, blockpos, sculkSpreader.isWorldGeneration());
                    Direction direction1 = direction.getOpposite();
                    for (Direction direction2 : DIRECTIONS) {
                        if (direction2 != direction1) {
                            BlockPos blockpos1 = blockpos.relative(direction2);
                            BlockState blockstate3 = levelAccessor.getBlockState(blockpos1);
                            if (blockstate3.is(this))
                                this.onDischarged(levelAccessor, blockstate3, blockpos1, randomSource);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onDischarged(@NotNull LevelAccessor levelAccessor, BlockState blockState, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
        if (blockState.is(this)) {
            for (Direction direction : DIRECTIONS) {
                BooleanProperty booleanproperty = getFaceProperty(direction);
                BlockState state = levelAccessor.getBlockState(blockPos.relative(direction));
                if (blockState.getValue(booleanproperty) && !ModSculkVeinSpreaderConfig.checkCanPlace(state))
                    blockState = blockState.setValue(booleanproperty, false);
            }
            if (!hasAnyFace(blockState)) {
                FluidState fluidstate = levelAccessor.getFluidState(blockPos);
                blockState = (fluidstate.isEmpty() ? Blocks.AIR : Blocks.WATER).defaultBlockState();
            }
            levelAccessor.setBlock(blockPos, blockState, 3);
        }
    }
}

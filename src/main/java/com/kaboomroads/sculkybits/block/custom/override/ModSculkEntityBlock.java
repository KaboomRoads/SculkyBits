package com.kaboomroads.sculkybits.block.custom.override;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public abstract class ModSculkEntityBlock extends BaseEntityBlock implements SculkBehaviour {
    public static final BooleanProperty IS_NATURAL = ModSculkBlock.IS_NATURAL;
    private final IntProvider xpRange;

    public ModSculkEntityBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(IS_NATURAL, false));
        this.xpRange = ConstantInt.of(0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(IS_NATURAL);
    }

    public ModSculkEntityBlock(Properties properties, IntProvider xpRange) {
        super(properties);
        this.xpRange = xpRange;
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.level.LevelReader level, RandomSource randomSource, BlockPos pos, int fortuneLevel, int silkTouchLevel) {
        return silkTouchLevel == 0 ? this.xpRange.sample(randomSource) : 0;
    }

    @Override
    public byte getSculkSpreadDelay() {
        return DEFAULT.getSculkSpreadDelay();
    }

    @Override
    public void onDischarged(@NotNull LevelAccessor levelAccessor, @NotNull BlockState blockState, @NotNull BlockPos pos, @NotNull RandomSource source) {
        DEFAULT.onDischarged(levelAccessor, blockState, pos, source);
    }

    @Override
    public boolean depositCharge(@NotNull LevelAccessor levelAccessor, @NotNull BlockPos pos, @NotNull RandomSource source) {
        return DEFAULT.depositCharge(levelAccessor, pos, source);
    }

    @Override
    public boolean attemptSpreadVein(@NotNull LevelAccessor levelAccessor, @NotNull BlockPos pos, @NotNull BlockState blockState, @Nullable Collection<Direction> directions, boolean p_222038_) {
        return DEFAULT.attemptSpreadVein(levelAccessor, pos, blockState, directions, p_222038_);
    }

    @Override
    public boolean canChangeBlockStateOnSpread() {
        return DEFAULT.canChangeBlockStateOnSpread();
    }

    @Override
    public int updateDecayDelay(int p_222045_) {
        return DEFAULT.updateDecayDelay(p_222045_);
    }

    @Override
    public int attemptUseCharge(SculkSpreader.@NotNull ChargeCursor cursor, @NotNull LevelAccessor levelAccessor, @NotNull BlockPos pos, @NotNull RandomSource source, @NotNull SculkSpreader spreader, boolean p_222044_) {
        return DEFAULT.attemptUseCharge(cursor, levelAccessor, pos, source, spreader, p_222044_);
    }
}

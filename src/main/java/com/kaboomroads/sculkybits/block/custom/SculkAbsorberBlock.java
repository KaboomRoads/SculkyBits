package com.kaboomroads.sculkybits.block.custom;

import com.kaboomroads.sculkybits.block.custom.override.ModSculkEntityBlock;
import com.kaboomroads.sculkybits.block.entity.ModBlockEntities;
import com.kaboomroads.sculkybits.block.entity.custom.SculkAbsorberBlockEntity;
import com.kaboomroads.sculkybits.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SculkAbsorberBlock extends ModSculkEntityBlock {
    public static final IntegerProperty ANIMATION_STATE = IntegerProperty.create("animation_state", 0, 3);
    public static final DirectionProperty FACING = DirectionalBlock.FACING;

    public static BlockState getStateForGeneration(LevelAccessor levelAccessor, BlockState blockState, BlockPos pos) {
        Direction direction = Utils.getRandomDirectionDependingOnBlocksAroundTheBlockPosition(levelAccessor, pos, FACING.getPossibleValues(), (state) -> !state.getMaterial().isSolidBlocking());
        if (direction == null)
            direction = Direction.UP;
        return blockState.setValue(FACING, direction);
    }

    public SculkAbsorberBlock(Properties properties) {
        this(properties, ConstantInt.of(5));
    }

    public SculkAbsorberBlock(Properties properties, IntProvider xpRange) {
        super(properties, xpRange);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(ANIMATION_STATE, 0).setValue(IS_NATURAL, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, ANIMATION_STATE);
    }

    @NotNull
    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @NotNull
    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @NotNull
    @Override
    public RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState blockState) {
        return new SculkAbsorberBlockEntity(pos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.SCULK_ABSORBER.get(), SculkAbsorberBlockEntity::tick);
    }
}

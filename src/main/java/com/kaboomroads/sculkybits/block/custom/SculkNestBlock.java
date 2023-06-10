package com.kaboomroads.sculkybits.block.custom;

import com.kaboomroads.sculkybits.block.custom.override.ModSculkEntityBlock;
import com.kaboomroads.sculkybits.block.entity.ModBlockEntities;
import com.kaboomroads.sculkybits.block.entity.custom.SculkNestBlockEntity;
import com.kaboomroads.sculkybits.entity.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SculkNestBlock extends ModSculkEntityBlock {
    public static final BooleanProperty INACTIVE = BooleanProperty.create("inactive");

    public SculkNestBlock(Properties properties) {
        this(properties, ConstantInt.of(5));
    }

    public SculkNestBlock(Properties properties, IntProvider xpRange) {
        super(properties, xpRange);
        registerDefaultState(stateDefinition.any().setValue(IS_NATURAL, false).setValue(INACTIVE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(INACTIVE);
    }

    @Override
    public void tick(BlockState blockState, @NotNull ServerLevel level, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
        if (blockState.getValue(INACTIVE)) level.setBlock(blockPos, blockState.setValue(INACTIVE, false), 3);
    }

    @NotNull
    @Override
    public VoxelShape getCollisionShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if (context instanceof EntityCollisionContext entityCollisionContext) {
            Entity entity = entityCollisionContext.getEntity();
            if (entity != null && entity.getType() == ModEntityTypes.SCULK_CRAWLER.get()) return Shapes.empty();
        }
        return super.getCollisionShape(blockState, blockGetter, pos, context);
    }

    @NotNull
    @Override
    public RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState blockState) {
        return new SculkNestBlockEntity(pos, blockState);
    }

    @Nullable
    public <T extends BlockEntity> GameEventListener getListener(@NotNull ServerLevel level, @NotNull T blockEntity) {
        return blockEntity instanceof SculkNestBlockEntity sculkNestBlockEntity ? sculkNestBlockEntity.getListener() : null;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> blockEntityType) {
        return !level.isClientSide ? BaseEntityBlock.createTickerHelper(blockEntityType, ModBlockEntities.SCULK_NEST.get(), (l, blockPos, s, blockEntity) -> VibrationSystem.Ticker.tick(l, blockEntity.getVibrationData(), blockEntity.getVibrationUser())) : null;
    }
}

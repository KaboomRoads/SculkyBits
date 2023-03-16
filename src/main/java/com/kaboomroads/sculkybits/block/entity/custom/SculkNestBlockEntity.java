package com.kaboomroads.sculkybits.block.entity.custom;

import com.kaboomroads.sculkybits.block.custom.SculkNestBlock;
import com.kaboomroads.sculkybits.block.entity.ModBlockEntities;
import com.kaboomroads.sculkybits.entity.ModEntityTypes;
import com.kaboomroads.sculkybits.entity.custom.SculkCrawler;
import com.kaboomroads.sculkybits.gamerule.ModGameRules;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public class SculkNestBlockEntity extends BlockEntity implements VibrationListener.VibrationListenerConfig {
    private static final Logger LOGGER = LogUtils.getLogger();
    private VibrationListener listener = new VibrationListener(new BlockPositionSource(worldPosition), 8, this);

    public static VoxelShape getRadius(float radius) {
        return Block.box(-radius * 16, -radius * 16, -radius * 16, radius * 16, radius * 16, radius * 16);
    }

    public SculkNestBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SCULK_NEST.get(), pos, blockState);
    }

    public void trySpawn(ServerLevel level, @Nullable ServerPlayer player) {
        BlockPos pos = getBlockPos();
        if (player == null || getBlockState().getValue(SculkNestBlock.INACTIVE)) return;
        SculkCrawler crawler = ModEntityTypes.SCULK_CRAWLER.get().create(level);
        if (crawler == null) return;
        int i = 0;
        VoxelShape spiderRadius = getRadius(ModGameRules.RULE_SCULK_NEST_RANGE != null ? level.getGameRules().getRule(ModGameRules.RULE_SCULK_NEST_RANGE).get() : 10);
        int amount = ModGameRules.RULE_SCULK_NEST_LIMIT != null ? level.getGameRules().getRule(ModGameRules.RULE_SCULK_NEST_LIMIT).get() : 3;
        for (LivingEntity entity : SculkAttacker.getAttackEntities(level, spiderRadius, new Vec3(pos.getX(), pos.getY(), pos.getZ())))
            if (entity.getType() == ModEntityTypes.SCULK_CRAWLER.get() && ++i >= amount) return;
        level.setBlock(getBlockPos(), getBlockState().setValue(SculkNestBlock.INACTIVE, true), 2);
        level.scheduleTick(getBlockPos(), getBlockState().getBlock(), 200);
        crawler.setPos(new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5));
        level.addFreshEntity(crawler);
    }

    public VibrationListener getListener() {
        return listener;
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        if (tag.contains("listener", 10)) {
            VibrationListener.codec(this).parse(new Dynamic<>(NbtOps.INSTANCE, tag.getCompound("listener"))).resultOrPartial(LOGGER::error).ifPresent((p_222864_) -> listener = p_222864_);
        }

    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        VibrationListener.codec(this).encodeStart(NbtOps.INSTANCE, listener).resultOrPartial(LOGGER::error).ifPresent((p_222871_) -> tag.put("listener", p_222871_));
    }

    @NotNull
    @Override
    public TagKey<GameEvent> getListenableEvents() {
        return GameEventTags.SHRIEKER_CAN_LISTEN;
    }

    @Override
    public boolean shouldListen(@NotNull ServerLevel p_222856_, @NotNull GameEventListener p_222857_, @NotNull BlockPos p_222858_, @NotNull GameEvent p_222859_, @NotNull GameEvent.Context p_222860_) {
        return !isRemoved() && SculkShriekerBlockEntity.tryGetPlayer(p_222860_.sourceEntity()) != null;
    }

    @Override
    public void onSignalReceive(@NotNull ServerLevel p_222848_, @NotNull GameEventListener p_222849_, @NotNull BlockPos p_222850_, @NotNull GameEvent p_222851_, @Nullable Entity p_222852_, @Nullable Entity p_222853_, float p_222854_) {
        trySpawn(p_222848_, SculkShriekerBlockEntity.tryGetPlayer(p_222853_ != null ? p_222853_ : p_222852_));
    }
}

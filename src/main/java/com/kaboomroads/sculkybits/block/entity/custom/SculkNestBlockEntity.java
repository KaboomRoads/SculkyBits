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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public class SculkNestBlockEntity extends BlockEntity implements GameEventListener.Holder<VibrationSystem.Listener>, VibrationSystem {
    private static final Logger LOGGER = LogUtils.getLogger();
    private VibrationSystem.Data vibrationData = new VibrationSystem.Data();
    private final VibrationSystem.Listener vibrationListener = new VibrationSystem.Listener(this);
    private final VibrationSystem.User vibrationUser = this.createVibrationUser();

    public VibrationSystem.User createVibrationUser() {
        return new VibrationUser(getBlockPos());
    }

    public static VoxelShape getRadius(float radius) {
        return Block.box(-radius * 16, -radius * 16, -radius * 16, radius * 16, radius * 16, radius * 16);
    }

    public SculkNestBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SCULK_NEST.get(), pos, blockState);
    }

    public void trySpawn(Level level, @Nullable ServerPlayer player) {
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

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("listener", 10))
            VibrationSystem.Data.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, tag.getCompound("listener"))).resultOrPartial(LOGGER::error).ifPresent((data) -> {
                vibrationData = data;
            });
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        VibrationSystem.Data.CODEC.encodeStart(NbtOps.INSTANCE, this.vibrationData).resultOrPartial(LOGGER::error).ifPresent((p_222820_) -> {
            tag.put("listener", p_222820_);
        });
    }

    @NotNull
    @Override
    public Data getVibrationData() {
        return vibrationData;
    }

    @NotNull
    @Override
    public User getVibrationUser() {
        return vibrationUser;
    }

    @NotNull
    @Override
    public Listener getListener() {
        return vibrationListener;
    }

    protected class VibrationUser implements VibrationSystem.User {
        protected final BlockPos blockPos;
        private final PositionSource positionSource;

        public VibrationUser(BlockPos blockPos) {
            this.blockPos = blockPos;
            positionSource = new BlockPositionSource(blockPos);
        }

        @Override
        public int getListenerRadius() {
            return 8;
        }

        @NotNull
        @Override
        public TagKey<GameEvent> getListenableEvents() {
            return GameEventTags.SHRIEKER_CAN_LISTEN;
        }

        @Override
        public PositionSource getPositionSource() {
            return this.positionSource;
        }

        @Override
        public boolean canTriggerAvoidVibration() {
            return true;
        }

        @Override
        public boolean canReceiveVibration(ServerLevel p_282127_, BlockPos p_283268_, GameEvent p_282187_, @Nullable GameEvent.Context p_282856_) {
            return !isRemoved() && p_282856_ != null && SculkShriekerBlockEntity.tryGetPlayer(p_282856_.sourceEntity()) != null;
        }

        @Override
        public void onReceiveVibration(ServerLevel p_282851_, BlockPos p_281608_, GameEvent p_282979_, @Nullable Entity p_282123_, @Nullable Entity p_283090_, float p_283130_) {
            SculkNestBlockEntity.this.trySpawn(level, SculkShriekerBlockEntity.tryGetPlayer(p_283090_ != null ? p_283090_ : p_282123_));
        }

        @Override
        public void onDataChanged() {
            SculkNestBlockEntity.this.setChanged();
        }
    }
}

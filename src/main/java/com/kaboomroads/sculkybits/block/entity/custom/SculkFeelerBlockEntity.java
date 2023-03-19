package com.kaboomroads.sculkybits.block.entity.custom;

import com.kaboomroads.sculkybits.block.entity.ModBlockEntities;
import com.kaboomroads.sculkybits.networking.ModMessages;
import com.kaboomroads.sculkybits.networking.packet.ClientboundSculkFeelerExtendPacket;
import com.kaboomroads.sculkybits.networking.packet.ClientboundSculkFeelerRetractPacket;
import com.kaboomroads.sculkybits.util.MathUtils;
import com.kaboomroads.sculkybits.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class SculkFeelerBlockEntity extends BlockEntity implements SculkAttacker {
    public static final VoxelShape ATTACK_SHAPE = Block.box(4.0D, -64.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    public LivingEntity entity = null;
    public float length = 0;
    public float lengthO = 0;
    public float maxLength = 5;
    public int cooldownTicks = 0;
    public int maxCooldown = 100;
    public boolean extending = false;
    public boolean extendingO = false;

    public SculkFeelerBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SCULK_FEELER.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, SculkFeelerBlockEntity blockEntity) {
        blockEntity.lengthO = blockEntity.length;
        blockEntity.length = blockEntity.extending
                ? MathUtils.interpolateLinear(blockEntity.length, blockEntity.maxLength, 0.25f)
                : MathUtils.interpolateLinear(blockEntity.length, 0, 0.1f);
        if (!level.isClientSide) {
            boolean onCooldown = blockEntity.cooldownTicks > 0;
            if (!onCooldown) {
                List<LivingEntity> entities = SculkAttacker.getAttackEntities(level, blockEntity);
                LivingEntity livingEntity = null;
                for (LivingEntity entity : entities)
                    if (Utils.entityIsDamageable(entity) && SculkAttacker.testAttackable(entity)) {
                        livingEntity = entity;
                        break;
                    }
                if (livingEntity != null) blockEntity.entity = livingEntity;
            } else blockEntity.cooldownTicks--;
            if (blockEntity.entity != null) {
                if (onCooldown) blockEntity.entity = null;
                else if (blockEntity.length >= blockEntity.maxLength) {
                    Vec3 v = blockEntity.entity.getDeltaMovement();
                    blockEntity.entity.setDeltaMovement(v.x, v.y + 0.25, v.z);
                    SculkAttacker.sculkDamage(blockEntity.entity);
                    blockEntity.cooldownTicks = blockEntity.maxCooldown;
                    blockEntity.entity = null;
                } else {
                    Vec3 v = blockEntity.entity.getDeltaMovement();
                    blockEntity.entity.setDeltaMovement(v.x * 0.1, v.y + 0.1, v.z * 0.1);
                    blockEntity.entity.hurtMarked = true;
                }
            }
            blockEntity.extending = blockEntity.entity != null;
            if (blockEntity.extending != blockEntity.extendingO) {
                if (blockEntity.level instanceof ServerLevel serverLevel)
                    for (ServerPlayer player : serverLevel.players())
                        ModMessages.sendToClient(blockEntity.extending
                                        ? new ClientboundSculkFeelerExtendPacket(blockPos)
                                        : new ClientboundSculkFeelerRetractPacket(blockPos)
                                , player);
            }
            blockEntity.extendingO = blockEntity.extending;
        }
    }

    @Override
    public VoxelShape getAttackShape() {
        return ATTACK_SHAPE;
    }

    @Override
    public double getLevelX() {
        return (double) worldPosition.getX() + 0.5D;
    }

    @Override
    public double getLevelY() {
        return (double) worldPosition.getY() + 0.5D;
    }

    @Override
    public double getLevelZ() {
        return (double) worldPosition.getZ() + 0.5D;
    }
}

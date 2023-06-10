package com.kaboomroads.sculkybits.util;

import com.kaboomroads.sculkybits.fluid.ModFluids;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class Utils {
    public static int getHighestBlockYAt(Level level, int x, int z) {
        return level.getChunk(x >> 4, z >> 4).getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
    }

    public static BlockState getHighestBlockAt(Level level, int x, int z) {
        return level.getBlockState(new BlockPos(x, getHighestBlockYAt(level, x, z), z));
    }

    public static void maybeDisableShield(Mob mob, Player player, ItemStack itemStack1, ItemStack itemStack2) {
        if (!itemStack1.isEmpty() && !itemStack2.isEmpty() && itemStack1.getItem() instanceof AxeItem && itemStack2.is(Items.SHIELD)) {
            float f = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(mob) * 0.05F;
            if (mob.level().random.nextFloat() < f) {
                player.getCooldowns().addCooldown(Items.SHIELD, 100);
                mob.level().broadcastEntityEvent(player, (byte) 30);
            }
        }
    }

    public static <T> T rObject(List<T> list, RandomSource random) {
        return list.get(random.nextInt(list.size()));
    }

    public static boolean spawnNext(LevelAccessor levelAccessor, BlockState blockState, BlockPos pos) {
        List<Direction> directions = HorizontalDirectionalBlock.FACING.getPossibleValues().stream().toList();
        List<Direction> all = BlockStateProperties.FACING.getPossibleValues().stream().toList();
        List<Direction> possibleDirections = new ArrayList<>();
        for (Direction d : directions) {
            BlockPos p = pos.relative(d);
            BlockState s = levelAccessor.getBlockState(p);
            if (s.is(Blocks.SCULK) && levelAccessor.getBlockState(p.above()).isAir())
                possibleDirections.add(d);
        }
        for (Direction d : all) {
            BlockPos p = pos.relative(d);
            BlockState s = levelAccessor.getBlockState(p);
            if (d != Direction.UP && s.isAir())
                return false;
            if (s.is(BlockTags.SCULK_REPLACEABLE_WORLD_GEN))
                levelAccessor.setBlock(p, Blocks.SCULK.defaultBlockState(), 3);
        }
        if (!possibleDirections.isEmpty()) {
            Direction direction = Utils.rObject(possibleDirections, levelAccessor.getRandom());
            BlockPos relativePos = pos.relative(direction);
            for (Direction d : all) {
                BlockPos p = relativePos.relative(d);
                BlockState s = levelAccessor.getBlockState(p);
                if (s.is(BlockTags.SCULK_REPLACEABLE_WORLD_GEN))
                    levelAccessor.setBlock(p, Blocks.SCULK.defaultBlockState(), 3);
                else if (s.isAir())
                    return true;
            }
            levelAccessor.setBlock(relativePos, ModFluids.SCULK_FLUID_BLOCK.get().defaultBlockState(), 3);
        }
        return true;
    }

    public static List<Entity> getNearbyEntities(ServerLevel level, AABB bb, Predicate<Entity> filter) {
        Iterable<Entity> entityList = level.getEntities().getAll();
        Iterator<Entity> var7 = entityList.iterator();
        List<Entity> returnList = new ArrayList<>();
        while (true) {
            Entity entity;
            do {
                if (!var7.hasNext()) {
                    return returnList;
                }
                entity = var7.next();
            } while (filter != null && !filter.test(entity));
            if (bb.intersects(entity.getBoundingBox()))
                returnList.add(entity);
        }
    }

    public static Direction getRandomDirectionDependingOnBlocksAroundTheBlockPosition(LevelAccessor levelAccessor, BlockPos pos, Collection<Direction> possibleDirections, Predicate<BlockState> predicate) {
        List<Direction> directions = new ArrayList<>();
        for (Direction direction : possibleDirections) {
            BlockState state = levelAccessor.getBlockState(pos.relative(direction));
            if (predicate.test(state))
                directions.add(direction);
        }
        if (directions.isEmpty())
            return null;
        return rObject(directions, levelAccessor.getRandom());
    }

    public static Vec3 directionToVector(Direction direction) {
        return switch (direction) {
            case UP -> new Vec3(0, 1, 0);
            case DOWN -> new Vec3(0, -1, 0);
            case NORTH -> new Vec3(0, 0, -1);
            case SOUTH -> new Vec3(0, 0, 1);
            case EAST -> new Vec3(1, 0, 0);
            case WEST -> new Vec3(-1, 0, -0);
        };
    }

    public static boolean entityIsDamageable(Entity entity) {
        if (entity instanceof LivingEntity livingEntity && livingEntity.isAlive() && !livingEntity.isInvulnerable())
            return (livingEntity instanceof Player player && !player.getAbilities().invulnerable) || !(livingEntity instanceof Player);
        return false;
    }

    public static int getPackedLight(Level level, BlockPos blockPos) {
        return level != null ? LevelRenderer.getLightColor(level, blockPos) : 15728880;
    }

    public static void spreadSculk(BlockPos pos, ServerLevel level, RandomSource source, int charge, int spread, int minDistMult, int maxDistMult, SculkSpreader spreader, boolean calculateHeight) {
        Vec3i vec3 = MathUtils.generateRandomNormal2d(level.random).multiply(MathUtils.randomInt(minDistMult, maxDistMult));
        pos = pos.subtract(vec3);
        if (calculateHeight)
            pos = getCorrectHeightPos(pos, level);
        for (int i = charge; i > 0; i--)
            spreader.getCursors().add(new SculkSpreader.ChargeCursor(pos, charge));
        for (int i = spread; i > 0; i--)
            spreader.updateCursors(level, pos, source, true);
    }

    public static BlockPos getCorrectHeightPos(BlockPos pPos, ServerLevel level) {
        BlockPos pos = new BlockPos(pPos);
        BlockState blockState;
        while (true) {
            blockState = level.getBlockState(pos);
            BlockState blockStateUnder = level.getBlockState(pos.subtract(new Vec3i(0, 1, 0)));
            BlockState blockStateAbove = level.getBlockState(pos.subtract(new Vec3i(0, -1, 0)));
            if (blockState.isAir()) {
                if (!blockStateUnder.isAir())
                    break;
                pos = pos.subtract(new Vec3i(0, 1, 0));
            } else {
                pos = pos.subtract(new Vec3i(0, -1, 0));
                if (blockStateAbove.isAir())
                    break;
            }
        }
        return pos;
    }

    public static void spreadSculk(BlockPos pos, ServerLevel level, RandomSource source, int charge, int spread, int minDistMult, int maxDistMult) {
        SculkSpreader spreader = SculkSpreader.createLevelSpreader();
        Vec3i vec3 = MathUtils.generateRandomNormal2d(level.random).multiply(MathUtils.randomInt(minDistMult, maxDistMult));
        pos = pos.subtract(vec3);
        for (int i = charge; i > 0; i--)
            spreader.getCursors().add(new SculkSpreader.ChargeCursor(pos, charge));
        for (int i = spread; i > 0; i--)
            spreader.updateCursors(level, pos, source, true);
    }

    public static void spreadSculk(BlockPos pos, ServerLevel level, RandomSource source, int charge, int spread) {
        spreadSculk(pos, level, source, charge, spread, 5, 10);
    }

    public static List<ServerPlayer> addEffectToPlayersAround(ServerLevel level, @Nullable Entity entity, Vec3 vec3, double radius, MobEffectInstance mobEffectInstance, boolean checkHasEffect, boolean overrideEffect, boolean checkGameMode) {
        MobEffect mobeffect = mobEffectInstance.getEffect();
        List<ServerPlayer> list = level.getPlayers((player) -> (!checkGameMode || player.gameMode.isSurvival()) && (entity == null || !entity.isAlliedTo(player)) && (!checkHasEffect || !player.hasEffect(mobeffect)) && vec3.closerThan(player.position(), radius) && (!player.hasEffect(mobeffect) || (!overrideEffect || (player.getEffect(mobeffect).getAmplifier() < mobEffectInstance.getAmplifier() || player.getEffect(mobeffect).getDuration() < mobEffectInstance.getDuration()))));
        list.forEach((player) -> player.addEffect(new MobEffectInstance(mobEffectInstance), entity));
        return list;
    }

    public static LivingEntity addEffect(LivingEntity livingEntity, @Nullable Entity entity, MobEffectInstance mobEffectInstance, boolean checkHasEffect, boolean overrideEffect) {
        MobEffect mobeffect = mobEffectInstance.getEffect();
        if ((entity == null || !entity.isAlliedTo(livingEntity)) && (!livingEntity.hasEffect(mobeffect) || (!overrideEffect || !checkHasEffect || (livingEntity.getEffect(mobeffect).getAmplifier() < mobEffectInstance.getAmplifier() || livingEntity.getEffect(mobeffect).getDuration() < mobEffectInstance.getDuration()))))
            livingEntity.addEffect(new MobEffectInstance(mobEffectInstance), entity);
        return livingEntity;
    }

    public static float distance(Vec3 pos1, Vec3 pos2) {
        float f = (float) (pos1.x - pos2.x);
        float f1 = (float) (pos1.y - pos2.y);
        float f2 = (float) (pos1.z - pos2.z);
        return Mth.sqrt(f * f + f1 * f1 + f2 * f2);
    }

    public static float distanceSquared(Vec3 pos1, Vec3 pos2) {
        float f = (float) (pos1.x - pos2.x);
        float f1 = (float) (pos1.y - pos2.y);
        float f2 = (float) (pos1.z - pos2.z);
        return f * f + f1 * f1 + f2 * f2;
    }
}

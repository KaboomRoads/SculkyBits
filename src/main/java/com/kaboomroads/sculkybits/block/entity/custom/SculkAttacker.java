package com.kaboomroads.sculkybits.block.entity.custom;

import com.kaboomroads.sculkybits.damagesource.ModDamageSources;
import com.kaboomroads.sculkybits.effect.ModEffects;
import com.kaboomroads.sculkybits.entity.ModEntityTypes;
import com.kaboomroads.sculkybits.gameevent.ModGameEvent;
import com.kaboomroads.sculkybits.util.Utils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public interface SculkAttacker {
    VoxelShape ATTACK_SHAPE_DEFAULT = Block.box(1.0D, 16.0D, 1.0D, 15.0D, 18.0D, 15.0D);
    LinkedList<EntityType<?>> NON_ATTACKABLE = new LinkedList<>(List.of(
            EntityType.WARDEN,
            ModEntityTypes.SCULK_SAPROPHYTE.get(),
            ModEntityTypes.SCULK_CRAWLER.get()
    ));

    static List<LivingEntity> getAttackEntities(Level level, SculkAttacker sculkAttacker) {
        return getAttackEntities(level, sculkAttacker.getAttackShape(), new Vec3(sculkAttacker.getLevelX(), sculkAttacker.getLevelY(), sculkAttacker.getLevelZ()));
    }

    static List<LivingEntity> getAttackEntities(Level level, VoxelShape shape, Vec3 pos) {
        return shape.toAabbs().stream().flatMap((p_155558_) -> level.getEntitiesOfClass(LivingEntity.class, p_155558_.move(pos.x - 0.5D, pos.y - 0.5D, pos.z - 0.5D), EntitySelector.ENTITY_STILL_ALIVE).stream()).collect(Collectors.toList());
    }

    static void sculkDamage(LivingEntity livingEntity) {
        if (livingEntity.getType() == EntityType.PLAYER) livingEntity.invulnerableTime = 0;
        livingEntity.hurt(((ModDamageSources) livingEntity.level().damageSources()).sculkAttack(), 10);
        livingEntity.level().gameEvent(ModGameEvent.SCULK_ATTACK, livingEntity.position(), GameEvent.Context.of(livingEntity));
        applySculk(livingEntity);
    }

    static void applySculk(LivingEntity livingEntity) {
        if (!livingEntity.hasEffect(ModEffects.SCULK.get()))
            livingEntity.addEffect(new MobEffectInstance(ModEffects.SCULK.get(), 200, 0));
        else {
            MobEffectInstance mobEffectInstance = livingEntity.getEffect(ModEffects.SCULK.get());
            int duration = mobEffectInstance.getDuration() - 40;
            int amplifier = mobEffectInstance.getAmplifier() + 1;
            if (duration < 1) duration = 1;
            if (amplifier > 2) amplifier = 2;
            livingEntity.removeEffect(ModEffects.SCULK.get());
            Utils.addEffect(livingEntity, null, new MobEffectInstance(ModEffects.SCULK.get(), duration, amplifier), false, true);
        }
    }

    default VoxelShape getAttackShape() {
        return ATTACK_SHAPE_DEFAULT;
    }

    static boolean testAttackable(Entity entity) {
        return entity != null && !NON_ATTACKABLE.contains(entity.getType());
    }

    double getLevelX();

    double getLevelY();

    double getLevelZ();
}

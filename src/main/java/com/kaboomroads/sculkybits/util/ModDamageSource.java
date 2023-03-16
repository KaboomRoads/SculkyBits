package com.kaboomroads.sculkybits.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;

public class ModDamageSource {
    public static final DamageSource SCULK = new DamageSource("sculkybits.sculk_effect").bypassArmor().bypassEnchantments();
    public static final DamageSource SCULK_ATTACK = new DamageSource("sculkybits.sculk_attack").bypassEnchantments();

    public static DamageSource SCULK_ENTITY_ATTACK(Entity attacker) {
        return new EntityDamageSource("sculkybits.sculk_entity_attack", attacker).bypassEnchantments();
    }
}

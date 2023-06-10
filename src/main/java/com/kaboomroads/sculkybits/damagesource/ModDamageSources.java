package com.kaboomroads.sculkybits.damagesource;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public interface ModDamageSources {
    DamageSource sculkEntityAttack(Entity entity);

    DamageSource sculkAttack();

    DamageSource sculk();
}

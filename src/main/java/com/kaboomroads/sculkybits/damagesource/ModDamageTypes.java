package com.kaboomroads.sculkybits.damagesource;

import com.kaboomroads.sculkybits.Sculkybits;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> SCULK = create("sculk");
    public static final ResourceKey<DamageType> SCULK_ATTACK = create("sculk_attack");
    public static final ResourceKey<DamageType> SCULK_ENTITY_ATTACK = create("sculk_entity_attack");

    public static ResourceKey<DamageType> create(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Sculkybits.MOD_ID, name));
    }
}
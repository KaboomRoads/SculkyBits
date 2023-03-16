package com.kaboomroads.sculkybits.effect;

import com.kaboomroads.sculkybits.Sculkybits;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Sculkybits.MOD_ID);

    public static final RegistryObject<MobEffect> SCULK = MOB_EFFECTS.register("sculk",
            () -> new SculkEffect(MobEffectCategory.HARMFUL, 0x006464).addAttributeModifier(Attributes.MOVEMENT_SPEED, "b4f3a203-5ca2-4cf9-9241-608f496f2866", -0.15f, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}

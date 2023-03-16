package com.kaboomroads.sculkybits.potion;

import com.kaboomroads.sculkybits.Sculkybits;
import com.kaboomroads.sculkybits.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, Sculkybits.MOD_ID);

    public static final RegistryObject<Potion> SCULK_POTION = POTIONS.register("sculk_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.SCULK.get(), 1200, 0)));
    public static final RegistryObject<Potion> SHORT_SCULK_POTION = POTIONS.register("short_sculk_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.SCULK.get(), 400, 0)));
    public static final RegistryObject<Potion> STRONG_SCULK_POTION = POTIONS.register("strong_sculk_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.SCULK.get(), 1200, 1)));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}

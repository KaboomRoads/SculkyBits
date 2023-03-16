package com.kaboomroads.sculkybits.entity;

import com.kaboomroads.sculkybits.Sculkybits;
import com.kaboomroads.sculkybits.entity.custom.SculkCrawler;
import com.kaboomroads.sculkybits.entity.custom.SculkSaprophyte;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Sculkybits.MOD_ID);

    public static final RegistryObject<EntityType<SculkSaprophyte>> SCULK_SAPROPHYTE =
            ENTITY_TYPES.register("sculk_saprophyte", () ->
                    EntityType.Builder.of(SculkSaprophyte::new, MobCategory.MONSTER)
                            .sized(0.6f, 0.4f)
                            .build("sculk_saprophyte"));

    public static final RegistryObject<EntityType<SculkCrawler>> SCULK_CRAWLER =
            ENTITY_TYPES.register("sculk_crawler", () ->
                    EntityType.Builder.of(SculkCrawler::new, MobCategory.MONSTER)
                            .sized(0.9f, 0.5f)
                            .build("sculk_crawler"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}

package com.kaboomroads.sculkybits.item;

import com.kaboomroads.sculkybits.Sculkybits;
import com.kaboomroads.sculkybits.entity.ModEntityTypes;
import com.kaboomroads.sculkybits.fluid.ModFluids;
import com.kaboomroads.sculkybits.item.custom.override.ModEchoShardItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Sculkybits.MOD_ID);
    public static final DeferredRegister<Item> ITEMS_OVERRIDE = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    public static final RegistryObject<Item> SCULK_BUCKET = registerItem("sculk_bucket", () ->
            new BucketItem(ModFluids.SCULK_FLUID, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SCULK_SAPROPHYTE_SPAWN_EGG = registerItem("sculk_saprophyte_spawn_egg", () ->
            new ForgeSpawnEggItem(ModEntityTypes.SCULK_SAPROPHYTE, 0x052a32, 0xa2af86, new Item.Properties()));
    public static final RegistryObject<Item> SCULK_CRAWLER_SPAWN_EGG = registerItem("sculk_crawler_spawn_egg", () ->
            new ForgeSpawnEggItem(ModEntityTypes.SCULK_CRAWLER, 0x111b21, 0x009295, new Item.Properties()));

    public static final RegistryObject<Item> ECHO_SHARD = overrideItem("echo_shard", () ->
            new ModEchoShardItem(new Item.Properties()));

    public static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    public static <T extends Item> RegistryObject<T> overrideItem(String name, Supplier<T> item) {
        return ITEMS_OVERRIDE.register(name, item);
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        ITEMS_OVERRIDE.register(eventBus);
    }
}

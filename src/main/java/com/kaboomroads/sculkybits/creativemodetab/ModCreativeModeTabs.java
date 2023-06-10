package com.kaboomroads.sculkybits.creativemodetab;

import com.kaboomroads.sculkybits.Sculkybits;
import com.kaboomroads.sculkybits.block.ModBlocks;
import com.kaboomroads.sculkybits.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Sculkybits.MOD_ID);

    public static final RegistryObject<CreativeModeTab> SCULKY_BITS = CREATIVE_MODE_TABS.register("sculky_bits", CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModBlocks.SCULK_LURER.get()))
            .title(Component.translatable("itemGroup.sculkybits"))
            .displayItems((parameters, output) -> {
                output.accept(ModBlocks.SCULK_FLESH.get().asItem());
                output.accept(ModBlocks.SCULK_GROWTH.get().asItem());
                output.accept(ModBlocks.SCULK_JAW.get().asItem());
                output.accept(ModBlocks.SCULK_TRAP.get().asItem());
                output.accept(ModBlocks.SCULK_LURKER.get().asItem());
                output.accept(ModBlocks.SCULK_STABBER.get().asItem());
                output.accept(ModBlocks.SCULK_ABSORBER.get().asItem());
                output.accept(ModBlocks.SCULK_LURER.get().asItem());
                output.accept(ModBlocks.SCULK_FEELER.get().asItem());
                output.accept(ModBlocks.SCULK_NEST.get().asItem());
                output.accept(ModBlocks.SCULK_CAGE.get().asItem());
                output.accept(ModBlocks.SCULK_BONE_BLOCK.get().asItem());
                output.accept(ModBlocks.SCULK_RIBS.get().asItem());
                output.accept(ModBlocks.SCULK_BONES.get().asItem());
                output.accept(ModItems.SCULK_BUCKET.get());
                output.accept(ModItems.SCULK_SAPROPHYTE_SPAWN_EGG.get());
                output.accept(ModItems.SCULK_CRAWLER_SPAWN_EGG.get());
            })::build);

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}

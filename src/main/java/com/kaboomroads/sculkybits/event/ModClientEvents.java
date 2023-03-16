package com.kaboomroads.sculkybits.event;

import com.kaboomroads.sculkybits.Sculkybits;
import com.kaboomroads.sculkybits.block.ModBlocks;
import com.kaboomroads.sculkybits.block.entity.ModBlockEntities;
import com.kaboomroads.sculkybits.block.entity.custom.renderer.SculkFeelerRenderer;
import com.kaboomroads.sculkybits.entity.ModEntityTypes;
import com.kaboomroads.sculkybits.entity.client.SculkCrawlerModel;
import com.kaboomroads.sculkybits.entity.client.SculkCrawlerRenderer;
import com.kaboomroads.sculkybits.entity.client.SculkSaprophyteModel;
import com.kaboomroads.sculkybits.entity.client.SculkSaprophyteRenderer;
import com.kaboomroads.sculkybits.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Sculkybits.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SculkSaprophyteModel.LAYER_LOCATION, SculkSaprophyteModel::createBodyLayer);
        event.registerLayerDefinition(SculkCrawlerModel.LAYER_LOCATION, SculkCrawlerModel::createBodyLayer);
        event.registerLayerDefinition(SculkFeelerRenderer.LAYER_LOCATION, SculkFeelerRenderer::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.SCULK_SAPROPHYTE.get(), SculkSaprophyteRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SCULK_CRAWLER.get(), SculkCrawlerRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SCULK_FEELER.get(), SculkFeelerRenderer::new);
    }

    @SubscribeEvent
    public static void onCreativeModeTab(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(Sculkybits.MOD_ID, "sculkybits"), builder -> builder
                .icon(() -> new ItemStack(ModBlocks.SCULK_LURER.get()))
                .title(Component.translatable("itemGroup.sculkybits"))
                .displayItems((flagSet, output, b) -> {
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
                }).build());
    }
}

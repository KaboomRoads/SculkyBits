package com.kaboomroads.sculkybits.event;

import com.kaboomroads.sculkybits.Sculkybits;
import com.kaboomroads.sculkybits.block.entity.ModBlockEntities;
import com.kaboomroads.sculkybits.block.entity.custom.renderer.SculkFeelerRenderer;
import com.kaboomroads.sculkybits.entity.ModEntityTypes;
import com.kaboomroads.sculkybits.entity.client.SculkCrawlerModel;
import com.kaboomroads.sculkybits.entity.client.SculkCrawlerRenderer;
import com.kaboomroads.sculkybits.entity.client.SculkSaprophyteModel;
import com.kaboomroads.sculkybits.entity.client.SculkSaprophyteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
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
}

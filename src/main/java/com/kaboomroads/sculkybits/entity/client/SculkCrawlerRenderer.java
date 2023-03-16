package com.kaboomroads.sculkybits.entity.client;

import com.kaboomroads.sculkybits.Sculkybits;
import com.kaboomroads.sculkybits.entity.custom.SculkCrawler;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SculkCrawlerRenderer extends MobRenderer<SculkCrawler, SculkCrawlerModel<SculkCrawler>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Sculkybits.MOD_ID, "textures/entity/sculk_crawler.png");

    public SculkCrawlerRenderer(EntityRendererProvider.Context context) {
        super(context, new SculkCrawlerModel<>(context.bakeLayer(SculkCrawlerModel.LAYER_LOCATION)), 0.25f);
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull SculkCrawler sculkCrawler) {
        return TEXTURE;
    }
}

package com.kaboomroads.sculkybits.entity.client;

import com.kaboomroads.sculkybits.Sculkybits;
import com.kaboomroads.sculkybits.entity.custom.SculkSaprophyte;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SculkSaprophyteRenderer extends MobRenderer<SculkSaprophyte, SculkSaprophyteModel<SculkSaprophyte>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Sculkybits.MOD_ID, "textures/entity/sculk_saprophyte.png");

    public SculkSaprophyteRenderer(EntityRendererProvider.Context context) {
        super(context, new SculkSaprophyteModel<>(context.bakeLayer(SculkSaprophyteModel.LAYER_LOCATION)), 0.25f);
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull SculkSaprophyte sculkSaprophyte) {
        return TEXTURE;
    }
}

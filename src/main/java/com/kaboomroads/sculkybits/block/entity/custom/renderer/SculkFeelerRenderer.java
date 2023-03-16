package com.kaboomroads.sculkybits.block.entity.custom.renderer;

import com.kaboomroads.sculkybits.Sculkybits;
import com.kaboomroads.sculkybits.block.entity.custom.SculkFeelerBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class SculkFeelerRenderer implements BlockEntityRenderer<SculkFeelerBlockEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Sculkybits.MOD_ID, "sculk_feeler"), "main");
    private final ModelPart body;
    private final ModelPart tendril1;
    private final ModelPart tendril2;
    private final ModelPart tendril3;
    private final ModelPart tendril4;
    private final ModelPart tendril5;
    private final ModelPart tendril6;
    private final ModelPart tendril7;
    private final ModelPart tendril8;
    private final ModelPart tentacle;
    private final BlockEntityRendererProvider.Context context;

    public SculkFeelerRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
        ModelPart root = context.bakeLayer(LAYER_LOCATION);
        this.body = root.getChild("body");
        this.tendril1 = root.getChild("tendril1");
        this.tendril2 = root.getChild("tendril2");
        this.tendril3 = root.getChild("tendril3");
        this.tendril4 = root.getChild("tendril4");
        this.tendril5 = root.getChild("tendril5");
        this.tendril6 = root.getChild("tendril6");
        this.tendril7 = root.getChild("tendril7");
        this.tendril8 = root.getChild("tendril8");
        this.tentacle = root.getChild("tentacle");
    }

    @Override
    public void render(@NotNull SculkFeelerBlockEntity blockEntity, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entitySolid(new ResourceLocation(Sculkybits.MOD_ID, "textures/block/sculk_feeler.png")));
        poseStack.pushPose();
        float length = blockEntity.length;
        poseStack.translate(0.5, -0.5, 0.5);
        renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, length, 1, 1, 1, 1);
        poseStack.popPose();
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, 1.0F, -7.0F, 14.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 15).addBox(-6.0F, 0.0F, -6.0F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(-2.0F, -1.0F, -3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-2.0F, -1.0F, 2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 5).addBox(-3.0F, -1.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(2.0F, -1.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, 0.0F));
        PartDefinition tendril1 = partdefinition.addOrReplaceChild("tendril1", CubeListBuilder.create().texOffs(8, 21).addBox(-0.5F, 10.0F, -2.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 10.0F, -3.5F));
        tendril1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(4, 21).addBox(-0.5F, -2.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, -1.5F, -0.3927F, 0.0F, 0.0F));
        PartDefinition tendril2 = partdefinition.addOrReplaceChild("tendril2", CubeListBuilder.create().texOffs(0, 21).addBox(-3.5F, 10.0F, -2.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 10.0F, -3.5F));
        tendril2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(8, 18).addBox(-0.5F, -2.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 10.0F, -1.5F, -0.3927F, 0.0F, 0.0F));
        PartDefinition tendril3 = partdefinition.addOrReplaceChild("tendril3", CubeListBuilder.create().texOffs(4, 18).addBox(-7.5F, 10.0F, 1.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 10.0F, -3.5F));
        tendril3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5F, 10.0F, 2.0F, 0.0F, 0.0F, 0.3927F));
        PartDefinition tendril4 = partdefinition.addOrReplaceChild("tendril4", CubeListBuilder.create().texOffs(8, 15).addBox(-7.5F, 10.0F, 1.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 10.0F, -0.5F));
        tendril4.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(4, 15).addBox(-1.0F, -2.0F, 2.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5F, 10.0F, -1.0F, 0.0F, 0.0F, 0.3927F));
        PartDefinition tendril5 = partdefinition.addOrReplaceChild("tendril5", CubeListBuilder.create().texOffs(0, 15).addBox(-0.5F, 10.0F, 8.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 10.0F, -3.5F));
        tendril5.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(10, 9).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 8.5F, 0.3927F, 0.0F, 0.0F));
        PartDefinition tendril6 = partdefinition.addOrReplaceChild("tendril6", CubeListBuilder.create().texOffs(10, 6).addBox(-0.5F, 10.0F, 8.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 10.0F, -3.5F));
        tendril6.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(10, 3).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 8.5F, 0.3927F, 0.0F, 0.0F));
        PartDefinition tendril7 = partdefinition.addOrReplaceChild("tendril7", CubeListBuilder.create().texOffs(10, 0).addBox(1.5F, 10.0F, 6.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 10.0F, -5.5F));
        tendril7.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(6, 5).addBox(0.0F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 10.0F, 7.0F, 0.0F, 0.0F, -0.3927F));
        PartDefinition tendril8 = partdefinition.addOrReplaceChild("tendril8", CubeListBuilder.create().texOffs(6, 0).addBox(1.5F, 10.0F, 3.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 10.0F, -5.5F));
        tendril8.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 5).addBox(0.0F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 10.0F, 4.0F, 0.0F, 0.0F, -0.3927F));
        partdefinition.addOrReplaceChild("tentacle", CubeListBuilder.create().texOffs(0, 28).addBox(-0.5F, -16.0F, -0.5F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }


    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float length, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tendril1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tendril2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tendril3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tendril4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tendril5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tendril6.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tendril7.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        tendril8.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        if (length != 0) {
            poseStack.pushPose();
            float offset = 0.125f;
            poseStack.scale(1, length, 1);
            poseStack.translate(0, (-length - length + length / 2 + 1.5 - offset) / length, 0);
            tentacle.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
            poseStack.popPose();
        }
    }
}
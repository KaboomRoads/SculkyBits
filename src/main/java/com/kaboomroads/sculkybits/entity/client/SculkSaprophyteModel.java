package com.kaboomroads.sculkybits.entity.client;

import com.kaboomroads.sculkybits.Sculkybits;
import com.kaboomroads.sculkybits.entity.custom.SculkSaprophyte;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SculkSaprophyteModel<T extends SculkSaprophyte> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Sculkybits.MOD_ID, "sculk_saprophyte"), "main");
    public final ModelPart body;
    public final ModelPart blob1;
    public final ModelPart blob2;
    public final ModelPart blob3;
    public final ModelPart blob4;

    public SculkSaprophyteModel(ModelPart root) {
        this.body = root.getChild("body");
        this.blob1 = root.getChild("blob1");
        this.blob2 = root.getChild("blob2");
        this.blob3 = root.getChild("blob3");
        this.blob4 = root.getChild("blob4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        partdefinition.addOrReplaceChild("blob1", CubeListBuilder.create().texOffs(15, 17).addBox(-5.0F, -3.99F, 0.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        partdefinition.addOrReplaceChild("blob2", CubeListBuilder.create().texOffs(0, 12).addBox(1.0F, -3.99F, -5.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        partdefinition.addOrReplaceChild("blob3", CubeListBuilder.create().texOffs(20, 8).addBox(-5.0F, -2.99F, -5.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        partdefinition.addOrReplaceChild("blob4", CubeListBuilder.create().texOffs(18, 0).addBox(1.0F, -2.99F, 2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        animateWalk(limbSwing, limbSwingAmount);
    }

    private void animateWalk(float limbSwing, float limbSwingAmount) {
        float sin = (float) Math.sin(limbSwing), cos = (float) Math.cos(limbSwing);
        blob1.x = sin;
        blob1.z = cos;
        blob2.x = -sin;
        blob2.z = -cos;
        blob3.x = sin;
        blob3.z = cos;
        blob4.x = -sin;
        blob4.z = -cos;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        blob1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        blob2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        blob3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        blob4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
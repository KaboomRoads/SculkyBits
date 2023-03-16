package com.kaboomroads.sculkybits.entity.client;

import com.kaboomroads.sculkybits.Sculkybits;
import com.kaboomroads.sculkybits.entity.custom.SculkCrawler;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SculkCrawlerModel<T extends SculkCrawler> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Sculkybits.MOD_ID, "sculk_crawler"), "main");
    private final ModelPart root;

    public SculkCrawlerModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition crawler = partdefinition.addOrReplaceChild("crawler", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition legs = crawler.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition leg1 = legs.addOrReplaceChild("leg1", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, -5.5F, -3.8F, 0.0F, -0.3927F, 0.0F));
        leg1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(28, 18).addBox(-2.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5475F, 3.7957F, 0.0F, 0.0F, 0.0F, -0.8727F));
        leg1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(24, 28).addBox(-2.0F, -1.0F, -0.501F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5475F, 2.0637F, 0.0F, 0.0F, 0.0F, -1.0472F));
        leg1.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(29, 23).addBox(-2.0F, 0.0F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8032F, -0.1577F, 0.0F, 0.0F, 0.0F, -1.2217F));
        leg1.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(29, 25).addBox(-2.0F, 0.0F, -0.501F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.389F, -1.5719F, 0.0F, 0.0F, 0.0F, -0.7854F));
        leg1.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 22).addBox(-3.0F, -1.0F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.3927F));
        PartDefinition leg2 = legs.addOrReplaceChild("leg2", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, -5.5F, -2.3F, 0.0F, -0.1309F, 0.0F));
        leg2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(12, 28).addBox(-2.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5475F, 3.7957F, 0.0F, 0.0F, 0.0F, -0.8727F));
        leg2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(28, 14).addBox(-2.0F, -1.0F, -0.501F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5475F, 2.0637F, 0.0F, 0.0F, 0.0F, -1.0472F));
        leg2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(28, 16).addBox(-2.0F, 0.0F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8032F, -0.1577F, 0.0F, 0.0F, 0.0F, -1.2217F));
        leg2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(18, 28).addBox(-2.0F, 0.0F, -0.501F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.389F, -1.5719F, 0.0F, 0.0F, 0.0F, -0.7854F));
        leg2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(21, 17).addBox(-3.0F, -1.0F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.3927F));
        PartDefinition leg3 = legs.addOrReplaceChild("leg3", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, -5.5F, -0.3F, 0.0F, 0.1309F, 0.0F));
        leg3.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(24, 26).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5475F, 3.7957F, 0.0F, 0.0F, 0.0F, -0.8727F));
        leg3.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 28).addBox(-2.0F, -1.0F, -1.001F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5475F, 2.0637F, 0.0F, 0.0F, 0.0F, -1.0472F));
        leg3.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(6, 28).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8032F, -0.1577F, 0.0F, 0.0F, 0.0F, -1.2217F));
        leg3.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(28, 9).addBox(-2.0F, 0.0F, -1.001F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.389F, -1.5719F, 0.0F, 0.0F, 0.0F, -0.7854F));
        leg3.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(16, 20).addBox(-3.0F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.3927F));
        PartDefinition leg4 = legs.addOrReplaceChild("leg4", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, -5.5F, 0.7F, 0.0F, 0.3927F, 0.0F));
        leg4.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(6, 26).addBox(-2.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5475F, 3.7957F, 0.0F, 0.0F, 0.0F, -0.8727F));
        leg4.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(26, 7).addBox(-2.0F, -1.0F, -0.499F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5475F, 2.0637F, 0.0F, 0.0F, 0.0F, -1.0472F));
        leg4.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(12, 26).addBox(-2.0F, 0.0F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8032F, -0.1577F, 0.0F, 0.0F, 0.0F, -1.2217F));
        leg4.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(18, 26).addBox(-2.0F, 0.0F, -0.499F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.389F, -1.5719F, 0.0F, 0.0F, 0.0F, -0.7854F));
        leg4.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(19, 4).addBox(-3.0F, -1.0F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.3927F));
        PartDefinition leg5 = legs.addOrReplaceChild("leg5", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, -5.5F, 0.7F, -3.1416F, 0.3927F, 3.1416F));
        leg5.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 26).addBox(-2.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5475F, 3.7957F, 0.0F, 0.0F, 0.0F, -0.8727F));
        leg5.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(26, 1).addBox(-2.0F, -1.0F, -0.499F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5475F, 2.0637F, 0.0F, 0.0F, 0.0F, -1.0472F));
        leg5.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(26, 3).addBox(-2.0F, 0.0F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8032F, -0.1577F, 0.0F, 0.0F, 0.0F, -1.2217F));
        leg5.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(26, 5).addBox(-2.0F, 0.0F, -0.499F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.389F, -1.5719F, 0.0F, 0.0F, 0.0F, -0.7854F));
        leg5.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(19, 2).addBox(-3.0F, -1.0F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.3927F));
        PartDefinition leg6 = legs.addOrReplaceChild("leg6", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, -5.5F, -0.8F, -3.1416F, 0.1309F, 3.1416F));
        leg6.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(12, 24).addBox(-2.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5475F, 3.7957F, 0.0F, 0.0F, 0.0F, -0.8727F));
        leg6.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(18, 24).addBox(-2.0F, -1.0F, -0.501F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5475F, 2.0637F, 0.0F, 0.0F, 0.0F, -1.0472F));
        leg6.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(24, 24).addBox(-2.0F, 0.0F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8032F, -0.1577F, 0.0F, 0.0F, 0.0F, -1.2217F));
        leg6.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(25, 21).addBox(-2.0F, 0.0F, -0.501F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.389F, -1.5719F, 0.0F, 0.0F, 0.0F, -0.7854F));
        leg6.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(19, 0).addBox(-3.0F, -1.0F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.3927F));
        PartDefinition leg7 = legs.addOrReplaceChild("leg7", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, -5.5F, -2.8F, 3.1416F, -0.1309F, -3.1416F));
        leg7.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(23, 15).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5475F, 3.7957F, 0.0F, 0.0F, 0.0F, -0.8727F));
        leg7.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(23, 19).addBox(-2.0F, -1.0F, -1.001F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5475F, 2.0637F, 0.0F, 0.0F, 0.0F, -1.0472F));
        leg7.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(0, 24).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8032F, -0.1577F, 0.0F, 0.0F, 0.0F, -1.2217F));
        leg7.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(6, 24).addBox(-2.0F, 0.0F, -1.001F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.389F, -1.5719F, 0.0F, 0.0F, 0.0F, -0.7854F));
        leg7.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(13, 18).addBox(-3.0F, -1.0F, -1.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.3927F));
        PartDefinition leg8 = legs.addOrReplaceChild("leg8", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, -5.5F, -3.8F, 3.1416F, -0.3927F, -3.1416F));
        leg8.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(5, 18).addBox(-2.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5475F, 3.7957F, 0.0F, 0.0F, 0.0F, -0.8727F));
        leg8.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(8, 22).addBox(-2.0F, -1.0F, -0.501F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5475F, 2.0637F, 0.0F, 0.0F, 0.0F, -1.0472F));
        leg8.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(14, 22).addBox(-2.0F, 0.0F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8032F, -0.1577F, 0.0F, 0.0F, 0.0F, -1.2217F));
        leg8.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(20, 22).addBox(-2.0F, 0.0F, -0.501F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.389F, -1.5719F, 0.0F, 0.0F, 0.0F, -0.7854F));
        leg8.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(16, 14).addBox(-3.0F, -1.0F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.3927F));
        PartDefinition head = crawler.addOrReplaceChild("head", CubeListBuilder.create().texOffs(16, 10).addBox(-2.5F, -1.0F, -2.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.25F, -5.0F));
        PartDefinition mandible1 = head.addOrReplaceChild("mandible1", CubeListBuilder.create().texOffs(8, 18).addBox(-0.4512F, -0.5F, -2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 0.0F, -1.5F, 0.0F, 0.0F, 0.2618F));
        mandible1.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(0, 13).addBox(-0.067F, -1.8442F, -1.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1923F, 1.3442F, -2.5F, 0.0F, -1.309F, 0.0F));
        PartDefinition mandible2 = head.addOrReplaceChild("mandible2", CubeListBuilder.create().texOffs(0, 18).addBox(-0.5488F, -0.5F, -2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 0.0F, -1.5F, 0.0F, 0.0F, -0.2618F));
        mandible2.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(0, 10).addBox(-0.9407F, -1.899F, -1.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1944F, 1.399F, -2.5074F, 0.0F, 1.309F, 0.0F));
        crawler.addOrReplaceChild("cephalothorax", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -0.5F, -3.5F, 6.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.5F, -1.5F));
        PartDefinition abdomen = crawler.addOrReplaceChild("abdomen", CubeListBuilder.create(), PartPose.offset(0.0F, -4.5F, 2.0F));
        abdomen.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(0, 10).addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));
        PartDefinition spinnerets = abdomen.addOrReplaceChild("spinnerets", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, 5.75F));
        spinnerets.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.25F, 0.5F, 0.0F, 0.0873F, 0.0F));
        spinnerets.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(0, 3).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -0.25F, 0.5F, 0.0F, -0.0873F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float f = Math.min((float) entity.getDeltaMovement().lengthSqr() * 200.0F, 8.0F);
        this.animate(entity.walkAnimationState, SculkCrawlerAnimation.SCULK_CRAWLER_CRAWL, ageInTicks, f);
        this.animate(entity.attackAnimationState, SculkCrawlerAnimation.SCULK_CRAWLER_BITE, ageInTicks);
    }

    @NotNull
    @Override
    public ModelPart root() {
        return root;
    }
}
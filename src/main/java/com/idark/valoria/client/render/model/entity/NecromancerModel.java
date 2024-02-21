package com.idark.valoria.client.render.model.entity;

import com.idark.valoria.registries.world.item.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class NecromancerModel<T extends Mob & RangedAttackMob> extends HumanoidModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "necromancer"), "main");

    public NecromancerModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(33, 0).addBox(-4.0F, -8.0F, -5.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.4F))
                .texOffs(0, 0).addBox(-4.0F, -8.0F, -5.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 1.0F));
        PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create()
                .texOffs(65, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 7.0F, 8.0F, new CubeDeformation(0.41F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(24, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.4F))
                .texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(24, 32).addBox(-4.0F, 12.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.4F))
                .texOffs(0, 32).addBox(-4.0F, 12.0F, -2.0F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create()
                .texOffs(27, 40).addBox(-1.6F, -1.5F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(27, 54).addBox(-1.5F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.4F))

                .texOffs(48, 30).addBox(-1.5F, -2.5F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create()
                .texOffs(18, 54).mirror().addBox(-0.5F, -1.5F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.4F)).mirror(false)

                .texOffs(18, 40).addBox(-0.6F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(48, 20).addBox(-1.5F, -2.5F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(0, 53).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.4F))
                .texOffs(0, 40).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 10.0F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(9, 53).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.4F))
                .texOffs(9, 40).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 10.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    public void prepareMobModel(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
        this.rightArmPose = ArmPose.EMPTY;
        this.leftArmPose = ArmPose.EMPTY;
        ItemStack $$4 = pEntity.getItemInHand(InteractionHand.MAIN_HAND);
        if ($$4.is(Items.BOW) && pEntity.isAggressive()) {
            if (pEntity.getMainArm() == HumanoidArm.RIGHT) {
                this.rightArmPose = ArmPose.BOW_AND_ARROW;
            } else {
                this.leftArmPose = ArmPose.BOW_AND_ARROW;
            }
        }

        super.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
    }

    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        ItemStack $$6 = pEntity.getMainHandItem();

        if (pEntity.isAggressive() && ($$6.isEmpty() || !$$6.is(Items.BOW))) {
            float $$7 = Mth.sin(this.attackTime * 3.1415927F);
            float $$8 = Mth.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * 3.1415927F);
            this.rightArm.zRot = 0.0F;
            this.leftArm.zRot = 0.0F;
            this.rightArm.yRot = -(0.1F - $$7 * 0.6F);
            this.leftArm.yRot = 0.1F - $$7 * 0.6F;
            this.rightArm.xRot = -1.5707964F;
            this.leftArm.xRot = -1.5707964F;
            ModelPart var10000 = this.rightArm;
            var10000.xRot -= $$7 * 1.2F - $$8 * 0.4F;
            var10000 = this.leftArm;
            var10000.xRot -= $$7 * 1.2F - $$8 * 0.4F;
            AnimationUtils.bobArms(this.rightArm, this.leftArm, pAgeInTicks);
        }

        if (pEntity.isAggressive() && ($$6.isEmpty() || $$6.is(ModItems.STAFF.get()))) {
            this.rightArm.z = 0.0F;
            this.rightArm.x = -5.0F;
            this.leftArm.z = 0.0F;
            this.leftArm.x = 5.0F;
            this.rightArm.xRot = Mth.cos(pAgeInTicks * 0.6662F) * 0.25F;
            this.leftArm.xRot = Mth.cos(pAgeInTicks * 0.6662F) * 0.25F;
            this.rightArm.zRot = 2.3561945F;
            this.leftArm.zRot = -2.3561945F;
            this.rightArm.yRot = 0.0F;
            this.leftArm.yRot = 0.0F;
        }
    }

    public void translateToHand(HumanoidArm pSide, PoseStack pPoseStack) {
        float $$2 = pSide == HumanoidArm.RIGHT ? 1.0F : -1.0F;
        ModelPart $$3 = this.getArm(pSide);
        $$3.x += $$2;
        $$3.translateAndRotate(pPoseStack);
        $$3.x -= $$2;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        hat.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
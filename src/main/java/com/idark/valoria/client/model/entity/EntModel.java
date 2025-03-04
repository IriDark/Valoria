package com.idark.valoria.client.model.entity;

import com.idark.valoria.client.model.animations.*;
import com.idark.valoria.registries.entity.living.elemental.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.*;

public class EntModel<T extends Ent> extends HierarchicalModel<T>{
    public ModelPart root;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public EntModel(ModelPart root) {
        this.root = root;
        this.rightLeg = root.getChild("bone").getChild("rightleg");
        this.leftLeg = root.getChild("bone").getChild("leftleg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 1.1667F, 0.0F));

        PartDefinition rightarm = bone.addOrReplaceChild("rightarm", CubeListBuilder.create().texOffs(20, 46).addBox(-2.5F, -4.6667F, -2.5F, 5.0F, 15.0F, 5.0F, new CubeDeformation(0.0F))
        .texOffs(60, 51).addBox(-2.5F, -4.6667F, -2.5F, 5.0F, 15.0F, 5.0F, new CubeDeformation(0.25F))
        .texOffs(20, 66).addBox(-2.5F, -7.1667F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offset(-9.0F, 0.5F, 0.0F));

        PartDefinition body = bone.addOrReplaceChild("body", CubeListBuilder.create().texOffs(40, 0).addBox(-6.0F, -8.6667F, -4.0F, 12.0F, 5.0F, 8.0F, new CubeDeformation(0.25F))
        .texOffs(0, 0).addBox(-6.0F, -4.1667F, -4.0F, 12.0F, 15.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftleg = bone.addOrReplaceChild("leftleg", CubeListBuilder.create().texOffs(40, 13).addBox(-3.5F, -6.0F, -3.5F, 7.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 16.8333F, 0.0F));

        PartDefinition rightleg = bone.addOrReplaceChild("rightleg", CubeListBuilder.create().texOffs(40, 32).addBox(-3.5F, -6.0F, -3.5F, 7.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 16.8333F, 0.0F));

        PartDefinition leftarm = bone.addOrReplaceChild("leftarm", CubeListBuilder.create().texOffs(0, 46).addBox(-2.5F, -4.3333F, -2.5F, 5.0F, 15.0F, 5.0F, new CubeDeformation(0.0F))
        .texOffs(40, 51).addBox(-2.5F, -4.3333F, -2.5F, 5.0F, 15.0F, 5.0F, new CubeDeformation(0.25F))
        .texOffs(0, 66).addBox(-2.5F, -8.8333F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offset(9.0F, 0.1667F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(Ent pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(EntAnimation.WALK, pLimbSwing, pLimbSwingAmount, pEntity.getSpeed(), pAgeInTicks);
        this.animate(pEntity.idleAnimationState, EntAnimation.IDLE, pAgeInTicks);
        this.animate(pEntity.attackAnimationState, EntAnimation.ATTACK, pAgeInTicks);

        boolean flag = pEntity.getFallFlyingTicks() > 4;
        float f = 1.0F;
        if (flag) {
            f = (float)pEntity.getDeltaMovement().lengthSqr();
            f /= 0.2F;
            f *= f * f;
        }

        if (f < 1.0F) {
            f = 1.0F;
        }

        this.rightLeg.xRot = Mth.cos(pLimbSwing * 0.3262F) * 1F * pLimbSwingAmount / f;
        this.leftLeg.xRot = Mth.cos(pLimbSwing * 0.3262F + (float)Math.PI) * 1F * pLimbSwingAmount / f;
        this.rightLeg.yRot = 0.00015F;
        this.leftLeg.yRot = -0.00015F;
        this.rightLeg.zRot = 0.00015F;
        this.leftLeg.zRot = -0.00015F;
    }

    @Override
    public ModelPart root(){
        return root;
    }
}
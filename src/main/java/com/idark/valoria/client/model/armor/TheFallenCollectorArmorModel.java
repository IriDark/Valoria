package com.idark.valoria.client.model.armor;

import com.google.common.collect.ImmutableList;
import mod.maxbogomol.fluffy_fur.client.model.armor.ArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.EquipmentSlot;

public class TheFallenCollectorArmorModel extends ArmorModel{
    public final ModelPart right_body_side;
    public final ModelPart left_body_side;

    public TheFallenCollectorArmorModel(ModelPart root){
        super(root);
        this.right_body_side = root.getChild("right_body_side");
        this.left_body_side = root.getChild("left_body_side");
    }

    public static LayerDefinition createBodyLayer(){
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = createHumanoidAlias(meshdefinition);
        PartDefinition hat = partdefinition.addOrReplaceChild("hat", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 23).addBox(-4.0F, -8F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -9F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(-4.0F, -12F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -14.3571F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition wings = head.addOrReplaceChild("wings", CubeListBuilder.create()
                .texOffs(48, -6).mirror().addBox(0.0F, -22F, 0.0F, 0.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(48, 9).mirror().addBox(0.0F, -22F, -7.0F, 0.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.6429F, 0.0F, 0.0F, 1.6581F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(80, 48).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(1.01F))
                .texOffs(80, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(1.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition right_body_side = partdefinition.addOrReplaceChild("right_body_side", CubeListBuilder.create().texOffs(112, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offset(-1.9F, 12.0F, 0.0F));
        PartDefinition left_body_side = partdefinition.addOrReplaceChild("left_body_side", CubeListBuilder.create().texOffs(112, 18).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(104, 48).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.8F)), PartPose.offset(-6.0F, 6.0F, 0.0F));
        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(104, 48).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.8F)).mirror(false), PartPose.offset(6.0F, 6.0F, 0.0F));
        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", new CubeListBuilder(), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    protected Iterable<ModelPart> bodyParts(){
        if(this.slot == EquipmentSlot.CHEST){
            return ImmutableList.of(this.body, this.leftArm, this.rightArm, this.right_body_side, this.left_body_side);
        }else{
            return this.slot == EquipmentSlot.FEET ? ImmutableList.of(this.leftFoot, this.rightFoot) : ImmutableList.of();
        }
    }

    public void copyFromDefault(HumanoidModel model){
        super.copyFromDefault(model);
        this.right_body_side.copyFrom(model.rightLeg);
        this.left_body_side.copyFrom(model.leftLeg);
    }
}
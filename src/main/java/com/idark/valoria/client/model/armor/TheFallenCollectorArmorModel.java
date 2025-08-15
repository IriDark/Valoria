package com.idark.valoria.client.model.armor;

import com.google.common.collect.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.*;
import pro.komaru.tridot.client.model.armor.*;

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

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(16, 8).addBox(4.0F, 4.0F, -4.0F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition model = head.addOrReplaceChild("model", CubeListBuilder.create().texOffs(36, 50).addBox(-13.0F, -14.0F, -1.0F, 14.0F, 14.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(32, 6).addBox(-10.0F, -1.001F, -5.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offset(6.0F, -7.0F, 1.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(1.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_body_side = partdefinition.addOrReplaceChild("right_body_side", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, 0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(1.01F)), PartPose.offset(-1.0F, 30.0F, 0.0F));

        PartDefinition left_body_side = partdefinition.addOrReplaceChild("left_body_side", CubeListBuilder.create().texOffs(24, 0).mirror().addBox(0.0F, 0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(1.01F)).mirror(false), PartPose.offset(1.0F, 30.0F, 0.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.8F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.8F)).mirror(false), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", new CubeListBuilder(), PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 64, 64);
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
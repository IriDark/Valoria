package com.idark.valoria.client.render.model.curio;

import com.google.common.collect.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;

public class JewelryBagModel extends HumanoidModel<LivingEntity>{
    public ModelPart root, model;

    public JewelryBagModel(ModelPart pRoot){
        super(pRoot);
        this.root = pRoot;
        this.model = root.getChild("body").getChild("model");
    }

    public static LayerDefinition createBodyLayer(){
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // Empty ones to prevent crashing
        PartDefinition head = partdefinition.addOrReplaceChild("head", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition hat = partdefinition.addOrReplaceChild("hat", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition body = partdefinition.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", new CubeListBuilder(), PartPose.ZERO);

        PartDefinition model = body.addOrReplaceChild("model", CubeListBuilder.create().texOffs(9, 9)
        .addBox(-5.0F, -12.0F, -2.5F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 5)
        .addBox(-5.0F, -12.0F, -2.5F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.25F)).texOffs(0, 0)
        .addBox(-3.0F, -12.0F, -2.0F, 7.0F, 1.0F, 4.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        ItemStack chestplate = entity.getItemBySlot(EquipmentSlot.LEGS);
        if(!chestplate.isEmpty() && chestplate.getItem() instanceof ArmorItem){
            this.model.xScale = 1.05f;
            this.model.yScale = 1.0f;
            this.model.zScale = 1.5f;
        }else{
            this.model.xScale = 1.0f;
            this.model.yScale = 1.0f;
            this.model.zScale = 1.0f;
        }

        if(entity instanceof LivingEntity){
            model.copyFrom(this.body);
            model.y += 20;
        } // hmmmm

        this.model.yRot = Mth.sin(limbSwing * 0.05F) * 0.03F;
        this.model.zRot = Mth.sin(limbSwing * 0.05F) * 0.03F;
        this.model.xRot = Mth.sin(limbSwing * 0.025F) * 0.03F;
    }

    @Override
    protected Iterable<ModelPart> headParts(){
        return ImmutableList.of(root.getChild("head"));
    }

    @Override
    protected Iterable<ModelPart> bodyParts(){
        return ImmutableList.of(root.getChild("body"));
    }
}

package com.idark.valoria.client.model.curio;

import com.google.common.collect.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import org.joml.*;

public class CrownModel extends HumanoidModel<LivingEntity>{
    public ModelPart root, crown;
    public CrownModel(ModelPart pRoot){
        super(pRoot);
        this.root = pRoot;
        this.crown = root.getChild("head").getChild("crown");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition hat = partdefinition.addOrReplaceChild("hat", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition body = partdefinition.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", new CubeListBuilder(), PartPose.ZERO);

        PartDefinition crown = head.addOrReplaceChild("crown", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = crown.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(32, -6).addBox(0.0F, -13.0F, 1.5F, 0.0F, 14.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r2 = crown.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(32, -6).addBox(0.0F, -13.0F, 1.5F, 0.0F, 14.0F, 6.0F, new CubeDeformation(0.0F))
        .texOffs(3, 0).addBox(-4.0F, -3.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(3, 0).addBox(4.0F, -3.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(0, 2).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
        .texOffs(8, 16).addBox(-3.0F, 2.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
        .texOffs(0, 23).addBox(-4.0F, 3.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        ItemStack head = entity.getItemBySlot(EquipmentSlot.HEAD);
        if(!head.isEmpty() && head.getItem() instanceof ArmorItem){
            this.head.offsetPos(new Vector3f(0, -1.25f, 0));
        }else{
            this.head.offsetScale(new Vector3f(0, 0, 0));
        }

        if(entity instanceof LivingEntity){
            crown.copyFrom(this.head);
        }
    }

    @Override
    protected Iterable<ModelPart> headParts(){
        return ImmutableList.of(crown);
    }
}

package com.idark.valoria.client.model.curio;

import com.google.common.collect.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import org.joml.*;

public class MonocleModel extends HumanoidModel<LivingEntity>{
    public ModelPart root, monocle, chain;

    public MonocleModel(ModelPart pRoot){
        super(pRoot);
        this.root = pRoot;
        this.chain = root.getChild("head").getChild("chain");
        this.monocle = root.getChild("head").getChild("monocle");
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

        PartDefinition chain = head.addOrReplaceChild("chain", CubeListBuilder.create().texOffs(0, 3).addBox(3.5F, -2F, -5.1F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offset(2.0F, -3.5F, -4.0F));

        PartDefinition monocle = head.addOrReplaceChild("monocle", CubeListBuilder.create().texOffs(0, 0).addBox(0.5F, -4.0F, -5.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.015F)), PartPose.offset(3.5F, -1.0F, -4.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        ItemStack chestplate = entity.getItemBySlot(EquipmentSlot.HEAD);
        if(!chestplate.isEmpty() && chestplate.getItem() instanceof ArmorItem){
            this.head.offsetPos(new Vector3f(0, 0, -0.15f));
        }else{
            this.head.offsetPos(new Vector3f(0, 0, 0));
        }

        if(entity instanceof LivingEntity){
            monocle.copyFrom(this.head);
            chain.copyFrom(this.head);
        }

        this.chain.yRot += Mth.sin(ageInTicks * 0.03F) * 0.03F;
        this.chain.zRot += Mth.sin(ageInTicks * 0.03F) * 0.03F;
        this.chain.xRot += Mth.sin(limbSwing * 0.50F) * 0.03F;

    }

    @Override
    protected Iterable<ModelPart> headParts(){
        return ImmutableList.of(monocle, chain);
    }
}

package com.idark.valoria.client.model.curio;

import com.google.common.collect.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import org.joml.*;

public class GasMaskModel extends HumanoidModel<LivingEntity>{
    public ModelPart root, gas_mask;

    public GasMaskModel(ModelPart pRoot){
        super(pRoot);
        this.root = pRoot;
        this.gas_mask = root.getChild("head").getChild("gas_mask");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition hat = partdefinition.addOrReplaceChild("hat", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition body = partdefinition.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", new CubeListBuilder(), PartPose.ZERO);

        PartDefinition gas_mask = head.addOrReplaceChild("gas_mask", CubeListBuilder.create().texOffs(10, 5).addBox(-2.0F, -1.4F, -5.2F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(0, 10).addBox(-5.0F, -1.4F, -5.2F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.1F))
        .texOffs(0, 5).addBox(3.0F, -1.4F, -5.2F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.1F))
        .texOffs(0, 16).addBox(-4.0F, -7.65F, -3.7F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.45F)), PartPose.offset(0.0F, -4.1F, -8.8F));
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        ItemStack head = entity.getItemBySlot(EquipmentSlot.HEAD);
        if(!head.isEmpty() && head.getItem() instanceof ArmorItem){
            this.head.offsetScale(new Vector3f(0.35f, 0.35f, 0.35f));
        }else{
            this.head.offsetScale(new Vector3f(0, 0, 0));
        }

        if(entity instanceof LivingEntity){
            gas_mask.copyFrom(this.head);
        }
    }

    @Override
    protected Iterable<ModelPart> headParts(){
        return ImmutableList.of(gas_mask);
    }
}

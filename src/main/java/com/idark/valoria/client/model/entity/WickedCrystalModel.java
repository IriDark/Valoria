package com.idark.valoria.client.model.entity;

import com.idark.valoria.client.model.animations.*;
import com.idark.valoria.registries.entity.living.boss.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;

public class WickedCrystalModel<T extends WickedCrystal> extends HierarchicalModel<T>{
    private final ModelPart root;
    public WickedCrystalModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition bone3 = partdefinition.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(40, 74).addBox(0.0F, -32.0F, 3.0F, 6.0F, 32.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-16.0F, -2.0F, -10.0F));

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -69.0F, -10.0F, 20.0F, 54.0F, 20.0F, new CubeDeformation(0.0F))
        .texOffs(80, 0).addBox(-4.0F, -74.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
        .texOffs(80, 13).addBox(-4.0F, -15.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bone2 = partdefinition.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 74).addBox(0.0F, -32.0F, 0.0F, 6.0F, 32.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, -2.0F, -7.0F));

        PartDefinition Crystal = partdefinition.addOrReplaceChild("Crystal", CubeListBuilder.create(), PartPose.offset(0.0F, -18.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.spawnAnimationState, CrystalAnimation.SPAWN, ageInTicks);
        this.animate(entity.deathAnimationState, CrystalAnimation.DEATH, ageInTicks);
    }

    @Override
    public ModelPart root(){
        return root;
    }
}
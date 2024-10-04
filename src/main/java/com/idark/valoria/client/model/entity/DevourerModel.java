package com.idark.valoria.client.model.entity;

import com.idark.valoria.client.model.animations.*;
import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class DevourerModel <T extends Devourer> extends HierarchicalModel<T>{
    private final ModelPart root;
    private final ModelPart skull;
    private final ModelPart mouth_top;
    private final ModelPart mouth_bottom;
    public DevourerModel(ModelPart pRoot) {
        this.root = pRoot;
        this.skull = pRoot.getChild("skull");
        this.mouth_top = skull.getChild("mouth_top");
        this.mouth_bottom = skull.getChild("mouth_bottom");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition skull = partdefinition.addOrReplaceChild("skull", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -13.0F, -7.0F, 14.0F, 13.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition mouth_top = skull.addOrReplaceChild("mouth_top", CubeListBuilder.create().texOffs(0, 27).addBox(-6.0F, -8.5F, -3.0F, 12.0F, 17.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -21.5F, -2.0F));
        PartDefinition mouth_bottom = skull.addOrReplaceChild("mouth_bottom", CubeListBuilder.create().texOffs(36, 27).addBox(-6.0F, -21.5F, 0.0F, 12.0F, 17.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.5F, 1.0F));
        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public ModelPart root(){
        return root;
    }

    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch){
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(pEntity.attackAnimationState, DevourerAnimations.ATTACK, pAgeInTicks);
    }
}
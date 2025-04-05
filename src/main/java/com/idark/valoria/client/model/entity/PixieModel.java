package com.idark.valoria.client.model.entity;

import com.idark.valoria.registries.entity.living.minions.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class PixieModel extends HierarchicalModel<PixieEntity>{
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart bodyGlow;
    private final ModelPart leftWing;
    private final ModelPart rightWing;

    public PixieModel(ModelPart pRoot){
        super(RenderType::entityTranslucent);
        this.root = pRoot;
        this.body = root.getChild("body");
        this.bodyGlow = body.getChild("body_glow");
        this.leftWing = root.getChild("left_wing");
        this.rightWing = root.getChild("right_wing");
    }

    public static LayerDefinition createBodyLayer(){
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -8.0F, -1.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 24.0F, -3.0F));
        PartDefinition body_glow = body.addOrReplaceChild("body_glow", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(-3.0F, -4.0F, 3.0F));
        PartDefinition left_wing = partdefinition.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(32, 0).addBox(-7.0F, -8.0F, -1.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 24.0F, 1.0F));
        PartDefinition right_wing = partdefinition.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(32, 0).mirror().addBox(4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    /**
     * Sets this entity's model rotation angles
     */
    public void setupAnim(PixieEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch){
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float beat = 0.9F + Mth.abs(Mth.sin(pAgeInTicks * 0.1F)) * 0.15F;
        this.bodyGlow.xScale = beat;
        this.bodyGlow.yScale = beat;
        this.bodyGlow.zScale = beat;

        this.leftWing.zRot =  Mth.cos(pAgeInTicks * 5f * ((float)Math.PI / 180f)) * ((float)Math.PI / 180f) * 6f;
        this.rightWing.zRot = -this.leftWing.zRot;

        this.leftWing.yRot = 0.25f + Mth.cos(pAgeInTicks * 45f * ((float)Math.PI / 180F)) * ((float)Math.PI / 180F) * 16.2F;
        this.rightWing.yRot = -this.leftWing.yRot;
    }

    public ModelPart root(){
        return this.root;
    }
}
package com.idark.valoria.client.model.armor;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;

public class InfernalArmorModel{

    public static MeshDefinition addPieces(CubeDeformation deformation) {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(deformation, 0);
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
        .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, deformation)

        .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition horn_2_r1 = head.addOrReplaceChild("horn_2_r1", CubeListBuilder.create().mirror().texOffs(56, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, deformation.extend(-0.5f)), PartPose.offsetAndRotation(5F, -8.45F, -2.5f, 0.0F, 0.0F, 0));

        PartDefinition horn_1_r1 = head.addOrReplaceChild("horn_1_r1", CubeListBuilder.create().texOffs(56, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, deformation.extend(-0.5f)), PartPose.offsetAndRotation(-5F, -8.45F, -2.5F, 0.0F, 0.0F, -0));

        return meshdefinition;
    }
}

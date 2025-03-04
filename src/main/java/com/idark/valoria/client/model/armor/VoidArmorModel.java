package com.idark.valoria.client.model.armor;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;

public class VoidArmorModel{

    public static MeshDefinition addPieces(CubeDeformation deformation) {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(deformation, 0);
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
        .texOffs(0, 0).addBox(-5.0F, -10.0F, -1.0F, 2.0F, 4.0F, 2.0F, deformation)
        .texOffs(0, 0).addBox(3.0F, -10.0F, -1.0F, 2.0F, 4.0F, 2.0F, deformation)
        .texOffs(53, 1).addBox(-5.75F, -2.75F, -5.5F, 2.0F, 3.0F, 2.0F, deformation.extend(-0.75F))
        .texOffs(53, 1).addBox(3.75F, -2.5F, -5.5F, 2.0F, 3.0F, 2.0F, deformation.extend(-0.75F))
        .texOffs(24, 4).addBox(-3.0F, -1.5F, -5.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F))
        .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, deformation)
        .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));


        return meshdefinition;
    }
}

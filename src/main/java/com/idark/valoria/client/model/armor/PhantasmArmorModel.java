package com.idark.valoria.client.model.armor;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;

public class PhantasmArmorModel{

    public static MeshDefinition addPieces(CubeDeformation deformation) {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(deformation, 0);
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
        .texOffs(53, 1).addBox(-5.75F, -2.75F, -5.5F, 2.0F, 3.0F, 2.0F, deformation.extend(-1.25F))
        .texOffs(53, 1).addBox(3.75F, -2.5F, -5.5F, 2.0F, 3.0F, 2.0F, deformation.extend(-1.25F))
        .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, deformation)

        .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, deformation), PartPose.offsetAndRotation(4.0F, -5.0F, -1.0F, 0.0F, 0.0F, 0.6981F));
        PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, deformation), PartPose.offsetAndRotation(-4.0F, -5.0F, -1.0F, 0.0F, 0.0F, -0.6981F));
        PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, deformation), PartPose.offsetAndRotation(4.0F, -8.0F, 2.0F, 0.0F, 0.0F, 0.3981F));
        PartDefinition cube_r4 = head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, deformation), PartPose.offsetAndRotation(-4.0F, -8.0F, 2.0F, 0.0F, 0.0F, -0.3981F));

        return meshdefinition;
    }
}

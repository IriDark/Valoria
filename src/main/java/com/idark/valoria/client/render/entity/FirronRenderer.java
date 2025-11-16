package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.living.boss.firron.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import software.bernie.geckolib.renderer.*;

public class FirronRenderer extends GeoEntityRenderer<Firron>{
    
    public FirronRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FirronModel());
    }

    @Override
    public ResourceLocation getTextureLocation(Firron animatable) {
        return Valoria.loc("textures/entity/firron.png");
    }

    @Override
    public void render(Firron entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

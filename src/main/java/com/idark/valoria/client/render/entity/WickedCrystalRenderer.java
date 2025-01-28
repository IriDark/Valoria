package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.event.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.living.boss.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;

public class WickedCrystalRenderer extends MobRenderer<WickedCrystal, WickedCrystalModel<WickedCrystal>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/wicked_crystal_phase1.png");
    protected static final ResourceLocation TEXTURE_PHASE = new ResourceLocation(Valoria.ID, "textures/entity/wicked_crystal_phase2.png");

    public WickedCrystalRenderer(EntityRendererProvider.Context context){
        super(context, new WickedCrystalModel<>(WickedCrystalModel.createBodyLayer().bakeRoot()), 0.5F);
    }

    @Override
    public void render(WickedCrystal pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight){
        double ticks = (ClientTickHandler.ticksInGame + pPartialTicks);
        double ticksUp = (ClientTickHandler.ticksInGame + pPartialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        pMatrixStack.pushPose();
        pMatrixStack.translate(0F, (float)(Math.sin(Math.toRadians(ticksUp)) * 0.12125F), 0F);
        pMatrixStack.mulPose(Axis.YP.rotationDegrees((float)ticks));
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        pMatrixStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(WickedCrystal pEntity){
        return pEntity.getHealth() < 1000 ? TEXTURE_PHASE : TEXTURE;
    }
}
package com.idark.valoria.client.render.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.model.entity.WickedCrystalModel;
import com.idark.valoria.registries.entity.living.boss.WickedCrystal;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WickedCrystalRenderer extends MobRenderer<WickedCrystal, WickedCrystalModel<WickedCrystal>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/wicked_crystal_phase1.png");
    protected static final ResourceLocation TEXTURE_PHASE = new ResourceLocation(Valoria.ID, "textures/entity/wicked_crystal_phase2.png");

    public WickedCrystalRenderer(EntityRendererProvider.Context context){
        super(context, new WickedCrystalModel<>(WickedCrystalModel.createBodyLayer().bakeRoot()), 0.5F);
    }

    @Override
    public void render(WickedCrystal pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight){
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(WickedCrystal pEntity){
        return pEntity.getHealth() < 1000 ? TEXTURE_PHASE : TEXTURE;
    }
}
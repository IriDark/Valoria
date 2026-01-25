package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.living.boss.dryador.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;

public class DryadorRenderer extends MobRenderer<DryadorEntity, DryadorModel<DryadorEntity>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/dryador.png");

    public DryadorRenderer(EntityRendererProvider.Context context){
        super(context, new DryadorModel<>(DryadorModel.createBodyLayer().bakeRoot()), 0.75F);
    }

    @Override
    public void render(DryadorEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight){
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(DryadorEntity pEntity){
        return TEXTURE;
    }
}
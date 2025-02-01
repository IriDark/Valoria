package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.client.render.layers.*;
import com.idark.valoria.registries.entity.living.elemental.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;

public class EntRenderer extends MobRenderer<Ent, EntModel<Ent>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/ent.png");

    public EntRenderer(EntityRendererProvider.Context context){
        super(context, new EntModel<>(EntModel.createBodyLayer().bakeRoot()), 0.4F);
        this.addLayer(new LuminescentLayer.Builder<>(this).setTexture(new ResourceLocation(Valoria.ID, "textures/entity/ent_glow.png"))
        .setAlpha(0.45f).build());
    }

    @Override
    public void render(Ent pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight){
        if(pEntity.isBaby()){
            pMatrixStack.scale(0.7f, 0.7f, 0.7f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Ent pEntity){
        return TEXTURE;
    }
}
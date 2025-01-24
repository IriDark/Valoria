package com.idark.valoria.client.render.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.model.entity.FleshSentinelModel;
import com.idark.valoria.registries.entity.living.minions.FleshSentinel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FleshSentinelRenderer extends MobRenderer<FleshSentinel, FleshSentinelModel<FleshSentinel>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/flesh_sentinel.png");

    public FleshSentinelRenderer(EntityRendererProvider.Context context){
        super(context, new FleshSentinelModel<>(FleshSentinelModel.createBodyLayer().bakeRoot()), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(FleshSentinel entity){
        return TEXTURE;
    }

    @Override
    public void render(FleshSentinel pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight){
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}

package com.idark.valoria.client.render.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.model.entity.DevilModel;
import com.idark.valoria.client.render.layers.LuminescentLayer;
import com.idark.valoria.registries.entity.living.Devil;
import com.idark.valoria.util.Pal;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class DevilRenderer extends MobRenderer<Devil, DevilModel<Devil>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/devil.png");
    protected static final ResourceLocation TEXTURE_ANGER = new ResourceLocation(Valoria.ID, "textures/entity/devil_anger.png");

    public DevilRenderer(EntityRendererProvider.Context context){
        super(context, new DevilModel<>(DevilModel.createBodyLayer().bakeRoot()), 0.4F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new LuminescentLayer.Builder<>(this)
                .setTexture(new ResourceLocation(Valoria.ID, "textures/entity/devil_eyes.png"))
                .setColor(Pal.diamond)
                .setAlpha(0.8f)
                .build());
    }

    @Override
    public void render(Devil pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight){
        if(pEntity.isBaby()){
            pMatrixStack.scale(0.7f, 0.7f, 0.7f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Devil pEntity){
        return pEntity.isAggressive() ? TEXTURE_ANGER : TEXTURE;
    }
}
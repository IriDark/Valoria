package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.projectile.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;


@OnlyIn(Dist.CLIENT)
public class AcidSpitRenderer extends EntityRenderer<AcidSpit>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/projectile/acid_spit.png");
    private final AcidSpitModel<AcidSpit> model;

    public AcidSpitRenderer(EntityRendererProvider.Context pContext){
        super(pContext);
        this.model = new AcidSpitModel<>(AcidSpitModel.createBodyLayer().bakeRoot());
    }

    public void render(AcidSpit pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight){
        pMatrixStack.pushPose();
        if(pEntity.isChild()){
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        pMatrixStack.translate(0.0D, -1.5, 0.0D);
        this.model.prepareMobModel(pEntity, pEntity.getYRot(), 0.0F, pPartialTicks);

        VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    /**
     * Returns the location of an entity's texture.
     */
    @Override
    public ResourceLocation getTextureLocation(AcidSpit pEntity){
        return TEXTURE;
    }
}
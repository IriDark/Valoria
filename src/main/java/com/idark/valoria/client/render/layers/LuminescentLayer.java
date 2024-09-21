package com.idark.valoria.client.render.layers;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import org.jetbrains.annotations.*;

public class LuminescentLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M>{
    public final ResourceLocation location;
    public float alpha;
    public float r, g, b;
    public LuminescentLayer(ResourceLocation texture, RenderLayerParent<T, M> parent) {
        super(parent);
        this.location = texture;
        this.alpha = 1;
        this.r = 1f;
        this.g = 1f;
        this.b = 1f;
    }

    public LuminescentLayer(ResourceLocation texture, float alpha, RenderLayerParent<T, M> parent) {
        super(parent);
        this.location = texture;
        this.alpha = alpha;
    }

    public LuminescentLayer(ResourceLocation texture, float alpha, float r, float g, float b, RenderLayerParent<T, M> parent) {
        super(parent);
        this.location = texture;
        this.alpha = alpha;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        VertexConsumer vertexconsumer = pBuffer.getBuffer(this.renderType());
        this.getParentModel().renderToBuffer(pPoseStack, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY, r, g, b, alpha);
    }

    @NotNull
    public RenderType renderType() {
        return RenderType.entityTranslucentEmissive(location);
    }
}

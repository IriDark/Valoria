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

import java.awt.*;

// todo move to lib
public class LuminescentLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M>{
    public final ResourceLocation location;
    private final float alpha;
    private final float r;
    private final float g;
    private final float b;
    LuminescentLayer(ResourceLocation texture, float alpha, float r, float g, float b, RenderLayerParent<T, M> parent) {
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

    public static class Builder<T extends LivingEntity, M extends EntityModel<T>> {
        private ResourceLocation texture;
        private final RenderLayerParent<T, M> parent;
        private float alpha = 1;
        private float r = 1;
        private float g = 1;
        private float b = 1;

        public Builder(RenderLayerParent<T, M> parent) {
            this.parent = parent;
        }

        public Builder<T, M> setTexture(ResourceLocation texture) {
            this.texture = texture;
            return this;
        }

        public Builder<T, M> setColor(Color color) {
            this.r = color.getRed() / 255.0F;
            this.g = color.getGreen() / 255.0F;
            this.b = color.getBlue() / 255.0F;
            this.alpha = color.getAlpha() / 255.0F;
            return this;
        }

        /**
         * @param alpha 0f - 1f
         */
        public Builder<T, M> setAlpha(float alpha) {
            this.alpha = alpha;
            return this;
        }

        /**
         * @param red 0f - 1f
         */
        public Builder<T, M> setRed(float red) {
            this.r = red;
            return this;
        }

        /**
         * @param green 0f - 1f
         */
        public Builder<T, M> setGreen(float green) {
            this.g = green;
            return this;
        }

        /**
         * @param blue 0f - 1f
         */
        public Builder<T, M> setBlue(float blue) {
            this.b = blue;
            return this;
        }

        public LuminescentLayer<T, M> build() {
            return new LuminescentLayer<>(texture, r, g, b, alpha, parent);
        }
    }
}

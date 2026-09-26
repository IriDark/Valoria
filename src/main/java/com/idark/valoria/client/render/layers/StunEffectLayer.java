package com.idark.valoria.client.render.layers;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.client.player.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import org.joml.*;

import java.lang.Math;

public class StunEffectLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>{
    private static final ResourceLocation STAR_TEXTURE = Valoria.loc("textures/entity/stun_star.png");
    public StunEffectLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if(!player.hasEffect(EffectsRegistry.STUN)) return;
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(STAR_TEXTURE));
        float time = ageInTicks + partialTick;

        poseStack.pushPose();
        this.getParentModel().head.translateAndRotate(poseStack);
        poseStack.translate(0.0D, -0.5D, 0.0D);
        int starCount = 3;
        float radius = 0.6f;
        float speed = 7.0F;

        for (int i = 0; i < starCount; i++) {
            poseStack.pushPose();
            float angle = (time * speed) + (i * (360.0F / starCount));
            poseStack.mulPose(Axis.YP.rotationDegrees(angle));

            float y = (float) Math.sin((time * 0.1F) + (i * 2.0F)) * 0.15F;
            poseStack.translate(0.0D, y, radius);
            float scale = -0.75f;
            poseStack.scale(scale, scale, scale);
            renderQuad(poseStack, vertexConsumer, LightTexture.pack(15, 15));
            poseStack.popPose();
        }

        poseStack.popPose();
    }

    private void renderQuad(PoseStack poseStack, VertexConsumer consumer, int light) {
        Matrix4f matrix = poseStack.last().pose();
        PoseStack.Pose normal = poseStack.last();

        addVertex(consumer, matrix, normal, light, -0.5F, -0.5F, 0, 1);
        addVertex(consumer, matrix, normal, light, 0.5F, -0.5F, 1, 1);
        addVertex(consumer, matrix, normal, light, 0.5F, 0.5F, 1, 0);
        addVertex(consumer, matrix, normal, light, -0.5F, 0.5F, 0, 0);
    }

    private void addVertex(VertexConsumer consumer, Matrix4f matrix, PoseStack.Pose normal, int light, float x, float y, float u, float v) {
        var player = Minecraft.getInstance().player;
        int alpha = 255;
        if(player == null || !player.hasEffect(EffectsRegistry.STUN)) return;

        var effect = player.getEffect(EffectsRegistry.STUN);
        if (effect.endsWithin(200)) {
            int k = effect.getDuration();
            int l = 10 - k / 20;
            alpha = (int)((Mth.clamp((float)k / 10.0F / 5.0F * 0.5F, 0.0F, 0.5F) + Mth.cos((float)k * (float)Math.PI / 5.0F) * Mth.clamp((float)l / 10.0F * 0.25F, 0.0F, 0.25F)) * 255);
        }

        consumer.addVertex(matrix, x, y, 0.0F)
                .setColor(255, 255, 255, alpha)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(normal, 0.0F, 1.0F, 0.0F);
    }
}
package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.shaders.*;
import com.idark.valoria.registries.entity.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import org.joml.*;

import java.lang.Math;

import static pro.komaru.tridot.client.render.TridotRenderTypes.getDelayedRender;

public class RiftRenderer extends EntityRenderer<RiftEntity> {
    private static final ResourceLocation TEXTURE = Valoria.loc("textures/entity/rift.png");
    private static final RenderType RENDER_TYPE = ShaderRegistry.RIFT_RENDER_TYPE;

    public RiftRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(RiftEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        pMatrixStack.mulPose(Axis.YP.rotationDegrees(180.0F));

        float maxLife = pEntity.getMaxLifeTime();
        float currentLife = pEntity.getLifeTime() - pPartialTicks;
        float age = maxLife - currentLife;

        float scaleAnim = 1.0F;
        if (age < 15.0F) {
            scaleAnim = (float) Math.sin((age / 15.0F) * (Math.PI / 2));
        } else if (currentLife < 15.0F) {
            scaleAnim = (float) Math.sin((currentLife / 15.0F) * (Math.PI / 2));
        }

        float hw = 1.0F;
        float hh = 1.5F;
        pMatrixStack.translate(0.0F, hh, 0.0F);
        pMatrixStack.scale(hw * scaleAnim, hh * scaleAnim, 1.0F);

        Matrix4f pose = pMatrixStack.last().pose();

        VertexConsumer consumer = getDelayedRender().getBuffer(RENDER_TYPE);
        
        vertex(consumer, pose, -1.0F, -1.0F, 0.0F, 1.0F);
        vertex(consumer, pose, 1.0F, -1.0F, 1.0F, 1.0F);
        vertex(consumer, pose, 1.0F,  1.0F, 1.0F, 0.0F);
        vertex(consumer, pose, -1.0F,  1.0F, 0.0F, 0.0F);

        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, 15728640);
    }

    private static void vertex(VertexConsumer pConsumer, Matrix4f pPose, float pX, float pY, float pU, float pV) {
        pConsumer.addVertex(pPose, pX, pY, 0.0F)
                 .setColor(255, 255, 255, 255)
                 .setUv(pU, pV);
    }

    @Override
    public ResourceLocation getTextureLocation(RiftEntity pEntity) {
        return TEXTURE;
    }
}

package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.living.elemental.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class KingCrabRenderer extends MobRenderer<KingCrabEntity, KingCrabModel<KingCrabEntity>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/king_crab.png");
    protected static final ResourceLocation TEXTURE_SHIELDED = new ResourceLocation(Valoria.ID, "textures/entity/king_crab_shielded.png");

    public KingCrabRenderer(EntityRendererProvider.Context context){
        super(context, new KingCrabModel<>(KingCrabModel.createBodyLayer().bakeRoot()), 0.8F);
    }

    @Override
    public void render(KingCrabEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight){
        if(pEntity.isBaby()){
            pMatrixStack.scale(0.7f, 0.7f, 0.7f);
        }

        Minecraft minecraft = Minecraft.getInstance();
        float f = Mth.rotLerp(pPartialTicks, pEntity.yBodyRotO, pEntity.yBodyRot);
        float f1 = Mth.rotLerp(pPartialTicks, pEntity.yHeadRotO, pEntity.yHeadRot);
        float f2 = f1 - f;

        float f6 = Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot());
        if (isEntityUpsideDown(pEntity)) {
            f6 *= -1.0F;
            f2 *= -1.0F;
        }

        boolean flag = this.isBodyVisible(pEntity);
        boolean flag1 = !flag && !pEntity.isInvisibleTo(minecraft.player);
        boolean flag2 = minecraft.shouldEntityAppearGlowing(pEntity);
        RenderType rendertype = this.getRenderType(pEntity, flag, flag1, flag2);
        if (pEntity.shieldHurtTime > 0 && rendertype != null) {
            pMatrixStack.pushPose();

            float f7 = this.getBob(pEntity, pPartialTicks);
            this.setupRotations(pEntity, pMatrixStack, f7, f, pPartialTicks);
            pMatrixStack.scale(-1.0F, -1.0F, 1.0F);
            this.scale(pEntity, pMatrixStack, pPartialTicks);
            pMatrixStack.translate(0.0F, -1.501F, 0.0F);
            float f8 = 0.0F;
            float f5 = 0.0F;

            this.model.prepareMobModel(pEntity, f5, f8, pPartialTicks);
            this.model.setupAnim(pEntity, f5, f8, f7, f2, f6);

            VertexConsumer buffer = pBuffer.getBuffer(rendertype);
            int overlay = LivingEntityRenderer.getOverlayCoords(pEntity, 0.5F);

            this.model.renderToBuffer(pMatrixStack, buffer, pPackedLight, overlay, 0.5f, 0.5f, 0.5f, 1.0f);

            pMatrixStack.popPose();
        } else {
            super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(KingCrabEntity pEntity){
        if(pEntity.getHits() > 0) return TEXTURE_SHIELDED;
        return TEXTURE;
    }
}
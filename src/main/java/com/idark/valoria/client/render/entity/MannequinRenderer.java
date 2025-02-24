package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.living.decoration.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.*;
import org.joml.*;

import java.awt.*;
import java.lang.Math;
import java.text.*;
import java.util.*;

@OnlyIn(Dist.CLIENT)
public class MannequinRenderer extends MobRenderer<MannequinEntity, MannequinModel<MannequinEntity>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/mannequin.png");
    private static final DecimalFormat FORMAT = new DecimalFormat("###.##", new DecimalFormatSymbols(Locale.ENGLISH));

    public MannequinRenderer(EntityRendererProvider.Context context){
        super(context, new MannequinModel<>(MannequinModel.createBodyLayer().bakeRoot()), 0.0F);
    }

    @Override
    public void render(MannequinEntity entityIn, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn){
        super.render(entityIn, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
        float lastDamage = entityIn.getLastDamage();
        if(lastDamage > 0f){
            renderText(entityIn, FORMAT.format(lastDamage), stack, bufferIn, packedLightIn, Color.RED);
        }
    }

    protected void renderText(MannequinEntity entityIn, String text, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Color textColor){
        if(entityIn.hurtTime > 0){
            float partialTicks = Minecraft.getInstance().getFrameTime();
            Component component = Component.literal(text);
            entityIn.lastDamageOffset = Mth.lerp(partialTicks, entityIn.lastDamageOffsetPrev, (float)Math.abs(Math.sin(((float)entityIn.hurtTime) / 4f)));
            entityIn.lastDamageOffsetPrev = entityIn.lastDamageOffset;
            float alpha = entityIn.lastDamageOffset;

            matrixStackIn.pushPose();
            matrixStackIn.translate(0, entityIn.getBbHeight() + entityIn.lastDamageOffset, 0.0D);
            matrixStackIn.mulPose(this.entityRenderDispatcher.cameraOrientation());
            matrixStackIn.scale(-entityIn.lastDamageOffset / 20f, -entityIn.lastDamageOffset / 20f, entityIn.lastDamageOffset / 20f);
            Matrix4f matrix4f = matrixStackIn.last().pose();

            Font font = this.getFont();
            Color color = new Color(textColor.getRed() / 255f, textColor.getGreen() / 255f, textColor.getBlue() / 255f, alpha);
            font.drawInBatch(component, (float)(-font.width(component) / 2) * entityIn.lastDamageOffset, entityIn.lastDamageOffset, color.getRGB(), false, matrix4f, bufferIn, Font.DisplayMode.NORMAL, 0, packedLightIn);
            matrixStackIn.popPose();
        }
    }

    @Override
    public ResourceLocation getTextureLocation(MannequinEntity entity){
        return TEXTURE;
    }
}
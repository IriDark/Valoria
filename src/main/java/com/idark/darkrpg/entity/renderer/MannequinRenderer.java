package com.idark.darkrpg.entity.renderer;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.entity.custom.MannequinEntity;
import com.idark.darkrpg.entity.model.MannequinModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.StringTextComponent;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MannequinRenderer extends MobRenderer<MannequinEntity, MannequinModel<MannequinEntity>>
{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/mannequin.png");

    private static final DecimalFormat FORMAT = new DecimalFormat("###.##",new DecimalFormatSymbols(Locale.ENGLISH));

    public MannequinRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn,new MannequinModel<>(),0.7F);
    }

    @Override
    public void render(MannequinEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        float lastDamage =entityIn.getLastDamage();
        if (lastDamage > 0f) {
            renderText(entityIn, FORMAT.format(lastDamage), matrixStackIn, bufferIn, packedLightIn, Color.RED);
        }
    }

    protected void renderText(MannequinEntity entityIn, String text, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,Color textColor) {
        if (entityIn.hurtTime > 0) {
            float partialTicks = Minecraft.getInstance().getRenderPartialTicks();
            StringTextComponent stringTextComponent = new StringTextComponent(text);
            entityIn.lastDamageOffset = MathHelper.lerp(partialTicks,entityIn.lastDamageOffsetPrev,(float) Math.abs(Math.sin(((float)entityIn.hurtTime)/4f)));
            entityIn.lastDamageOffsetPrev = entityIn.lastDamageOffset;
            float alpha = entityIn.lastDamageOffset;
            matrixStackIn.push();
            matrixStackIn.translate(0, entityIn.getHeight()+entityIn.lastDamageOffset, 0.0D);
            matrixStackIn.rotate(this.renderManager.getCameraOrientation());
            matrixStackIn.scale(-entityIn.lastDamageOffset/20f, -entityIn.lastDamageOffset/20f, entityIn.lastDamageOffset/20f);
            Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
            FontRenderer fontrenderer = this.getFontRendererFromRenderManager();
            Color color = new Color(textColor.getRed()/255f,textColor.getGreen()/255f,textColor.getBlue()/255f,alpha);
            fontrenderer.func_243247_a(stringTextComponent, (float)(-fontrenderer.getStringPropertyWidth(stringTextComponent) / 2), entityIn.lastDamageOffset, color.getRGB(), false, matrix4f, bufferIn, false, 0, packedLightIn);
            matrixStackIn.pop();
        }
    }

    @Override
    public ResourceLocation getEntityTexture(MannequinEntity entity) {
        return TEXTURE;
	}
}
package com.idark.valoria.client.render;

import com.idark.valoria.*;
import com.mojang.blaze3d.platform.*;
import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import org.joml.*;
import pro.komaru.tridot.client.*;

import javax.annotation.*;

@OnlyIn(Dist.CLIENT)
public class ValoriaEffects extends DimensionSpecialEffects {
    private static final ResourceLocation ARETHEA_LOCATION = new ResourceLocation(Valoria.ID, "textures/environment/arethea.png");
    private static final ResourceLocation EARTH_LOCATION = new ResourceLocation(Valoria.ID, "textures/environment/earth.png");

    private static final ResourceLocation MOON_LOCATION = new ResourceLocation("textures/environment/moon_phases.png");

    public ValoriaEffects(){
        super(Float.NaN, true, SkyType.NORMAL, false, true);
    }

    public boolean tickRain(ClientLevel level, int ticks, Camera camera){
        return true;
    }

    public boolean renderClouds(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, double camX, double camY, double camZ, Matrix4f projectionMatrix){
        return true;
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 skyColor, float partialTicks) {
        return new Vec3(0.125f, 0.025f, 0.125f);
    }

    @Override
    public boolean isFoggyAt(int x, int y) {
        return false;
    }

    @Nullable
    public float[] getSunriseColor(float pTimeOfDay, float pPartialTicks){
        return null;
    }

    private boolean doesMobEffectBlockSky(Camera pCamera){
        Entity entity = pCamera.getEntity();
        if(!(entity instanceof LivingEntity livingentity)){
            return false;
        }else{
            return livingentity.hasEffect(MobEffects.BLINDNESS) || livingentity.hasEffect(MobEffects.DARKNESS);
        }
    }

    public boolean renderSky(ClientLevel level, int ticks, float pPartialTick, PoseStack pPoseStack, Camera pCamera, Matrix4f pProjectionMatrix, boolean pIsFoggy, Runnable pSkyFogSetup){
        var mc = Minecraft.getInstance();
        LevelRenderer renderer = mc.levelRenderer;
        pSkyFogSetup.run();
        FogType fogtype = pCamera.getFluidInCamera();
        if(fogtype != FogType.POWDER_SNOW && fogtype != FogType.LAVA && !doesMobEffectBlockSky(pCamera)){
            Vec3 vec3 = level.getSkyColor(mc.gameRenderer.getMainCamera().getPosition(), pPartialTick);
            float f = (float)vec3.x;
            float f1 = (float)vec3.y;
            float f2 = (float)vec3.z;
            FogRenderer.levelFogColor();
            BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
            RenderSystem.depthMask(false);
            RenderSystem.setShaderColor(f, f1, f2, 1.0F);
            ShaderInstance shaderinstance = RenderSystem.getShader();
            renderer.skyBuffer.bind();
            renderer.skyBuffer.drawWithShader(pPoseStack.last().pose(), pProjectionMatrix, shaderinstance);
            VertexBuffer.unbind();
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            pPoseStack.pushPose();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));

            long totalTicks = (long)(ClientTick.ticksInGame * 0.5f);
            float dayFraction = (totalTicks % 24000L + pPartialTick) / 24000.0F;
            float rotation = dayFraction * 360.0F;

            pPoseStack.mulPose(Axis.XP.rotationDegrees(rotation));
            Matrix4f matrix4f1 = pPoseStack.last().pose();
            float f12 = 15f;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, MOON_LOCATION);
            int k = level.getMoonPhase();
            int l = k % 4;
            int i1 = k / 4 % 2;
            float f13 = (float)(l) / 4.0F;
            float f14 = (float)(i1) / 2.0F;
            float f15 = (float)(l + 1) / 4.0F;
            float f16 = (float)(i1 + 1) / 2.0F;
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(matrix4f1, -f12, -100.0F, f12).uv(f15, f16).endVertex();
            bufferbuilder.vertex(matrix4f1, f12, -100.0F, f12).uv(f13, f16).endVertex();
            bufferbuilder.vertex(matrix4f1, f12, -100.0F, -f12).uv(f13, f14).endVertex();
            bufferbuilder.vertex(matrix4f1, -f12, -100.0F, -f12).uv(f15, f14).endVertex();
            BufferUploader.drawWithShader(bufferbuilder.end());

            RenderSystem.setShaderTexture(0, ARETHEA_LOCATION);
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(matrix4f1, -f12, 100.0F, -f12).uv(0.0F, 0.0F).endVertex();
            bufferbuilder.vertex(matrix4f1, 4, 100.0F, -f12).uv(1.0F, 0.0F).endVertex();
            bufferbuilder.vertex(matrix4f1, 4, 100.0F, 4).uv(1.0F, 1.0F).endVertex();
            bufferbuilder.vertex(matrix4f1, -f12, 100.0F, 4).uv(0.0F, 1.0F).endVertex();
            BufferUploader.drawWithShader(bufferbuilder.end());

            RenderSystem.setShaderTexture(0, EARTH_LOCATION);
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(matrix4f1, -5, -100.0F, 45).uv(0, 0).endVertex();
            bufferbuilder.vertex(matrix4f1, 45, -100.0F, 45).uv(1, 0).endVertex();
            bufferbuilder.vertex(matrix4f1, 45, -100.0F, -5).uv(1, 1).endVertex();
            bufferbuilder.vertex(matrix4f1, -5, -100.0F, -5).uv(0, 1).endVertex();
            BufferUploader.drawWithShader(bufferbuilder.end());

            RenderSystem.setShaderColor(0.25f, 0.25f, 0.25f, 1);
            FogRenderer.setupNoFog();
            renderer.starBuffer.bind();
            renderer.starBuffer.drawWithShader(pPoseStack.last().pose(), pProjectionMatrix, GameRenderer.getPositionShader());
            VertexBuffer.unbind();
            pSkyFogSetup.run();

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            pPoseStack.popPose();
            RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
            double d0 = mc.player.getEyePosition(pPartialTick).y - level.getLevelData().getHorizonHeight(level);
            if(d0 < 0.0D){
                pPoseStack.pushPose();
                pPoseStack.translate(0.0F, 12.0F, 0.0F);
                renderer.darkBuffer.bind();
                renderer.darkBuffer.drawWithShader(pPoseStack.last().pose(), pProjectionMatrix, shaderinstance);
                VertexBuffer.unbind();
                pPoseStack.popPose();
            }

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.depthMask(true);
        }


        return true;
    }
}
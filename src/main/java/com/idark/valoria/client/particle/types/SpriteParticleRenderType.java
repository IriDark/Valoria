package com.idark.valoria.client.particle.types;

import com.idark.valoria.*;
import com.idark.valoria.util.*;
import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.*;
import org.lwjgl.opengl.*;

public class SpriteParticleRenderType implements ParticleRenderType{
    public static final SpriteParticleRenderType INSTANCE = new SpriteParticleRenderType();

    private static void beginRenderCommon(BufferBuilder bufferBuilder, TextureManager textureManager){
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShader(ValoriaClient::getSpriteParticleShader);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
        RenderUtils.particleMVMatrix = RenderSystem.getModelViewMatrix();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
    }

    private static void endRenderCommon(){
        Minecraft.getInstance().textureManager.getTexture(TextureAtlas.LOCATION_PARTICLES).restoreLastBlurMipmap();
        RenderSystem.depthMask(true);
    }

    @Override
    public void begin(BufferBuilder b, TextureManager tex){
        beginRenderCommon(b, tex);
    }

    @Override
    public void end(Tesselator t){
        t.end();
        RenderSystem.enableDepthTest();
        endRenderCommon();
    }
}
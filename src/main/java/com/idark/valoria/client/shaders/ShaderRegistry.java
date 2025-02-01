package com.idark.valoria.client.shaders;

import com.idark.valoria.*;
import com.idark.valoria.client.render.tile.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.event.lifecycle.*;

import java.io.*;

import static pro.komaru.tridot.client.TridotRenderTypes.*;


@OnlyIn(Dist.CLIENT)
public class ShaderRegistry{
    public static ShaderInstance VALORIA_PORTAL;

    public static ShaderInstance getValoriaPortal(){
        return VALORIA_PORTAL;
    }

    public static final RenderStateShard.ShaderStateShard VALORIA_PORTAL_SHADER = new RenderStateShard.ShaderStateShard(ShaderRegistry::getValoriaPortal);
    public static final RenderType VALORIA_PORTAL_RENDER_TYPE = RenderType.create(Valoria.ID + ":valoria_portal", DefaultVertexFormat.POSITION, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.builder().setShaderState(VALORIA_PORTAL_SHADER).setWriteMaskState(COLOR_WRITE).setTransparencyState(NORMAL_TRANSPARENCY).setTextureState(RenderStateShard.MultiTextureStateShard.builder().add(ValoriaPortalRenderer.BACKGROUND_LOC, false, false).add(ValoriaPortalRenderer.LAYER_LOC, false, false).build()).createCompositeState(false));

    public static RenderType valoriaPortal(){
        return VALORIA_PORTAL_RENDER_TYPE;
    }

    public static void registerRenderTypes(FMLClientSetupEvent event){
        addTranslucentRenderType(ShaderRegistry.VALORIA_PORTAL_RENDER_TYPE);
    }

    public static void shaderRegistry(RegisterShadersEvent event) throws IOException{
        event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(Valoria.ID, "valoria_portal"), DefaultVertexFormat.POSITION), shader -> ShaderRegistry.VALORIA_PORTAL = shader);
    }
}

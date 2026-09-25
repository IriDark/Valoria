package com.idark.valoria.client.shaders;

import com.idark.valoria.*;
import com.idark.valoria.client.render.tile.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.event.lifecycle.*;
import pro.komaru.tridot.client.render.*;

import java.io.*;

import static pro.komaru.tridot.client.render.TridotRenderTypes.*;


@OnlyIn(Dist.CLIENT)
public class ShaderRegistry{

    public static ShaderInstance VALORIA_PORTAL;
    public static ShaderInstance RIFT_SWIRL;

    public static ShaderInstance getValoriaPortal(){
        return VALORIA_PORTAL;
    }

    public static ShaderInstance getRiftSwirl(){
        return RIFT_SWIRL;
    }

    public static final RenderStateShard.ShaderStateShard VALORIA_PORTAL_SHADER = new RenderStateShard.ShaderStateShard(ShaderRegistry::getValoriaPortal);
    public static final RenderType VALORIA_PORTAL_RENDER_TYPE = RenderType.create(Valoria.ID + ":valoria_portal", DefaultVertexFormat.POSITION, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.builder().setShaderState(VALORIA_PORTAL_SHADER).setWriteMaskState(COLOR_WRITE).setTransparencyState(NORMAL_TRANSPARENCY).setTextureState(RenderStateShard.MultiTextureStateShard.builder().add(ValoriaPortalRenderer.BACKGROUND_LOC, false, false).add(ValoriaPortalRenderer.LAYER_LOC, false, false).build()).createCompositeState(false));
    public static final RenderStateShard.ShaderStateShard RIFT_SWIRL_SHADER = new RenderStateShard.ShaderStateShard(ShaderRegistry::getRiftSwirl);
    public static final RenderType RIFT_RENDER_TYPE = ShaderRegistry.riftSwirl(Valoria.loc("textures/entity/rift.png"));

    public static RenderType riftSwirl(ResourceLocation texture) {
        RenderType.CompositeState state = RenderType.CompositeState.builder()
        .setShaderState(RIFT_SWIRL_SHADER)
        .setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
        .setTransparencyState(TridotRenderTypes.NORMAL_TRANSPARENCY)
        .setCullState(TridotRenderTypes.NO_CULL)
        .setWriteMaskState(TridotRenderTypes.COLOR_WRITE)
        .createCompositeState(false);

        return RenderType.create(Valoria.ID + ":rift_swirl", DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, false, false, state);
    }

    public static RenderType valoriaPortal(){
        return VALORIA_PORTAL_RENDER_TYPE;
    }

    public static void registerRenderTypes(FMLClientSetupEvent event){
        addTranslucentRenderType(ShaderRegistry.VALORIA_PORTAL_RENDER_TYPE);
        addTranslucentRenderType(ShaderRegistry.RIFT_RENDER_TYPE);
    }

    public static void shaderRegistry(RegisterShadersEvent event) throws IOException{
        event.registerShader(new ShaderInstance(event.getResourceProvider(), Valoria.loc("valoria_portal"), DefaultVertexFormat.POSITION), shader -> ShaderRegistry.VALORIA_PORTAL = shader);
        event.registerShader(new ShaderInstance(event.getResourceProvider(), Valoria.loc("rift_swirl"), DefaultVertexFormat.POSITION_COLOR_TEX), shader -> ShaderRegistry.RIFT_SWIRL = shader);
    }
}

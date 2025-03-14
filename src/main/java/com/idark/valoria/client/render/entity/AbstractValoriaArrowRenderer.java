package com.idark.valoria.client.render.entity;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.common.registry.entity.projectiles.*;

@OnlyIn(Dist.CLIENT)
public class AbstractValoriaArrowRenderer<T extends AbstractTridotArrow> extends ArrowRenderer<T>{

    public AbstractValoriaArrowRenderer(EntityRendererProvider.Context context){
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(T arrow){
        if(arrow instanceof TexturedArrow texture){
            return texture.getTexture();
        }

        return null;
    }
}
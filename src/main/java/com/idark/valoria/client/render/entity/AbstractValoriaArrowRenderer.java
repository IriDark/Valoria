package com.idark.valoria.client.render.entity;

import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.core.interfaces.*;

@OnlyIn(Dist.CLIENT)
public class AbstractValoriaArrowRenderer<T extends AbstractValoriaArrow> extends ArrowRenderer<T>{

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
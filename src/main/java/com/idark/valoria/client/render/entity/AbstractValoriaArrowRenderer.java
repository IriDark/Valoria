package com.idark.valoria.client.render.entity;

import com.idark.valoria.core.interfaces.TexturedArrow;
import com.idark.valoria.registries.entity.projectile.AbstractValoriaArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AbstractValoriaArrowRenderer<T extends AbstractValoriaArrow> extends ArrowRenderer<T> {

    public AbstractValoriaArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(T arrow) {
        if (arrow instanceof TexturedArrow texture) {
            return texture.getTexture();
        }

        return null;
    }
}
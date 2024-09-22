package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.client.render.layers.*;
import com.idark.valoria.registries.entity.living.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class ShadewoodSpiderRenderer<T extends ShadewoodSpider> extends MobRenderer<T, ShadewoodSpiderModel<T>> {
    private static final ResourceLocation SPIDER_LOCATION = new ResourceLocation(Valoria.ID, "textures/entity/shadewood_spider.png");

    public ShadewoodSpiderRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ShadewoodSpiderModel<>(ShadewoodSpiderModel.createBodyLayer().bakeRoot()), 0.8F);
        this.addLayer(new LuminescentLayer.Builder<>(this)
        .setTexture(new ResourceLocation(Valoria.ID, "textures/entity/shadewood_spider_eyes.png"))
        .build());
    }

    protected float getFlipDegrees(T pLivingEntity) {
        return 180.0F;
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(T pEntity) {
        return SPIDER_LOCATION;
    }
}
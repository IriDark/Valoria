package com.idark.valoria.client.render.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.render.layers.ShadewoodSpiderEyeLayer;
import com.idark.valoria.client.render.model.entity.ShadewoodSpiderModel;
import com.idark.valoria.registries.entity.living.ShadewoodSpider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShadewoodSpiderRenderer<T extends ShadewoodSpider> extends MobRenderer<T, ShadewoodSpiderModel<T>> {
    private static final ResourceLocation SPIDER_LOCATION = new ResourceLocation(Valoria.ID, "textures/entity/shadewood_spider.png");

    public ShadewoodSpiderRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ShadewoodSpiderModel<>(ShadewoodSpiderModel.createBodyLayer().bakeRoot()), 0.8F);
        this.addLayer(new ShadewoodSpiderEyeLayer<>(this));
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
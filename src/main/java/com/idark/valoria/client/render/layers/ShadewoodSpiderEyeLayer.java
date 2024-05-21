package com.idark.valoria.client.render.layers;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.render.model.entity.ShadewoodSpiderModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShadewoodSpiderEyeLayer<T extends Entity, M extends ShadewoodSpiderModel<T>> extends EyesLayer<T, M>{
    private static final RenderType SPIDER_EYES = RenderType.eyes(new ResourceLocation(Valoria.ID, "textures/entity/shadewood_spider_eyes.png"));

    public ShadewoodSpiderEyeLayer(RenderLayerParent<T, M> p_117507_){
        super(p_117507_);
    }

    public RenderType renderType(){
        return SPIDER_EYES;
    }
}
package com.idark.valoria.client.render.layers;

import com.idark.valoria.*;
import com.idark.valoria.client.render.model.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraftforge.api.distmarker.*;

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
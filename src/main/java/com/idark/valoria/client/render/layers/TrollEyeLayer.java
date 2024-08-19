package com.idark.valoria.client.render.layers;

import com.idark.valoria.*;
import com.idark.valoria.client.render.model.entity.*;
import com.idark.valoria.registries.entity.living.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class TrollEyeLayer<T extends Troll, M extends TrollModel<T>> extends EyesLayer<T, M>{
    private static final RenderType EYES = RenderType.eyes(new ResourceLocation(Valoria.ID, "textures/entity/troll_eyes_layer.png"));

    public TrollEyeLayer(RenderLayerParent<T, M> p_117507_){
        super(p_117507_);
    }

    public RenderType renderType(){
        return EYES;
    }
}
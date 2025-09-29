package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.living.decoration.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class MannequinRenderer extends MobRenderer<MannequinEntity, MannequinModel<MannequinEntity>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/mannequin.png");

    public MannequinRenderer(EntityRendererProvider.Context context){
        super(context, new MannequinModel<>(MannequinModel.createBodyLayer().bakeRoot()), 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(MannequinEntity entity){
        return TEXTURE;
    }
}
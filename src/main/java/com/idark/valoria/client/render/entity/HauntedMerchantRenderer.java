package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.living.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;

public class HauntedMerchantRenderer extends MobRenderer<HauntedMerchant, HauntedMerchantModel<HauntedMerchant>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/haunted_merchant.png");

    public HauntedMerchantRenderer(EntityRendererProvider.Context context){
        super(context, new HauntedMerchantModel<>(HauntedMerchantModel.createBodyLayer().bakeRoot()), 1.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(HauntedMerchant pEntity){
        return TEXTURE;
    }
}
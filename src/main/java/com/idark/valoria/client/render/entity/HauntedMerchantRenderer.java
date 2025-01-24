package com.idark.valoria.client.render.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.model.entity.HauntedMerchantModel;
import com.idark.valoria.client.render.layers.LuminescentLayer;
import com.idark.valoria.registries.entity.living.HauntedMerchant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class HauntedMerchantRenderer extends MobRenderer<HauntedMerchant, HauntedMerchantModel<HauntedMerchant>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/haunted_merchant.png");

    public HauntedMerchantRenderer(EntityRendererProvider.Context context){
        super(context, new HauntedMerchantModel<>(HauntedMerchantModel.createBodyLayer().bakeRoot()), 0.8F);
        this.addLayer(new LuminescentLayer.Builder<>(this)
                .setTexture(new ResourceLocation(Valoria.ID, "textures/entity/haunted_merchant_glow.png"))
                .setBlue(0.5f)
                .build());
    }

    @Override
    public ResourceLocation getTextureLocation(HauntedMerchant pEntity){
        return TEXTURE;
    }
}
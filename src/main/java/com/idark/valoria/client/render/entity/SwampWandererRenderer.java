package com.idark.valoria.client.render.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.world.entity.living.SwampWandererEntity;
import com.idark.valoria.client.render.model.entity.SwampWandererModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SwampWandererRenderer extends MobRenderer<SwampWandererEntity, SwampWandererModel<SwampWandererEntity>>
{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.MOD_ID, "textures/entity/swamp_wanderer.png");

    public SwampWandererRenderer(EntityRendererProvider.Context context) {
        super(context, new SwampWandererModel<>(),0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(SwampWandererEntity entity) {
        return TEXTURE;
 }
}
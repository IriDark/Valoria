package com.idark.darkrpg.entity.renderer;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.entity.custom.SwampWandererEntity;
import com.idark.darkrpg.entity.model.SwampWandererModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SwampWandererRenderer extends MobRenderer<SwampWandererEntity, SwampWandererModel<SwampWandererEntity>>
{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/swamp_wanderer.png");

    public SwampWandererRenderer(EntityRendererProvider.Context context) {
        super(context, new SwampWandererModel<>(),0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(SwampWandererEntity entity) {
        return TEXTURE;
 }
}
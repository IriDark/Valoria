package com.idark.darkrpg.entity.render;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.entity.custom.GoblinEntity;
import com.idark.darkrpg.entity.model.GoblinModel;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class GoblinRenderer extends MobRenderer<GoblinEntity, GoblinModel<GoblinEntity>>
{
    protected static final ResourceLocation TEXTURE =
    	new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/goblin.png");

    public GoblinRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn,new GoblinModel<>(),0.7F);
    }

    @Override
    public ResourceLocation getEntityTexture(GoblinEntity entity) {
        return TEXTURE;
 }
}
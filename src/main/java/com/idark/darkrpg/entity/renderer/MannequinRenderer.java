package com.idark.darkrpg.entity.render;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.entity.custom.MannequinEntity;
import com.idark.darkrpg.entity.model.MannequinModel;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class MannequinRenderer extends MobRenderer<MannequinEntity, MannequinModel<MannequinEntity>>
{
    protected static final ResourceLocation TEXTURE =
    	new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/mannequin.png");

    public MannequinRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn,new MannequinModel<>(),0.7F);
    }

    @Override
    public ResourceLocation getEntityTexture(MannequinEntity entity) {
        return TEXTURE;
 }
}
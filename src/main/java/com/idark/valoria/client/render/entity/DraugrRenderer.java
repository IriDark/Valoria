package com.idark.valoria.client.render.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.render.model.entity.DraugrModel;
import com.idark.valoria.registries.entity.living.DraugrEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DraugrRenderer extends HumanoidMobRenderer<DraugrEntity, DraugrModel<DraugrEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/draugr.png");

    public DraugrRenderer(EntityRendererProvider.Context context) {
        super(context, new DraugrModel<>(DraugrModel.createBodyLayer().bakeRoot()), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(DraugrEntity pEntity) {
        return TEXTURE;
    }

    public DraugrRenderer(EntityRendererProvider.Context pContext, ModelLayerLocation p_174383_, ModelLayerLocation pInnerModelLayer, ModelLayerLocation pOuterModelLayer) {
        super(pContext, new DraugrModel<>(pContext.bakeLayer(p_174383_)), 0.5F);
        this.addLayer(new HumanoidArmorLayer(this, new DraugrModel(pContext.bakeLayer(pInnerModelLayer)), new DraugrModel(pContext.bakeLayer(pOuterModelLayer)), pContext.getModelManager()));
    }

    protected boolean isShaking(DraugrEntity pEntity) {
        return pEntity.isShaking();
    }
}

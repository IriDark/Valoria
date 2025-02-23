package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.registries.entity.living.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class MaggotRenderer extends MobRenderer<MaggotEntity, SilverfishModel<MaggotEntity>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/maggot.png");
    protected static final ResourceLocation TEXTURE_ROT = new ResourceLocation(Valoria.ID, "textures/entity/maggot_rot.png");
    protected static final ResourceLocation TEXTURE_ROTTEN = new ResourceLocation(Valoria.ID, "textures/entity/maggot_rotten.png");

   public MaggotRenderer(EntityRendererProvider.Context p_174378_) {
      super(p_174378_, new SilverfishModel<>(p_174378_.bakeLayer(ModelLayers.SILVERFISH)), 0.3F);
   }

   protected float getFlipDegrees(MaggotEntity pLivingEntity) {
      return 180.0F;
   }

   /**
    * Returns the location of an entity's texture.
    */
   public ResourceLocation getTextureLocation(MaggotEntity pEntity) {
       return switch(pEntity.getVariant()){
           case MAGGOT -> TEXTURE;
           case MAGGOT_ROT -> TEXTURE_ROT;
           case MAGGOT_ROTTEN -> TEXTURE_ROTTEN;
       };
   }
}
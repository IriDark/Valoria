package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.living.minions.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;

@OnlyIn(Dist.CLIENT)
public class PixieRenderer extends MobRenderer<PixieEntity, PixieModel>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/pixie.png");

    public PixieRenderer(EntityRendererProvider.Context p_174435_){
        super(p_174435_, new PixieModel(PixieModel.createBodyLayer().bakeRoot()), 0.3F);
    }

    @Override
    protected int getBlockLightLevel(PixieEntity pEntity, BlockPos pPos){
        return 15;
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getTextureLocation(@NotNull PixieEntity pEntity){
        return TEXTURE;
    }
}
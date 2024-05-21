package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.render.model.entity.*;
import com.idark.valoria.registries.entity.living.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;

@OnlyIn(Dist.CLIENT)
public class UndeadRenderer extends MobRenderer<UndeadEntity, UndeadModel>{

    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/undead.png");

    public UndeadRenderer(EntityRendererProvider.Context p_174435_){
        super(p_174435_, new UndeadModel(p_174435_.bakeLayer(ModelLayers.VEX)), 0.3F);
        this.addLayer(new ItemInHandLayer<>(this, p_174435_.getItemInHandRenderer()));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getTextureLocation(@NotNull UndeadEntity pEntity){
        return TEXTURE;
    }
}
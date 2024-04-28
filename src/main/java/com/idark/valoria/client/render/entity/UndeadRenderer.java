package com.idark.valoria.client.render.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.render.model.entity.UndeadModel;
import com.idark.valoria.registries.entity.living.UndeadEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class UndeadRenderer extends MobRenderer<UndeadEntity, UndeadModel> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/undead.png");

    public UndeadRenderer(EntityRendererProvider.Context p_174435_) {
        super(p_174435_, new UndeadModel(p_174435_.bakeLayer(ModelLayers.VEX)), 0.3F);
        this.addLayer(new ItemInHandLayer<>(this, p_174435_.getItemInHandRenderer()));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getTextureLocation(@NotNull UndeadEntity pEntity) {
        return TEXTURE;
    }
}
package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.living.elemental.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.client.model.render.entity.*;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class RiverGolemRenderer extends MobRenderer<RiverGolem, RiverGolemModel<RiverGolem>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/river_golem.png");

    public RiverGolemRenderer(EntityRendererProvider.Context context){
        super(context, new RiverGolemModel<>(RiverGolemModel.createBodyLayer().bakeRoot()), 0.6F);
        this.addLayer(new LuminescentLayer.Builder<>(this).setTexture(new ResourceLocation(Valoria.ID, "textures/entity/river_golem_glow.png"))
        .setAlpha(0.5f).setColor(Color.WHITE).build());
    }

    @Override
    public ResourceLocation getTextureLocation(RiverGolem pEntity){
        return TEXTURE;
    }

    @Override
    public void render(RiverGolem pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight){
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
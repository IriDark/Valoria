package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.living.elemental.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import net.neoforged.api.distmarker.*;
import pro.komaru.tridot.client.model.render.entity.*;

@OnlyIn(Dist.CLIENT)
public class MagmaticGolemRenderer extends MobRenderer<MagmaticGolem, MagmaticGolemModel<MagmaticGolem>> {
    protected static final ResourceLocation TEXTURE = Valoria.loc("textures/entity/magmatic_golem.png");

    public MagmaticGolemRenderer(EntityRendererProvider.Context context){
        super(context, new MagmaticGolemModel<>(MagmaticGolemModel.createBodyLayer().bakeRoot()), 0.6F);
        this.addLayer(new LuminescentLayer.Builder<>(this).setTexture(Valoria.loc("textures/entity/magmatic_golem_glow.png"))
        .setAlpha(0.45f).build());
    }

    @Override
    public ResourceLocation getTextureLocation(MagmaticGolem pEntity){
        return TEXTURE;
    }

    @Override
    public void render(MagmaticGolem pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight){
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
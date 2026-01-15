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

@OnlyIn(Dist.CLIENT)
public class NatureGolemRenderer extends MobRenderer<NatureGolem, NatureGolemModel<NatureGolem>> {

    public NatureGolemRenderer(EntityRendererProvider.Context context){
        super(context, new NatureGolemModel<>(NatureGolemModel.createBodyLayer().bakeRoot()), 0.6F);
        this.addLayer(new LuminescentLayer.Builder<>(this).setTexture(new ResourceLocation(Valoria.ID, "textures/entity/nature_golem_glow.png"))
        .setAlpha(0.5f).build());
    }

    @Override
    public ResourceLocation getTextureLocation(NatureGolem pEntity){
        return pEntity.getVariant().texture();
    }

    @Override
    public void render(NatureGolem pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight){
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
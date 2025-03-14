package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.living.minions.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import pro.komaru.tridot.client.*;

public class CrystalRenderer extends MobRenderer<CrystalEntity, CrystalModel<CrystalEntity>>{
    protected static final ResourceLocation TEXTURE_ICE = new ResourceLocation(Valoria.ID, "textures/entity/crystal_ice.png");
    protected static final ResourceLocation TEXTURE_FIRE = new ResourceLocation(Valoria.ID, "textures/entity/crystal_fire.png");
    protected static final ResourceLocation TEXTURE_POISON = new ResourceLocation(Valoria.ID, "textures/entity/crystal_poison.png");

    public CrystalRenderer(EntityRendererProvider.Context context){
        super(context, new CrystalModel<>(CrystalModel.createBodyLayer().bakeRoot()), 0.5F);
    }

    @Override
    public void render(CrystalEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight){
        double ticksUp = (ClientTick.ticksInGame + pPartialTicks) * 8;
        ticksUp = (ticksUp) % 360;

        pMatrixStack.pushPose();
        pMatrixStack.translate(0F, (float)(Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        pMatrixStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(CrystalEntity pEntity){
        return switch(pEntity.getVariant()){
            case POISON -> TEXTURE_POISON;
            case ICE -> TEXTURE_ICE;
            case FIRE -> TEXTURE_FIRE;
        };
    }
}
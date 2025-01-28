package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.event.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.living.minions.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class WickedShieldRenderer extends MobRenderer<WickedShield, WickedShieldModel<WickedShield>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/item/wicked_shield.png");

    public WickedShieldRenderer(EntityRendererProvider.Context context){
        super(context, new WickedShieldModel<>(WickedShieldModel.createBodyLayer().bakeRoot()), 0.0F);
    }

    @Override
    public void render(WickedShield pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight){
        double ticksUp = (ClientTickHandler.ticksInGame + pPartialTicks) * 8;
        ticksUp = (ticksUp) % 360;

        pMatrixStack.pushPose();
        pMatrixStack.translate(0F, (float)(Math.sin(Math.toRadians(ticksUp)) * 0.15125F), 0F);
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        pMatrixStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(WickedShield pEntity){
        return TEXTURE;
    }
}
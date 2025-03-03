package com.idark.valoria.client.render.curio;

import com.idark.valoria.*;
import com.idark.valoria.client.*;
import com.idark.valoria.client.model.curio.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.client.*;

public class JewelryBagRenderer implements ICurioRenderer{
    public static ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/jewelry_bag.png");
    public static ResourceLocation OVERLAY = new ResourceLocation(Valoria.ID, "textures/entity/jewelry_bag_overlay.png");
    JewelryBagModel model = null;

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch){
        if(model == null){
            model = new JewelryBagModel(Minecraft.getInstance().getEntityModels().bakeLayer(ValoriaLayers.BAG_LAYER));
        }

        LivingEntity entity = slotContext.entity();
        ICurioRenderer.followBodyRotations(entity, model);  // breaks on epic fight anims
        model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        int k = ((DyeableLeatherItem)stack.getItem()).getColor(stack);
        float f = (float)(k >> 16 & 255) / 255.0F;
        float f1 = (float)(k >> 8 & 255) / 255.0F;
        float f2 = (float)(k & 255) / 255.0F;

        model.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(RenderType.entityCutoutNoCull(OVERLAY)), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        model.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, OverlayTexture.NO_OVERLAY, f, f1, f2, 1);
    }
}
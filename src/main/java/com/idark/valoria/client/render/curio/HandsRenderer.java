package com.idark.valoria.client.render.curio;

import com.idark.valoria.*;
import com.idark.valoria.client.render.curio.model.*;
import com.idark.valoria.registries.item.types.curio.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.client.player.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.client.*;

public class HandsRenderer implements ICurioRenderer{
    public static ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/necklace/empty.png");

    public static boolean isDefault;

    public boolean isDefault(LivingEntity entity){
        if(entity instanceof AbstractClientPlayer player){
            isDefault = player.getModelName().equals("default");
            return player.getModelName().equals("default");
        }

        return isDefault;
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch){
        LivingEntity entity = slotContext.entity();
        if(stack.getItem() instanceof ICurioTexture curio){
            TEXTURE = curio.getTexture(stack, entity);
        }

        if(!isDefault(slotContext.entity())){
            HandsModel model;
            model = new HandsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ValoriaClient.HANDS_LAYER_SLIM));
            ICurioRenderer.followBodyRotations(entity, model);
            model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            model.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }else if(isDefault(slotContext.entity())){
            HandsModelDefault model;
            model = new HandsModelDefault(Minecraft.getInstance().getEntityModels().bakeLayer(ValoriaClient.HANDS_LAYER));
            ICurioRenderer.followBodyRotations(entity, model);
            model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            model.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }
    }
}
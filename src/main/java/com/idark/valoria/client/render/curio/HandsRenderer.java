package com.idark.valoria.client.render.curio;

import com.idark.valoria.*;
import com.idark.valoria.client.*;
import com.idark.valoria.client.model.curio.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.item.types.curio.hands.*;
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
    public static ResourceLocation TEXTURE = Valoria.loc("textures/entity/necklace/empty.png");
    private HandsModel handsModel;
    private HandsModelSlim handsModelSlim;

    public boolean isDefault(LivingEntity entity){
        if(entity instanceof AbstractClientPlayer player){
            return player.getModelName().equals("default");
        }

        return false;
    }

    public float[] getColor(ItemStack stack){
        if(stack.getItem() instanceof DyeableGlovesItem){
            int color = ((DyeableLeatherItem)stack.getItem()).getColor(stack);
            float r = (float)(color >> 16 & 255) / 255.0F;
            float g = (float)(color >> 8 & 255) / 255.0F;
            float b = (float)(color & 255) / 255.0F;
            return new float[]{r, g, b};
        }else{
            return new float[]{1, 1, 1};
        }
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch){
        LivingEntity entity = slotContext.entity();
        if(stack.getItem() instanceof ICurioTexture curio){
            TEXTURE = curio.getTexture(stack, entity);
        }

        float[] color = getColor(stack);
        boolean isSlim = !isDefault(entity);
        
        HandsModel model;
        if (isSlim) {
            if (handsModelSlim == null) handsModelSlim = new HandsModelSlim(Minecraft.getInstance().getEntityModels().bakeLayer(ValoriaLayers.HANDS_LAYER_SLIM));
            model = handsModelSlim;
        } else {
            if (handsModel == null) handsModel = new HandsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ValoriaLayers.HANDS_LAYER));
            model = handsModel;
        }

        ICurioRenderer.followBodyRotations(entity, model);
        if(renderLayerParent.getModel() instanceof HumanoidModel<?> parentModel){
            parentModel.copyPropertiesTo((HumanoidModel)model);
        }

        model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        model.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, OverlayTexture.NO_OVERLAY, color[0], color[1], color[2], 1);
    }
}
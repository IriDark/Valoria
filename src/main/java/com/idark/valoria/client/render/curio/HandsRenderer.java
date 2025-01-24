package com.idark.valoria.client.render.curio;

import com.idark.valoria.Valoria;
import com.idark.valoria.ValoriaClient;
import com.idark.valoria.client.model.curio.HandsModel;
import com.idark.valoria.client.model.curio.HandsModelSlim;
import com.idark.valoria.registries.item.types.curio.DyeableGlovesItem;
import com.idark.valoria.registries.item.types.curio.ICurioTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class HandsRenderer implements ICurioRenderer{
    public static ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/necklace/empty.png");

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
        if(isDefault(slotContext.entity())){
            HandsModel model = new HandsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ValoriaClient.HANDS_LAYER));
            ICurioRenderer.followBodyRotations(entity, model);
            model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            model.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, OverlayTexture.NO_OVERLAY, color[0], color[1], color[2], 1);
        }else{
            HandsModelSlim model = new HandsModelSlim(Minecraft.getInstance().getEntityModels().bakeLayer(ValoriaClient.HANDS_LAYER_SLIM));
            ICurioRenderer.followBodyRotations(entity, model);
            model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            model.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, OverlayTexture.NO_OVERLAY, color[0], color[1], color[2], 1);
        }
    }
}
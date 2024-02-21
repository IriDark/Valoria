package com.idark.valoria.client.render.curio;

import com.idark.valoria.Valoria;
import com.idark.valoria.ValoriaClient;
import com.idark.valoria.client.render.curio.model.HandsModel;
import com.idark.valoria.client.render.curio.model.HandsModelDefault;
import com.idark.valoria.registries.world.item.types.curio.ICurioTexture;
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
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class HandsRenderer implements ICurioRenderer {
    public static ResourceLocation TEXTURE = new ResourceLocation(Valoria.MOD_ID, "textures/entity/necklace/empty.png");

    public static boolean isDefault2;
    public boolean isDefault(LivingEntity entity) {
       if (entity instanceof AbstractClientPlayer player) {
            isDefault2 = player.getModelName().equals("default");
            return player.getModelName().equals("default");
       }

        return isDefault2;
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext,
                                                                          PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer,
                                                                          int light, float limbSwing, float limbSwingAmount, float partialTicks,
                                                                          float ageInTicks, float netHeadYaw, float headPitch) {

        if (!isDefault(slotContext.entity())) {
            HandsModel model = null;
            if (model == null) {
                model = new HandsModel(Minecraft.getInstance().getEntityModels().bakeLayer(ValoriaClient.HANDS_LAYER_SLIM));
            }

            LivingEntity entity = slotContext.entity();
            if (stack.getItem() instanceof ICurioTexture) {
                ICurioTexture curio = (ICurioTexture) stack.getItem();
                TEXTURE = curio.getTexture(stack, entity);
            }

            ICurioRenderer.followBodyRotations(entity, model);
            model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            model.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        } else if (isDefault(slotContext.entity())) {
            HandsModelDefault model = null;
            if (model == null) {
                model = new HandsModelDefault(Minecraft.getInstance().getEntityModels().bakeLayer(ValoriaClient.HANDS_LAYER));
            }

            LivingEntity entity = slotContext.entity();
            if (stack.getItem() instanceof ICurioTexture) {
                ICurioTexture curio = (ICurioTexture) stack.getItem();
                TEXTURE = curio.getTexture(stack, entity);
            }

            ICurioRenderer.followBodyRotations(entity, model);
            model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            model.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }
    }
}
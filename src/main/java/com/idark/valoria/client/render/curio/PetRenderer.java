package com.idark.valoria.client.render.curio;

import com.idark.valoria.registries.item.types.curio.pet.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import software.bernie.geckolib.model.*;
import software.bernie.geckolib.renderer.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.client.*;

public class PetRenderer extends GeoItemRenderer<PetItem> implements ICurioRenderer{

    public PetRenderer(PetItem item) {
        super(new DefaultedItemGeoModel<>(item.modelPath()));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource buf, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch){
        poseStack.pushPose();
        ICurioRenderer.translateIfSneaking(poseStack, slotContext.entity());
        poseStack.scale(0.5f, 0.5f, 0.5f);
        poseStack.translate(-2.0f, -1.25f, -0.5f);

        this.renderByItem(stack, ItemDisplayContext.NONE, poseStack, buf, light, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }
}
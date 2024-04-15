package com.idark.valoria.core.mixin.client;


import com.idark.valoria.ValoriaClient;
import com.idark.valoria.client.render.curio.model.HandsModel;
import com.idark.valoria.registries.item.types.curio.GlovesItem;
import com.idark.valoria.registries.item.types.curio.ICurioTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    @Shadow
    protected abstract void setupRotations(AbstractClientPlayer entityLiving, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks);

    @Shadow
    protected abstract void setModelProperties(AbstractClientPlayer clientPlayer);

    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Inject(method = "renderHand", at = @At("HEAD"))
    private void valoria$renderHand(PoseStack pPose, MultiBufferSource pBuffer, int pLight, AbstractClientPlayer pPlayer, ModelPart pArm, ModelPart pWear, CallbackInfo ci) {
        List<SlotResult> curioSlots = CuriosApi.getCuriosHelper().findCurios(pPlayer, (i) -> true);
        for (SlotResult slot : curioSlots) {
            if (slot.slotContext().cosmetic() || slot.slotContext().visible()) {
                if (slot.stack().getItem() instanceof ICurioTexture item) {
                    if (item instanceof GlovesItem) {
                        var playerModel = getModel();

                        setModelProperties(pPlayer);
                        playerModel.attackTime = 0.0F;
                        playerModel.crouching = false;
                        playerModel.swimAmount = 0.0F;
                        playerModel.setupAnim(pPlayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
                        pArm.xRot = 0.0F;

                        var pRenderLayer = playerModel.slim ? ValoriaClient.HANDS_LAYER_SLIM : ValoriaClient.HANDS_LAYER;
                        var pTexture = item.getTexture(slot.stack(), pPlayer);
                        if (pRenderLayer == null || pTexture == null) return;

                        var pModel = new HandsModel(Minecraft.getInstance().getEntityModels().bakeLayer(pRenderLayer));
                        if (pArm == pModel.right_glove) {
                            pModel.right_glove.copyFrom(pArm);
                            pModel.right_glove.render(pPose, pBuffer.getBuffer(RenderType.entityTranslucent(pTexture)), pLight, OverlayTexture.NO_OVERLAY);
                        } else {
                            pModel.left_glove.copyFrom(pArm);
                            pModel.left_glove.render(pPose, pBuffer.getBuffer(RenderType.entityTranslucent(pTexture)), pLight, OverlayTexture.NO_OVERLAY);
                        }
                    }
                }
            }
        }
    }
}
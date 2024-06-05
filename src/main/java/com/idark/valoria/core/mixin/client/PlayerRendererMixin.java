package com.idark.valoria.core.mixin.client;


import com.idark.valoria.*;
import com.idark.valoria.client.render.curio.model.*;
import com.idark.valoria.registries.item.types.curio.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.player.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.player.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>{

    @Shadow
    protected abstract void setupRotations(AbstractClientPlayer entityLiving, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks);

    @Shadow
    protected abstract void setModelProperties(AbstractClientPlayer clientPlayer);

    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> model, float shadowRadius){
        super(context, model, shadowRadius);
    }

    @Inject(method = "renderHand", at = @At("HEAD"))
    private void valoria$renderHand(PoseStack pPose, MultiBufferSource pBuffer, int pLight, AbstractClientPlayer pPlayer, ModelPart pArm, ModelPart pWear, CallbackInfo ci){
        List<SlotResult> curioSlots = CuriosApi.getCuriosHelper().findCurios(pPlayer, (i) -> true);
        for(SlotResult slot : curioSlots){
            if(slot.slotContext().cosmetic() || slot.slotContext().visible()){
                if(slot.stack().getItem() instanceof ICurioTexture item){
                    if(item instanceof GlovesItem){
                        var playerModel = getModel();

                        setModelProperties(pPlayer);
                        playerModel.attackTime = 0.0F;
                        playerModel.crouching = false;
                        playerModel.swimAmount = 0.0F;
                        playerModel.setupAnim(pPlayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
                        pArm.xRot = 0.0F;

                        var pRenderLayer = playerModel.slim ? ValoriaClient.HANDS_LAYER_SLIM : ValoriaClient.HANDS_LAYER;
                        var pTexture = item.getTexture(slot.stack(), pPlayer);
                        if(pRenderLayer == null || pTexture == null) return;

                        var pModel = new HandsModel(Minecraft.getInstance().getEntityModels().bakeLayer(pRenderLayer));
                        int k = ((DyeableLeatherItem)item).getColor(slot.stack());
                        float f = (float)(k >> 16 & 255) / 255.0F;
                        float f1 = (float)(k >> 8 & 255) / 255.0F;
                        float f2 = (float)(k & 255) / 255.0F;
                        if(item instanceof DyeableGlovesItem){
                            if(pArm == pModel.right_glove){
                                pModel.right_glove.copyFrom(pArm);
                                pModel.right_glove.render(pPose, pBuffer.getBuffer(RenderType.entityTranslucent(pTexture)), pLight, OverlayTexture.NO_OVERLAY, f, f1, f2, 1);
                            }else{
                                pModel.left_glove.copyFrom(pArm);
                                pModel.left_glove.render(pPose, pBuffer.getBuffer(RenderType.entityTranslucent(pTexture)), pLight, OverlayTexture.NO_OVERLAY, f, f1, f2, 1);
                            }
                        } else {
                            if(pArm == pModel.right_glove){
                                pModel.right_glove.copyFrom(pArm);
                                pModel.right_glove.render(pPose, pBuffer.getBuffer(RenderType.entityTranslucent(pTexture)), pLight, OverlayTexture.NO_OVERLAY);
                            }else{
                                pModel.left_glove.copyFrom(pArm);
                                pModel.left_glove.render(pPose, pBuffer.getBuffer(RenderType.entityTranslucent(pTexture)), pLight, OverlayTexture.NO_OVERLAY);
                            }
                        }
                    }
                }
            }
        }
    }
}
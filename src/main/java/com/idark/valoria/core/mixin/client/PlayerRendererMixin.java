package com.idark.valoria.core.mixin.client;


import com.idark.valoria.client.*;
import com.idark.valoria.client.model.curio.*;
import com.idark.valoria.registries.item.types.curio.*;
import com.idark.valoria.registries.item.types.curio.hands.*;
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

    @Unique
    public float[] valoria$getColor(ItemStack stack){
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

    @SuppressWarnings("removal")
    @Inject(method = "renderHand", at = @At("HEAD"))
    private void valoria$renderHand(PoseStack pPose, MultiBufferSource pBuffer, int pLight, AbstractClientPlayer pPlayer, ModelPart pArm, ModelPart pWear, CallbackInfo ci){
        List<SlotResult> curioSlots = CuriosApi.getCuriosHelper().findCurios(pPlayer, (i) -> true);
        for(SlotResult slot : curioSlots){
            if(slot.slotContext().cosmetic() || slot.slotContext().visible()){
                if(slot.stack().getItem() instanceof ICurioTexture item){
                    if(item instanceof GlovesItem){
                        float[] color = valoria$getColor(slot.stack());
                        var playerModel = getModel();

                        setModelProperties(pPlayer);
                        playerModel.attackTime = 0.0F;
                        playerModel.crouching = false;
                        playerModel.swimAmount = 0.0F;
                        playerModel.setupAnim(pPlayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
                        pArm.xRot = 0.0F;

                        var pRenderLayer = playerModel.slim ? ValoriaLayers.HANDS_LAYER_SLIM : ValoriaLayers.HANDS_LAYER;
                        var pTexture = item.getTexture(slot.stack(), pPlayer);
                        if(pRenderLayer == null || pTexture == null) return;

                        var pModel = new HandsModelSlim(Minecraft.getInstance().getEntityModels().bakeLayer(pRenderLayer));
                        if(pArm == playerModel.rightArm){
                            pModel.right_glove.copyFrom(pArm);
                            pModel.right_glove.render(pPose, pBuffer.getBuffer(RenderType.entityTranslucent(pTexture)), pLight, OverlayTexture.NO_OVERLAY, color[0], color[1], color[2], 1);
                        }else{
                            pModel.left_glove.copyFrom(pArm);
                            pModel.left_glove.render(pPose, pBuffer.getBuffer(RenderType.entityTranslucent(pTexture)), pLight, OverlayTexture.NO_OVERLAY, color[0], color[1], color[2], 1);
                        }
                    }
                }
            }
        }
    }
}
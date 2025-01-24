package com.idark.valoria.core.mixin.client;

import com.idark.valoria.core.interfaces.SpinAttackItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity>{

    @Inject(at = @At("HEAD"), method = "setupRotations")
    protected void setupRotations(T pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks, CallbackInfo ci){
        if(pEntityLiving.isUsingItem() && pEntityLiving.getUseItem().getItem() instanceof SpinAttackItem){
            if(pEntityLiving.getUsedItemHand() != InteractionHand.MAIN_HAND){
                pPoseStack.mulPose(Axis.YP.rotationDegrees(((float)pEntityLiving.getTicksUsingItem() + pPartialTicks) * -42f));
            }else{
                pPoseStack.mulPose(Axis.YP.rotationDegrees(((float)pEntityLiving.getTicksUsingItem() + pPartialTicks) * -42f).invert());
            }
        }
    }
}

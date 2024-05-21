package com.idark.valoria.core.mixin.client;

import com.idark.valoria.registries.item.interfaces.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity>{

    @Inject(at = @At("HEAD"), method = "setupRotations")
    protected void setupRotations(T pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks, CallbackInfo ci){
        if(pEntityLiving.isUsingItem() && pEntityLiving.getUseItem().getItem() instanceof ISpinAttackItem){
            if(pEntityLiving.getUsedItemHand() != InteractionHand.MAIN_HAND){
                pPoseStack.mulPose(Axis.YP.rotationDegrees(((float)pEntityLiving.getTicksUsingItem() + pPartialTicks) * -42f));
            }else{
                pPoseStack.mulPose(Axis.YP.rotationDegrees(((float)pEntityLiving.getTicksUsingItem() + pPartialTicks) * -42f).invert());
            }
        }
    }
}

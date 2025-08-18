package com.idark.valoria.core.mixin;

import com.idark.valoria.registries.item.types.ranged.bows.*;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(CrossbowItem.class)
public class CrossbowMixin{

    @Inject(method = "getChargeDuration", at = @At("RETURN"), cancellable = true)
    private static void getChargeDuration(ItemStack pCrossbowStack, CallbackInfoReturnable<Integer> cir){
        if(pCrossbowStack.getItem() instanceof ConfigurableCrossbow crossbow) cir.setReturnValue(crossbow.getCustomChargeDuration(pCrossbowStack));
    }
}
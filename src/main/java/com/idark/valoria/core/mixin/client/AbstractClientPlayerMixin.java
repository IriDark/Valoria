package com.idark.valoria.core.mixin.client;

import com.idark.valoria.registries.*;
import net.minecraft.client.player.*;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin{

    @Inject(at = @At("RETURN"), method = "getFieldOfViewModifier", cancellable = true)
    private void evaluateWhichHandsToRender(CallbackInfoReturnable<Float> cir){
        AbstractClientPlayer self = (AbstractClientPlayer)((Object)this);
        ItemStack itemstack = self.getUseItem();
        if(self.isUsingItem()){
            if(itemstack.is(TagsRegistry.BOWS)){
                float f = cir.getReturnValue();
                int i = self.getTicksUsingItem();
                float f1 = (float)i / 20.0F;
                if(f1 > 1.0F){
                    f1 = 1.0F;
                }else{
                    f1 *= f1;
                }

                f *= 1.0F - f1 * 0.15F;
                cir.setReturnValue(net.minecraftforge.client.ForgeHooksClient.getFieldOfViewModifier(self, f));
            }
        }
    }
}
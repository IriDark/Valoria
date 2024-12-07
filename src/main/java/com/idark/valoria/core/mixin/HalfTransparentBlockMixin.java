package com.idark.valoria.core.mixin;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin({HalfTransparentBlock.class})
public class HalfTransparentBlockMixin extends Block {

    public HalfTransparentBlockMixin(Properties pProperties) {
        super(pProperties);
    }

    @Inject(method = "skipRendering", at = @At("RETURN"), cancellable = true)
    public void onSkip(BlockState pState, BlockState pAdjacentBlockState, Direction pSide, CallbackInfoReturnable<Boolean> cir) {
        if (pAdjacentBlockState.is(BlockRegistry.suspiciousIce.get())) {
            cir.setReturnValue(true);
        }
    }
}
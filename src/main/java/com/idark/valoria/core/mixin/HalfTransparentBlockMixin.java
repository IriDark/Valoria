package com.idark.valoria.core.mixin;

import com.idark.valoria.registries.BlockRegistry;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({HalfTransparentBlock.class})
public class HalfTransparentBlockMixin extends Block{

    public HalfTransparentBlockMixin(Properties pProperties){
        super(pProperties);
    }

    @Inject(method = "skipRendering", at = @At("RETURN"), cancellable = true)
    public void onSkip(BlockState pState, BlockState pAdjacentBlockState, Direction pSide, CallbackInfoReturnable<Boolean> cir){
        if(pAdjacentBlockState.is(BlockRegistry.suspiciousIce.get())){
            cir.setReturnValue(true);
        }
    }
}
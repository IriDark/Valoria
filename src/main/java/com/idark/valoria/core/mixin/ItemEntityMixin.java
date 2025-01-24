package com.idark.valoria.core.mixin;

import com.idark.valoria.Valoria;
import com.idark.valoria.core.interfaces.ParticleItemEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin{

    @Shadow
    public abstract ItemStack getItem();

    @Inject(at = @At("RETURN"), method = "tick")
    public void addParticles(CallbackInfo ci){
        ItemEntity self = (ItemEntity)((Object)this);
        if(self.level().isClientSide){
            if(self.getItem().getItem() instanceof ParticleItemEntity item){
                item.spawnParticles(Valoria.proxy.getLevel(), self);
            }
        }
    }
}
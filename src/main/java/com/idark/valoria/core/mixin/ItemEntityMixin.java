package com.idark.valoria.core.mixin;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.interfaces.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin{

    @Shadow
    public abstract ItemStack getItem();

    @Inject(at = @At("RETURN"), method = "tick")
    public void addParticles(CallbackInfo ci){
        ItemEntity self = (ItemEntity)((Object)this);
        if(self.level().isClientSide){
            if(self.getItem().getItem() instanceof IParticleItemEntity item){
                item.spawnParticles(Valoria.proxy.getLevel(), self);
            }
        }
    }
}
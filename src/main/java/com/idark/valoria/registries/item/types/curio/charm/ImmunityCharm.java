package com.idark.valoria.registries.item.types.curio.charm;

import com.idark.valoria.registries.*;
import net.minecraft.server.level.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

public class ImmunityCharm extends AbstractCharm{
    public ImmunityCharm(Properties pProperties){
        super(pProperties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        super.curioTick(slotContext, stack);
        if (slotContext.getWearer().level() instanceof ServerLevel server && server.getServer().getTickCount() % 20 != 0) return;
        if(stack.is(TagsRegistry.FIRE_IMMUNE)) {
            slotContext.getWearer().extinguishFire();
        }

        if(stack.is(TagsRegistry.POISON_IMMUNE) && slotContext.getWearer().hasEffect(MobEffects.POISON)) {
            slotContext.getWearer().removeEffect(MobEffects.POISON);
        }
    }
}

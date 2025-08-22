package com.idark.valoria.registries.item.types.curio;

import com.idark.valoria.core.capability.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.util.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

public class NihilityItem extends Item implements ICurioItem, Vanishable{

    public NihilityItem(Properties pProperties){
        super(pProperties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        ICurioItem.super.curioTick(slotContext, stack);
        LivingEntity wearer = slotContext.entity();
        if(wearer instanceof Player player){
            if(player.isCreative()) return;
            player.getCapability(INihilityLevel.INSTANCE).ifPresent((nihilityLevel) -> {
                if(nihilityLevel.getAmount(false) > 0) {
                    if(slotContext.entity().tickCount % 160 == 0){
                        if(Tmp.rnd.chance(0.75f)) stack.hurtAndBreak(Tmp.rnd.nextInt(1, 4), slotContext.entity(), (plr) -> plr.broadcastBreakEvent(EquipmentSlot.HEAD));
                    }
                }
            });
        }
    }

    @Override
    public boolean canEquipFromUse(SlotContext slot, ItemStack stack){
        return true;
    }
}

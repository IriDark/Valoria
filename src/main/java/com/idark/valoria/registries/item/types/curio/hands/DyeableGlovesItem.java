package com.idark.valoria.registries.item.types.curio.hands;

import com.idark.valoria.registries.item.types.curio.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.util.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

public class DyeableGlovesItem extends GlovesItem implements ICurioItem, ICurioTexture, DyeableLeatherItem, Vanishable{
    public DyeableGlovesItem(DyeableBuilder builder){
        super(builder);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        Player player = (Player)slotContext.entity();
        int pDefaultDamage = Tmp.rnd.nextInt(0, 2);
        if(player.hurtMarked){
            stack.hurtAndBreak(pDefaultDamage, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
    }

    public static class DyeableBuilder extends GlovesBuilder{

        public DyeableBuilder(Tier tier, Properties properties){
            super(tier, properties);
        }

        @Override
        public DyeableGlovesItem build(){
            return new DyeableGlovesItem(this);
        }
    }
}
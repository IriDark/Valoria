package com.idark.valoria.registries.item.types.curio;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.*;
import com.idark.valoria.registries.item.types.builders.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.util.*;

import java.util.*;

public class VoidCrystalItem extends AbstractTalismanItem implements ISoulItem{
    public int max;
    public int current;
    public VoidCrystalItem(Builder builder){
        super(builder);
        this.max = 25;
        this.current = 0;
    }

    @Override
    public ItemStack getDefaultInstance(){
        return setSoulItem(super.getDefaultInstance());
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack){
        if(getCurrentSouls(stack) > 0){
            return super.getDefaultAttributeModifiers(stack);
        }

        return ItemAttributeModifiers.EMPTY;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotIndex, boolean selected){
        super.inventoryTick(stack, level, entity, slotIndex, selected);
        if(entity instanceof Player player && !player.level().isClientSide()){
            if(player.getItemBySlot(EquipmentSlot.OFFHAND) == stack){
                if(player.tickCount % Tmp.rnd.nextInt(140, 180) == 0){
                    consumeSouls(1, stack);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext world, List<Component> tooltip, TooltipFlag flags){
        tooltip.add(Component.translatable("tooltip.valoria.souls", getCurrentSouls(stack)).append(" / ").append(String.valueOf(getMaxSouls())).withStyle(ChatFormatting.GRAY).append("").withStyle(style -> style.withFont(Valoria.FONT)));
        super.appendHoverText(stack, world, tooltip, flags);
    }

    public boolean isBarVisible(ItemStack pStack){
        return barVisible(pStack);
    }

    public int getBarWidth(ItemStack pStack){
        return barWidth(pStack);
    }

    public int getBarColor(ItemStack pStack){
        return barColor();
    }

    @Override
    public int getBaseSouls(){
        return current;
    }

    @Override
    public int getMaxSouls(){
        return max;
    }

    public static class Builder extends AbstractTalismanBuilder<VoidCrystalItem>{
        public Builder(Properties pProperties){
            super(pProperties);
        }

        public VoidCrystalItem build(){
            return new VoidCrystalItem(this);
        }
    }
}

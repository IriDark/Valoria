package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.registries.item.*;
import com.idark.valoria.registries.item.types.builders.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
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

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);
        if (slot == EquipmentSlot.OFFHAND && getCurrentSouls(stack) > 0) {
            return modifiers;
        }

        return ImmutableMultimap.of();
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex){
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
        if(!player.level().isClientSide()){
            if(player.getItemBySlot(EquipmentSlot.OFFHAND) == stack){
                if(player.tickCount % Tmp.rnd.nextInt(140, 180) == 0){
                    consumeSouls(1, stack);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        tooltip.add(Component.translatable("tooltip.valoria.souls", getCurrentSouls(stack)).append(" / ").append(String.valueOf(getMaxSouls())).withStyle(ChatFormatting.GRAY).append("\uE253").withStyle(style -> style.withFont(Valoria.FONT)));
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

package com.idark.valoria.registries.item.types.consumables;

import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class RotItem extends Item{
    public RotItem(Properties pProperties){
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced){
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        var tag = pStack.getOrCreateTag();
        if(tag.contains("OriginalItem")){
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("OriginalItem")));
            if(item != null) {
                pTooltipComponents.add(Component.translatable("tooltip.valoria.original_food", new ItemStack(item).getDisplayName()).withStyle(ChatFormatting.GRAY));
            }
        }
    }
}

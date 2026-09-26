package com.idark.valoria.registries.item.types.consumables;

import com.idark.valoria.registries.*;
import net.minecraft.*;
import net.minecraft.core.component.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;

import java.util.*;

public class RotItem extends Item{
    public static final String LEGACY_KEY = "OriginalItem";
    public RotItem(Properties pProperties){
        super(pProperties);
    }

    public static Optional<ResourceLocation> getOriginalItem(ItemStack stack){
        ResourceLocation id = stack.get(DataComponentsRegistry.ORIGINAL_ITEM);
        if(id != null) return Optional.of(id);
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if(data != null && data.contains(LEGACY_KEY)){
            return Optional.ofNullable(ResourceLocation.tryParse(data.copyTag().getString(LEGACY_KEY)));
        }

        return Optional.empty();
    }

    public static void setOriginalItem(ItemStack stack, ResourceLocation id){
        stack.set(DataComponentsRegistry.ORIGINAL_ITEM, id);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced){
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        getOriginalItem(pStack).ifPresent(id -> {
            Item item = BuiltInRegistries.ITEM.get(id);
            if(item != null) {
                pTooltipComponents.add(Component.translatable("tooltip.valoria.original_food", new ItemStack(item).getDisplayName()).withStyle(ChatFormatting.GRAY));
            }
        });
    }
}

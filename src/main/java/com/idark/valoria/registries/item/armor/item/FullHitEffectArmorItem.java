package com.idark.valoria.registries.item.armor.item;

import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.common.registry.item.armor.*;

import java.util.*;

public class FullHitEffectArmorItem extends SuitArmorItem{
    public Type type;

    public FullHitEffectArmorItem(ArmorMaterial material, Type type, Properties settings){
        super(material, type, settings);
        this.type = type;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags){
        super.appendHoverText(stack, world, list, flags);
        if (stack.getItem() instanceof ArmorItem armor) {
            var effects = AbstractArmorRegistry.EFFECTS.get(armor.getMaterial());
            if (effects != null) {
                var component = Component.translatable("tooltip.tridot.applies_to_target").withStyle(ChatFormatting.GRAY);
                for (int i = 0; i < effects.size(); i++) {
                    var effectName = effects.get(i).effect().get().getDisplayName().getString();
                    component.append(Component.literal(effectName).withStyle(stack.getRarity().getStyleModifier()));
                    if (i < effects.size() - 1) {
                        component.append(Component.literal(", ").withStyle(ChatFormatting.GRAY));
                    }
                }

                list.add(component);
            }
        }
    }
}
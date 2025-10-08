package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.armor.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.player.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.Nullable;
import pro.komaru.tridot.common.registry.item.armor.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import javax.annotation.*;
import java.util.*;

import static pro.komaru.tridot.common.registry.item.armor.SuitArmorItem.getArmorSetItem;

public class CrownItem extends Item implements ICurioItem, Vanishable{
    public CrownItem(Properties properties){
        super(properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack){
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.LUCK, new AttributeModifier(uuid, "bonus", 0.10, Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "bonus", 0.05, Operation.MULTIPLY_TOTAL));
        if(slotContext.entity() instanceof Player player){
            if(SuitArmorItem.hasCorrectArmorOn(ArmorRegistry.FALLEN_COLLECTOR, player)){
                atts.put(AttributeReg.NECROMANCY_LIFETIME.get(), new AttributeModifier(uuid, "bonus", 0.05, Operation.MULTIPLY_TOTAL));
            }
        }

        return atts;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced){
        super.appendHoverText(stack, pLevel, list, pIsAdvanced);
        list.add(Component.translatable("tooltip.valoria.fallen_collector_crown").withStyle(ChatFormatting.GRAY));
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            if (Screen.hasShiftDown()) {
                list.add(Component.translatable("tooltip.tridot.equipped").withStyle(ChatFormatting.GRAY));
                list.add(this.getArmorSetItemComponent(player, ItemsRegistry.fallenCollectorHood.get().getDefaultInstance(), EquipmentSlot.HEAD));
                list.add(this.getArmorSetItemComponent(player, ItemsRegistry.fallenCollectorCoat.get().getDefaultInstance(), EquipmentSlot.CHEST));
                list.add(this.getArmorSetItemComponent(player, ItemsRegistry.fallenCollectorLeggings.get().getDefaultInstance(), EquipmentSlot.LEGS));
                list.add(this.getArmorSetItemComponent(player, ItemsRegistry.fallenCollectorBoots.get().getDefaultInstance(), EquipmentSlot.FEET));
            } else {
                list.add(Component.translatable("tooltip.tridot.shift_for_details", Component.translatable("key.keyboard.left.shift").getString()).withStyle(ChatFormatting.GRAY));
            }

            list.add(Component.empty());
        }
    }

    public boolean hasArmorItem(Player player, ItemStack stack, EquipmentSlot slot) {
        return player != null && player.getItemBySlot(slot).getItem() == getArmorSetItem(stack, slot).getItem();
    }

    public ChatFormatting getDisplayColor() {
        return ChatFormatting.GREEN;
    }

    public MutableComponent getArmorSetItemComponent(Player player, ItemStack stack, EquipmentSlot slot) {
        return Component.literal(" ").append(Component.translatable(getArmorSetItem(stack, slot).getDescriptionId()).withStyle(Style.EMPTY.withColor(hasArmorItem(player, stack, slot) ? this.getDisplayColor() : ChatFormatting.RED)));
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack){
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
    }
}
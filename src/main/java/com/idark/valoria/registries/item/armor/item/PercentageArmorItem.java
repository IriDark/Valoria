package com.idark.valoria.registries.item.armor.item;

import com.google.common.collect.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.ItemStack.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;

import java.util.*;

//todo move to lib
public class PercentageArmorItem extends ArmorItem{
    public ArmorMaterial material;
    private final float defense;
    private static final EnumMap<ArmorItem.Type, UUID> ARMOR_MODIFIER_UUID_PER_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266744_) -> {
        p_266744_.put(ArmorItem.Type.BOOTS, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"));
        p_266744_.put(ArmorItem.Type.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"));
        p_266744_.put(ArmorItem.Type.CHESTPLATE, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"));
        p_266744_.put(ArmorItem.Type.HELMET, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"));
    });

    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    public PercentageArmorItem(ArmorMaterial pMaterial, Type pType, Properties pProperties){
        super(pMaterial, pType, pProperties);
        this.material = pMaterial;
        this.defense = pMaterial.getDefenseForType(pType);
        UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(pType);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", this.defense, Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    public int getDefense() {
        return Math.round(this.defense);
    }

    public float getPercentDefense() {
        return this.defense / 100;
    }

    public float getToughness() {
        return 0;
    }

    public int getTotalDefense(ArmorMaterial material) {
        return material.getDefenseForType(Type.HELMET) +
        material.getDefenseForType(Type.CHESTPLATE) +
        material.getDefenseForType(Type.LEGGINGS) +
        material.getDefenseForType(Type.BOOTS);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced){
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pStack.hideTooltipPart(TooltipPart.MODIFIERS);
        pTooltipComponents.add(Component.translatable("tooltip.valoria.total_armor", getTotalDefense(((PercentageArmorItem)pStack.getItem()).getMaterial()) + "%").withStyle(ChatFormatting.GRAY));
        pTooltipComponents.add(Component.translatable("attribute.modifier.plus.1", defense, Component.translatable("attribute.name.generic.armor")).withStyle(ChatFormatting.BLUE));
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == this.type.getSlot() ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }
}

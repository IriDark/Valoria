package com.idark.valoria.registries.item.armor.item;

import com.google.common.collect.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.ItemStack.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;

import java.text.*;
import java.util.*;

//todo move to lib
public class PercentageArmorItem extends ArmorItem{
    public ArmorMaterial material;
    private final float defense;
    private final float toughness;
    protected final float knockbackResistance;
    public static final EnumMap<ArmorItem.Type, UUID> ARMOR_MODIFIER_UUID_PER_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266744_) -> {
        p_266744_.put(ArmorItem.Type.BOOTS, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"));
        p_266744_.put(ArmorItem.Type.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"));
        p_266744_.put(ArmorItem.Type.CHESTPLATE, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"));
        p_266744_.put(ArmorItem.Type.HELMET, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"));
    });

    public final DecimalFormat ATTRIBUTE_MODIFIER_FORMAT = Util.make(new DecimalFormat("#.##"), (p_41704_) -> p_41704_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT)));
    public final Multimap<Attribute, AttributeModifier> defaultModifiers;

//    public PercentageArmorItem(ArmorRegistry pMaterial, Type pType, Properties pProperties){
//        super(pMaterial, pType, pProperties);
//        this.material = pMaterial;
//        this.toughness = pMaterial.getToughness();
//        this.defense = pMaterial.getDefenseForType(pType);
//        this.knockbackResistance = pMaterial.getKnockbackResistance();
//        UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(pType);
//        ArmorRegistry.Builder builder = pMaterial.builder;
//        if(this.knockbackResistance > 0){
//            builder.put(Attributes.KNOCKBACK_RESISTANCE, uuid, AttributeModifier.Operation.ADDITION, this.knockbackResistance);
//        }
//
//        builder.put(Attributes.ARMOR_TOUGHNESS, uuid, AttributeModifier.Operation.ADDITION, this.toughness);
//        this.defaultModifiers = builder.attributes.build();
//    }

    public PercentageArmorItem(ArmorMaterial pMaterial, Type pType, Properties pProperties){
        super(pMaterial, pType, pProperties);
        this.material = pMaterial;
        this.toughness = pMaterial.getToughness();
        this.defense = pMaterial.getDefenseForType(pType);
        this.knockbackResistance = pMaterial.getKnockbackResistance();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(pType);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        if (this.knockbackResistance > 0) {
            builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", this.knockbackResistance, AttributeModifier.Operation.ADDITION));
        }

        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", this.toughness, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    public int getDefense() {
        return Math.round(this.defense);
    }

    public float getPercentDefense() {
        return this.defense / 100;
    }

    public float getToughness() {
        return toughness;
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
        Player pPlayer = Minecraft.getInstance().player;
        for(EquipmentSlot equipmentslot : EquipmentSlot.values()){
            Multimap<Attribute, AttributeModifier> multimap = this.getAttributeModifiers(equipmentslot, pStack);
            if(!multimap.isEmpty()){
                pTooltipComponents.add(CommonComponents.EMPTY);
                pTooltipComponents.add(Component.translatable("item.modifiers." + equipmentslot.getName()).withStyle(ChatFormatting.GRAY));
                for(Map.Entry<Attribute, AttributeModifier> entry : multimap.entries()){
                    AttributeModifier attributemodifier = entry.getValue();
                    double d0 = attributemodifier.getAmount();
                    boolean flag = false;
                    if(pPlayer != null){
                        if(attributemodifier.getId() == Item.BASE_ATTACK_DAMAGE_UUID){
                            d0 += pPlayer.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
                            d0 += EnchantmentHelper.getDamageBonus(pStack, MobType.UNDEFINED);
                            flag = true;
                        }else if(attributemodifier.getId() == Item.BASE_ATTACK_SPEED_UUID){
                            d0 += pPlayer.getAttributeBaseValue(Attributes.ATTACK_SPEED);
                            flag = true;
                        }
                    }

                    double d1;
                    if(attributemodifier.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL){
                        if(entry.getKey().equals(Attributes.KNOCKBACK_RESISTANCE)){
                            d1 = d0 * 10.0D;
                        }else{
                            d1 = d0;
                        }
                    }else{
                        d1 = d0 * 100.0D;
                    }

                    if(flag){
                        pTooltipComponents.add(CommonComponents.space().append(Component.translatable("attribute.modifier.equals." + attributemodifier.getOperation().toValue(), ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(entry.getKey().getDescriptionId()))).withStyle(ChatFormatting.DARK_GREEN));
                    }else if(d0 > 0.0D){
                        pTooltipComponents.add(Component.translatable("attribute.modifier.plus." + attributemodifier.getOperation().toValue(), ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(entry.getKey().getDescriptionId())).withStyle(ChatFormatting.BLUE));
                    }else if(d0 < 0.0D){
                        d1 *= -1.0D;
                        pTooltipComponents.add(Component.translatable("attribute.modifier.take." + attributemodifier.getOperation().toValue(), ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(entry.getKey().getDescriptionId())).withStyle(ChatFormatting.RED));
                    }
                }
            }
        }
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == this.type.getSlot() ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }
}

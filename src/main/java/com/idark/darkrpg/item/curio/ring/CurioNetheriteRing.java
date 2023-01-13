package com.idark.darkrpg.item.curio.ring;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.item.ModItemGroup;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvents;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nonnull;
import java.util.UUID;

public class CurioNetheriteRing extends Item implements ICurioItem {
	
   public CurioNetheriteRing(Properties properties) {
        super(properties);
	}
	

   @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext,
                                                                        UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "bonus", 5, AttributeModifier.Operation.ADDITION));
        return atts;
		}
		
    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        PlayerEntity player = (PlayerEntity) livingEntity;

        if(random.nextFloat() > 0.6f) {
        stack.damageItem(1, player, p -> CuriosApi.getCuriosHelper().onBrokenCurio(
        SlotTypePreset.RING.getIdentifier(), index, p));
	    }
	}
}
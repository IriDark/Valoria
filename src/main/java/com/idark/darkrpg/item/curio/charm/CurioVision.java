package com.idark.darkrpg.item.curio.charm;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

import net.minecraft.item.Item.Properties;

public class CurioVision extends Item implements ICurioItem {

    public CurioVision(Properties properties) {
        super(properties);
	}
	
	@Nonnull
	@Override
	public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
    return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
	}

    @Override
    public boolean canRightClickEquip(ItemStack stack) {
        return true;
	}
	
    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        PlayerEntity player = (PlayerEntity) livingEntity;

        if(!player.level.isClientSide()) {
            boolean hasPlayerFireResistance =
                    !Objects.equals(player.getEffect(Effects.NIGHT_VISION), null);

            if(!hasPlayerFireResistance) {
                player.addEffect(new EffectInstance(Effects.NIGHT_VISION, 400));

                if(random.nextFloat() > 0.6f) {
                    stack.hurtAndBreak(1, player, p -> CuriosApi.getCuriosHelper().onBrokenCurio(
                            SlotTypePreset.CHARM.getIdentifier(), index, p));
                }
            }
        }

        ICurioItem.super.curioTick(identifier, index, livingEntity, stack);
    }
	
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
	super.appendHoverText(stack, world, tooltip, flags);
	tooltip.add(new TranslationTextComponent("tooltip.darkrpg.vision").withStyle(TextFormatting.GRAY));
	}
}
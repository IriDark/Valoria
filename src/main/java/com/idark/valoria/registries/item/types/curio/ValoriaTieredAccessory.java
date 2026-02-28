package com.idark.valoria.registries.item.types.curio;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.util.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;
import top.theillusivec4.curios.api.type.capability.ICurio.*;

import javax.annotation.*;
import java.util.*;

public class ValoriaTieredAccessory extends TieredItem implements ICurioItem, Vanishable{
    public Tier tier;
    public boolean rmbEquip;
    public ValoriaTieredAccessory(Tier tier, Properties pProperties){
        super(tier, pProperties);
        this.tier = tier;
        rmbEquip = true;
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack){
        if(tier == Tiers.IRON) return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_IRON, 1.0f, 1.0f);
        if(tier == Tiers.GOLD) return new SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
        if(tier == Tiers.DIAMOND) return new SoundInfo(SoundEvents.ARMOR_EQUIP_DIAMOND, 1.0f, 1.0f);
        if(tier == Tiers.NETHERITE) return new SoundInfo(SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 1.0f);
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GENERIC, 1.0f, 1.0f);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slot, ItemStack stack){
        return rmbEquip;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        ICurioItem.super.curioTick(slotContext, stack);
        if(stack.is(TagsRegistry.FIRE_IMMUNE)) {
            slotContext.getWearer().extinguishFire();
        }

        if(stack.is(TagsRegistry.POISON_IMMUNE) && slotContext.getWearer().hasEffect(MobEffects.POISON)) {
            slotContext.getWearer().removeEffect(MobEffects.POISON);
        }

        var entity = slotContext.entity();
        if(entity.hurtMarked) accessoryHurt(entity, stack, tier);
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant){
        return enchant == Enchantments.VANISHING_CURSE || enchant == Enchantments.UNBREAKING || enchant == Enchantments.MENDING;
    }

    @Override
    public boolean makesPiglinsNeutral(SlotContext slotContext, ItemStack stack){
        return tier == Tiers.GOLD;
    }

    public static void accessoryHurt(LivingEntity entity, ItemStack stack, Tier material){
        int pGoldDamage = Tmp.rnd.nextInt(0, 8);
        int pDefaultDamage = Tmp.rnd.nextInt(0, 2);
        stack.hurtAndBreak(material == Tiers.GOLD ? pGoldDamage : pDefaultDamage, entity, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
    }

    public int immunityTime() {
        return 0;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        if(stack.is(TagsRegistry.GRANTS_IMMUNITIES)){
            tooltip.add(Component.translatable("tooltip.valoria.immunity", MobEffects.POISON.getDisplayName()).withStyle(ChatFormatting.GRAY));
            if(stack.is(TagsRegistry.POISON_IMMUNE)){
                tooltip.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY)
                .append(Component.translatable("tooltip.tridot.value", MobEffects.POISON.getDisplayName()).withStyle(Styles.nature)));
            }

            if(stack.is(TagsRegistry.BLEEDING_IMMUNE)){
                tooltip.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY)
                .append(Component.translatable("tooltip.tridot.value", EffectsRegistry.BLEEDING.get().getDisplayName()).withStyle(Styles.create(Pal.strongRed))));
            }

            if(stack.is(TagsRegistry.FIRE_IMMUNE_TIMED)){
                tooltip.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY)
                .append(Component.translatable("tooltip.tridot.value", Blocks.FIRE.getName()).withStyle(Styles.create(Pal.infernalBright))
                .append(Component.literal(" ")
                .append(Component.translatable("tooltip.valoria.timed", ValoriaUtils.formatDuration(immunityTime() * 20, 1))).withStyle(Styles.create(Pal.lightishGray))
                )));
            } else if(stack.is(TagsRegistry.FIRE_IMMUNE_TIMED)){
                tooltip.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY)
                .append(Component.translatable("tooltip.tridot.value", Blocks.FIRE.getName()).withStyle(Styles.create(Pal.infernalBright))));
            }
        }

        if(stack.is(TagsRegistry.INFLICTS_FIRE)){
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("tooltip.valoria.inflicts_fire").withStyle(ChatFormatting.GRAY));
        }
    }
}

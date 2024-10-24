package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.core.enums.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import javax.annotation.*;
import java.util.*;

public abstract class AbstractTieredAccessory extends TieredItem implements ICurioItem, Vanishable {
    public AccessoryType type;
    public AccessoryGem gem;
    public AccessoryMaterial material;
    public Tier tier;
    public final ImmutableList<MobEffectInstance> effects;
    public AbstractTieredAccessory(Tier tier, Properties pProperties) {
        super(tier, pProperties);
        effects = gem == AccessoryGem.AMBER ? ImmutableList.of(new MobEffectInstance(MobEffects.DIG_SPEED, 600)) : ImmutableList.of();
    }

    public AbstractTieredAccessory(Tier tier, AccessoryType type, AccessoryGem gem, AccessoryMaterial material, Properties pProperties) {
        super(tier, pProperties);
        this.tier = tier;
        this.type = type;
        this.gem = gem;
        this.material = material;
        effects = gem == AccessoryGem.AMBER ? ImmutableList.of(new MobEffectInstance(MobEffects.DIG_SPEED, 600)) : ImmutableList.of();
    }

    public AbstractTieredAccessory(Tier tier, AccessoryType type, AccessoryGem gem, AccessoryMaterial material, Properties pProperties, MobEffectInstance... pEffects) {
        super(tier, pProperties);
        this.tier = tier;
        this.type = type;
        this.gem = gem;
        this.material = material;
        effects = ImmutableList.copyOf(pEffects);
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slot, ItemStack stack) {
        return true;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        if (player.hurtMarked) accessoryHurt(player, stack, material);
        if (player.level().isClientSide() && ValoriaClient.JEWELRY_BONUSES_KEY.isDown()) applyEffects(player, stack);
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant) {
        return enchant == Enchantments.VANISHING_CURSE || enchant == Enchantments.UNBREAKING || enchant == Enchantments.MENDING;
    }

    @Override
    public boolean makesPiglinsNeutral(SlotContext slotContext, ItemStack stack) {
        return material == AccessoryMaterial.GOLD;
    }

    public static void accessoryHurt(Player player, ItemStack stack, AccessoryMaterial material) {
        int pGoldDamage = new Random().nextInt(0, 8);
        int pDefaultDamage = new Random().nextInt(0, 2);
        stack.hurtAndBreak(material == AccessoryMaterial.GOLD ? pGoldDamage : pDefaultDamage, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
    }

    public void applyEffects(Player player, ItemStack stack) {
        if (!player.getCooldowns().isOnCooldown(stack.getItem())) {
            if (!effects.isEmpty()) {
                for (MobEffectInstance effectInstance : effects) {
                    player.addEffect(new MobEffectInstance(effectInstance));
                    player.getCooldowns().addCooldown(stack.getItem(), effectInstance.getDuration() + 300);
                    accessoryHurt(player, stack, material);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        if (gem == AccessoryGem.AMBER) {
            tooltip.add(Component.translatable("tooltip.valoria.amber").withStyle(ChatFormatting.GRAY));
        }

        if (type == AccessoryType.BELT) {
            tooltip.add(Component.translatable("tooltip.valoria.belt").withStyle(ChatFormatting.GRAY));
        }

        if (!effects.isEmpty()) {
            ValoriaUtils.addEffectsTooltip(effects, tooltip, 1, 1);
            tooltip.add(Component.translatable("tooltip.valoria.jewelry_bonus", ValoriaClient.JEWELRY_BONUSES_KEY.getKey().getDisplayName()).withStyle(ChatFormatting.GREEN));
        }

        tooltip.add(Component.translatable("tooltip.valoria.rmb_equip").withStyle(ChatFormatting.GREEN));
    }
}

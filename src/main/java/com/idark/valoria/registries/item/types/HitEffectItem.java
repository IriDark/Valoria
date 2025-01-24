package com.idark.valoria.registries.item.types;

import com.google.common.collect.ImmutableList;
import com.idark.valoria.util.ArcRandom;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HitEffectItem extends SwordItem{
    public float chance = 1;
    public final ImmutableList<MobEffectInstance> effects;
    public ArcRandom arcRandom = new ArcRandom();

    public HitEffectItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn, float pChance, MobEffectInstance... pEffects){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.chance = pChance;
        this.effects = ImmutableList.copyOf(pEffects);
    }

    public HitEffectItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn, MobEffectInstance... pEffects){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.effects = ImmutableList.copyOf(pEffects);
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker){
        stack.hurtAndBreak(2, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        ValoriaUtils.chanceEffect(target, effects, chance, arcRandom);
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        ValoriaUtils.addEffectsTooltip(effects, tooltip, 1, chance);
    }
}
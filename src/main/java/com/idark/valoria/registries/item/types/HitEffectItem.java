package com.idark.valoria.registries.item.types;

import com.google.common.collect.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.*;

public class HitEffectItem extends SwordItem{
    public float chance = 1;
    public final ImmutableList<MobEffectInstance> effects;
    public ArcRandom arcRandom = Tmp.rnd;

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
        Utils.Entities.applyWithChance(target, effects, chance, arcRandom);
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        Utils.Items.effectTooltip(effects, tooltip, 1, chance);
    }
}
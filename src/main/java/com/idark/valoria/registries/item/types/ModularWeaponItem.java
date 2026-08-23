package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.item.ability.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;

import java.util.*;

public class ModularWeaponItem extends SwordItem {
    private final Map<CastType, AbilityComponent> innateAbilities = new HashMap<>();

    public ModularWeaponItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    public ModularWeaponItem addAbility(CastType type, AbilityComponent ability) {
        this.innateAbilities.put(type, ability);
        return this;
    }

    public Map<CastType, AbilityComponent> getInnateAbilities() {
        return this.innateAbilities;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        
        if (AbilityHelper.handleUse(stack, level, player, hand)) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        }
        
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity player, ItemStack stack, int count) {
        AbilityHelper.handleUseTick(stack, level, player, count);
        super.onUseTick(level, player, stack, count);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity player, int timeLeft) {
        AbilityHelper.handleReleaseUsing(stack, level, player, timeLeft);
        super.releaseUsing(stack, level, player, timeLeft);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        AbilityHelper.handleHurtEnemy(stack, target, attacker);
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        // High use duration for abilities that require charging
        return 72000;
    }
}

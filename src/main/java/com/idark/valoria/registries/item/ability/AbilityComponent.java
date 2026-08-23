package com.idark.valoria.registries.item.ability;

import com.idark.valoria.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.util.struct.data.*;

public abstract class AbilityComponent{
    public final AbilityType<?> type;
    public ResourceLocation icon = Valoria.loc("textures/gui/tooltips/unknown.png");
    public int itemCooldown = 0;
    public int maxUsages = 1;
    public int durabilityUsage = 1;
    public boolean cancelVanillaBehaviour = false;

    public AbilityComponent(AbilityType<?> type) {
        this.type = type;
    }

    public AbilityComponent setIcon(ResourceLocation icon) {
        this.icon = icon;
        return this;
    }

    public AbilityComponent setItemCooldown(int ticks) {
        this.itemCooldown = ticks;
        return this;
    }

    public AbilityComponent setMaxUsages(int maxUsages) {
        this.maxUsages = maxUsages;
        return this;
    }

    public AbilityComponent setDurabilityUsage(int usage) {
        this.durabilityUsage = usage;
        return this;
    }

    public AbilityComponent setCancelVanilla(boolean cancel) {
        this.cancelVanillaBehaviour = cancel;
        return this;
    }

    public Seq<TooltipComponent> getTooltips(ItemStack pStack, CastType castType){
        return Seq.with();
    }

    public int onCastStart(ServerPlayer player, Level level, ItemStack stack) {
        return this.execute(player, level, stack);
    }

    /**
     * @return Ability custom cooldown in ticks
     */
    public abstract int execute(ServerPlayer player, Level level, ItemStack stack);

    public boolean canCast(ServerPlayer player, ItemStack stack) {
        Level level = player.level();
        boolean noCooldown = !AbilityHelper.isOnCooldown(player, this);
        boolean noItemCooldown = !player.getCooldowns().isOnCooldown(stack.getItem());
        return noCooldown && noItemCooldown;
    }

    public boolean onUse(Level level, Player player, ItemStack stack, InteractionHand hand) {
        return false;
    }

    public void onUseTick(Level level, LivingEntity player, ItemStack stack, int count) {
    }

    public void onReleaseUsing(ItemStack stack, Level level, LivingEntity player, int timeLeft) {
    }

    public void onHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
    }
}
package com.idark.valoria.registries.item.types.consumables;

import com.google.common.collect.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.advancements.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.food.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.api.*;

import java.util.*;

public class BandageItem extends Item{
    public boolean removeAllEffects;
    public ImmutableList<MobEffectInstance> effects;

    public BandageItem(boolean pCure, MobEffectInstance... pEffects){
        super(new Properties().food(new FoodProperties.Builder()
                .alwaysEat()
                .nutrition(0)
                .saturationMod(0)
                .build())
        );

        this.removeAllEffects = pCure;
        this.effects = ImmutableList.copyOf(pEffects);
    }

    public BandageItem(boolean pCure, int time, int power){
        super(new Properties().food(new FoodProperties.Builder()
                .alwaysEat()
                .nutrition(0)
                .saturationMod(0)
                .build())
        );

        this.removeAllEffects = pCure;
        this.effects = ImmutableList.of(new MobEffectInstance(EffectsRegistry.ALOEREGEN.get(), time, power));
    }

    public SoundEvent getDrinkingSound(){
        return SoundEvents.BAMBOO_HIT;
    }

    public SoundEvent getEatingSound(){
        return SoundEvents.BAMBOO_HIT;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, Level level, @NotNull LivingEntity entity) {
        if (level.isClientSide) return stack;
        if (!(entity instanceof Player player)) return stack;

        CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);

        player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        effects.forEach(eff -> entity.addEffect(new MobEffectInstance(eff.getEffect(), eff.getDuration(), eff.getAmplifier(), eff.isAmbient(), eff.isVisible(), eff.showIcon())));

        if (!player.getAbilities().instabuild) stack.shrink(1);

        cureEffects(stack, entity);
        if (level instanceof ServerLevel serv) serv.sendParticles(ParticleRegistry.HEAL.get(), entity.getX(), entity.getY() + 0.7D, entity.getZ(), 12, 0, 0, 0, 0.025f);

        return stack;
    }

    private void cureEffects(ItemStack stack, LivingEntity entity) {
        if (removeAllEffects) {
            entity.getActiveEffects().stream()
                    .filter(ValoriaUtils::isCurable)
                    .forEach(e -> e.getCurativeItems().add(stack));

            entity.curePotionEffects(stack);
            return;
        }

        entity.removeEffect(EffectsRegistry.BLEEDING.get());
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced){
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        Utils.Items.effectTooltip(effects, pTooltipComponents, 1, 1);
        if (removeAllEffects) {
            pTooltipComponents.add(Component.translatable("tooltip.valoria.effect_cure").withStyle(ChatFormatting.GRAY));
        } else pTooltipComponents.add(Component.translatable("tooltip.valoria.bleeding_cure").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public int getUseDuration(ItemStack pStack){
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack){
        return UseAnim.BOW;
    }
}
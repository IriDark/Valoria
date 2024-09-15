package com.idark.valoria.registries.item.types.food;

import com.google.common.collect.ImmutableList;
import com.idark.valoria.client.particle.ParticleRegistry;
import com.idark.valoria.registries.EffectsRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.List;

public class BandageItem extends Item {
    private final boolean removeAllEffects;
    private final ImmutableList<MobEffectInstance> effects;

    public BandageItem(boolean pCure, MobEffectInstance... pEffects) {
        super(new Properties().food(new FoodProperties.Builder()
                .alwaysEat()
                .nutrition(0)
                .saturationMod(0)
                .build())
        );

        this.removeAllEffects = pCure;
        this.effects = ImmutableList.copyOf(pEffects);
    }

    public BandageItem(boolean pCure, int time, int power) {
        super(new Properties().food(new FoodProperties.Builder()
                .alwaysEat()
                .nutrition(0)
                .saturationMod(0)
                .build())
        );

        this.removeAllEffects = pCure;
        this.effects = ImmutableList.of(new MobEffectInstance(EffectsRegistry.ALOEREGEN.get(), time, power));
    }

    public SoundEvent getDrinkingSound() {
        return SoundEvents.BAMBOO_HIT;
    }

    public SoundEvent getEatingSound() {
        return SoundEvents.BAMBOO_HIT;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        Player player = entity instanceof Player ? (Player) entity : null;
        if (!world.isClientSide) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            for (MobEffectInstance effect : effects) {
                entity.addEffect(effect, entity);
            }

            cureEffects(stack, entity);
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        if (world instanceof ServerLevel serv) {
            double y = entity.getY() + 0.7D;
            serv.sendParticles(ParticleRegistry.HEAL.get(), entity.getX(), y, entity.getZ(), 12, 0, 0, 0, 0.025f);
        }

        return stack;
    }

    private void cureEffects(ItemStack stack, LivingEntity entity) {
        if (removeAllEffects) {
            List<MobEffectInstance> harmfulEffects = entity.getActiveEffects().stream().filter(effect -> effect.getEffect().getCategory() == MobEffectCategory.HARMFUL).toList();
            for (MobEffectInstance effect : harmfulEffects) {
                effect.getCurativeItems().add(stack);
            }

            entity.curePotionEffects(stack);
        } else {
            if (entity.hasEffect(EffectsRegistry.BLEEDING.get())) entity.removeEffect(EffectsRegistry.BLEEDING.get());
        }
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }
}
package com.idark.valoria.registries.item.types.food;

import com.idark.valoria.registries.EffectsRegistry;
import com.idark.valoria.registries.ItemsRegistry;
import net.minecraft.advancements.CriteriaTriggers;
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

public class AloeBandageItem extends Item {

    private final int power;
    private final int time;

    public AloeBandageItem(int time, int power) {
        super(new Properties().food(new FoodProperties.Builder()
                .alwaysEat()
                .nutrition(0)
                .saturationMod(0)
                .build())
        );

        this.power = power;
        this.time = time;
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
            player.addEffect(new MobEffectInstance(EffectsRegistry.ALOEREGEN.get(), time, power));
            if(stack.is(ItemsRegistry.ALOE_BANDAGE_UPGRADED.get())) {
                List<MobEffectInstance> harmfulEffects = player.getActiveEffects().stream().filter(effect -> effect.getEffect().getCategory() == MobEffectCategory.HARMFUL).toList();
                for (MobEffectInstance effect : harmfulEffects) {
                    System.out.print(effect);
                    effect.getCurativeItems().add(stack);
                }

                player.curePotionEffects(stack);
            } else {
                if (player.hasEffect(EffectsRegistry.BLEEDING.get())) {
                    player.removeEffect(EffectsRegistry.BLEEDING.get());
                }
            }

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        return stack;
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
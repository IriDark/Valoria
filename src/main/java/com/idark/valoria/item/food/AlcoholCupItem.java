package com.idark.valoria.item.food;

import com.idark.valoria.effect.ModEffects;
import com.idark.valoria.item.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class AlcoholCupItem extends Item {

    private int power = 0;
    private int time = 0;
    private boolean isBottle;

    public AlcoholCupItem(int time, int power, boolean isBottle) {
        super(new Properties()
                .food(new FoodProperties.Builder()
                        .alwaysEat().nutrition(0)
                        .saturationMod(3)
                        .build())
                .stacksTo(1)
        );

        this.power = power;
        this.time = time;
        this.isBottle = isBottle;
    }

    public SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        Player playerEntity = entity instanceof Player ? (Player) entity : null;
        if (playerEntity instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) playerEntity, stack);
        }

        if (!world.isClientSide) {
            entity.addEffect(new MobEffectInstance(ModEffects.TIPSY.get(), time, power));
            if (playerEntity == null || !playerEntity.getAbilities().instabuild) {
                stack.shrink(1);
                if (stack.isEmpty()) {
                    return new ItemStack(this.isBottle ? ModItems.BOTTLE.get() : ModItems.WOODEN_CUP.get());
                }
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
        return UseAnim.DRINK;
    }
}
package com.idark.valoria.registries.world.item.types.food;

import com.idark.valoria.registries.world.effect.ModEffects;
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

public class AloeBandageItem extends Item {

    private final int power;
    private final int time;
	
    public AloeBandageItem(int time, int power)  {
		super(
        new Properties().food(
                new FoodProperties
                        .Builder()
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
        Player player = entity instanceof Player ? (Player)entity : null;
		if (player instanceof ServerPlayer) {
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, stack);
		}
		
		if (!world.isClientSide) {
			entity.addEffect(new MobEffectInstance(ModEffects.ALOEREGEN.get(), time, power));
			if (player == null || !player.getAbilities().instabuild) {
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
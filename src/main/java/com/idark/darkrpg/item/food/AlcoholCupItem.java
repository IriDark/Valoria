package com.idark.darkrpg.item.food;

import com.idark.darkrpg.effect.ModEffects;
import com.idark.darkrpg.item.*;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.*;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class AlcoholCupItem extends Item {

    private int power = 0;
    private int time = 0;

    public AlcoholCupItem(int time, int power)  {
		super(
        new Properties().food(new Food.Builder().alwaysEat().nutrition(0).saturationMod(3).build()).stacksTo(1).tab(ModItemGroup.DARKRPG_GROUP)
    );
        this.power = power;
        this.time = time;
	}

    public SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity) {
        PlayerEntity playerentity = entity instanceof PlayerEntity ? (PlayerEntity)entity : null;
		if (playerentity instanceof ServerPlayerEntity) {
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)playerentity, stack);
		}
		
		if (!world.isClientSide) {
			entity.addEffect(new EffectInstance(ModEffects.TIPSY.get(),time,power));
			if (playerentity == null || !playerentity.abilities.instabuild) {
				stack.shrink(1);
				if (stack.isEmpty()) {
					return new ItemStack(ModItems.WOODEN_CUP.get());
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
    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.DRINK;
    }
}
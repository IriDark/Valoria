package com.idark.darkrpg.item.food;

import com.idark.darkrpg.effect.ModEffects;
import com.idark.darkrpg.item.*;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class GreenTeaCupItem extends Item {

    private int power = 0;
    private int time = 0;

    public GreenTeaCupItem(int time, int power)  {
		super(
        new Properties().food(new Food.Builder().setAlwaysEdible().hunger(0).saturation(3).build()).maxStackSize(1).group(ModItemGroup.DARKRPG_GROUP)
    );
        this.power = power;
        this.time = time;
	}

    public SoundEvent getDrinkingSound() {
        return SoundEvents.ENTITY_GENERIC_DRINK;
    }

    public SoundEvent getEatingSound() {
        return SoundEvents.ENTITY_GENERIC_DRINK;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
        PlayerEntity playerentity = entity instanceof PlayerEntity ? (PlayerEntity)entity : null;
		if (playerentity instanceof ServerPlayerEntity) {
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)playerentity, stack);
		}

		if (!world.isRemote) {
			entity.addPotionEffect(new EffectInstance(ModEffects.ALOEREGEN.get(),time,power));
			if (playerentity == null || !playerentity.abilities.isCreativeMode) {
				stack.shrink(1);
				if (stack.isEmpty()) {
					return new ItemStack(ModItems.CUP.get());
				}
			}
		}
		return stack;
	}

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 25;
    }
    
    @Override
    public UseAction getUseAction(ItemStack p_77661_1_) {
        return UseAction.DRINK;
    }
}
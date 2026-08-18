package com.idark.valoria.registries.item.types;

import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;

public class SummonerBladeItem extends SwordItem {
    public SummonerBladeItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (pAttacker instanceof Player player) {
            pTarget.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0, false, false));
            player.getPersistentData().putUUID("ValoriaSummonFocus", pTarget.getUUID());
            
            if (!player.level().isClientSide) {
                player.displayClientMessage(Component.translatable("message.valoria.summon_focus_set", pTarget.getDisplayName()).withStyle(ChatFormatting.GREEN), true);
            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        
        if (pPlayer.isShiftKeyDown()) {
            if (pPlayer.getPersistentData().contains("ValoriaSummonFocus")) {
                pPlayer.getPersistentData().remove("ValoriaSummonFocus");
                if (!pLevel.isClientSide) {
                    pPlayer.displayClientMessage(Component.translatable("message.valoria.summon_focus_clear").withStyle(ChatFormatting.YELLOW), true);
                }
                pPlayer.swing(pUsedHand);
            }
            return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
        }
        
        return InteractionResultHolder.pass(itemstack);
    }
}

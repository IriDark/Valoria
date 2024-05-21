package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.entity.projectile.*;
import com.idark.valoria.registries.item.tiers.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;

import java.util.*;

public class SpectralBladeItem extends SwordItem{
    public SpectralBladeItem(Item.Properties builderIn){
        super(ModItemTier.NONE, 1, -2.3F, builderIn);
    }

    public UseAnim getUseAnimation(ItemStack stack){
        return UseAnim.SPEAR;
    }

    public int getUseDuration(ItemStack stack){
        return 72000;
    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft){
        if(entityLiving instanceof Player playerEntity){
            int i = this.getUseDuration(stack) - timeLeft;
            if(i >= 6 && playerEntity.getXRot() > -55 && playerEntity.getXRot() < 65){
                if(!level.isClientSide){
                    stack.hurtAndBreak(10, playerEntity, (player) -> player.broadcastBreakEvent(entityLiving.getUsedItemHand()));
                    SpectralBladeEntity spectral = new SpectralBladeEntity(level, playerEntity, stack);
                    spectral.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), (float)playerEntity.getZ() * 5, 2.5F + (float)0 * 0.5F, 3.2F);
                    if(playerEntity.getAbilities().instabuild){
                        spectral.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }

                    level.addFreshEntity(spectral);
                    level.playSound(playerEntity, spectral, SoundEvents.SOUL_ESCAPE, SoundSource.PLAYERS, 1.0F, 1.0F);
                    playerEntity.getCooldowns().addCooldown(this, 75);
                }

                playerEntity.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(!playerIn.isShiftKeyDown()){
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public int getEnchantmentValue(){
        return 1;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.kunai").withStyle(ChatFormatting.GRAY));
    }
}

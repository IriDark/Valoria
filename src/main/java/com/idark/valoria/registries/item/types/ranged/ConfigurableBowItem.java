package com.idark.valoria.registries.item.types.ranged;

import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraftforge.event.*;

public class ConfigurableBowItem extends BowItem{
    double baseDamage;

    public ConfigurableBowItem(double pBaseDamage, Properties pProperties){
        super(pProperties);
        baseDamage = pBaseDamage;
    }

    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft){
        if(pEntityLiving instanceof Player player){
            boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getTagEnchantmentLevel(Enchantments.INFINITY_ARROWS, pStack) > 0;
            ItemStack itemstack = player.getProjectile(pStack);
            int i = this.getUseDuration(pStack) - pTimeLeft;
            i = ForgeEventFactory.onArrowLoose(pStack, pLevel, player, i, !itemstack.isEmpty() || flag);
            if(i < 0) return;

            if(!itemstack.isEmpty() || flag){
                if(itemstack.isEmpty()){
                    itemstack = new ItemStack(Items.ARROW);
                }

                float power = getPowerForTime(i);
                if(!((double)power < 0.1D)){
                    boolean infiniteArrows = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, pStack, player));
                    if(!pLevel.isClientSide){
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrow abstractarrow = arrowitem.createArrow(pLevel, itemstack, player);
                        abstractarrow = customArrow(abstractarrow);
                        abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + baseDamage);
                        abstractarrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, power * 3.0F, 1.0F);
                        if(power == 1.0F){
                            abstractarrow.setCritArrow(true);
                        }

                        int enchantmentPower = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.POWER_ARROWS, pStack);
                        if(enchantmentPower > 0){
                            abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + (double)enchantmentPower * 0.5D + 0.5D);
                        }

                        int enchantmentPunch = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PUNCH_ARROWS, pStack);
                        if(enchantmentPunch > 0){
                            abstractarrow.setKnockback(enchantmentPunch);
                        }

                        if(EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FLAMING_ARROWS, pStack) > 0){
                            abstractarrow.setSecondsOnFire(100);
                        }

                        pStack.hurtAndBreak(1, player, (p_289501_) -> p_289501_.broadcastBreakEvent(player.getUsedItemHand()));
                        if(infiniteArrows || player.getAbilities().instabuild && (itemstack.is(Items.SPECTRAL_ARROW) || itemstack.is(Items.TIPPED_ARROW))){
                            abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }

                        pLevel.addFreshEntity(abstractarrow);
                    }

                    pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F);
                    if(!infiniteArrows && !player.getAbilities().instabuild){
                        itemstack.shrink(1);
                        if(itemstack.isEmpty()){
                            player.getInventory().removeItem(itemstack);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }
}
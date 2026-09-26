package com.idark.valoria.registries.item.types.ranged.bows;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.projectile.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.event.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.common.registry.item.components.*;
import pro.komaru.tridot.common.registry.item.types.*;
import pro.komaru.tridot.util.struct.data.*;

public class PhantasmBow extends ConfigurableBowItem implements TooltipComponentItem{
    public int abilityUseDuration = 10;
    public PhantasmBow(double pBaseDamage, int pArrowBaseDamage, Properties pProperties){
        super(EntityTypeRegistry.PHANTOM_ARROW, pBaseDamage, pArrowBaseDamage, pProperties);
    }

    public ItemStack getDefaultInstance(){
        return setBow(super.getDefaultInstance());
    }

    public ItemStack setBow(ItemStack pStack){
        pStack.set(DataComponentsRegistry.BAR_VISIBLE, false);
        pStack.set(DataComponentsRegistry.SOUND_PLAYED, false);
        return pStack;
    }

    public boolean isPlayed(ItemStack pStack) {
        return DataComponentsRegistry.getBool(pStack, DataComponentsRegistry.SOUND_PLAYED.get(), "isPlayed");
    }

    public boolean isVisible(ItemStack pStack) {
        return DataComponentsRegistry.getBool(pStack, DataComponentsRegistry.BAR_VISIBLE.get(), "isVisible");
    }

    public void setPlayed(ItemStack pStack, boolean value){
        pStack.set(DataComponentsRegistry.SOUND_PLAYED, value);
    }

    public void setVisible(ItemStack pStack, boolean value){
        pStack.set(DataComponentsRegistry.BAR_VISIBLE, value);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.isDamaged() || isVisible(stack);
    }

    @Override
    public void onStopUsing(ItemStack stack, LivingEntity entity, int count){
        super.onStopUsing(stack, entity, count);
        setPlayed(stack, false);
        setVisible(stack, false);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        if(isVisible(stack)){
            Player plr = Valoria.proxy.getPlayer();
            if(plr != null){
                int current = plr.getUseItemRemainingTicks();
                int used = this.getUseDuration(stack, plr) - current;

                float overcharge = Mth.clamp(used - time, 0, abilityUseDuration);
                float progress = overcharge / abilityUseDuration;
                return Math.round(13.0F * progress);
            }
        }

        return super.getBarWidth(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        if(isVisible(stack)) return Pal.crystalBlue.pack();
        return super.getBarColor(stack);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration){
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
        int i = this.getUseDuration(pStack, pLivingEntity) - pRemainingUseDuration;
        if(i < 0) return;

        ItemStack itemstack = pLivingEntity.getProjectile(pStack);
        if(!itemstack.is(Items.ARROW)) return;

        if (i >= time && !isPlayed(pStack)) {
            this.setVisible(pStack, true);
            if (isOvercharged(i, time)) {
                pLivingEntity.playSound(SoundsRegistry.MAGIC_SHOOT.get());
                setPlayed(pStack, true);
            }
        }
    }

    public boolean isOvercharged(int progress, float time) {
        if (progress >= time) {
            float overcharge = progress - time;
            return overcharge > abilityUseDuration;
        }

        return false;
    }

    public Seq<TooltipComponent> getTooltips(ItemStack pStack) {
        return Seq.with(
        new SeparatorComponent(Component.translatable("tooltip.tridot.abilities")),
        new AbilityComponent(Component.translatable("tooltip.valoria.phantasm_bow").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/gui/tooltips/phantom_rain.png"))
        );
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft){
        if(pEntityLiving instanceof Player player){
            boolean flag = player.getAbilities().instabuild || EnchantmentsRegistry.getLevel(pLevel, pStack, Enchantments.INFINITY) > 0;
            ItemStack itemstack = player.getProjectile(pStack);
            int i = this.getUseDuration(pStack, pEntityLiving) - pTimeLeft;
            i = EventHooks.onArrowLoose(pStack, pLevel, player, i, !itemstack.isEmpty() || flag);
            if(i < 0) return;
            if(!itemstack.isEmpty() || flag){
                if(itemstack.isEmpty()){
                    itemstack = new ItemStack(Items.ARROW);
                }

                float power = getPowerForTime(i, time);

                if(!((double)power < 0.1D)){
                    boolean infiniteArrows = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, pStack, player));
                    if(!pLevel.isClientSide()){
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrow abstractarrow = arrowitem == Items.ARROW && arrow.get() != EntityType.ARROW ? createArrow(pLevel, player) : arrowitem.createArrow(pLevel, itemstack, player, pStack);
                        abstractarrow.setOwner(player);
                        doPreSpawn(abstractarrow, player, itemstack, power, infiniteArrows);
                        if(isOvercharged(i, time)) {
                            if(abstractarrow instanceof PhantomArrow phantomArrow) {
                                phantomArrow.burst = true;
                                phantomArrow.spread -= ValoriaUtils.enchantmentAccuracy(pStack);
                            }
                        }

                        applyWeaponEnchantments(pLevel, abstractarrow, pStack);

                        pStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
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

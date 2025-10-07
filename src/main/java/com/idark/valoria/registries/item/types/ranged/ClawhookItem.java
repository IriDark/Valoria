package com.idark.valoria.registries.item.types.ranged;

import com.idark.valoria.*;
import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.common.registry.item.components.*;
import pro.komaru.tridot.util.struct.data.*;

public class ClawhookItem extends Item implements TooltipComponentItem{
    public ClawhookItem(Properties pProperties){
        super(pProperties);
    }

    public UseAnim getUseAnimation(ItemStack stack){
        return UseAnim.BOW;
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(!playerIn.isShiftKeyDown()){
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public int getUseDuration(ItemStack stack){
        return 72000;
    }

    private @NotNull ClawEntity shootProjectile(Level worldIn, Player playerEntity){
        ClawEntity claw = new ClawEntity(playerEntity, worldIn);
        claw.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0F, 1.5F + (float)0 * 0.5F, 1.0F);
        return claw;
    }

    public Seq<TooltipComponent> getTooltips(ItemStack pStack) {
        return Seq.with(
        new SeparatorComponent(Component.translatable("tooltip.valoria.abilities")),
        new AbilityComponent(Component.translatable("tooltip.valoria.claw_hook").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/item/crab_claw.png"))
        );
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged){
        if(pLivingEntity instanceof Player playerEntity){
            int i = this.getUseDuration(pStack) - pTimeCharged;
            if(i >= 6){
                if(!pLevel.isClientSide){
                    ClawEntity claw = shootProjectile(pLevel, playerEntity);
                    playerEntity.getCooldowns().addCooldown(pStack.getItem(), 125);
                    pLevel.addFreshEntity(claw);
                    pLevel.playSound(playerEntity, claw, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                    if(!playerEntity.getAbilities().instabuild){
                        pStack.hurtAndBreak((int)Mth.clamp(pLivingEntity.distanceTo(pLivingEntity) * 1, 1, 10), pLivingEntity, (e) -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                    }
                }

                playerEntity.awardStat(Stats.ITEM_USED.get(this));
            }
        }

        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }
}
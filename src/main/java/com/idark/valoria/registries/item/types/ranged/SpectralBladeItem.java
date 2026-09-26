package com.idark.valoria.registries.item.types.ranged;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.common.registry.item.*;

import java.util.*;

import static pro.komaru.tridot.Tridot.BASE_PROJECTILE_DAMAGE_ID;

public class SpectralBladeItem extends SwordItem{
    private final ItemAttributeModifiers atr;

    public SpectralBladeItem(int damage, float speed, Item.Properties builderIn){
        super(ItemTierRegistry.NONE, builderIn);
        this.atr = ItemAttributeModifiers.builder()
            .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(AttributeRegistry.PROJECTILE_DAMAGE, new AttributeModifier(BASE_PROJECTILE_DAMAGE_ID, damage * 1.8, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .build();
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(){
        return this.atr;
    }


    public UseAnim getUseAnimation(ItemStack stack){
        return UseAnim.SPEAR;
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity){
        return 72000;
    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft){
        if(entityLiving instanceof Player playerEntity){
            int i = this.getUseDuration(stack, entityLiving) - timeLeft;
            if(i >= 6 && playerEntity.getXRot() > -55 && playerEntity.getXRot() < 65){
                if(!level.isClientSide){
                    stack.hurtAndBreak(10, playerEntity, LivingEntity.getSlotForHand(entityLiving.getUsedItemHand()));
                    SpectralBladeEntity spectral = new SpectralBladeEntity(level, playerEntity, stack);
                    spectral.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), (float)playerEntity.getZ(), 2.5F + (float)0 * 0.5F, 3.2F);
                    if(playerEntity.getAbilities().instabuild){
                        spectral.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }

                    level.addFreshEntity(spectral);
                    level.playSound(playerEntity, spectral, SoundEvents.SOUL_ESCAPE.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
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

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced){
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("tooltip.valoria.spectral_blade").withStyle(ChatFormatting.GRAY));
    }

    public int getEnchantmentValue(){
        return 1;
    }
}

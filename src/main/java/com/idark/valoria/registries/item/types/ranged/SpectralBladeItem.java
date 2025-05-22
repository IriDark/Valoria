package com.idark.valoria.registries.item.types.ranged;

import com.google.common.collect.*;
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
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.common.registry.item.*;

import java.util.*;

import static pro.komaru.tridot.Tridot.BASE_PROJECTILE_DAMAGE_UUID;

public class SpectralBladeItem extends SwordItem{
    private final Multimap<Attribute, AttributeModifier> atr;

    public SpectralBladeItem(int damage, float speed, Item.Properties builderIn){
        super(ItemTierRegistry.NONE, damage, speed, builderIn);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damage, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.PROJECTILE_DAMAGE.get(), new AttributeModifier(BASE_PROJECTILE_DAMAGE_UUID, "Tool modifier", damage * 1.8, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -speed, AttributeModifier.Operation.ADDITION));
        this.atr = builder.build();
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot){
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.atr : super.getDefaultAttributeModifiers(equipmentSlot);
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

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced){
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("tooltip.valoria.spectral_blade").withStyle(ChatFormatting.GRAY));
    }

    public int getEnchantmentValue(){
        return 1;
    }
}

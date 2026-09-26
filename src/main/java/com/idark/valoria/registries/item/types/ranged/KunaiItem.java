package com.idark.valoria.registries.item.types.ranged;

import com.google.common.collect.*;
import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.*;

public class KunaiItem extends Item{
    private final ItemAttributeModifiers tridentAttributes;
    public final ImmutableList<MobEffectInstance> effects;
    public float chance = 1;
    public ArcRandom arc = Tmp.rnd;

    public KunaiItem(int damage, Item.Properties builderIn, float chance, MobEffectInstance... pEffects){
        super(builderIn);
        this.tridentAttributes = ShurikenItem.projectileAttributes(damage);
        this.chance = chance;
        this.effects = ImmutableList.copyOf(pEffects);
    }

    public KunaiItem(int damage, Item.Properties builderIn, MobEffectInstance... pEffects){
        super(builderIn);
        this.tridentAttributes = ShurikenItem.projectileAttributes(damage);
        this.effects = ImmutableList.copyOf(pEffects);
    }

    public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player){
        return !player.isCreative();
    }

    public UseAnim getUseAnimation(ItemStack stack){
        return UseAnim.SPEAR;
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity){
        return 72000;
    }

    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft){
        if(entityLiving instanceof Player playerEntity){
            int i = this.getUseDuration(stack, entityLiving) - timeLeft;
            if(i >= 6){
                if(!worldIn.isClientSide){
                    KunaiProjectile kunaiProjectile = shootProjectile(stack, worldIn, playerEntity);
                    worldIn.addFreshEntity(kunaiProjectile);
                    worldIn.playSound(playerEntity, kunaiProjectile, SoundEvents.TRIDENT_THROW.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    if(!playerEntity.getAbilities().instabuild){
                        stack.shrink(1);
                    }
                }

                playerEntity.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    private @NotNull KunaiProjectile shootProjectile(ItemStack stack, Level worldIn, Player playerEntity){
        KunaiProjectile kunaiProjectile = new KunaiProjectile(playerEntity, worldIn, stack);
        kunaiProjectile.setItem(stack);
        kunaiProjectile.setEffectsFromList(effects);
        kunaiProjectile.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0F, 2.5F + (float)0 * 0.5F, 1.0F);
        if(playerEntity.getAbilities().instabuild){
            kunaiProjectile.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }

        return kunaiProjectile;
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(!playerIn.isShiftKeyDown()){
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker){
        Utils.Entities.applyWithChance(target, effects, chance, arc);
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(){
        return this.tridentAttributes;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        Utils.Items.effectTooltip(effects, tooltip, 1, chance);
    }
}

package com.idark.valoria.registries.item.types.ranged;

import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;

public class AcornItem extends Item{
    boolean poisoned;
    public AcornItem(Properties pProperties, boolean pPoisoned){
        super(pProperties);
        this.poisoned = pPoisoned;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand){
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        if(!pLevel.isClientSide){
            AcornProjectile bomb = new AcornProjectile(pPlayer, pLevel);
            if(poisoned) bomb.addEffect(new MobEffectInstance(MobEffects.POISON, 60));
            bomb.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.25F, 2.5f);
            pLevel.addFreshEntity(bomb);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if(!pPlayer.getAbilities().instabuild){
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
}
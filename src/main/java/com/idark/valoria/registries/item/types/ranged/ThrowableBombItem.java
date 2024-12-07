package com.idark.valoria.registries.item.types.ranged;

import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.Level.*;

public class ThrowableBombItem extends Item {
    private final float radius;
    private final int lifetime;
    private final Level.ExplosionInteraction bombInteraction;
    public ThrowableBombItem(Item.Properties pProperties) {
        super(pProperties);
        this.radius = 1.25f;
        this.lifetime = 80;
        this.bombInteraction = ExplosionInteraction.TNT;
    }

    public ThrowableBombItem(Level.ExplosionInteraction bombInteraction, float radius, int lifetime, Item.Properties pProperties) {
        super(pProperties);
        this.radius = radius;
        this.lifetime = lifetime;
        this.bombInteraction = bombInteraction;
    }

    public ThrowableBombItem(float radius, int lifetime, Item.Properties pProperties) {
        super(pProperties);
        this.radius = radius;
        this.lifetime = lifetime;
        this.bombInteraction = ExplosionInteraction.TNT;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!pLevel.isClientSide) {
            ThrowableBomb bomb = new ThrowableBomb(pLevel, pPlayer);
            bomb.setItem(itemstack);
            bomb.setInteraction(bombInteraction);
            bomb.setFuse(lifetime);
            bomb.setRadius(radius);
            bomb.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.25F, 2.5f);
            pLevel.addFreshEntity(bomb);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
}
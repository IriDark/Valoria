package com.idark.valoria.registries.item.types.ranged;

import com.idark.valoria.registries.entity.projectile.ThrowableBomb;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThrowableBombItem extends Item {
    private final float radius;
    private final int lifetime;

    public ThrowableBombItem(Item.Properties pProperties) {
        super(pProperties);
        this.radius = 1.25f;
        this.lifetime = 80;
    }

    public ThrowableBombItem(float radius, int lifetime, Item.Properties pProperties) {
        super(pProperties);
        this.radius = radius;
        this.lifetime = lifetime;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!pLevel.isClientSide) {
            ThrowableBomb bomb = new ThrowableBomb(pLevel, pPlayer);
            bomb.setItem(itemstack);
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
package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.EffectsRegistry;
import com.idark.valoria.util.RandomUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class ClubItem extends SwordItem {
    public ClubItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(2, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        if (RandomUtil.percentChance(0.25f)) {
            target.addEffect(new MobEffectInstance(EffectsRegistry.STUN.get(), 85, 0));
            if (target.level().isClientSide) {
                for (int i = 0; i < 10; i++) {
                    target.level().addParticle(ParticleTypes.POOF, target.getX() + new Random().nextDouble(), target.getY(), target.getZ() + new Random().nextDouble(), 0d, 0.05d, 0d);
                }
            }
        }

        return true;
    }

    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (state.getDestroySpeed(worldIn, pos) != 0.0F) {
            stack.hurtAndBreak(6, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        return true;
    }
}
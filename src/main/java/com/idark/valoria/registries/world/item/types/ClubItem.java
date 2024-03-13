package com.idark.valoria.registries.world.item.types;

import com.idark.valoria.registries.world.effect.ModEffects;
import com.idark.valoria.util.math.RandUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class ClubItem extends SwordItem implements Vanishable {
    Random rand = new Random();

    public ClubItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(2, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));

        if (RandUtils.doWithChance(7)) {
            target.addEffect(new MobEffectInstance(ModEffects.STUN.get(), 200, 1));
            if (target.level().isClientSide) {
                for (int i = 0; i < 10; i++) {
                    target.level().addParticle(ParticleTypes.POOF, target.getX() + rand.nextDouble(), target.getY(), target.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
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
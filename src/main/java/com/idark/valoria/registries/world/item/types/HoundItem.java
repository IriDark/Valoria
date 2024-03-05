package com.idark.valoria.registries.world.item.types;

import com.idark.valoria.util.ModUtils;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class HoundItem extends SwordItem implements Vanishable {

    public HoundItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    public int getUseDuration(ItemStack stack) {
        return 5;
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        if (pState.is(BlockTags.ICE)) {
            return 15.0F;
        } else {
            return pState.is(BlockTags.MINEABLE_WITH_PICKAXE) ? 1.5F : 1.0F;
        }
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
		Player player = (Player)entityLiving;
		player.awardStat(Stats.ITEM_USED.get(this));
        player.getCooldowns().addCooldown(this, 120);
        Vec3 pos = new Vec3(player.getX(), player.getY() + 0.2f, player.getZ());
		List<LivingEntity> hitEntities = new ArrayList<LivingEntity>();
        List<LivingEntity> markedEntities = new ArrayList<LivingEntity>();
        for (int i = 0; i < 360; i += 10) {
			float yawDouble = 0;
			if (i <= 180) {
				yawDouble = ((float) i) / 180F;
			} else {
				yawDouble = 1F - ((((float) i) - 180F) / 180F);
			}

            ModUtils.markNearbyMobs(level, player, markedEntities, pos, 0, player.getRotationVector().y + i, 15);
            ModUtils.spawnParticlesLineToNearbyMobs(level, player, new BlockParticleOption(ParticleTypes.BLOCK, Blocks.CRIMSON_NYLIUM.defaultBlockState()), hitEntities, pos, 0, player.getRotationVector().y + i, 15);
        }
		return stack;
	}
}
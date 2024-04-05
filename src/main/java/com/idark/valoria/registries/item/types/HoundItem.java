package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.sounds.ModSoundRegistry;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class HoundItem extends SwordItem implements Vanishable {

    public HoundItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    public int getUseDuration(ItemStack stack) {
        return 25;
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

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player) entityLiving;
        player.awardStat(Stats.ITEM_USED.get(this));
        player.getCooldowns().addCooldown(this, 120);
        player.level().playSound(null, player.getOnPos(), ModSoundRegistry.BLOODHOUND_ABILITY.get(), SoundSource.AMBIENT, 0.4f, 1.2f);
        Vec3 pos = new Vec3(player.getX(), player.getY() + 0.2f, player.getZ());
        List<LivingEntity> hitEntities = new ArrayList<>();
        List<LivingEntity> markedEntities = new ArrayList<>();
        for (int i = 0; i < 360; i += 10) {
            ValoriaUtils.markNearbyMobs(level, player, markedEntities, pos, 0, player.getRotationVector().y + i, 15);
            ValoriaUtils.spawnParticlesLineToNearbyMobs(level, player, new BlockParticleOption(ParticleTypes.BLOCK, Blocks.CRIMSON_NYLIUM.defaultBlockState()), hitEntities, pos, 0, player.getRotationVector().y + i, 15);
        }

        return stack;
    }
}
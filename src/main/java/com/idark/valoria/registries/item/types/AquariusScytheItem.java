package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.core.particles.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraftforge.registries.*;
import org.joml.*;
import pro.komaru.tridot.core.math.*;

import java.util.*;

public class AquariusScytheItem extends ScytheItem{
    public ArcRandom arcRandom = new ArcRandom();

    public AquariusScytheItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker){
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        if(arcRandom.chance(0.15f)){
            pTarget.knockback(0.6F, pAttacker.getX() - pTarget.getX(), pAttacker.getZ() - pTarget.getZ());
            pTarget.level().playSound(null, pTarget.getOnPos(), SoundsRegistry.WATER_ABILITY.get(), SoundSource.AMBIENT, 0.7f, 1.2f);
        }

        return true;
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving){
        Player player = (Player)entityLiving;
        player.awardStat(Stats.ITEM_USED.get(this));
        float damage = (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);
        for(Item item : ForgeRegistries.ITEMS){
            if(item instanceof ScytheItem){
                player.getCooldowns().addCooldown(item, 100);
            }
        }

        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        List<LivingEntity> hitEntities = new ArrayList<>();
        ValoriaUtils.radiusHit(level, stack, player, ParticleTypes.BUBBLE_POP, hitEntities, pos, 0, player.getRotationVector().y, 3);
        ValoriaUtils.spawnParticlesInRadius(level, stack, ParticleTypes.UNDERWATER, pos, 0, player.getRotationVector().y, 3);
        for(LivingEntity entity : hitEntities){
            entity.hurt(level.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
            entity.knockback(0.4F, player.getX() - entity.getX(), player.getZ() - entity.getZ());
            if(arcRandom.chance(0.25f)){
                entity.knockback(0.6F, player.getX() - entity.getX(), player.getZ() - entity.getZ());
                level.playSound(null, entity.getOnPos(), SoundsRegistry.WATER_ABILITY.get(), SoundSource.AMBIENT, 0.2f, 1.2f);
            }

            if(!player.isCreative()){
                stack.hurtAndBreak(hitEntities.size(), player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }

        level.playSound(null, player.blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.AMBIENT, 1f, 1f);
        level.playSound(null, player.getOnPos(), SoundsRegistry.SWIFTSLICE.get(), SoundSource.PLAYERS, 1F, 1F);
        return stack;
    }
}
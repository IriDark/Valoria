package com.idark.valoria.registries.item.types.elemental;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.util.*;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.minecraft.world.level.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.*;

import static com.idark.valoria.Valoria.BASE_ATTACK_RADIUS_ID;

public class AquariusScytheItem extends ScytheItem{
    public ArcRandom arcRandom = Tmp.rnd;
    private final float attackDamage;

    public AquariusScytheItem(Tier tier, float attackDamageIn, float attackSpeedIn, Item.Properties builderIn){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.attackDamage = attackDamageIn + tier.getAttackDamageBonus();
        this.defaultModifiers = ItemAttributeModifiers.builder()
            .add(AttributeReg.DEPTH_DAMAGE, new AttributeModifier(Valoria.BASE_DEPTH_DAMAGE_ID, 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, this.attackDamage - 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeedIn, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(AttributeReg.ATTACK_RADIUS, new AttributeModifier(BASE_ATTACK_RADIUS_ID, 3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .build();
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker){
        if(!(pAttacker instanceof Player player)) return true;
        pStack.hurtAndBreak(1, pAttacker, EquipmentSlot.MAINHAND);
        if(Utils.Items.getAttackStrengthScale(player, 0.9f)){
            if(arcRandom.chance(0.15f)){
                pTarget.knockback(0.6F, pAttacker.getX() - pTarget.getX(), pAttacker.getZ() - pTarget.getZ());
                pTarget.level().playSound(null, pTarget.getOnPos(), SoundsRegistry.WATER_ABILITY.get(), SoundSource.AMBIENT, 0.7f, 1.2f);
            }
        }

        return true;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving){
        Player player = (Player)entityLiving;
        player.awardStat(Stats.ITEM_USED.get(this));
        float damage = (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + CombatCompat.sweepingRatio(player);
        for(Item item : BuiltInRegistries.ITEM){
            if(item instanceof ScytheItem){
                player.getCooldowns().addCooldown(item, 100);
            }
        }

        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        List<LivingEntity> hitEntities = new ArrayList<>();
        ValoriaUtils.radiusHit(level, stack, player, ParticleTypes.BUBBLE_POP, hitEntities, pos, 0, player.getRotationVector().y, 3);
        Utils.Particles.inRadius(level, stack, ParticleTypes.UNDERWATER, pos, 0, player.getRotationVector().y, 3);
        for(LivingEntity entity : hitEntities){
            if(!player.canAttack(entity)) continue;

            entity.hurt(level.damageSources().playerAttack(player), (damage + CombatCompat.damageBonus(player, stack, entity)) * 1.35f);
            entity.knockback(0.4F, player.getX() - entity.getX(), player.getZ() - entity.getZ());
            if(arcRandom.chance(0.25f)){
                entity.knockback(0.6F, player.getX() - entity.getX(), player.getZ() - entity.getZ());
                level.playSound(null, entity.getOnPos(), SoundsRegistry.WATER_ABILITY.get(), SoundSource.AMBIENT, 0.2f, 1.2f);
            }

            if(!player.isCreative()){
                stack.hurtAndBreak(hitEntities.size(), player, EquipmentSlot.MAINHAND);
            }
        }

        level.playSound(null, player.blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.AMBIENT, 1f, 1f);
        level.playSound(null, player.getOnPos(), SoundsRegistry.SWIFTSLICE.get(), SoundSource.PLAYERS, 1F, 1F);
        return stack;
    }
}

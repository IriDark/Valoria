package com.idark.valoria.registries.item.types.elemental;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.component.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.client.render.screenshake.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.*;

public class InfernalScytheItem extends ScytheItem{
    public ArcRandom arcRandom = Tmp.rnd;
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public InfernalScytheItem(Tier tier, float attackDamageIn, float attackSpeedIn, Item.Properties builderIn){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.attackDamage = attackDamageIn + tier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(AttributeReg.INFERNAL_DAMAGE.get(), new AttributeModifier(Valoria.BASE_INFERNAL_DAMAGE_UUID, "Weapon modifier", 2, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage - 2, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker){
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        if(EnchantmentHelper.getFireAspect(pAttacker) > 0){
            pAttacker.level().playSound(null, pTarget.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 1, 1);
        }else if(arcRandom.chance(0.07f)){
            pTarget.setSecondsOnFire(4);
            pAttacker.level().playSound(null, pTarget.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 1, 1);
        }

        return true;
    }

    public void performAttack(Level level, ItemStack stack, Player player){
        List<LivingEntity> hitEntities = new ArrayList<>();
        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        float damage = (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);
        float radius = (float)player.getAttributeValue(AttributeReg.ATTACK_RADIUS.get());

        ValoriaUtils.radiusHit(level, player, ParticleTypes.FLAME, hitEntities, pos, 0, player.getRotationVector().y, radius);
        applyCooldown(player, hitEntities.isEmpty() ? builder.minCooldownTime : builder.cooldownTime);
        for(LivingEntity entity : hitEntities){
            if(!player.canAttack(entity)) continue;

            entity.hurt(level.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
            performEffects(entity, player);
            Utils.Entities.applyWithChance(entity, builder.effects, builder.chance, arcRandom);
            if(!player.isCreative()){
                stack.hurtAndBreak(hitEntities.size(), player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }

        ScreenshakeHandler.add(new PositionedScreenshakeInstance(3, pro.komaru.tridot.util.phys.Vec3.from(player.getEyePosition()), 0, 30).intensity(0.5f).interp(Interp.circleOut));
    }

    public void performEffects(LivingEntity targets, Player player){
        targets.knockback(0.4F, player.getX() - targets.getX(), player.getZ() - targets.getZ());
        if(EnchantmentHelper.getFireAspect(player) > 0){
            int i = EnchantmentHelper.getFireAspect(player);
            targets.setSecondsOnFire(i * 4);
            targets.level().playSound(null, targets.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 1, 1);
        }else if(arcRandom.chance(0.07f)){
            targets.setSecondsOnFire(4);
            targets.level().playSound(null, targets.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 1, 1);
        }
    }

    public Seq<TooltipComponent> getTooltips(ItemStack pStack){
        if(builder.attackUsages > 1){
            return Seq.with(
            new SeparatorComponent(Component.translatable("tooltip.valoria.abilities")),
            new AbilityComponent(Component.translatable("tooltip.valoria.scythe").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/gui/tooltips/infernal_strike.png")),
            new ClientTextComponent(Component.translatable("tooltip.valoria.usage_count", builder.attackUsages).withStyle(ChatFormatting.GRAY)),
            new ClientTextComponent(Component.translatable("tooltip.valoria.rmb").withStyle(style -> style.withFont(Valoria.FONT)))
            );
        } else {
            return Seq.with(
            new SeparatorComponent(Component.translatable("tooltip.valoria.abilities")),
            new AbilityComponent(Component.translatable("tooltip.valoria.scythe").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/gui/tooltips/infernal_strike.png")),
            new ClientTextComponent(Component.translatable("tooltip.valoria.rmb").withStyle(style -> style.withFont(Valoria.FONT)))
            );
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(1, Component.translatable("tooltip.valoria.infernal_scythe", Component.literal(String.format("%.1f%%", 0.07f * 100))).withStyle(ChatFormatting.GRAY));
        Utils.Items.effectTooltip(builder.effects, tooltip, 1, builder.chance);
    }
}
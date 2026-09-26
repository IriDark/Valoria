package com.idark.valoria.registries.item.types.elemental;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
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
import net.minecraft.world.item.component.*;
import net.minecraft.world.level.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.client.render.screenshake.*;
import pro.komaru.tridot.common.registry.item.components.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.*;

import static com.idark.valoria.Valoria.BASE_ATTACK_RADIUS_ID;

public class InfernalScytheItem extends ScytheItem{
    public ArcRandom arcRandom = Tmp.rnd;
    private final float attackDamage;

    public InfernalScytheItem(Tier tier, float attackDamageIn, float attackSpeedIn, Item.Properties builderIn){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.attackDamage = attackDamageIn + tier.getAttackDamageBonus();
        this.defaultModifiers = ItemAttributeModifiers.builder()
            .add(AttributeReg.INFERNAL_DAMAGE, new AttributeModifier(Valoria.BASE_INFERNAL_DAMAGE_ID, 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, this.attackDamage - 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeedIn, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(AttributeReg.ATTACK_RADIUS, new AttributeModifier(BASE_ATTACK_RADIUS_ID, 3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .build();
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker){
        pStack.hurtAndBreak(1, pAttacker, EquipmentSlot.MAINHAND);
        if(CombatCompat.fireAspect(pAttacker) > 0){
            pAttacker.level().playSound(null, pTarget.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 1, 1);
        }else if(arcRandom.chance(0.07f)){
            pTarget.igniteForSeconds(4);
            pAttacker.level().playSound(null, pTarget.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 1, 1);
        }

        return true;
    }

    public void performAttack(Level level, ItemStack stack, Player player){
        List<LivingEntity> hitEntities = new ArrayList<>();
        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        float damage = (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + CombatCompat.sweepingRatio(player);
        float radius = (float)player.getAttributeValue(AttributeReg.ATTACK_RADIUS);

        ValoriaUtils.radiusHit(level, player, ParticleTypes.FLAME, hitEntities, pos, 0, player.getRotationVector().y, radius);
        applyCooldown(player, hitEntities.isEmpty() ? builder.minCooldownTime : builder.cooldownTime);
        for(LivingEntity entity : hitEntities){
            if(!player.canAttack(entity)) continue;

            entity.hurt(level.damageSources().playerAttack(player), (damage + CombatCompat.damageBonus(player, stack, entity)) * 1.35f);
            performEffects(entity, player);
            Utils.Entities.applyWithChance(entity, builder.effects, builder.chance, arcRandom);
            if(!player.isCreative()){
                stack.hurtAndBreak(hitEntities.size(), player, EquipmentSlot.MAINHAND);
            }
        }

        ScreenshakeHandler.add(new PositionedScreenshakeInstance(3, pro.komaru.tridot.util.phys.Vec3.from(player.getEyePosition()), 0, 30).intensity(0.5f).interp(Interp.circleOut));
    }

    public void performEffects(LivingEntity targets, Player player){
        targets.knockback(0.4F, player.getX() - targets.getX(), player.getZ() - targets.getZ());
        int i = CombatCompat.fireAspect(player);
        if(i > 0){
            targets.igniteForSeconds(i * 4);
            targets.level().playSound(null, targets.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 1, 1);
        }else if(arcRandom.chance(0.07f)){
            targets.igniteForSeconds(4);
            targets.level().playSound(null, targets.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 1, 1);
        }
    }

    public Seq<TooltipComponent> getTooltips(ItemStack pStack){
        if(builder.attackUsages > 1){
            return Seq.with(
            new SeparatorComponent(Component.translatable("tooltip.tridot.abilities")),
            new AbilityComponent(Component.translatable("tooltip.valoria.scythe").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/gui/tooltips/infernal_strike.png")),
            new TextComponent(Component.translatable("tooltip.valoria.usage_count", builder.attackUsages).withStyle(ChatFormatting.GRAY)),
            new TextComponent(Component.translatable("tooltip.valoria.rmb").withStyle(style -> style.withFont(Valoria.FONT)))
            );
        } else {
            return Seq.with(
            new SeparatorComponent(Component.translatable("tooltip.tridot.abilities")),
            new AbilityComponent(Component.translatable("tooltip.valoria.scythe").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/gui/tooltips/infernal_strike.png")),
            new TextComponent(Component.translatable("tooltip.valoria.rmb").withStyle(style -> style.withFont(Valoria.FONT)))
            );
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(1, Component.translatable("tooltip.valoria.infernal_scythe", Component.literal(String.format("%.1f%%", 0.07f * 100))).withStyle(ChatFormatting.GRAY));
        Utils.Items.effectTooltip(builder.effects, tooltip, 1, builder.chance);
    }
}

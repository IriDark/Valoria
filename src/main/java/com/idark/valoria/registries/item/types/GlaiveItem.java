package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.builders.*;
import com.idark.valoria.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.client.render.screenshake.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.*;

import static com.idark.valoria.Valoria.BASE_ENTITY_REACH_ID;

public class GlaiveItem extends ScytheItem{
    public ArcRandom arcRandom = Tmp.rnd;
    public GlaiveItem(Builder pBuilder){
        super(pBuilder.addAttribute(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BASE_ENTITY_REACH_ID, 1, AttributeModifier.Operation.ADD_VALUE)));
    }

    public void performAttack(Level level, ItemStack stack, Player player){
        List<LivingEntity> hitEntities = new ArrayList<>();
        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        float damage = (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + CombatCompat.sweepingRatio(player);
        float radius = (float)player.getAttributeValue(AttributeReg.ATTACK_RADIUS);
        ValoriaUtils.radiusHit(level, stack, player, builder.particleOptions, hitEntities, pos, 0, player.getRotationVector().y, radius);
        int cooldown = hitEntities.isEmpty() ? builder.minCooldownTime : builder.cooldownTime;
        applyCooldown(player, getCooldownReduction(cooldown, stack));

        for(LivingEntity entity : hitEntities){
            if(!player.canAttack(entity)) continue;

            entity.hurt(level.damageSources().playerAttack(player), (damage + CombatCompat.damageBonus(player, stack, entity)) * 1.35f);
            performEffects(entity, player);
            Utils.Entities.applyWithChance(entity, builder.effects, builder.chance, arcRandom);
            if(!player.isCreative()){
                stack.hurtAndBreak(hitEntities.size(), player, EquipmentSlot.MAINHAND);
            }
        }

        ScreenshakeHandler.add(player, new PositionedScreenshakeInstance(builder.screenShakeDuration, player.getEyePosition(), 0, 30).intensity(builder.screenShakeIntensity).interp(builder.screenShakeEasing));
    }

    public static class Builder extends AbstractScytheBuilder<GlaiveItem>{
        public Builder(float attackDamageIn, float attackSpeedIn, Properties itemProperties){
            super(attackDamageIn, attackSpeedIn, itemProperties);
        }

        @Override
        public GlaiveItem build(){
            return new GlaiveItem(this);
        }
    }
}

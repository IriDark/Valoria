package com.idark.valoria.registries.item.types;

import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.builders.*;
import com.idark.valoria.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraftforge.common.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.client.render.screenshake.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.*;

import static com.idark.valoria.Valoria.*;

public class GlaiveItem extends ScytheItem{
    public ArcRandom arcRandom = Tmp.rnd;
    public GlaiveItem(Builder pBuilder){
        super(pBuilder);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.builder.attackDamageIn, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", this.builder.attackSpeedIn, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(BASE_ENTITY_REACH_UUID, "Spear modifier", 1, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeReg.ATTACK_RADIUS.get(), new AttributeModifier(BASE_ATTACK_RADIUS_UUID, "Tool modifier", this.builder.attackRadius, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    public void performAttack(Level level, ItemStack stack, Player player){
        List<LivingEntity> hitEntities = new ArrayList<>();
        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        float damage = (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);
        float radius = (float)player.getAttributeValue(AttributeReg.ATTACK_RADIUS.get());
        ValoriaUtils.radiusHit(level, stack, player, builder.particleOptions, hitEntities, pos, 0, player.getRotationVector().y, radius);
        int cooldown = hitEntities.isEmpty() ? builder.minCooldownTime : builder.cooldownTime;
        applyCooldown(player, getCooldownReduction(cooldown, stack));

        for(LivingEntity entity : hitEntities){
            if(!player.canAttack(entity)) continue;

            entity.hurt(level.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
            performEffects(entity, player);
            Utils.Entities.applyWithChance(entity, builder.effects, builder.chance, arcRandom);
            if(!player.isCreative()){
                stack.hurtAndBreak(hitEntities.size(), player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }

        ScreenshakeHandler.add(new PositionedScreenshakeInstance(builder.screenShakeDuration, pro.komaru.tridot.util.phys.Vec3.from(player.getEyePosition()), 0, 30).intensity(builder.screenShakeIntensity).interp(builder.screenShakeEasing));
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

package com.idark.valoria.registries.item.types.curio.charm;

import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import net.minecraft.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class GolemCoreItem extends TimedMagmaImmunityItem {
    public enum Type {
        NATURE(0),
        RIVER(0),
        MAGMATIC(15),
        ELEMENTAL(15);

        private final int magmaImmunitySec;

        Type(int magmaImmunitySec) {
            this.magmaImmunitySec = magmaImmunitySec;
        }

        public int getMagmaImmunitySec() {
            return magmaImmunitySec;
        }
    }

    private final Type type;

    public GolemCoreItem(Type type, Properties properties) {
        super(type.getMagmaImmunitySec(), properties);
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();

        switch (this.type) {
            case NATURE -> {
                modifiers.put(AttributeReg.NATURE_RESISTANCE.get(), new AttributeModifier(
                        uuid, "Golem core nature resistance", 25.0D, AttributeModifier.Operation.ADDITION));
                modifiers.put(AttributeReg.INFERNAL_RESISTANCE.get(), new AttributeModifier(
                        new UUID(uuid.getMostSignificantBits() + 1L, uuid.getLeastSignificantBits()),
                        "Golem core fire weakness", -15.0D, AttributeModifier.Operation.ADDITION));
                modifiers.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(
                        new UUID(uuid.getMostSignificantBits() + 2L, uuid.getLeastSignificantBits()),
                        "Golem core knockback resistance", 0.15D, AttributeModifier.Operation.ADDITION));
            }
            case RIVER -> {
                modifiers.put(AttributeReg.DEPTH_RESISTANCE.get(), new AttributeModifier(
                        uuid, "Golem core depth resistance", 25.0D, AttributeModifier.Operation.ADDITION));
                modifiers.put(AttributeReg.INFERNAL_RESISTANCE.get(), new AttributeModifier(
                        new UUID(uuid.getMostSignificantBits() + 1L, uuid.getLeastSignificantBits()),
                        "Golem core fire weakness", -15.0D, AttributeModifier.Operation.ADDITION));
                modifiers.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(
                    new UUID(uuid.getMostSignificantBits() + 2L, uuid.getLeastSignificantBits()),
                    "Golem core knockback resistance", 0.15D, AttributeModifier.Operation.ADDITION));
                modifiers.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(
                    new UUID(uuid.getMostSignificantBits() + 3L, uuid.getLeastSignificantBits()),
                    "Golem core knockback resistance", 0.05D, Operation.MULTIPLY_TOTAL));
            }
            case MAGMATIC -> {
                modifiers.put(AttributeReg.INFERNAL_RESISTANCE.get(), new AttributeModifier(
                        uuid, "Golem core infernal resistance", 25.0D, AttributeModifier.Operation.ADDITION));
                modifiers.put(AttributeReg.DEPTH_RESISTANCE.get(), new AttributeModifier(
                        new UUID(uuid.getMostSignificantBits() + 1L, uuid.getLeastSignificantBits()),
                        "Golem core water weakness", -15.0D, AttributeModifier.Operation.ADDITION));
                modifiers.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(
                        new UUID(uuid.getMostSignificantBits() + 2L, uuid.getLeastSignificantBits()),
                        "Golem core knockback resistance", 0.15D, AttributeModifier.Operation.ADDITION));
            }
            case ELEMENTAL -> {
                modifiers.put(AttributeReg.NATURE_RESISTANCE.get(), new AttributeModifier(
                        uuid, "Elemental core nature resistance", 25.0D, AttributeModifier.Operation.ADDITION));
                modifiers.put(AttributeReg.DEPTH_RESISTANCE.get(), new AttributeModifier(
                        new UUID(uuid.getMostSignificantBits() + 1L, uuid.getLeastSignificantBits()),
                        "Elemental core depth resistance", 25.0D, AttributeModifier.Operation.ADDITION));
                modifiers.put(AttributeReg.INFERNAL_RESISTANCE.get(), new AttributeModifier(
                        new UUID(uuid.getMostSignificantBits() + 2L, uuid.getLeastSignificantBits()),
                        "Elemental core infernal resistance", 25.0D, AttributeModifier.Operation.ADDITION));
                modifiers.put(AttributeReg.ELEMENTAL_RESISTANCE.get(), new AttributeModifier(
                        new UUID(uuid.getMostSignificantBits() + 3L, uuid.getLeastSignificantBits()),
                        "Elemental core elemental resistance", 25.0D, AttributeModifier.Operation.ADDITION));
                modifiers.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(
                        new UUID(uuid.getMostSignificantBits() + 4L, uuid.getLeastSignificantBits()),
                        "Elemental core knockback resistance", 0.35D, AttributeModifier.Operation.ADDITION));
                modifiers.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(
                        new UUID(uuid.getMostSignificantBits() + 5L, uuid.getLeastSignificantBits()),
                        "Elemental core toughness", 2.0D, AttributeModifier.Operation.ADDITION));
            }
        }

        return modifiers;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        LivingEntity wearer = slotContext.entity();
        if (wearer.level().isClientSide() || !(wearer.level() instanceof ServerLevel serverLevel)) return;

        if (this.type == Type.RIVER || this.type == Type.ELEMENTAL) {
            if (wearer.isInWaterOrRain()) {
                wearer.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 100, 0, true, false, false));
            }
        }

        if (this.type == Type.NATURE || this.type == Type.ELEMENTAL) {
            if (serverLevel.getServer().getTickCount() % 120 == 0) {
                if (serverLevel.canSeeSky(wearer.blockPosition()) && wearer.getHealth() < wearer.getMaxHealth()) {
                    wearer.heal(1.0F);
                }
            }
        }
    }

    @Override
    public void onHurt(ItemStack stack, LivingEntity target, DamageSource source, float damage) {
        super.onHurt(stack, target, source, damage);
        if (target.level().isClientSide() || !(target.level() instanceof ServerLevel serverLevel)) return;

        Entity directAttacker = source.getEntity();
        if (!(directAttacker instanceof LivingEntity attacker) || attacker == target) return;

        if (this.type == Type.MAGMATIC || this.type == Type.ELEMENTAL) {
            attacker.setSecondsOnFire(4);
            attacker.hurt(target.damageSources().inFire(), 2.5F);
            serverLevel.sendParticles(ParticleTypes.FLAME, attacker.getX(), attacker.getY(0.5), attacker.getZ(), 12, 0.25, 0.25, 0.25, 0.05);
            serverLevel.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.6F, 1.2F);
        }

        if (this.type == Type.RIVER || this.type == Type.ELEMENTAL) {
            double dx = target.getX() - attacker.getX();
            double dz = target.getZ() - attacker.getZ();
            attacker.knockback(0.8D, dx, dz);
            attacker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 0));
            target.extinguishFire();
            serverLevel.sendParticles(ParticleTypes.SPLASH, attacker.getX(), attacker.getY(0.5), attacker.getZ(), 16, 0.3, 0.3, 0.3, 0.1);
            serverLevel.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.GENERIC_SPLASH, SoundSource.PLAYERS, 0.7F, 1.0F);
        }

        if (this.type == Type.NATURE || this.type == Type.ELEMENTAL) {
            attacker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 1));
            attacker.hurt(target.damageSources().thorns(target), 2.5F);
            serverLevel.sendParticles(ParticleTypes.COMPOSTER, attacker.getX(), attacker.getY(0.5), attacker.getZ(), 10, 0.2, 0.2, 0.2, 0.05);
            serverLevel.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.THORNS_HIT, SoundSource.PLAYERS, 0.6F, 1.0F);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.empty());
        switch (this.type) {
            case NATURE -> tooltip.add(Component.translatable("tooltip.valoria.nature_golem_core").withStyle(ChatFormatting.GRAY));
            case RIVER -> tooltip.add(Component.translatable("tooltip.valoria.river_golem_core").withStyle(ChatFormatting.GRAY));
            case MAGMATIC -> tooltip.add(Component.translatable("tooltip.valoria.magmatic_golem_core").withStyle(ChatFormatting.GRAY));
            case ELEMENTAL -> tooltip.add(Component.translatable("tooltip.valoria.elemental_golem_core").withStyle(ChatFormatting.GRAY));
        }
    }
}

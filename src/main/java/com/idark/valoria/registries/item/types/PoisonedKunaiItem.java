package com.idark.valoria.registries.item.types;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.idark.valoria.registries.entity.ai.attributes.ModAttributes;
import com.idark.valoria.registries.entity.projectile.PoisonedKunaiEntity;
import com.idark.valoria.util.RandomUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Random;

public class PoisonedKunaiItem extends KunaiItem implements Vanishable {
    private final Multimap<Attribute, AttributeModifier> tridentAttributes;
    Random rand = new Random();

    public PoisonedKunaiItem(Item.Properties builderIn) {
        super(builderIn);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 3.0D, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -1.9F, AttributeModifier.Operation.ADDITION));
        builder.put(ModAttributes.PROJECTILE_DAMAGE.get(), new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 6.0F, AttributeModifier.Operation.ADDITION));
        this.tridentAttributes = builder.build();
    }

    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player playerEntity) {
            int i = this.getUseDuration(stack) - timeLeft;
            if (i >= 6) {
                if (!worldIn.isClientSide) {
                    stack.hurtAndBreak(1, playerEntity, (player) -> player.broadcastBreakEvent(entityLiving.getUsedItemHand()));
                    PoisonedKunaiEntity poisoned = new PoisonedKunaiEntity(worldIn, playerEntity, stack);
                    poisoned.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0F, 2.5F + (float) 0 * 0.5F, 1.0F);
                    if (playerEntity.getAbilities().instabuild) {
                        poisoned.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }

                    worldIn.addFreshEntity(poisoned);
                    worldIn.playSound(playerEntity, poisoned, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                    if (!playerEntity.getAbilities().instabuild) {
                        playerEntity.getInventory().removeItem(stack);
                    }
                }

                playerEntity.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        if (RandomUtil.percentChance(25d)) {
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 425, 0));
            if (target.level().isClientSide) {
                for (int i = 0; i < 10; i++) {
                    target.level().addParticle(ParticleTypes.POOF, target.getX() + rand.nextDouble(), target.getY(), target.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
                }
            }

            return true;
        }

        return true;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.tridentAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.empty());
        tooltip.add(Component.literal(" 25% ").withStyle(ChatFormatting.DARK_GREEN).append(Component.translatable("tooltip.valoria.poison_chance").withStyle(ChatFormatting.DARK_GREEN)));
        tooltip.add(Component.literal(" 100% ").withStyle(ChatFormatting.DARK_GREEN).append(Component.translatable("tooltip.valoria.poison_chance_ranged").withStyle(ChatFormatting.DARK_GREEN)));
    }
}
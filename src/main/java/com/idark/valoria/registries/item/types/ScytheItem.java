package com.idark.valoria.registries.item.types;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.idark.valoria.client.render.model.item.ItemAnims;
import com.idark.valoria.client.render.model.item.RadiusAttackAnim;
import com.idark.valoria.registries.SoundsRegistry;
import com.idark.valoria.util.ValoriaUtils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScytheItem extends SwordItem implements Vanishable, ICustomAnimationItem, ICooldownItem, IRadiusItem {
    public static RadiusAttackAnim animation = new RadiusAttackAnim();
    public int radius = 3;
    private final ImmutableList<MobEffectInstance> effects;

    public ScytheItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.effects = ImmutableList.of();
    }

    /**
     * @param radius Default value is 3, specified in blocks
     * @param pEffects Effects applied on attack
     * <p>
     * <pre>{@code public static final RegistryObject<Item> SCYTHE_NAME = ITEMS.register("scythe_id", () -> new ScytheItem(TIER, ATTACK_DAMAGE, ATTACK_SPEED, RADIUS, new Item.Properties(), new MobEffectInstance(EFFECT, EFFECT DURATION, EFFECT_LEVEL)));
     *}</pre>
     */
    public ScytheItem(Tier tier, int attackDamageIn, float attackSpeedIn, int radius, Properties builderIn, MobEffectInstance... pEffects) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.radius = radius;
        this.effects = ImmutableList.copyOf(pEffects);
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

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.CUSTOM;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemAnims getAnimation(ItemStack stack) {
        return animation;
    }

    public int getUseDuration(ItemStack stack) {
        return 5;
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player) entityLiving;
        player.awardStat(Stats.ITEM_USED.get(this));
        float damage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);
        for (Item item : ForgeRegistries.ITEMS) {
            if (item instanceof ScytheItem) {
                player.getCooldowns().addCooldown(item, 100);
            }
        }

        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        List<LivingEntity> hitEntities = new ArrayList<>();
        ValoriaUtils.radiusHit(level, stack, player, ParticleTypes.POOF, hitEntities, pos, 0, player.getRotationVector().y, radius);
        for (LivingEntity entity : hitEntities) {
            entity.hurt(level.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
            entity.knockback(0.4F, player.getX() - entity.getX(), player.getZ() - entity.getZ());
            if (EnchantmentHelper.getFireAspect(player) > 0) {
                int i = EnchantmentHelper.getFireAspect(player);
                entity.setSecondsOnFire(i * 4);
            }

            if (!effects.isEmpty()) {
                for(MobEffectInstance effectInstance : effects) {
                    entity.addEffect(new MobEffectInstance(effectInstance));
                }
            }

            if (!player.isCreative()) {
                stack.hurtAndBreak(hitEntities.size(), player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }

        level.playSound(null, player.blockPosition(), SoundsRegistry.SWIFTSLICE.get(), SoundSource.AMBIENT, 10f, 1f);
        return stack;
    }

    public void addEffectsTooltip(List<Component> pTooltips, float pDurationFactor) {
        List<Pair<Attribute, AttributeModifier>> list = Lists.newArrayList();
        if (!effects.isEmpty()) {
            for (MobEffectInstance mobeffectinstance : effects) {
                pTooltips.add(CommonComponents.EMPTY);
                MutableComponent mutablecomponent = Component.translatable(mobeffectinstance.getDescriptionId());
                MobEffect mobeffect = mobeffectinstance.getEffect();
                Map<Attribute, AttributeModifier> map = mobeffect.getAttributeModifiers();
                if (!map.isEmpty()) {
                    for (Map.Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
                        AttributeModifier attributemodifier = entry.getValue();
                        AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), mobeffect.getAttributeModifierValue(mobeffectinstance.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                        list.add(new Pair<>(entry.getKey(), attributemodifier1));
                    }
                }

                if (mobeffectinstance.getAmplifier() > 0) {
                    mutablecomponent = Component.translatable("potion.withAmplifier", mutablecomponent, Component.translatable("potion.potency." + mobeffectinstance.getAmplifier()));
                }

                if (!mobeffectinstance.endsWithin(20)) {
                    mutablecomponent = Component.translatable("potion.withDuration", mutablecomponent, MobEffectUtil.formatDuration(mobeffectinstance, pDurationFactor));
                }

                pTooltips.add(mutablecomponent.withStyle(mobeffect.getCategory().getTooltipFormatting()));
            }
        }

        if (!list.isEmpty()) {
            pTooltips.add(CommonComponents.EMPTY);
            pTooltips.add(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));

            for (Pair<Attribute, AttributeModifier> pair : list) {
                AttributeModifier attributemodifier2 = pair.getSecond();
                double d0 = attributemodifier2.getAmount();
                double d1;
                if (attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                    d1 = attributemodifier2.getAmount();
                } else {
                    d1 = attributemodifier2.getAmount() * 100.0D;
                }

                if (d0 > 0.0D) {
                    pTooltips.add(Component.translatable("attribute.modifier.plus." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(pair.getFirst().getDescriptionId())).withStyle(ChatFormatting.BLUE));
                } else if (d0 < 0.0D) {
                    d1 *= -1.0D;
                    pTooltips.add(Component.translatable("attribute.modifier.take." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(pair.getFirst().getDescriptionId())).withStyle(ChatFormatting.RED));
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.scythe").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.valoria.rmb").withStyle(ChatFormatting.GREEN));
        addEffectsTooltip(tooltip, 1);
    }
}
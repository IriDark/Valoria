package com.idark.valoria.registries.item.types;

import com.google.common.collect.*;
import com.idark.valoria.client.model.animations.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.client.animation.*;
import mod.maxbogomol.fluffy_fur.common.item.*;
import net.minecraft.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.registries.*;
import org.joml.*;

import javax.annotation.*;
import java.util.*;

public class ScytheItem extends SwordItem implements ICustomAnimationItem, ICooldownItem, IRadiusItem, ISpinAttackItem {
    public static SpinAttackAnimation animation = new SpinAttackAnimation();
    public float chance = 1;
    public final ImmutableList<MobEffectInstance> effects;
    public final Multimap<Attribute, AttributeModifier> defaultModifiers;
    public ArcRandom arcRandom = new ArcRandom();
    private final AttributeModifier radiusModifier;

    public ScytheItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.effects = ImmutableList.of();
        radiusModifier = new AttributeModifier(UUID.fromString("49438567-6ad2-41bd-8385-676ad2a1bd5e"), "Tool modifier", 3, AttributeModifier.Operation.ADDITION);

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", attackDamageIn, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.ATTACK_RADIUS.get(), radiusModifier);
        this.defaultModifiers = builder.build();
    }

    /**
     * @param radius   Default value is 3, specified in blocks
     * @param pEffects Effects applied on attack
     *                 <p>
     *                 <pre>{@code public static final RegistryObject<Item> SCYTHE_NAME = ITEMS.register("scythe_id", () -> new ScytheItem(TIER, ATTACK_DAMAGE, ATTACK_SPEED, RADIUS, new Item.Properties(), new MobEffectInstance(EFFECT, EFFECT DURATION, EFFECT_LEVEL)));
     *                 }</pre>
     */
    public ScytheItem(Tier tier, int attackDamageIn, float attackSpeedIn, int radius, Properties builderIn, MobEffectInstance... pEffects) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.effects = ImmutableList.copyOf(pEffects);
        radiusModifier = new AttributeModifier(UUID.fromString("49438567-6ad2-41bd-8385-676ad2a1bd5e"), "Tool modifier", radius, AttributeModifier.Operation.ADDITION);

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", attackDamageIn, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.ATTACK_RADIUS.get(), radiusModifier);
        this.defaultModifiers = builder.build();
    }

    /**
     * @param radius   Default value is 3, specified in blocks
     * @param chance   Chance to apply effects
     * @param pEffects Effects applied on attack
     *                 <p>
     *                 <pre>{@code public static final RegistryObject<Item> SCYTHE_NAME = ITEMS.register("scythe_id", () -> new ScytheItem(TIER, ATTACK_DAMAGE, ATTACK_SPEED, RADIUS, new Item.Properties(), CHANCE, new MobEffectInstance(EFFECT, EFFECT DURATION, EFFECT_LEVEL)));
     *                 }</pre>
     */
    public ScytheItem(Tier tier, int attackDamageIn, float attackSpeedIn, int radius, Properties builderIn, float chance, MobEffectInstance... pEffects) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.effects = ImmutableList.copyOf(pEffects);
        this.chance = chance;
        radiusModifier = new AttributeModifier(UUID.fromString("49438567-6ad2-41bd-8385-676ad2a1bd5e"), "Tool modifier", radius, AttributeModifier.Operation.ADDITION);

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", attackDamageIn, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.ATTACK_RADIUS.get(), radiusModifier);
        this.defaultModifiers = builder.build();
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        if (pEquipmentSlot == EquipmentSlot.OFFHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> atts = ImmutableMultimap.builder();
            atts.put(AttributeRegistry.ATTACK_RADIUS.get(), radiusModifier);
            return atts.build();
        }

        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (!playerIn.isShiftKeyDown()) {
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.CUSTOM;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemAnimation getAnimation(ItemStack stack) {
        return animation;
    }

    public int getUseDuration(ItemStack stack) {
        return 7;
    }

    public SoundEvent getAttackSound() {
        return SoundsRegistry.SWIFTSLICE.get();
    }

    @Nullable
    public ParticleOptions getAttackParticle() {
        return ParticleTypes.POOF;
    }

    public void applyCooldown(List<LivingEntity> hitEntities, Player playerIn) {
        for (Item item : ForgeRegistries.ITEMS) {
            if (item instanceof ScytheItem) {
                playerIn.getCooldowns().addCooldown(item, hitEntities.isEmpty() ? 15 : 75);
            }
        }
    }

    public void performEffects(LivingEntity targets, Player player) {
        targets.knockback(0.4F, player.getX() - targets.getX(), player.getZ() - targets.getZ());
        if (EnchantmentHelper.getFireAspect(player) > 0) {
            int i = EnchantmentHelper.getFireAspect(player);
            targets.setSecondsOnFire(i * 4);
        }
    }

    public void performAttack(Level level, ItemStack stack, Player player) {
        List<LivingEntity> hitEntities = new ArrayList<>();
        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        float damage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);
        float radius = (float) player.getAttributeValue(AttributeRegistry.ATTACK_RADIUS.get());

        ValoriaUtils.radiusHit(level, stack, player, getAttackParticle(), hitEntities, pos, 0, player.getRotationVector().y, radius);
        applyCooldown(hitEntities, player);
        for (LivingEntity entity : hitEntities) {
            entity.hurt(level.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
            performEffects(entity, player);
            ValoriaUtils.chanceEffect(entity, effects, chance, arcRandom);
            if (!player.isCreative()) {
                stack.hurtAndBreak(hitEntities.size(), player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player) entityLiving;
        performAttack(level, stack, player);
        player.awardStat(Stats.ITEM_USED.get(this));
        level.playSound(null, player.getOnPos(), getAttackSound(), SoundSource.PLAYERS, 1.0F, 1F);
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.scythe").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.valoria.rmb").withStyle(ChatFormatting.GREEN));
        ValoriaUtils.addEffectsTooltip(effects, tooltip, 1, chance);
    }
}
package com.idark.valoria.registries.item.types;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.idark.valoria.Valoria;
import com.idark.valoria.client.ui.OverlayRender;
import com.idark.valoria.core.interfaces.ICooldownItem;
import com.idark.valoria.registries.AttributeRegistry;
import com.idark.valoria.registries.SoundsRegistry;
import com.idark.valoria.util.ArcRandom;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

import java.util.List;
import java.util.UUID;

public class KatanaItem extends SwordItem implements ICooldownItem {
    public float chance = 1;
    public final ImmutableList<MobEffectInstance> effects;
    public final Multimap<Attribute, AttributeModifier> defaultModifiers;
    public ArcRandom arcRandom = new ArcRandom();
    private final AttributeModifier dashModifier;

    public KatanaItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.effects = ImmutableList.of();
        dashModifier = new AttributeModifier(UUID.fromString("b0e5853a-d071-40db-a585-3ad07100db82"), "Tool modifier", 1.8f, AttributeModifier.Operation.ADDITION);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", attackDamageIn, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.DASH_DISTANCE.get(), dashModifier);
        this.defaultModifiers = builder.build();
    }

    /**
     * @param dashDistance Default value: 1.8f
     * @param pEffects     MobEffect instance that applied on hit enemies
     */
    public KatanaItem(Tier tier, int attackDamageIn, float attackSpeedIn, float dashDistance, Item.Properties builderIn, MobEffectInstance... pEffects) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.effects = ImmutableList.copyOf(pEffects);
        dashModifier = new AttributeModifier(UUID.fromString("b0e5853a-d071-40db-a585-3ad07100db82"), "Tool modifier", dashDistance, AttributeModifier.Operation.ADDITION);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", attackDamageIn, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.DASH_DISTANCE.get(), dashModifier);
        this.defaultModifiers = builder.build();
    }

    /**
     * @param dashDistance Default value: 1.8f
     * @param chance       Chance to apply effects
     * @param pEffects     Effects applied on attack
     *                     <p>
     *                     <pre>{@code public static final RegistryObject<Item> KATANA_NAME = ITEMS.register("katana_id", () -> new KatanaItem(TIER, ATTACK_DAMAGE, ATTACK_SPEED, DISTANCE, new Item.Properties(), CHANCE, new MobEffectInstance(EFFECT, EFFECT DURATION, EFFECT_LEVEL)));
     *                     }</pre>
     */
    public KatanaItem(Tier tier, int attackDamageIn, float attackSpeedIn, float dashDistance, Item.Properties builderIn, float chance, MobEffectInstance... pEffects) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.effects = ImmutableList.copyOf(pEffects);
        this.chance = chance;
        dashModifier = new AttributeModifier(UUID.fromString("b0e5853a-d071-40db-a585-3ad07100db82"), "Tool modifier", dashDistance, AttributeModifier.Operation.ADDITION);

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", attackDamageIn, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.DASH_DISTANCE.get(), dashModifier);
        this.defaultModifiers = builder.build();
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        if (pEquipmentSlot == EquipmentSlot.OFFHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> atts = ImmutableMultimap.builder();
            atts.put(AttributeRegistry.DASH_DISTANCE.get(), dashModifier);
            return atts.build();
        }

        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    public static double distance(double distance, Level level, Player player) {
        double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
        double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;
        double X = Math.sin(pitch) * Math.cos(yaw) * distance;
        double Y = Math.cos(pitch) * distance;
        double Z = Math.sin(pitch) * Math.sin(yaw) * distance;

        Vec3 pos = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        Vec3 EndPos = (player.getViewVector(0.0f).scale(2.0d));
        HitResult hitresult = ValoriaUtils.getHitResult(player.getEyePosition(), player, (e) -> true, EndPos, level);
        if (hitresult != null) {
            switch (hitresult.getType()) {
                case BLOCK, MISS:
                    X = hitresult.getLocation().x();
                    Y = hitresult.getLocation().y();
                    Z = hitresult.getLocation().z();
                    break;
                case ENTITY:
                    Entity entity = ((EntityHitResult) hitresult).getEntity();
                    X = entity.getX();
                    Y = entity.getY();
                    Z = entity.getZ();
                    break;
            }
        }

        return Math.sqrt((X - pos.x) * (X - pos.x) + (Y - pos.y) * (Y - pos.y) + (Z - pos.z) * (Z - pos.z));
    }

    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level worldIn, BlockState state, @NotNull BlockPos pos, LivingEntity entityLiving) {
        if (state.getDestroySpeed(worldIn, pos) != 0.0F) {
            stack.hurtAndBreak(5, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        return true;
    }

    public void applyCooldown(Player playerIn) {
        for (Item item : ForgeRegistries.ITEMS) {
            if (item instanceof KatanaItem) {
                playerIn.getCooldowns().addCooldown(item, 75);
            }
        }
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (!playerIn.isShiftKeyDown()) {
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    public SoundEvent getDashSound() {
        return SoundsRegistry.SWIFTSLICE.get();
    }

    public double getDashDistance(Player player) {
        return player.getAttributeValue(AttributeRegistry.DASH_DISTANCE.get());
    }

    public ParticleOptions getDashParticle() {
        return ParticleTypes.POOF;
    }

    public ResourceLocation getOverlayTexture() {
        return new ResourceLocation(Valoria.ID, "textures/gui/overlay/speedlines.png");
    }

    public int getOverlayTime() {
        return 35;
    }

    public int getHurtAmount(List<LivingEntity> detectedEntities) {
        return 5 + detectedEntities.size();
    }

    public void performEffects(LivingEntity targets, Player player) {
        targets.knockback(0.4F, player.getX() - targets.getX(), player.getZ() - targets.getZ());
        if (EnchantmentHelper.getFireAspect(player) > 0) {
            int i = EnchantmentHelper.getFireAspect(player);
            targets.setSecondsOnFire(i * 4);
        }
    }

    public void performDash(@NotNull ItemStack stack, @NotNull Level level, @NotNull Player player, Vector3d pos, RandomSource rand) {
        double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
        double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;
        double dashDistance = getDashDistance(player);
        Vec3 dir = (player.getViewVector(0.0f).scale(dashDistance));
        player.push(dir.x, dir.y * 0.25, dir.z);
        double ii = 1D;
        if (level instanceof ServerLevel srv) {
            for (int i = 0; i < 10; i += 1) {
                double locDistance = i * 0.5D;
                double X = Math.sin(pitch) * Math.cos(yaw) * locDistance;
                double Y = Math.cos(pitch) * 2;
                double Z = Math.sin(pitch) * Math.sin(yaw) * locDistance;

                srv.sendParticles(getDashParticle(), pos.x + X + (rand.nextDouble() - 0.5D), pos.y + Y, pos.z + Z + (rand.nextDouble() - 0.5D), 1, 0, 0.5, 0, 0);
                List<LivingEntity> detectedEntities = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos.x + X - 0.5D, pos.y + Y - 0.5D, pos.z + Z - 0.5D, pos.x + X + 0.5D, pos.y + Y + 0.5D, pos.z + Z + 0.5D));
                for (LivingEntity entity : detectedEntities) {
                    if (!entity.equals(player)) {
                        entity.hurt(level.damageSources().playerAttack(player), (float) ((player.getAttributeValue(Attributes.ATTACK_DAMAGE) * ii) + EnchantmentHelper.getSweepingDamageRatio(player) + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
                        performEffects(entity, player);
                        ValoriaUtils.chanceEffect(entity, effects, chance, arcRandom);
                        if (!player.isCreative()) {
                            stack.hurtAndBreak(getHurtAmount(detectedEntities), player, (plr) -> plr.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                        }
                    }

                    ii = ii - (1D / (detectedEntities.size() * 2));
                }

                if (locDistance >= distance(dashDistance, level, player)) {
                    break;
                }
            }
        }
    }

    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entityLiving, int timeLeft) {
        RandomSource rand = level.getRandom();
        Player player = (Player) entityLiving;
        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        if (!player.isFallFlying()) {
            player.awardStat(Stats.ITEM_USED.get(this));
            applyCooldown(player);
            performDash(stack, level, player, pos, rand);
            level.playSound(null, player.getOnPos(), getDashSound(), SoundSource.PLAYERS, 1F, 1F);
            if (level.isClientSide) {
                OverlayRender.setOverlayTexture(getOverlayTexture());
                OverlayRender.showDashOverlay(getOverlayTime());
            }
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.katana").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.valoria.rmb").withStyle(ChatFormatting.GREEN));
        ValoriaUtils.addEffectsTooltip(effects, tooltip, 1, chance);
    }
}
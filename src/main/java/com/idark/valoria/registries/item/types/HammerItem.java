package com.idark.valoria.registries.item.types;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.client.model.animations.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.neoforge.common.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.api.render.animation.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.common.registry.item.components.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.*;
import java.util.stream.*;

import static com.idark.valoria.Valoria.*;

public class HammerItem extends SwordItem implements ICustomAnimationItem, CooldownReductionItem, TooltipComponentItem{
    @OnlyIn(Dist.CLIENT)
    private static HammerAnimation hammerAnimation = new HammerAnimation();
    public static final Set<ItemAbility> HAMMER = of(ItemAbilities.SWORD_DIG);
    private final float attackDamage;
    private final ItemAttributeModifiers defaultModifiers;

    public HammerItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties){
        super(pTier, pProperties);
        this.attackDamage = pAttackDamageModifier + pTier.getAttackDamageBonus();
        this.defaultModifiers = ItemAttributeModifiers.builder()
        .add(AttributeReg.NATURE_DAMAGE, new AttributeModifier(Valoria.BASE_NATURE_DAMAGE_ID, 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
        .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, this.attackDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
        .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, pAttackSpeedModifier, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
        .add(AttributeReg.DASH_DISTANCE, new AttributeModifier(BASE_DASH_DISTANCE_ID, 1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
        .add(AttributeReg.ATTACK_RADIUS, new AttributeModifier(BASE_ATTACK_RADIUS_ID, 3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
        .build();
    }

    private static Set<ItemAbility> of(ItemAbility... actions){
        return Stream.of(actions).collect(Collectors.toCollection(Sets::newIdentityHashSet));
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        return this.defaultModifiers;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility){
        return HAMMER.contains(itemAbility);
    }

    public UseAnim getUseAnimation(ItemStack stack){
        return UseAnim.CUSTOM;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemAnimation getAnimation(ItemStack stack){
        return hammerAnimation;
    }

    public int getChargingTime() {
        return 35;
    }

    public void onUseTick(@NotNull Level worldIn, @NotNull LivingEntity livingEntityIn, @NotNull ItemStack stack, int count){
        Player player = (Player)livingEntityIn;
        if(player.getTicksUsingItem() == this.getChargingTime()){
            player.playNotifySound(SoundsRegistry.SPEAR_RETURN.get(), SoundSource.PLAYERS, 1, 1);
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged){
        if(!slotChanged){
            return false;
        }

        return super.shouldCauseReequipAnimation(oldStack, newStack, true);
    }

    public static double distance(double distance, Level level, Player player){
        double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
        double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;
        double X = Math.sin(pitch) * Math.cos(yaw) * distance;
        double Y = Math.cos(pitch) * distance;
        double Z = Math.sin(pitch) * Math.sin(yaw) * distance;

        Vec3 pos = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        Vec3 EndPos = (player.getViewVector(0.0f).scale(2.0d));
        HitResult hitresult = Utils.Hit.hitResult(player.getEyePosition(), player, (e) -> true, EndPos, level);
        if(hitresult != null){
            switch(hitresult.getType()){
                case BLOCK, MISS:
                    X = hitresult.getLocation().x();
                    Y = hitresult.getLocation().y();
                    Z = hitresult.getLocation().z();
                    break;
                case ENTITY:
                    Entity entity = ((EntityHitResult)hitresult).getEntity();
                    X = entity.getX();
                    Y = entity.getY();
                    Z = entity.getZ();
                    break;
            }
        }

        return Math.sqrt((X - pos.x) * (X - pos.x) + (Y - pos.y) * (Y - pos.y) + (Z - pos.z) * (Z - pos.z));
    }

    public void applyCooldown(Player playerIn){
        for(Item item : BuiltInRegistries.ITEM){
            if(item instanceof HammerItem){
                playerIn.getCooldowns().addCooldown(item, getCooldownReduction(500, playerIn.getUseItem()));
            }
        }
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(!playerIn.isShiftKeyDown() && handIn != InteractionHand.OFF_HAND){
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity){
        return 72000;
    }

    public double getDashDistance(Player player){
        return player.getAttributeValue(AttributeReg.DASH_DISTANCE);
    }

    public double getSmashRadius(Player player){
        return player.getAttributeValue(AttributeReg.ATTACK_RADIUS);
    }

    public void performEffects(LivingEntity targets, Player player){
        performEffects(targets, player, player.getMainHandItem());
    }

    public void performEffects(LivingEntity target, Player player, ItemStack stack){
        int collapseLevel = EnchantmentsRegistry.getLevel(stack, EnchantmentsRegistry.COLLAPSE);
        int repulsionLevel = EnchantmentsRegistry.getLevel(stack, EnchantmentsRegistry.REPULSION) + EnchantmentsRegistry.getLevel(stack, Enchantments.KNOCKBACK);
        if (collapseLevel > 0) {
            double dx = target.getX() - player.getX();
            double dz = target.getZ() - player.getZ();
            float pullStrength = 0.6F + (collapseLevel * 0.4F);
            target.knockback(pullStrength, dx, dz);
            target.hurtMarked = true;
            target.hasImpulse = true;
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, collapseLevel - 1));
        } else {
            float kbStrength = 0.6F + (repulsionLevel * 0.8F);
            double dx = player.getX() - target.getX();
            double dz = player.getZ() - target.getZ();
            target.knockback(kbStrength, dx, dz);

            if (repulsionLevel > 0) {
                target.push(0, 0.25D * repulsionLevel, 0);
            }

            target.hurtMarked = true;
            target.hasImpulse = true;
        }

        int sunderingLevel = EnchantmentsRegistry.getLevel(stack, EnchantmentsRegistry.SUNDERING);
        if (sunderingLevel > 0) {
            target.addEffect(new MobEffectInstance(EffectsRegistry.SUNDERED, 100, sunderingLevel - 1));
        }

        int stunLevel = EnchantmentsRegistry.getLevel(stack, EnchantmentsRegistry.CONCUSSION);
        if (stunLevel > 0) {
            int duration = switch (stunLevel) {
                case 1 -> 4;
                case 2 -> 8;
                default -> 16;
            };
            target.addEffect(new MobEffectInstance(EffectsRegistry.STUN, duration, 0));
        }

        if(CombatCompat.fireAspect(player) > 0){
            int i = CombatCompat.fireAspect(player);
            target.setRemainingFireTicks(i * 4);
        }

        if(collapseLevel == 0){
            target.push(0, 0.5, 0);
        }
    }

    public void performDash(Player player, ItemStack stack, double dashDistance) {
        Vec3 look = player.getViewVector(0.0f);
        player.hurtMarked = true;
        player.push(look.x * dashDistance, 1.0, look.z * dashDistance);
        
        int enchantLevel = EnchantmentsRegistry.getLevel(stack, EnchantmentsRegistry.SHOCK_ABSORPTION);
        player.addEffect(new MobEffectInstance(EffectsRegistry.HAMMER_SMASH, 60, enchantLevel, false, false, false));
    }

    public SmashType getSmashType(ItemStack stack) {
        if (EnchantmentsRegistry.getLevel(stack, EnchantmentsRegistry.COLLAPSE) > 0) {
            return SmashType.COLLAPSE;
        }
        Tier tier = this.getTier();
        if (tier == ItemTierRegistry.INFERNAL) {
            return SmashType.INFERNAL;
        } else if (tier == ItemTierRegistry.NIHILITY) {
            return SmashType.VOID;
        } else if (tier == ItemTierRegistry.BLACK_GOLD) {
            return SmashType.BLACK_GOLD;
        }
        return SmashType.DEFAULT;
    }

    public void performSmash(@NotNull ItemStack stack, @NotNull Level level, @NotNull Player player){
        BlockPos pos = player.blockPosition();
        if(level instanceof ServerLevel serv){
            PacketHandler.sendToTracking(serv, pos, new SmashParticlePacket(player.getUUID(), getSmashType(stack)));
        }

        var radius = getSmashRadius(player);
        AABB smashBox = player.getBoundingBox().inflate(radius);
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, smashBox);
        for (LivingEntity target : targets) {
            if (target != player) {
                if (player.distanceToSqr(target) <= radius * radius) {
                    target.hurt(level.damageSources().playerAttack(player), this.getDamage(stack) * 1.75f);
                    performEffects(target, player, stack);
                }
            }
        }

        level.playSound(null, player.blockPosition(), SoundsRegistry.HAMMER_SMASH.get(), SoundSource.PLAYERS, 1f, 1f);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker){
        pAttacker.level().playSound(null, pTarget.blockPosition(), SoundsRegistry.HAMMER_HIT.get(), SoundSource.PLAYERS, 1, 1);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entityLiving, int timeLeft){
        Player player = (Player)entityLiving;
        if(!player.isFallFlying() && player.getTicksUsingItem() >= this.getChargingTime()){
            player.awardStat(Stats.ITEM_USED.get(this));
            applyCooldown(player);
            performDash(player, stack, getDashDistance(player));
            if(level instanceof ServerLevel serv) PacketHandler.sendToTracking(serv, player.getOnPos(), new JumpParticlePacket(player.getUUID()));
            level.playSound(null, player.getOnPos(), SoundsRegistry.HAMMER_SWOOSH.get(), SoundSource.PLAYERS, 1F, 1F);
        }
    }

    public Seq<TooltipComponent> getTooltips(ItemStack pStack) {
        Seq<TooltipComponent> seq = Seq.with(
        new SeparatorComponent(Component.translatable("tooltip.tridot.abilities")),
        new AbilityComponent(Component.translatable("tooltip.valoria.hammer_smash").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/gui/tooltips/hammer_smash.png"))
        );

        seq.add(new TextComponent(Component.translatable("tooltip.tridot.crossbow.speed", Utils.Items.formatTickDuration(this.getChargingTime())).withStyle(style -> style.withColor(ChatFormatting.GRAY).withFont(Valoria.FONT))));
        seq.add(new TextComponent(Component.translatable("tooltip.valoria.rmb").withStyle(style -> style.withFont(Valoria.FONT))));
        return seq;
    }
}
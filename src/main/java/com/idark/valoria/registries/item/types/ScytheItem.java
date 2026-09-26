package com.idark.valoria.registries.item.types;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.builders.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.minecraft.world.level.*;
import net.neoforged.api.distmarker.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.api.render.animation.*;
import pro.komaru.tridot.client.render.screenshake.*;
import pro.komaru.tridot.common.registry.EnchantmentsRegistry;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.common.registry.item.components.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;
import pro.komaru.tridot.util.phys.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.*;

import static com.idark.valoria.Valoria.BASE_ATTACK_RADIUS_ID;

public class ScytheItem extends SwordItem implements ICustomAnimationItem, CooldownNotifyItem, RadiusItem, SpinAttackItem, DashItem, CooldownReductionItem, TooltipComponentItem{
    public AbstractScytheBuilder<? extends ScytheItem> builder;
    public ItemAttributeModifiers defaultModifiers;
    public final ArcRandom arcRandom = Tmp.rnd;
    public int usageCount;

    public ScytheItem(AbstractScytheBuilder<? extends ScytheItem> builderIn){
        super(builderIn.tier, builderIn.itemProperties);
        this.builder = builderIn;
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        builderIn.extraAttributes.forEach((attribute, modifier) -> builder.add(attribute, modifier, EquipmentSlotGroup.MAINHAND));
        builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, builderIn.attackDamageIn + builderIn.tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, builderIn.attackSpeedIn, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        builder.add(AttributeReg.ATTACK_RADIUS, new AttributeModifier(BASE_ATTACK_RADIUS_ID, builderIn.attackRadius, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        this.defaultModifiers = builder.build();
    }

    public ScytheItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn){
        this(new Builder(attackDamageIn, attackSpeedIn, builderIn).setTier(tier));
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(){
        return this.defaultModifiers;
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(!playerIn.isShiftKeyDown() && handIn != InteractionHand.OFF_HAND){
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public SoundEvent getSoundEvent(){
        return builder.cooldownSound;
    }

    public UseAnim getUseAnimation(ItemStack pStack){
        return UseAnim.CUSTOM;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemAnimation getAnimation(ItemStack stack){
        return builder.animation;
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity){
        return builder.useTime;
    }

    public void applyCooldown(Player playerIn, int time){
        for(Item item : BuiltInRegistries.ITEM){
            if(item instanceof ScytheItem){
                playerIn.getCooldowns().addCooldown(item, time);
            }
        }
    }

    public void performEffects(LivingEntity targets, Player player){
        targets.knockback(0.4F, player.getX() - targets.getX(), player.getZ() - targets.getZ());
        int i = CombatCompat.fireAspect(player);
        if(i > 0){
            targets.igniteForSeconds(i * 4);
        }
    }

    public void performAttack(Level level, ItemStack stack, Player player){
        List<LivingEntity> hitEntities = new ArrayList<>();
        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        float damage = (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + CombatCompat.sweepingRatio(player);
        float radius = (float)player.getAttributeValue(AttributeReg.ATTACK_RADIUS);
        usageCount = DataComponentsRegistry.getInt(stack, DataComponentsRegistry.USAGE_COUNT.get(), "usageCount");

        usageCount++;
        stack.set(DataComponentsRegistry.USAGE_COUNT, usageCount);
        ValoriaUtils.radiusHit(level, stack, player, builder.particleOptions, hitEntities, pos, 0, player.getRotationVector().y, radius);
        if(usageCount > builder.attackUsages - 1){
            int cooldown = hitEntities.isEmpty() ? builder.minCooldownTime : builder.cooldownTime;
            applyCooldown(player, getCooldownReduction(cooldown, stack));
            stack.set(DataComponentsRegistry.USAGE_COUNT, 0);
        }else{
            applyCooldown(player, builder.attackDelay);
        }

        for(LivingEntity entity : hitEntities){
            if(!player.canAttack(entity)) continue;

            entity.hurt(level.damageSources().playerAttack(player), (damage + CombatCompat.damageBonus(player, stack, entity)) * 1.35f);
            performEffects(entity, player);
            Utils.Entities.applyWithChance(entity, builder.effects, builder.chance, arcRandom);
            if(!player.isCreative()){
                stack.hurtAndBreak(hitEntities.size(), player, EquipmentSlot.MAINHAND);
            }
        }

        ScreenshakeHandler.add(new PositionedScreenshakeInstance(builder.screenShakeDuration, Vec3.from(player.getEyePosition()), 0, 30).intensity(builder.screenShakeIntensity).interp(builder.screenShakeEasing));
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving){
        Player player = (Player)entityLiving;
        if(!player.isFallFlying() && EnchantmentsRegistry.getLevel(level, stack, EnchantmentsRegistry.DASH) > 0) performDash(player, stack);
        performAttack(level, stack, player);
        player.awardStat(Stats.ITEM_USED.get(this));
        level.playSound(null, player.getOnPos(), builder.attackSound, SoundSource.PLAYERS, 1.0F, 1F);
        return stack;
    }

    public Seq<TooltipComponent> getTooltips(ItemStack pStack){
        if(builder.attackUsages > 1){
            return Seq.with(
            new SeparatorComponent(Component.translatable("tooltip.tridot.abilities")),
            new AbilityComponent(Component.translatable("tooltip.valoria.scythe").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/gui/tooltips/circular_strike.png")),
            new TextComponent(Component.translatable("tooltip.valoria.usage_count", builder.attackUsages).withStyle(ChatFormatting.GRAY)),
            new TextComponent(Component.translatable("tooltip.valoria.hold_rmb").withStyle(style -> style.withFont(Valoria.FONT)))
            );
        } else {
            return Seq.with(
            new SeparatorComponent(Component.translatable("tooltip.tridot.abilities")),
            new AbilityComponent(Component.translatable("tooltip.valoria.scythe").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/gui/tooltips/circular_strike.png")),
            new TextComponent(Component.translatable("tooltip.valoria.hold_rmb").withStyle(style -> style.withFont(Valoria.FONT)))
            );
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        Utils.Items.effectTargetTooltip(builder.effects, tooltip, 1, builder.chance);
    }

    public static class Builder extends AbstractScytheBuilder<ScytheItem>{
        public Builder(float attackDamageIn, float attackSpeedIn, Properties itemProperties){
            super(attackDamageIn, attackSpeedIn, itemProperties);
        }

        @Override
        public ScytheItem build(){
            return new ScytheItem(this);
        }
    }
}
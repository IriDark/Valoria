package com.idark.valoria.registries.item.types;

import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.builders.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.registries.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.api.render.animation.*;
import pro.komaru.tridot.client.render.screenshake.*;
import pro.komaru.tridot.common.registry.EnchantmentsRegistry;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.*;

import static com.idark.valoria.Valoria.BASE_ATTACK_RADIUS_UUID;

public class ScytheItem extends SwordItem implements ICustomAnimationItem, CooldownNotifyItem, RadiusItem, SpinAttackItem, DashItem, CooldownReductionItem{
    public AbstractScytheBuilder<? extends ScytheItem> builder;
    public final Multimap<Attribute, AttributeModifier> defaultModifiers;
    public final ArcRandom arcRandom = Tmp.rnd;
    public int usageCount;

    public ScytheItem(AbstractScytheBuilder<? extends ScytheItem> builderIn){
        super(builderIn.tier, (int)builderIn.attackDamageIn, builderIn.attackSpeedIn, builderIn.itemProperties);
        this.builder = builderIn;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", builderIn.attackDamageIn + builderIn.tier.getAttackDamageBonus(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", builderIn.attackSpeedIn, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeReg.ATTACK_RADIUS.get(), new AttributeModifier(BASE_ATTACK_RADIUS_UUID, "Tool modifier", builderIn.attackRadius, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    public ScytheItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn){
        this(new Builder(attackDamageIn, attackSpeedIn, builderIn).setTier(tier));
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot){
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
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

    public int getUseDuration(ItemStack stack){
        return builder.useTime;
    }

    public void applyCooldown(Player playerIn, int time){
        for(Item item : ForgeRegistries.ITEMS){
            if(item instanceof ScytheItem){
                playerIn.getCooldowns().addCooldown(item, time);
            }
        }
    }

    public void performEffects(LivingEntity targets, Player player){
        targets.knockback(0.4F, player.getX() - targets.getX(), player.getZ() - targets.getZ());
        if(EnchantmentHelper.getFireAspect(player) > 0){
            int i = EnchantmentHelper.getFireAspect(player);
            targets.setSecondsOnFire(i * 4);
        }
    }

    public void performAttack(Level level, ItemStack stack, Player player){
        List<LivingEntity> hitEntities = new ArrayList<>();
        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        float damage = (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);
        float radius = (float)player.getAttributeValue(AttributeReg.ATTACK_RADIUS.get());
        CompoundTag tag = stack.getOrCreateTag();
        usageCount = tag.getInt("usageCount");

        usageCount++;
        tag.putInt("usageCount", usageCount);
        stack.setTag(tag);
        ValoriaUtils.radiusHit(level, stack, player, builder.particleOptions, hitEntities, pos, 0, player.getRotationVector().y, radius);
        if(usageCount > builder.attackUsages - 1){
            int cooldown = hitEntities.isEmpty() ? builder.minCooldownTime : builder.cooldownTime;
            applyCooldown(player, cooldown - getCooldownReduction(stack));
            tag.putInt("usageCount", 0);
            stack.setTag(tag);
        }else{
            applyCooldown(player, builder.attackDelay);
        }

        for(LivingEntity entity : hitEntities){
            entity.hurt(level.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
            performEffects(entity, player);
            Utils.Entities.applyWithChance(entity, builder.effects, builder.chance, arcRandom);
            if(!player.isCreative()){
                stack.hurtAndBreak(hitEntities.size(), player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }

        ScreenshakeHandler.add(new PositionedScreenshakeInstance(builder.screenShakeDuration, pro.komaru.tridot.util.phys.Vec3.from(player.getEyePosition()), 0, 30).intensity(builder.screenShakeIntensity).interp(builder.screenShakeEasing));
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving){
        Player player = (Player)entityLiving;
        if(stack.getEnchantmentLevel(EnchantmentsRegistry.DASH.get()) > 0) performDash(player, stack);
        performAttack(level, stack, player);
        player.awardStat(Stats.ITEM_USED.get(this));
        level.playSound(null, player.getOnPos(), builder.attackSound, SoundSource.PLAYERS, 1.0F, 1F);
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        Utils.Items.addContributorTooltip(stack, tooltip);
        tooltip.add(Component.translatable("tooltip.valoria.scythe").withStyle(ChatFormatting.GRAY));
        if(builder.attackUsages > 1){
            tooltip.add(Component.translatable("tooltip.valoria.usage_count", builder.attackUsages).withStyle(ChatFormatting.GRAY));
        }

        tooltip.add(Component.translatable("tooltip.valoria.rmb").withStyle(ChatFormatting.GREEN));
        Utils.Items.effectTooltip(builder.effects, tooltip, 1, builder.chance);
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
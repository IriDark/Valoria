package com.idark.valoria.registries.item.types.elemental;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.component.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.*;

public class CoralReefItem extends ValoriaSword implements TooltipComponentItem{
    public ArcRandom arcRandom = Tmp.rnd;
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public CoralReefItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.attackDamage = attackDamageIn + tier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(AttributeReg.DEPTH_DAMAGE.get(), new AttributeModifier(Valoria.BASE_DEPTH_DAMAGE_UUID, "Weapon modifier", 2, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage - 2, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker){
        if(!(pAttacker instanceof Player player)) return true;
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        if(Utils.Items.getAttackStrengthScale(player, 0.9f)){
            if(arcRandom.chance(0.15f)){
                pTarget.knockback(0.6F, pAttacker.getX() - pTarget.getX(), pAttacker.getZ() - pTarget.getZ());
                pTarget.level().playSound(null, pTarget.getOnPos(), SoundsRegistry.WATER_ABILITY.get(), SoundSource.AMBIENT, 0.7f, 1.2f);
            }
        }

        return true;
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(!playerIn.isShiftKeyDown()){
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public int getUseDuration(ItemStack stack){
        return 72000;
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft){
        Player player = (Player)entityLiving;
        player.getCooldowns().addCooldown(this, 250);
        player.awardStat(Stats.ITEM_USED.get(this));

        float damage = (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);
        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        List<LivingEntity> hitEntities = new ArrayList<>();

        ValoriaUtils.radiusHit(worldIn, stack, player, ParticleTypes.BUBBLE_POP, hitEntities, pos, 0, player.getRotationVector().y, 4);
        Utils.Particles.inRadius(worldIn, stack, ParticleTypes.FALLING_WATER, pos, 0, player.getRotationVector().y, 4);
        for(LivingEntity damagedEntity : hitEntities){
            if(!player.canAttack(damagedEntity)) continue;
            damagedEntity.hurt(worldIn.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(stack, damagedEntity.getMobType())) * 1.35f);
            damagedEntity.hurtMarked = true;
            damagedEntity.knockback(2.5F, player.getX() - damagedEntity.getX(), player.getZ() - damagedEntity.getZ());
            worldIn.playSound(null, damagedEntity.getOnPos(), SoundsRegistry.WATER_ABILITY.get(), SoundSource.AMBIENT, 0.2f, 1.2f);
        }

        if(!player.isCreative()){
            stack.hurtAndBreak(hitEntities.size(), player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        worldIn.playSound(null, player.blockPosition(), SoundsRegistry.WATER_ABILITY.get(), SoundSource.AMBIENT, 0.8f, 1f);
    }

    public Seq<TooltipComponent> getTooltips(ItemStack pStack){
        return Seq.with(
        new SeparatorComponent(Component.translatable("tooltip.valoria.abilities")),
        new AbilityComponent(Component.translatable("tooltip.valoria.coral_reef").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/gui/tooltips/tidal_push.png")),
        new ClientTextComponent(Component.translatable("tooltip.valoria.rmb").withStyle(style -> style.withFont(Valoria.FONT)))
        );
    }
}
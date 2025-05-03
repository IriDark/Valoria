package com.idark.valoria.registries.item.types;

import com.idark.valoria.core.config.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.client.*;
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
import net.minecraftforge.common.Tags.*;
import org.jetbrains.annotations.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.util.*;

import java.lang.Math;
import java.util.*;

//TODO:
// Fix the ability (Works weird on server)
// Work on GFX side of the ability
// Something like on-screen particles would be cool
public class PhantomItem extends ValoriaSword implements RadiusItem, CooldownReductionItem{
    public float pRadius = 3;

    public PhantomItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
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

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced){
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        Utils.Items.addContributorTooltip(pStack, pTooltipComponents);
    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft){
        Player player = (Player)entityLiving;
        player.awardStat(Stats.ITEM_USED.get(this));
        player.getCooldowns().addCooldown(this, 650 - getCooldownReduction(stack));
        float damage = (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);

        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        List<LivingEntity> hitEntities = new ArrayList<>();
        ValoriaUtils.radiusHit(level, stack, player, null, hitEntities, pos, 0, player.getRotationVector().y, pRadius);
        if(level.isClientSide()){
            double radius = pRadius - 0.5f;
            for(int a = 0; a < 20; a++){
                double baseAngle = (a * Math.PI) * 0.925;

                float angle = (float)(baseAngle + (a / 2 * Math.PI));
                float y = (angle * 0.05f) - 0.5f;

                double x = Math.cos(angle) * radius;
                double z = Math.sin(angle) * radius;

                ParticleBuilder.create(TridotParticles.WISP)
                .setColorData(ColorParticleData.create(Pal.softBlue, Col.darkGray).build())
                .setTransparencyData(GenericParticleData.create(0.125f, 0f).build())
                .setScaleData(GenericParticleData.create(0.235f, 0.1f, 0).build())
                .setLifetime(35)
                .randomVelocity(0.015f)
                .spawn(level, player.getX() + x, player.getY() + 1 + y, player.getZ() + z);
            }

            if(ClientConfig.RENDER_PHANTOM_ACTIVATION.get()){
                Minecraft.getInstance().gameRenderer.displayItemActivation(ItemsRegistry.eternity.get().getDefaultInstance());
            }
        }

        level.playSound(null, player.blockPosition(), SoundsRegistry.PHANTASM_ABILITY.get(), SoundSource.AMBIENT, 1.0F, 1.0F);
        if(!player.isCreative()){
            stack.hurtAndBreak(35, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        for(LivingEntity entityInRadius : hitEntities){
            if((entityInRadius instanceof Player && ((Player)entityInRadius).isCreative()) || (entityInRadius instanceof BossEntity || entityInRadius.getType().is(EntityTypes.BOSSES))){
                continue;
            }

            entityInRadius.hurt(level.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(stack, entityInRadius.getMobType())) * 1.35f);
            entityInRadius.addEffect(new MobEffectInstance(EffectsRegistry.STUN.get(), 25));
            entityInRadius.setDeltaMovement(0, 1, 0);
            entityInRadius.hurtMarked = true;
            if(EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) > 0){
                int e = EnchantmentHelper.getFireAspect(player);
                entityInRadius.setSecondsOnFire(e * 4);
            }
        }
    }
}
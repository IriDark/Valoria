package com.idark.valoria.registries.item.types;

import com.idark.valoria.core.config.*;
import com.idark.valoria.core.interfaces.RadiusItem;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.client.*;
import net.minecraft.core.particles.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraftforge.common.Tags.*;
import org.joml.*;
import pro.komaru.tridot.core.interfaces.*;

import java.util.*;

//TODO:
// Fix the ability (Works weird on server)
// Work on GFX side of the ability
// Something like on-screen particles would be cool
public class PhantomItem extends SwordItem implements RadiusItem{
    public float pRadius = 3;

    public PhantomItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn){
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

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft){
        RandomSource rand = level.getRandom();
        Player player = (Player)entityLiving;
        player.awardStat(Stats.ITEM_USED.get(this));
        player.getCooldowns().addCooldown(this, 650);
        float damage = (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);

        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        List<LivingEntity> hitEntities = new ArrayList<>();
        ValoriaUtils.radiusHit(level, stack, player, ParticleTypes.SOUL_FIRE_FLAME, hitEntities, pos, 0, player.getRotationVector().y, pRadius);
        if(level.isClientSide()){
            for(int a = 0; a < 12; a++){
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() + ((rand.nextDouble() - 0.7D) * 1), player.getY() + ((rand.nextDouble() - 1D) * 1), player.getZ() + ((rand.nextDouble() - 0.5D) * 1), 0.05d * ((rand.nextDouble() - 0.5D) * 1), 0.05d * ((rand.nextDouble() - 0.5D) * 1), 0.05d * ((rand.nextDouble() - 0.5D) * 1));
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() + ((rand.nextDouble() - 0.5D) * 2), player.getY() + ((rand.nextDouble() - 0.5D) * 2), player.getZ() + ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2));
            }
        }

        level.playSound(null, player.blockPosition(), SoundsRegistry.PHANTASM_ABILITY.get(), SoundSource.AMBIENT, 1.0F, 1.0F);
        if(ClientConfig.PHANTOM_ACTIVATION.get()){
            Minecraft.getInstance().gameRenderer.displayItemActivation(ItemsRegistry.eternity.get().getDefaultInstance());
        }

        if(!player.isCreative()){
            stack.hurtAndBreak(35, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        for(LivingEntity entityInRadius : hitEntities){
            if(entityInRadius instanceof Player && ((Player)entityInRadius).isCreative() || entityInRadius instanceof BossEntity || entityInRadius.getType().is(EntityTypes.BOSSES)){
                continue;
            }

            entityInRadius.hurt(level.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(stack, entityInRadius.getMobType())) * 1.35f);
            entityInRadius.setDeltaMovement((player.getX() - entityInRadius.getX()) * 0.06, 0.1D, (player.getZ() - entityInRadius.getZ()) * 0.06);
            if(EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) > 0){
                int e = EnchantmentHelper.getFireAspect(player);
                entityInRadius.setSecondsOnFire(e * 4);
            }
        }
    }
}
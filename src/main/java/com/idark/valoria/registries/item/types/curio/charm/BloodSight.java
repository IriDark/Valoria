package com.idark.valoria.registries.item.types.curio.charm;

import com.idark.valoria.core.interfaces.ParticleItemEntity;
import com.idark.valoria.registries.ItemsRegistry;
import com.idark.valoria.registries.SoundsRegistry;
import com.idark.valoria.util.ArcRandom;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class BloodSight extends Item implements ICurioItem, Vanishable, ParticleItemEntity{
    private int hits = 0;
    public ArcRandom arcRandom = new ArcRandom();

    public BloodSight(Properties properties){
        super(properties);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slot, ItemStack stack){
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack pStack){
        return false;
    }

    public int getDamage(int pLevel, RandomSource pRandom){
        return pLevel > 10 ? pLevel - 10 : 1 + pRandom.nextInt(1);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        Player player = (Player)slotContext.entity();
        Level pLevel = player.level();
        LivingEntity lastHurtMob = player.getLastAttacker();
        boolean flag = stack.getItem() == ItemsRegistry.monocle.get();
        int duration = flag ? 12 : 6;
        int damageAmount = flag ? new Random().nextInt(2, 6) : new Random().nextInt(2, 8);
        if(!pLevel.isClientSide() && pLevel instanceof ServerLevel serverLevel){
            if(lastHurtMob != null && !player.getCooldowns().isOnCooldown(stack.getItem())){
                ValoriaUtils.damageLastAttackedMob(serverLevel, player, this.getDamage(0, RandomSource.create()));
                if(flag){
                    if(arcRandom.chance(0.25f)){
                        player.hurt(pLevel.damageSources().magic(), 0.5f);
                    }
                }

                if(lastHurtMob.hurtMarked){
                    hits++;
                }

                ValoriaUtils.spawnParticlesLineToAttackedMobWithCooldown(serverLevel, player, new BlockParticleOption(ParticleTypes.BLOCK, Blocks.REDSTONE_BLOCK.defaultBlockState()), duration);
                if(player.hurtMarked){
                    stack.hurtAndBreak(damageAmount, player, (p0) -> p0.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                }

                if(hits >= duration / 2){
                    stack.hurtAndBreak(damageAmount, player, (p0) -> p0.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                    player.getCooldowns().addCooldown(stack.getItem(), 600);
                    hits = 0;
                }
            }
        }
    }

    @Override
    public boolean makesPiglinsNeutral(SlotContext slotContext, ItemStack stack){
        return true;
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack){
        return stack.getItem() == ItemsRegistry.monocle.get() ? new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_CHAIN, 1.0f, 1.0f) : new ICurio.SoundInfo(SoundsRegistry.EQUIP_CURSE.get(), 1.0f, 1.0f);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.bloodsight").withStyle(ChatFormatting.GRAY));
        if(stack.getItem() == ItemsRegistry.brokenMonocle.get()){
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("tooltip.valoria.bloodsight_curse").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
        }
    }

    @Override
    public void spawnParticles(Level level, ItemEntity entity){
        //todo
//        if(entity.getItem().is(ItemsRegistry.BROKEN_BLOODSIGHT_MONOCLE.get())){
//            Particles.create(ParticleRegistry.SKULL)
//            .addVelocity(0.05f, 0.04f, 0.05f)
//            .setAlpha(0.25f, 0)
//            .setScale(0.1f, 0)
//            .setColor(0.366f, 0.643f, 0.315f, 0.915f, 0.225f, 0.915f)
//            .setLifetime(6)
//            .spawn(level, entity.getX() + (new Random().nextDouble() - 0.5f) / 2, entity.getY() + (new Random().nextDouble() + 0.1f) / 2, entity.getZ());
//        }else{
//            Particles.create(ParticleRegistry.GLITTER)
//            .addVelocity(0f, 0.04f, 0f)
//            .setAlpha(0.95f, 0)
//            .setScale(0.1f, 0)
//            .setColor(0f, 0f, 0f, 0f, 0f, 0.915f)
//            .setLifetime(6)
//            .spawn(level, entity.getX() + (new Random().nextDouble() - 0.5f) / 2, entity.getY() + (new Random().nextDouble() + 0.1f) / 2, entity.getZ());
//        }
    }
}
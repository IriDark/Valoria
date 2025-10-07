package com.idark.valoria.registries.item.types.curio;

import com.idark.valoria.registries.*;
import net.minecraft.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import javax.annotation.*;
import java.util.*;

public class BloodSight extends Item implements ICurioItem, Vanishable, ParticleItemEntity{
    private int hits = 0;
    public ArcRandom arcRandom = Tmp.rnd;

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

    public int getDamage(RandomSource pRandom){
        return 1 + pRandom.nextInt(3);
    }

    public void hitLast(Level pLevel, Player pPlayer, float pAmount) {
        LivingEntity lastHurtMob = pPlayer.getLastAttacker();
        if (!pLevel.isClientSide() && pLevel instanceof ServerLevel pServer) {
            if (lastHurtMob == null) {
                return;
            }

            lastHurtMob.hurt(pServer.damageSources().magic(), pAmount);
        }
    }


    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        Player player = (Player)slotContext.entity();
        Level pLevel = player.level();
        LivingEntity lastHurtMob = player.getLastAttacker();
        boolean flag = stack.getItem() == ItemsRegistry.monocle.get();
        int duration = flag ? 12 : 6;
        int damageAmount = flag ? Tmp.rnd.nextInt(2, 6) : Tmp.rnd.nextInt(2, 8);
        if(!pLevel.isClientSide() && pLevel instanceof ServerLevel serverLevel){
            if(lastHurtMob != null && !player.getCooldowns().isOnCooldown(stack.getItem())){
                hitLast(serverLevel, player, this.getDamage(RandomSource.create()));
                if(!flag){
                    if(arcRandom.chance(0.25f)){
                        player.hurt(pLevel.damageSources().magic(), 0.5f);
                    }
                }

                if(lastHurtMob.hurtMarked){
                    hits++;
                }

                Utils.Particles.lineToAttacked(serverLevel, player, new BlockParticleOption(ParticleTypes.BLOCK, Blocks.REDSTONE_BLOCK.defaultBlockState()), duration);
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
        if(entity.getItem().is(ItemsRegistry.brokenMonocle.get())){
            ParticleBuilder.create(TridotParticles.SKULL)
            .randomVelocity(0.05f, 0.04f, 0.05f)
            .setTransparencyData(GenericParticleData.create(0.1f, 0).build())
            .setScaleData(GenericParticleData.create(0.1f, 0).build())
            .setColorData(ColorParticleData.create(0.666f, 0.343f, 0.315f, 0.915f, 0.225f, 0.915f).build())
            .setLifetime(15)
            .spawn(level, entity.getX() + (Tmp.rnd.nextDouble() - 0.5f) / 2, entity.getY() + (new Random().nextDouble() + 0.1f) / 2, entity.getZ());
        }else{
            ParticleBuilder.create(TridotParticles.TINY_WISP)
            .randomVelocity(0f, 0.04f, 0f)
            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
            .setScaleData(GenericParticleData.create(0.1f, 0).build())
            .setColorData(ColorParticleData.create(0f, 0f, 0f, 0f, 0f, 0.915f).build())
            .setLifetime(45)
            .spawn(level, entity.getX() + (Tmp.rnd.nextDouble() - 0.5f) / 2, entity.getY() + (new Random().nextDouble() + 0.1f) / 2, entity.getZ());
        }
    }
}
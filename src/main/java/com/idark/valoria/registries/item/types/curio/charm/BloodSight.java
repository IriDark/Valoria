package com.idark.valoria.registries.item.types.curio.charm;

import com.idark.valoria.client.event.ClientTickHandler;
import com.idark.valoria.registries.ItemsRegistry;
import com.idark.valoria.registries.sounds.ModSoundRegistry;
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

public class BloodSight extends Item implements ICurioItem, Vanishable {
    private int hits = 0;

    public BloodSight(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slot, ItemStack stack) {
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    public int getDamage(int pLevel, RandomSource pRandom) {
        return pLevel > 10 ? pLevel - 10 : 1 + pRandom.nextInt(1);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Level pLevel = player.level();
        LivingEntity lastHurtMob = player.getLastAttacker();
        int duration = (stack.getItem() == ItemsRegistry.BLOODSIGHT_MONOCLE.get()) ? 12 : 6;
        if (lastHurtMob != null && ClientTickHandler.ticksInGame % duration == 1 && !pLevel.isClientSide() && pLevel instanceof ServerLevel
                && !player.getCooldowns().isOnCooldown(stack.getItem())) {
            for (int i = 0; i < ClientTickHandler.ticksInGame % duration; i++) {
                ValoriaUtils.damageLastAttackedMob(pLevel, player, this.getDamage(0, RandomSource.create()));
                if (lastHurtMob.hurtMarked) {
                    hits++;
                }
            }

            ValoriaUtils.spawnParticlesLineToAttackedMobWithCooldown(pLevel, player, new BlockParticleOption(ParticleTypes.BLOCK, Blocks.REDSTONE_BLOCK.defaultBlockState()), duration);
            if (player.hurtMarked) {
                int damageAmount = (stack.getItem() == ItemsRegistry.BLOODSIGHT_MONOCLE.get()) ? new Random().nextInt(0, 4) : new Random().nextInt(1, 5);
                stack.hurtAndBreak(damageAmount, player, (p0) -> p0.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }

            if (hits >= duration / 2) {
                player.getCooldowns().addCooldown(stack.getItem(), 600);
                hits = 0;
            }
        }
    }

    @Override
    public boolean makesPiglinsNeutral(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return stack.getItem() == ItemsRegistry.BLOODSIGHT_MONOCLE.get() ? new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_CHAIN, 1.0f, 1.0f) : new ICurio.SoundInfo(ModSoundRegistry.EQUIP_CURSE.get(), 1.0f, 1.0f);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.bloodsight").withStyle(ChatFormatting.GRAY));
        if (stack.getItem() == ItemsRegistry.BROKEN_BLOODSIGHT_MONOCLE.get()) {
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("tooltip.valoria.bloodsight_curse").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
        }
    }
}
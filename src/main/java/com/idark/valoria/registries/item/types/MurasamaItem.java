package com.idark.valoria.registries.item.types;

import com.idark.valoria.client.ui.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.client.particle.*;
import mod.maxbogomol.fluffy_fur.client.particle.data.*;
import mod.maxbogomol.fluffy_fur.registry.client.*;
import net.minecraft.core.particles.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.*;
import org.joml.*;

import java.lang.Math;
import java.util.*;

//todo
public class MurasamaItem extends KatanaItem implements IParticleItemEntity {
    public MurasamaItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    public void onUseTick(@NotNull Level worldIn, @NotNull LivingEntity livingEntityIn, @NotNull ItemStack stack, int count) {
        Player player = (Player) livingEntityIn;
        if (worldIn instanceof ServerLevel srv) {
            for (int ii = 0; ii < 1 + Mth.nextInt(RandomSource.create(), 0, 2); ii += 1) {
                PacketHandler.sendToTracking(srv, player.getOnPos(), new MurasamaParticlePacket(3F, player.getX(), (player.getY() + (player.getEyeHeight() / 2)), player.getZ(), 235, 0, 25));
            }
        }

        if (player.getTicksUsingItem() == 20) {
            player.playNotifySound(SoundsRegistry.RECHARGE.get(), SoundSource.PLAYERS, 0.6f, 1);
        }
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (!playerIn.isShiftKeyDown()) {
            playerIn.startUsingItem(InteractionHand.MAIN_HAND);
            if (!playerIn.isFallFlying() && playerIn.isUsingItem() && playerIn.getTicksUsingItem() >= 20) {
                applyCooldown(playerIn);
            }

            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        if (!slotChanged) {
            return false;
        }

        return super.shouldCauseReequipAnimation(oldStack, newStack, true);
    }

    public void performDash(@NotNull ItemStack stack, @NotNull Level level, @NotNull Player player, Vector3d pos, RandomSource rand) {
        double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
        double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;
        float dashDistance = (float) player.getAttributeValue(AttributeRegistry.DASH_DISTANCE.get());
        Vec3 dir = (player.getViewVector(0.0f).scale(dashDistance));
        player.push(dir.x, dir.y * 0.25, dir.z);
        applyCooldown(player);
        float ii = 1F;
        if (level instanceof ServerLevel srv) {
            for (int i = 0; i < 10; i += 1) {
                double locDistance = i * 0.5D;
                double X = Math.sin(pitch) * Math.cos(yaw) * locDistance;
                double Y = Math.cos(pitch) * locDistance;
                double Z = Math.sin(pitch) * Math.sin(yaw) * locDistance;
                level.addParticle(ParticleTypes.WAX_OFF, pos.x + X + (rand.nextDouble() - 0.5D), pos.y + Y, pos.z + Z + (rand.nextDouble() - 0.5D), 0d, 0.05d, 0d);
                for (int count = 0; count < 1 + Mth.nextInt(RandomSource.create(), 0, 2); count += 1) {
                    PacketHandler.sendToTracking(srv, player.getOnPos(), new MurasamaParticlePacket(3F, (pos.x + X), (pos.y + Y), (pos.z + Z), 255, 0, 0));
                }

                List<LivingEntity> detectedEntities = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos.x + X - 0.5D, pos.y + Y - 0.5D, pos.z + Z - 0.5D, pos.x + X + 0.5D, pos.y + Y + 0.5D, pos.z + Z + 0.5D));
                for (LivingEntity entity : detectedEntities) {
                    if (!entity.equals(player)) {
                        entity.hurt(level.damageSources().playerAttack(player), (float) ((player.getAttributeValue(Attributes.ATTACK_DAMAGE) * (double) ii) + EnchantmentHelper.getSweepingDamageRatio(player) + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
                        performEffects(entity, player);
                        ValoriaUtils.chanceEffect(entity, effects, chance, arcRandom);
                        if (!player.isCreative()) {
                            stack.hurtAndBreak(getHurtAmount(detectedEntities), player, (plr) -> plr.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                        }
                    }

                    ii = ii - (1F / (detectedEntities.size() * 2));
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
        if (!player.isFallFlying() && player.getTicksUsingItem() >= 20) {
            player.awardStat(Stats.ITEM_USED.get(this));
            applyCooldown(player);
            performDash(stack, level, player, pos, rand);
            level.playSound(null, player.getOnPos(), getDashSound(), SoundSource.PLAYERS, 1.0F, 1F);
            if (level.isClientSide) {
                OverlayRender.setOverlayTexture(getOverlayTexture());
                OverlayRender.showDashOverlay(getOverlayTime());
            }
        }
    }

    @Override
    public void spawnParticles(Level level, ItemEntity entity) {
        RandomSource rand = level.getRandom();
        double X = ((rand.nextDouble() - 0.5D) * 0.3f);
        double Y = ((rand.nextDouble() - 0.5D) + 0.3f);
        double Z = ((rand.nextDouble() - 0.5D) * 0.3f);

        double dX = -X;
        double dY = -Y;
        double dZ = -Z;
        int count = Mth.nextInt(rand, 0, 1);
        for (int ii = 0; ii < count; ii += 1) {
            double yaw = Math.atan2(dZ, dX);
            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;
            double XX = Math.sin(pitch) * Math.cos(yaw) * (float) (rand.nextDouble() * 0.025F) / (ii + 1), YY = Math.sin(pitch) * Math.sin(yaw) * (float) (rand.nextDouble() * 0.025F) / (ii + 1), ZZ = Math.cos(pitch) * (float) (rand.nextDouble() * 0.025F) / (ii + 1);
            Vec3 pos = new Vec3(entity.getX() + X, entity.getY() + Y, entity.getZ() + Z);
            RandomSource random = level.getRandom();
            ParticleBuilder.create(FluffyFurParticles.SPARKLE)
            .setTransparencyData(GenericParticleData.create(0.25f, 0f).build())
            .setScaleData(GenericParticleData.create(0.05f + arcRandom.randomValueUpTo(0.25f), arcRandom.randomValueUpTo(0.2f)).build())
            .setLifetime(6)
            .setColorData(ColorParticleData.create(Pal.strongRed, Pal.moderateViolet).build())
            .setSpinData(SpinParticleData.create(0.5f * (float)((random.nextDouble() - 0.5D) * 2), 0).build())
            .setVelocity(XX, YY, ZZ)
            .spawn(level, pos.x, pos.y, pos.z);
        }
    }

//    @OnlyIn(Dist.CLIENT)
//    @Override
//    public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
//        ScreenParticleRegistry.spawnCoreParticles(target, ColorParticleData.create(Pal.strongRed, Pal.moderateViolet).build());
//    }
}
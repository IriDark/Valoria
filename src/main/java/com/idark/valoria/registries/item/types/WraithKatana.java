package com.idark.valoria.registries.item.types;

import com.idark.valoria.Valoria;
import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.DashParticlePacket;
import com.idark.valoria.registries.SoundsRegistry;
import com.idark.valoria.util.Pal;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

import java.util.List;

public class WraithKatana extends KatanaItem {
    public WraithKatana(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    public WraithKatana(Tier tier, int attackDamageIn, float attackSpeedIn, float dashDistance, Properties builderIn, MobEffectInstance... pEffects) {
        super(tier, attackDamageIn, attackSpeedIn, dashDistance, builderIn, pEffects);
    }

    public WraithKatana(Tier tier, int attackDamageIn, float attackSpeedIn, float dashDistance, Properties builderIn, float chance, MobEffectInstance... pEffects) {
        super(tier, attackDamageIn, attackSpeedIn, dashDistance, builderIn, chance, pEffects);
    }

    public ResourceLocation getOverlayTexture() {
        return new ResourceLocation(Valoria.ID, "textures/gui/overlay/roots.png");
    }


    public SoundEvent getDashSound() {
        return SoundsRegistry.HALLOWEEN_SLICE.get();
    }

    public void performDash(@NotNull ItemStack stack, @NotNull Level level, @NotNull Player player, Vector3d pos, RandomSource rand) {
        double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
        double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;
        double dashDistance = getDashDistance(player);
        Vec3 dir = (player.getViewVector(0.0f).scale(dashDistance));
        player.push(dir.x, dir.y * 0.25, dir.z);
        double ii = 1D;
        if (level instanceof ServerLevel srv) {
            PacketHandler.sendToTracking(srv, player.getOnPos(), new DashParticlePacket(player.getUUID(), 1, 0, 0, 0, Pal.halloween));
            for (int i = 0; i < 10; i += 1) {
                double locDistance = i * 0.5D;
                double X = Math.sin(pitch) * Math.cos(yaw) * locDistance;
                double Y = Math.cos(pitch) * locDistance;
                double Z = Math.sin(pitch) * Math.sin(yaw) * locDistance;
                List<LivingEntity> detectedEntities = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos.x + X - 0.5D, pos.y + Y - 0.5D, pos.z + Z - 0.5D, pos.x + X + 0.5D, pos.y + Y + 0.5D, pos.z + Z + 0.5D));
                for (LivingEntity entity : detectedEntities) {
                    if (!entity.equals(player)) {
                        entity.hurt(level.damageSources().playerAttack(player), (float) ((player.getAttributeValue(Attributes.ATTACK_DAMAGE) * ii) + EnchantmentHelper.getSweepingDamageRatio(player) + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
                        performEffects(entity, player);
                        ValoriaUtils.chanceEffect(entity, effects, chance, arcRandom);
                        if (!player.isCreative()) {
                            stack.hurtAndBreak(getHurtAmount(detectedEntities), player, (plr) -> plr.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                        }
                    }

                    ii = ii - (1D / (detectedEntities.size() * 2));
                }

                if (locDistance >= distance(dashDistance, level, player)) {
                    break;
                }
            }
        }
    }
}
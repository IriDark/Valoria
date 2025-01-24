package com.idark.valoria.registries.item.types;

import com.idark.valoria.client.particle.ParticleRegistry;
import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.particle.BeastAttackParticlePacket;
import com.idark.valoria.registries.entity.projectile.Devourer;
import com.idark.valoria.registries.item.types.builders.AbstractScytheBuilder;
import com.idark.valoria.util.ColorUtil;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class BeastScytheItem extends ScytheItem{
    public BeastScytheItem(Builder builderIn){
        super(builderIn);
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker){
        pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        pAttacker.level().playSound(null, pTarget.getOnPos(), SoundEvents.PHANTOM_BITE, SoundSource.PLAYERS, 0.25f, 0.34f);
        return true;
    }

    protected void performSpellCasting(Level level, Player player){
        double d0 = Math.min(player.getYRot(), player.getY());
        double d1 = Math.max(player.getYRot(), player.getY()) + 1.0D;
        float playerYaw = player.getYRot() * (float)(Math.PI / 180);
        for(int i = 0; i < 5; ++i){
            float angle = playerYaw + (i * (float)Math.PI / 2);
            double spellX = player.getX() + Math.cos(angle) * 1.5D;
            double spellZ = player.getZ() + Math.sin(angle) * 1.5D;
            float yaw = (float)(Math.atan2(player.getZ() - spellZ, player.getX() - spellX) * (180F * Math.PI));
            this.createSpellEntity(level, player.getX() + Math.cos(angle) * 1.5D, player.getZ() + Math.sin(angle) * 1.5D, d0, d1, yaw, 0);
        }

        for(int k = 0; k < 8; ++k){
            float angle = playerYaw + (k * (float)Math.PI / 4) + 1.2566371F;
            double spellX = player.getX() + Math.cos(angle) * 3D;
            double spellZ = player.getZ() + Math.sin(angle) * 3D;
            float yaw = (float)(Math.atan2(player.getZ() - spellZ, player.getX() - spellX) * (90F * Math.PI));
            this.createSpellEntity(level, player.getX() + Math.cos(angle) * 3D, player.getZ() + Math.sin(angle) * 3D, d0, d1, yaw, 6);
        }
    }

    private void createSpellEntity(Level level, double pX, double pZ, double pMinY, double pMaxY, float pYRot, int pWarmupDelay){
        BlockPos blockpos = BlockPos.containing(pX, pMaxY, pZ);
        boolean flag = false;
        double d0 = 0.0D;
        do{
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = level.getBlockState(blockpos1);
            if(blockstate.isFaceSturdy(level, blockpos1, Direction.UP)){
                if(!level.isEmptyBlock(blockpos)){
                    BlockState blockstate1 = level.getBlockState(blockpos);
                    VoxelShape voxelshape = blockstate1.getCollisionShape(level, blockpos);
                    if(!voxelshape.isEmpty()){
                        d0 = voxelshape.max(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            blockpos = blockpos.below();
        }while(blockpos.getY() >= Mth.floor(pMinY) - 1);
        if(flag){
            if(level instanceof ServerLevel server){
                PacketHandler.sendToTracking(server, blockpos, new BeastAttackParticlePacket(pX, (double)blockpos.getY() + d0, pZ, ColorUtil.valueOf("66b4a3")));
                server.addFreshEntity(new Devourer(server, pX, (double)blockpos.getY() + d0, pZ, pYRot, pWarmupDelay, null));
            }
        }
    }

    public void performAttack(Level level, ItemStack stack, Player player){
        float damage = (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE)) + EnchantmentHelper.getSweepingDamageRatio(player);
        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        List<LivingEntity> hitEntities = new ArrayList<>();
        List<LivingEntity> markEntities = new ArrayList<>();
        ValoriaUtils.radiusHit(level, stack, player, null, hitEntities, pos, 0, player.getRotationVector().y, 3);
        ValoriaUtils.spawnParticlesMark(level, player, markEntities, ParticleRegistry.CHOMP.get(), pos, 0, player.getRotationVector().y, 3);
        applyCooldown(player, hitEntities.isEmpty() ? builder.minCooldownTime : builder.cooldownTime);
        performSpellCasting(level, player);
        for(LivingEntity entity : hitEntities){
            entity.hurt(level.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
            performEffects(entity, player);
            ValoriaUtils.chanceEffect(entity, builder.effects, builder.chance, arcRandom);
            if(!player.isCreative()){
                stack.hurtAndBreak(hitEntities.size(), player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }

            if(entity instanceof Player && ((Player)entity).isCreative()){
                continue;
            }

            entity.hurt(level.damageSources().playerAttack(player), (damage + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
            entity.setDeltaMovement((player.getX() - entity.getX()) * 0.06, 0.1D, (player.getZ() - entity.getZ()) * 0.06);
        }

        level.playSound(null, player.blockPosition(), SoundEvents.EVOKER_FANGS_ATTACK, SoundSource.AMBIENT, 1f, 1f);
    }

    public static class Builder extends AbstractScytheBuilder<BeastScytheItem>{
        public Builder(int attackDamageIn, float attackSpeedIn, Properties itemProperties){
            super(attackDamageIn, attackSpeedIn, itemProperties);
        }

        @Override
        public BeastScytheItem build(){
            return new BeastScytheItem(this);
        }
    }
}
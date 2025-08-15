package com.idark.valoria.registries.item.types.ranged;

import net.minecraft.core.particles.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.navigation.*;
import net.minecraft.world.entity.boss.enderdragon.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import org.joml.*;
import pro.komaru.tridot.api.Utils.*;
import pro.komaru.tridot.common.registry.entity.*;

import java.lang.Math;

public class ClawhookItem extends Item{
    public ClawhookItem(Properties pProperties){
        super(pProperties);
    }

    public UseAnim getUseAnimation(ItemStack stack){
        return UseAnim.BOW;
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration){
        if(!pLivingEntity.isShiftKeyDown() && pLivingEntity.tickCount % 10 == 0){
            Vec3 pos = new Vec3(pLivingEntity.getX(), pLivingEntity.getY() + pLivingEntity.getEyeHeight(), pLivingEntity.getZ());
            double X = 0;
            double Y = 0;
            double Z = 0;
            Vec3 pLivingEntityPos = pLivingEntity.getEyePosition();
            Vec3 EndPos = (pLivingEntity.getViewVector(0.0f).scale(15D));
            if(ProjectileUtil.getEntityHitResult(pLivingEntity, pLivingEntityPos, EndPos, new AABB(pos.x + X - 3D, pos.y + Y - 3D, pos.z + Z - 3D, pos.x + X + 3D, pos.y + Y + 3D, pos.z + Z + 3D), (e) -> true, 15) == null){
                HitResult hitresult = Hit.hitResult(pLivingEntityPos, pLivingEntity, (e) -> true, EndPos, pLevel);
                if(hitresult != null){
                    switch(hitresult.getType()){
                        case BLOCK, MISS:
                            break;
                        case ENTITY:
                            Entity entity = ((EntityHitResult)hitresult).getEntity();
                            X = entity.getX() - pos.x;
                            Y = entity.getY() - pos.y;
                            Z = entity.getZ() - pos.z;

                            Vec3 to = new Vec3(pos.x + X, pos.y + Y, pos.z + Z);
                            Particles.line(pLevel, pos, to, ParticleTypes.BUBBLE_POP);
                            Particles.around(new Vector3d(to.x, to.y, to.z), 3, 1, pLevel, ParticleTypes.DRIPPING_WATER);
                            break;
                    }
                }
            }
        }

        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
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
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged){
        Vec3 pos = new Vec3(pLivingEntity.getX(), pLivingEntity.getY() + pLivingEntity.getEyeHeight(), pLivingEntity.getZ());
        double pitch = ((pLivingEntity.getRotationVector().x + 90) * Math.PI) / 180;
        double yaw = ((pLivingEntity.getRotationVector().y + 90) * Math.PI) / 180;
        double X = Math.sin(pitch) * Math.cos(yaw) * 15;
        double Y = Math.cos(pitch) * 15;
        double Z = Math.sin(pitch) * Math.sin(yaw) * 15;
        Vec3 pLivingEntityPos = pLivingEntity.getEyePosition();
        Vec3 EndPos = (pLivingEntity.getViewVector(0.0f).scale(20.0d));
        if(ProjectileUtil.getEntityHitResult(pLivingEntity, pLivingEntityPos, EndPos, new AABB(pos.x + X - 3D, pos.y + Y - 3D, pos.z + Z - 3D, pos.x + X + 3D, pos.y + Y + 3D, pos.z + Z + 3D), (e) -> true, 15) == null){
            HitResult hitresult = Hit.hitResult(pLivingEntityPos, pLivingEntity, (e) -> true, EndPos, pLevel);
            if(hitresult != null){
                switch(hitresult.getType()){
                    case BLOCK, MISS:
                        break;
                    case ENTITY:
                        Entity entity = ((EntityHitResult)hitresult).getEntity();
                        if(entity instanceof LivingEntity target && pLivingEntity.canAttack(target)){
                            if(entity instanceof Mob mob && mob.getNavigation() instanceof FlyingPathNavigation) return;
                            if(entity instanceof EnderDragon || entity instanceof AbstractBoss) return;
                            Vec3 direction = pLivingEntity.position().subtract(entity.position()).normalize();
                            double strength = Mth.clamp(pLivingEntity.distanceTo(entity) * 0.2D, 0.25D, 1.5D);

                            entity.setDeltaMovement(direction.scale(strength));
                            entity.hurtMarked = true;
                            pStack.hurtAndBreak((int)Mth.clamp(pLivingEntity.distanceTo(entity) * 1, 1, 10), pLivingEntity, (e) -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                        }

                        break;
                }
            }
        }

        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }
}
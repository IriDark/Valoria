package com.idark.valoria.registries.entity.living.boss.firron;

import com.idark.valoria.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import pro.komaru.tridot.common.registry.entity.system.*;
import software.bernie.geckolib.animatable.*;

public class PiercingDashAttack extends AttackInstance{
    private final float speedModifier;
    public int ticksUntilNextPathRecalc;
    public double lastTargetX, lastTargetY, lastTargetZ;

    public PiercingDashAttack(PathfinderMob mob, float range, float speedMod, int attackDelay, int attackDuration, int cooldown){
        super(mob, 0, range, attackDelay, attackDuration, cooldown);
        this.speedModifier = speedMod;
    }

    @Override
    public ResourceLocation getId(){
        return Valoria.loc("firron_pierce");
    }

    @Override
    public void tick() {
        LivingEntity target = mob.getTarget();
        if (target == null) return;
        mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

        if (--ticksUntilNextPathRecalc <= 0 && (distanceToStoredTargetSqr(target) >= 1.0D || mob.getRandom().nextFloat() < 0.05F)) {
            storeTargetPosition();
            this.ticksUntilNextPathRecalc += 5;
            mob.getNavigation().moveTo(target, speedModifier);
        }

        double distSq = this.mob.getPerceivedTargetDistanceSquareForMeleeAttack(target);
        if(distSq > 1024.0D){
            this.ticksUntilNextPathRecalc += 10;
        }else if(distSq > 256.0D){
            this.ticksUntilNextPathRecalc += 5;
        }
    }

    private double distanceToStoredTargetSqr(LivingEntity target) {
        return target.distanceToSqr(lastTargetX, lastTargetY, lastTargetZ);
    }

    private void storeTargetPosition() {
        LivingEntity target = mob.getTarget();
        this.lastTargetX = target.getX();
        this.lastTargetY = target.getY();
        this.lastTargetZ = target.getZ();
    }

    @Override
    public void start(AttackSystemMob systemMob){
        super.start(systemMob);
        mob.setAggressive(true);

        this.ticksUntilNextPathRecalc = 0;
        this.mob.getNavigation().moveTo(mob.getTarget(), speedModifier);
        storeTargetPosition();
    }

    @Override
    public void stop(){
        super.stop();
        this.mob.setAggressive(false);
    }

    private void faceTargetSmooth(LivingEntity target, float maxTurn) {
        double dx = target.getX() - mob.getX();
        double dz = target.getZ() - mob.getZ();
        float desiredYaw = (float)(Mth.atan2(dz, dx) * (180F / Math.PI)) - 90F;
        float currentYaw = mob.getYRot();

        float newYaw = Mth.approachDegrees(currentYaw, desiredYaw, maxTurn);
        mob.setYRot(newYaw);
        mob.yBodyRot = newYaw;
        mob.setYHeadRot(newYaw);
    }

    @Override
    public int preference(Entity entity){
        return 0;
    }

    @Override
    public boolean canPerformAttack(LivingEntity target){
        return isWithinAttackRange(target, Firron.pierceAttackRange);
    }

    @Override
    public void performAttack(){
        LivingEntity target = mob.getTarget();
        if (target != null && target.isAlive() && isWithinAttackRange(target, Firron.pierceAttackRange)){
            if(mob instanceof GeoEntity geo) {
                geo.triggerAnim("AttackController", "pierce");
            }

            faceTargetSmooth(target, 10f);
        }
    }
}
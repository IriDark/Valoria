package com.idark.valoria.registries.entity.living.minions;

import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.living.*;
import com.idark.valoria.registries.entity.projectile.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;

import java.awt.*;
import java.util.*;
import java.util.function.*;

public class CrystalEntity extends AbstractFlyingAroundMinion implements RangedAttackMob{
    public Variant variant;
    private static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(CrystalEntity.class, EntityDataSerializers.INT);
    public CrystalEntity(EntityType<? extends MultiAttackMob> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        if(!pLevel.isClientSide){
            this.setType(Variant.byId(this.random.nextInt(Variant.values().length)));
            this.setNoGravity(true);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TYPE, 0);
    }

    public void setType(Variant variant) {
        this.variant = variant;
        this.entityData.set(TYPE, variant.getId());
    }

    public Variant getVariant() {
        return Variant.byId(this.entityData.get(TYPE));
    }

    public enum Variant{
        ICE(0, "Ice", Pal.diamond), FIRE(1, "Fire", Pal.magmatic), POISON(2, "Poison", Pal.nature);

        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous((p_263091_) -> p_263091_.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public String type;
        public final int id;
        public final Color color;

        Variant(int id, String type, Color color){
            this.id = id;
            this.type = type;
            this.color = color;
        }

        public Color getColor(){
            return color;
        }

        public String getString(){
            return type;
        }

        public int getId(){
            return id;
        }

        public static Variant byId(int pId){
            return BY_ID.apply(pId);
        }
    }

    public void spawnDisappearParticles(ServerLevel serverLevel){
        double posX = this.getOnPos().getCenter().x;
        double posY = this.getOnPos().above().getCenter().y;
        double posZ = this.getOnPos().getCenter().z;
        PacketHandler.sendToTracking(serverLevel, this.getOnPos(), new SmokeParticlePacket(3, posX, posY - 0.5f, posZ, 0.005f, 0.025f, 0.005f, 255, 255, 255));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound){
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Variant", variant.getId());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound){
        super.readAdditionalSaveData(pCompound);
        this.variant = Variant.byId(pCompound.getInt("Variant"));
    }

    protected void registerGoals(){
        super.registerGoals();
        this.targetSelector.addGoal(0, new CopyOwnerTargetGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Mob.class, 12.0F));
        this.goalSelector.addGoal(1, new CastSpellGoal(this, 12.0F));
    }

    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos){
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity){
        SpellProjectile spell = new SpellProjectile(this.level(), this, 8);
        double d0 = pTarget.getX() - this.getX();
        double d1 = pTarget.getY(0.3333333333333333D) - spell.getY();
        double d2 = pTarget.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        switch(this.getVariant()) {
            case POISON -> spell.addEffect(new MobEffectInstance(MobEffects.POISON, 120));
            case ICE -> spell.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120));
            case FIRE -> spell.igniteOnHit(5);
        };

        spell.setColor(this.getVariant().getColor());
        spell.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
        this.level().addFreshEntity(spell);
    }

    public class CastSpellGoal extends AttackGoal{
        private final Mob mob;
        private final RangedAttackMob rangedAttackMob;
        private final float attackRadius;

        public CastSpellGoal(RangedAttackMob pRangedAttackMob, float pAttackRadius){
            this.rangedAttackMob = pRangedAttackMob;
            this.mob = (Mob)pRangedAttackMob;
            this.attackRadius = pAttackRadius;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean isInterruptable(){
            return false;
        }

        public boolean requiresUpdateEveryTick(){
            return true;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            LivingEntity livingentity = this.mob.getTarget();
            return super.canUse() && livingentity != null;
        }

        @Override
        protected void performAttack(){
            double d0 = this.mob.distanceToSqr(this.mob.getTarget().getX(), this.mob.getTarget().getY(), this.mob.getTarget().getZ());
            float f = (float)Math.sqrt(d0) / this.attackRadius;
            float f1 = Mth.clamp(f, 0.1F, 1.0F);
            this.rangedAttackMob.performRangedAttack(this.mob.getTarget(), f1);
        }

        @Override
        public void onPrepare(){
            CrystalEntity.this.level().broadcastEntityEvent(CrystalEntity.this, (byte)62);
        }

        @Override
        public int getPreparingTime(){
            return 20;
        }

        @Override
        public int getAttackInterval(){
            return 70;
        }

        @Override
        public SoundEvent getPrepareSound(){
            return null;
        }

        @Override
        public AttackRegistry getAttack(){
            return EntityStatsRegistry.MAGIC;
        }
    }
}
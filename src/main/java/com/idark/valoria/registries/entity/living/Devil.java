package com.idark.valoria.registries.entity.living;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.AttackRegistry;
import com.idark.valoria.registries.ItemsRegistry;
import com.idark.valoria.registries.entity.ai.goals.ReasonableAvoidEntityGoal;
import com.idark.valoria.registries.entity.projectile.ThrownSpearEntity;
import com.idark.valoria.util.ArcRandom;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class Devil extends AbstractDevil implements RangedAttackMob{
    public static final AttackRegistry MAGIC = new AttackRegistry(Valoria.ID, "magic");
    public static final AttackRegistry THROW = new AttackRegistry(Valoria.ID, "throw");
    public final AnimationState idleAnimationState = new AnimationState();
    public AnimationState throwAnimationState = new AnimationState();
    public AnimationState magicAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;
    public int throwAnimationTimeout = 0;
    public int magicAnimationTimeout = 0;
    public int hits = 0;

    public Devil(EntityType<? extends Devil> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public void handleEntityEvent(byte pId){
        if(pId == 62 && throwAnimationTimeout <= 0){
            this.throwAnimationTimeout = 40;
            this.idleAnimationState.stop();
            this.throwAnimationState.start(this.tickCount);
        }

        if(pId == 61 && magicAnimationTimeout <= 0){
            this.magicAnimationTimeout = 80;
            this.idleAnimationState.stop();
            this.magicAnimationState.start(this.tickCount);
        }

        super.handleEntityEvent(pId);
    }

    public void tick(){
        super.tick();
        if(this.level().isClientSide()){
            setupAnimationStates();
        }
    }

    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = 60;
            this.idleAnimationState.start(this.tickCount);
        }else{
            --this.throwAnimationTimeout;
            --this.idleAnimationTimeout;
            //--this.magicAnimationTimeout;
        }
    }

    // panic reason
    public final boolean isLowHP(){
        return this.getHealth() < 10;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount){
        if(!this.level().isClientSide() && hits < 4){
            hits++;
            amplifyStats();
        }

        return super.hurt(pSource, pAmount);
    }

    private void amplifyStats(){
        this.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier("modifier", this.level().getDifficulty().getId() * 0.5f, Operation.ADDITION));
        this.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier("modifier", 0.025f, Operation.MULTIPLY_TOTAL));
    }

    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty){
        super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
        this.setItemSlot(EquipmentSlot.MAINHAND, ItemsRegistry.infernalSpear.get().getDefaultInstance());
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag){
        RandomSource randomsource = pLevel.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, pDifficulty);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    protected void registerGoals(){
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1, false));
//        this.goalSelector.addGoal(0, new MagicAttackGoal(this, 1.0D));
        this.goalSelector.addGoal(0, new ThrowSpearGoal(this, 1.0D, 12.0F));
        this.goalSelector.addGoal(0, new ReasonableAvoidEntityGoal<>(this, Player.class, 16, 1.25, 2, isLowHP()));

        this.goalSelector.addGoal(1, new MoveTowardsTargetGoal(this, 0.9D, 12.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.2));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Player.class, 8.0F));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers(Devil.class));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity){
        ThrownSpearEntity spear = new ThrownSpearEntity(this.level(), this, new ItemStack(ItemsRegistry.infernalSpear.get()));
        double d0 = pTarget.getX() - this.getX();
        double d1 = pTarget.getY(0.3333333333333333D) - spear.getY();
        double d2 = pTarget.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        spear.setBaseDamage(6);
        spear.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.DROWNED_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(spear);
    }

//    public class MagicAttackGoal extends AttackGoal{
//        private final Devil mob;
//        private LivingEntity target;
//        private final double speedModifier;
//        private int seeTime;
//
//        public MagicAttackGoal(Devil mob, double pSpeedModifier){
//            this.mob = mob;
//            this.speedModifier = pSpeedModifier;
//            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
//        }
//
//        /**
//         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
//         * method as well.
//         */
//        public boolean canUse() {
//            LivingEntity livingentity = this.mob.getTarget();
//            if (livingentity == null) return false;
//            if (livingentity.isAlive()) {
//                this.target = livingentity;
//                return this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ()) > 5;
//            }
//
//            return false;
//        }
//
//        @Override
//        public void onPrepare(){
//            mob.level().broadcastEntityEvent(Devil.this, (byte) 61);
//        }
//
//        @Override
//        protected void performAttack(){
//            boolean flag = this.mob.getSensing().hasLineOfSight(this.target);
//            if(flag){
//                ++this.seeTime;
//            }else{
//                this.seeTime = 0;
//            }
//
//            Level level = mob.level();
//            for(BlockPos pos : BlockPos.randomInCube(mob.random, 16, target.getOnPos().above(), 8)) {
//                Block block = level.getBlockState(pos.below()).getBlock();
//                if(block != Blocks.AIR){
//                    if(level instanceof ServerLevel server){
//                        PacketHandler.sendToTracking(server, pos, new BeastAttackParticlePacket(pos.getX(), pos.getY(), pos.getZ(), Pal.infernal));
//                        level.setBlock(pos, Blocks.FIRE.defaultBlockState(), 2);
//                    }
//                }
//            }
//
//            this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
//        }
//
//        @Override
//        public void tick(){
//            double d0 = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
//            if (d0 > 7.5f && this.seeTime >= 5) {
//                this.mob.getNavigation().stop();
//            } else {
//                this.mob.getNavigation().moveTo(this.target, this.speedModifier);
//            }
//
//            super.tick();
//            this.mob.getLookControl().setLookAt(this.target.position());
//        }
//
//        @Override
//        public int getPreparingTime(){
//            return 60;
//        }
//
//        @Override
//        public int getAttackInterval(){
//            return 500;
//        }
//
//        @Override
//        public SoundEvent getPrepareSound(){
//            return SoundsRegistry.BLAZECHARGE.get();
//        }
//
//        @Override
//        public AttackRegistry getAttack(){
//            return MAGIC;
//        }
//    }

    public class ThrowSpearGoal extends AttackGoal{
        private final Mob mob;
        private final RangedAttackMob rangedAttackMob;
        private LivingEntity target;
        private final double speedModifier;
        private final float attackRadius;
        private final float attackRadiusSqr;

        public ThrowSpearGoal(RangedAttackMob pRangedAttackMob, double pSpeedModifier, float pAttackRadius){
            this.rangedAttackMob = pRangedAttackMob;
            this.mob = (Mob)pRangedAttackMob;
            this.speedModifier = pSpeedModifier;
            this.attackRadius = pAttackRadius;
            this.attackRadiusSqr = pAttackRadius * pAttackRadius;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
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
            if(livingentity == null) return false;
            if(livingentity.isAlive() && super.canUse()){
                this.target = livingentity;
                return cantReachTarget(target) || isFleeing(mob, 5);
            }

            return false;
        }

        @Override
        protected void performAttack(){
            double d0 = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
            float f = (float)Math.sqrt(d0) / this.attackRadius;
            float f1 = Mth.clamp(f, 0.1F, 1.0F);
            this.rangedAttackMob.performRangedAttack(this.target, f1);
        }

        public boolean raytrace(Mob mob, Vec3 EndPos){
            Vec3 pos = new Vec3(mob.getX(), mob.getY() + mob.getEyeHeight(), mob.getZ());
            double pitch = ((mob.getRotationVector().x + 90) * Math.PI) / 180;
            double yaw = ((mob.getRotationVector().y + 90) * Math.PI) / 180;
            double X = Math.sin(pitch) * Math.cos(yaw) * 15;
            double Y = Math.cos(pitch) * 15;
            double Z = Math.sin(pitch) * Math.sin(yaw) * 15;
            Vec3 playerPos = mob.getEyePosition();
            if(ProjectileUtil.getEntityHitResult(mob, playerPos, EndPos, new AABB(pos.x + X - 3D, pos.y + Y - 3D, pos.z + Z - 3D, pos.x + X + 3D, pos.y + Y + 3D, pos.z + Z + 3D), (e) -> true, 15) == null){
                HitResult hitresult = ValoriaUtils.getHitResult(playerPos, mob, (e) -> true, EndPos, mob.level());
                if(hitresult != null){
                    return switch(hitresult.getType()){
                        case BLOCK, MISS -> false;
                        case ENTITY -> true;
                    };
                }
            }

            return false;
        }

        private Vec3 getRandomPositionWithLineOfSight(Mob mob, LivingEntity target, int radius, int attempts){
            var random = mob.getRandom();
            for(int i = 0; i < attempts; i++){
                double randomX = target.getX() + (random.nextDouble() - 0.5) * radius * 2;
                double randomY = target.getY();
                double randomZ = target.getZ() + (random.nextDouble() - 0.5) * radius * 2;

                Vec3 randomPos = new Vec3(randomX, randomY, randomZ);
                if(raytrace(mob, randomPos)){
                    return randomPos;
                }
            }

            return null;
        }

        @Override
        public void tick(){
            super.tick();
            this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
            if(!canUse()) return;
            if(cantReachTarget(target)){
                this.mob.getMoveControl().strafe(-0.5F, new ArcRandom().nextBoolean() ? 0.5F : -0.5F);
                return;
            }

            double d0 = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
            if(!(d0 > (double)this.attackRadiusSqr) && this.mob.getSensing().hasLineOfSight(this.target)){
                this.mob.getNavigation().stop();
                return;
            }else if(!this.mob.getSensing().hasLineOfSight(this.target)){
                Vec3 randomPos = getRandomPositionWithLineOfSight(this.mob, this.target, 12, 8);
                if(randomPos != null){
                    this.mob.getNavigation().moveTo(randomPos.x, randomPos.y, randomPos.z, this.speedModifier);
                    return;
                }
            }

            this.mob.getNavigation().moveTo(target, this.speedModifier);
        }

        @Override
        public void onPrepare(){
            Devil.this.level().broadcastEntityEvent(Devil.this, (byte)62);
        }

        @Override
        public int getPreparingTime(){
            return 25;
        }

        @Override
        public int getAttackInterval(){
            return 85;
        }

        @Override
        public SoundEvent getPrepareSound(){
            return null;
        }

        @Override
        public AttackRegistry getAttack(){
            return THROW;
        }
    }
}

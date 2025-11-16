package com.idark.valoria.registries.entity.living.boss.dryador;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.*;
import com.idark.valoria.registries.entity.living.boss.*;
import com.idark.valoria.registries.entity.living.boss.dryador.phases.*;
import com.idark.valoria.registries.entity.living.minions.*;
import com.idark.valoria.registries.entity.projectile.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.targeting.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.*;
import org.joml.*;
import pro.komaru.tridot.api.entity.*;
import pro.komaru.tridot.api.render.bossbars.*;
import pro.komaru.tridot.client.render.screenshake.*;
import pro.komaru.tridot.common.registry.entity.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.lang.Math;
import java.util.*;

public class DryadorEntity extends AbstractBoss implements RangedAttackMob{
    public final ServerBossBar bossEvent = new ServerBossBar(this.getDisplayName(), Valoria.loc("basic")).setTexture(Valoria.loc("textures/gui/bossbars/dryador.png")).setDarkenScreen(true);
    private int spawnTime = 0;
    public final AnimationState idleAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;
    public AnimationState spawnAnimationState = new AnimationState();
    public StaticAnimationState phaseTransitionAnimationState = new StaticAnimationState();
    public StaticAnimationState rangedAttackAnimationState = new StaticAnimationState();
    public StaticAnimationState meleeAttackAnimationState = new StaticAnimationState();
    public StaticAnimationState summonAnimationState = new StaticAnimationState();
    public StaticAnimationState stompAnimationState = new StaticAnimationState();

    public IBossPhase currentPhase = new BossPhase(this, () -> DryadorEntity.this.getHealth() <= DryadorEntity.this.getMaxHealth() / 2).setSound(SoundEvents.ANVIL_PLACE);
    public static final AttackRegistry DRYADOR_RADIAL = new AttackRegistry(Valoria.ID, "dryador_radial");
    public boolean flag = !(phaseTransitionAnimationState.isStarted() || meleeAttackAnimationState.isStarted() || rangedAttackAnimationState.isStarted() || summonAnimationState.isStarted());

    public DryadorEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        this.xpReward = 100;
    }

    @Override
    public void die(DamageSource pDamageSource){
        bossEvent.setAboutToDie(true);
        super.die(pDamageSource);
    }

    public boolean isBusy(){
        return phaseTransitionAnimationState.isPlaying
        || summonAnimationState.isPlaying
        || rangedAttackAnimationState.isPlaying
        || stompAnimationState.isPlaying
        || meleeAttackAnimationState.isPlaying;
    }

    public void handleEntityEvent(byte pId){
        if(pId == 62){
            this.idleAnimationState.stop();
            this.rangedAttackAnimationState.start(this.tickCount, 60);
        }

        if(pId == 61){
            this.idleAnimationState.stop();
            this.phaseTransitionAnimationState.start(this.tickCount, 60);
        }

        if(pId == 60) {
            this.idleAnimationState.stop();
            this.summonAnimationState.start(this.tickCount, 60);
        }

        if(pId == 59) {
            this.idleAnimationState.stop();
            this.meleeAttackAnimationState.start(this.tickCount, 30);
        }

        if(pId == 58) {
            this.idleAnimationState.stop();
            this.stompAnimationState.start(this.tickCount, 60);
        }

        if (pId == 4){
            this.idleAnimationState.stop();
            this.meleeAttackAnimationState.start(this.tickCount, 30);
            this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        }

        super.handleEntityEvent(pId);
    }

    private float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    public boolean doHurtTarget(Entity pEntity) {
        this.level().broadcastEntityEvent(this, (byte)4);
        float f = this.getAttackDamage();
        float f1 = (int)f > 0 ? f / 2.0F + (float)this.random.nextInt((int)f) : f;
        boolean flag = pEntity.hurt(this.damageSources().mobAttack(this), f1);
        if (flag) {
            double d2;
            if (pEntity instanceof LivingEntity livingentity) {
                d2 = livingentity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            } else {
                d2 = 0.0D;
            }

            double d0 = d2;
            double d1 = Math.max(0.0D, 1.0D - d0);
            pEntity.setDeltaMovement(pEntity.getDeltaMovement().add(0.0D, (double)0.4F * d1, 0.0D));
            this.doEnchantDamageEffects(this, pEntity);
        }

        this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        return flag;
    }

    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 1.0F, 1.0F);
    }

    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0 && flag){
            this.idleAnimationTimeout = 80;
            this.idleAnimationState.start(this.tickCount);
        }else{
            --this.idleAnimationTimeout;
        }
    }

    private void amplifyStats(){
        this.getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier("modifier", this.level().getDifficulty().getId() * 0.5f, Operation.MULTIPLY_TOTAL));
        this.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier("modifier", this.level().getDifficulty().getId() * 0.5f, Operation.ADDITION));
        this.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier("modifier", 0.025f, Operation.MULTIPLY_TOTAL));
    }

    public int animationTicks = 0;
    public void checkPhaseTransition() {
        if (currentPhase.shouldTransition() && !currentPhase.playedSound()) {
            animationTicks = 90;
            this.navigation.stop();
            phaseTransitionAnimationState.start(tickCount);
            currentPhase.onEnter();
            amplifyStats();
        }

        if(phaseTransitionAnimationState.isStarted()) animationTicks--;
    }

    public boolean isInvulnerableTo(DamageSource pSource) {
        return animationTicks > 0 && !pSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) || super.isInvulnerableTo(pSource);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound){
        super.readAdditionalSaveData(pCompound);
        if(this.hasCustomName()){
            this.bossEvent.setName(this.getDisplayName());
        }
    }

    public void setCustomName(@Nullable Component pName){
        super.setCustomName(pName);
        this.bossEvent.setName(this.getDisplayName());
    }

    public void startSeenByPlayer(ServerPlayer pPlayer){
        super.startSeenByPlayer(pPlayer);
        this.bossEvent.addPlayer(pPlayer);
    }

    public void stopSeenByPlayer(ServerPlayer pPlayer){
        super.stopSeenByPlayer(pPlayer);
        this.bossEvent.removePlayer(pPlayer);
    }

    protected void customServerAiStep(){
        super.customServerAiStep();
        this.bossEvent.setHealth(this.getHealth(), this.getMaxHealth());
    }

    protected void doPush(@NotNull Entity pEntity){
    }

    @Override
    public void knockback(double strength, double x, double z){
    }

    protected void pushEntities(){
        List<Entity> list = this.level().getEntities(this, this.getBoundingBox(), (p_31582_) -> p_31582_ instanceof LivingEntity);
        for(Entity entity : list){
            if(this.distanceToSqr(entity) <= 1){
                entity.push(this);
            }
        }
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource){
        return SoundsRegistry.WICKED_CRYSTAL_HURT.get();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound(){
        return SoundsRegistry.WICKED_CRYSTAL_DEATH.get();
    }

    @Override
    protected void registerGoals(){
        super.registerGoals();
        this.goalSelector.addGoal(1, new MoveTowardsTargetGoal(this, 1f, 32.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.2));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));

        this.goalSelector.addGoal(0, new net.minecraft.world.entity.ai.goal.MeleeAttackGoal(this, 1, false));
        this.goalSelector.addGoal(0, new ThrowAcornsGoal());
        this.goalSelector.addGoal(1, new StompAttack());
        this.goalSelector.addGoal(2, new RadialAcornAttack());
        this.goalSelector.addGoal(1, new PixieSummonGoal());
        this.goalSelector.addGoal(5, new JumpToTargetGoal());

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity){
        this.playSound(SoundEvents.DROWNED_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        for(int i = 0; i < 10; i++){
            AcornProjectile acorn = new AcornProjectile(this, this.level());
            double d0 = pTarget.getX() - this.getX();
            double d1 = pTarget.getY(0.3333333333333333D) - this.getY();
            double d2 = pTarget.getZ() - this.getZ();
            double d3 = Math.sqrt(d0 * d0 + d2 * d2);
            if(Tmp.rnd.chance(0.25f)) {
                acorn.addEffect(new MobEffectInstance(EffectsRegistry.STUN.get(), 60, 0));
            }

            acorn.shoot(d0, d1 + d3 * (double)0.2F, d2, pVelocity, (float)(25 - this.level().getDifficulty().getId() * 4));
            this.level().addFreshEntity(acorn);
        }
    }

    private final Map<Integer, List<BlockPos>> scheduledLifts = new HashMap<>();

    @Override
    public void tick(){
        super.tick();
        setupAnimationStates();
        checkPhaseTransition();
        if(this.spawnTime < 10){
            this.spawnTime++;
            this.spawnAnimationState.start(tickCount);
        }

        if (!level().isClientSide) {
            int currentTick = ((ServerLevel) level()).getServer().getTickCount();
            List<BlockPos> blocks = scheduledLifts.remove(currentTick);
            if (blocks != null) {
                for (BlockPos pos : blocks) {
                    liftBlock(level(), pos);
                }
            }
        }
    }

    public class PixieSummonGoal extends AttackGoal {
        private final TargetingConditions pixiesCount = TargetingConditions.forNonCombat().range(8).ignoreLineOfSight().ignoreInvisibilityTesting();

        @Override
        public void onPrepare(){
            DryadorEntity.this.getNavigation().stop();
            DryadorEntity.this.level().broadcastEntityEvent(DryadorEntity.this, (byte)60);
        }

        @Override
        public boolean canUse(){
            int i = DryadorEntity.this.level().getNearbyEntities(PixieEntity.class, this.pixiesCount, DryadorEntity.this, DryadorEntity.this.getBoundingBox().inflate(16.0D)).size();
            return super.canUse() && i < 8 && flag && DryadorEntity.this.currentPhase.shouldTransition();
        }

        private void summonPixies(ServerLevel serverLevel, BlockPos spawnPos, float angle, double speed){
            PixieEntity pixie = EntityTypeRegistry.PIXIE.get().create(DryadorEntity.this.level());
            if(pixie != null){
                pixie.moveTo(spawnPos.getX() + 0.5, spawnPos.getY() + 1, spawnPos.getZ() + 0.5, 0.0F, 0.0F);
                pixie.setOwner(DryadorEntity.this);
                pixie.setLimitedLife(360);

                double vx = Math.cos(angle) * speed;
                double vz = Math.sin(angle) * speed;
                pixie.setDeltaMovement(vx, 0.4, vz);
                serverLevel.addFreshEntity(pixie);
            }
        }

        @Override
        protected void performAttack(){
            if (level().isClientSide()) return;
            ServerLevel serv = (ServerLevel) level();
            BlockPos center = DryadorEntity.this.blockPosition();
            for(int i = 0; i < 4; i++){
                float angle = (float)((2 * Math.PI / 4) * i);
                summonPixies(serv, center, angle, 0.25);
            }
        }

        @Override
        public int getPreparingTime(){
            return 40;
        }

        @Override
        public int getAttackInterval(){
            return 650;
        }

        @Override
        public @Nullable SoundEvent getPrepareSound(){
            return null;
        }

        @Override
        public AttackRegistry getAttack(){
            return EntityStatsRegistry.SUMMON;
        }
    }

    public class ThrowAcornsGoal extends AttackGoal {
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
            LivingEntity livingentity = DryadorEntity.this.getTarget();
            if(livingentity == null) return false;
            if(livingentity.isAlive() && super.canUse()){
                return cantReachTarget(livingentity) || DryadorEntity.this.distanceTo(livingentity) > 15;
            }

            return false;
        }

        @Override
        protected void performAttack(){
            DryadorEntity.this.performRangedAttack(DryadorEntity.this.getTarget(), 1);
        }

        @Override
        public void onPrepare(){
            DryadorEntity.this.level().broadcastEntityEvent(DryadorEntity.this, (byte)58);
        }

        @Override
        public int getPreparingTime(){
            return 20;
        }

        @Override
        public int getAttackInterval(){
            return 80;
        }

        @Override
        public @Nullable SoundEvent getPrepareSound(){
            return null;
        }

        @Override
        public AttackRegistry getAttack(){
            return EntityStatsRegistry.THROW;
        }
    }

    public class JumpToTargetGoal extends AttackGoal {
        @Override
        public void onPrepare(){
            DryadorEntity.this.getNavigation().stop();
            DryadorEntity.this.level().broadcastEntityEvent(DryadorEntity.this, (byte)61);
        }

        @Override
        public boolean canUse(){
            LivingEntity livingentity = DryadorEntity.this.getTarget();
            if(livingentity == null) return false;
            if(livingentity.isAlive() && super.canUse()){
                return cantReachTarget(livingentity) || DryadorEntity.this.distanceTo(livingentity) > 25;
            }

            return false;
        }

        public void jumpToward(LivingEntity target) {
            Vec3 mobPos = DryadorEntity.this.position();
            Vec3 targetPos = target.position();

            double dx = targetPos.x - mobPos.x;
            double dz = targetPos.z - mobPos.z;
            double distance = Math.sqrt(dx * dx + dz * dz);
            if (distance < 0.001) return;

            double strength = 2;
            double upward = 0.6;

            double normX = dx / distance;
            double normZ = dz / distance;
            Vec3 motion = new Vec3(normX * strength, upward, normZ * strength);
            DryadorEntity.this.setDeltaMovement(motion);
            DryadorEntity.this.hasImpulse = true;
            DryadorEntity.this.setJumping(true);
        }
        @Override
        protected void performAttack(){
            if (level().isClientSide()) return;
            jumpToward(DryadorEntity.this.getTarget());
        }

        @Override
        public int getPreparingTime(){
            return 60;
        }

        @Override
        public int getAttackInterval(){
            return 350;
        }

        @Override
        public @Nullable SoundEvent getPrepareSound(){
            return null;
        }

        @Override
        public AttackRegistry getAttack(){
            return EntityStatsRegistry.JUMP;
        }
    }

    public class RadialAcornAttack extends AttackGoal {
        private final TargetingConditions targeting = TargetingConditions.forCombat().range(6).ignoreLineOfSight().ignoreInvisibilityTesting();

        @Override
        public void onPrepare(){
            DryadorEntity.this.getNavigation().stop();
            DryadorEntity.this.level().broadcastEntityEvent(DryadorEntity.this, (byte)61);
        }

        @Override
        public boolean canUse(){
            List<LivingEntity> entities = DryadorEntity.this.level().getNearbyEntities(LivingEntity.class, this.targeting, DryadorEntity.this, DryadorEntity.this.getBoundingBox().inflate(6));
            return super.canUse() && DryadorEntity.this.getTarget() != null && !entities.isEmpty() && flag;
        }

        private void summonAcorns(ServerLevel serverLevel, BlockPos spawnPos, float angle, double speed){
            AcornProjectile acorn = EntityTypeRegistry.ACORN.get().create(DryadorEntity.this.level());
            if(acorn != null){
                acorn.moveTo(spawnPos.getX() + 0.5, spawnPos.getY() + 2, spawnPos.getZ() + 0.5, 0.0F, 0.0F);
                acorn.setOwner(DryadorEntity.this);
                if(currentPhase.shouldTransition() || Tmp.rnd.chance(0.15)){
                    acorn.addEffect(new MobEffectInstance(MobEffects.POISON, 80, currentPhase.shouldTransition() ? 2 : 0));
                }

                double vx = Math.cos(angle) * speed;
                double vz = Math.sin(angle) * speed;
                acorn.setDeltaMovement(vx, 0.4, vz);
                serverLevel.addFreshEntity(acorn);
            }
        }

        @Override
        protected void performAttack(){
            if (level().isClientSide()) return;
            ServerLevel serv = (ServerLevel) level();
            BlockPos center = DryadorEntity.this.blockPosition();
            for(int i = 0; i < 12; i++){
                float angle = (float)((2 * Math.PI / 12) * i);
                summonAcorns(serv, center, angle, 0.25);
            }

            if(Tmp.rnd.chance(0.25)){
                for(int i = 0; i < 6; i++){
                    float angle = (float)((2 * Math.PI / 6) * i);
                    summonAcorns(serv, center.above(1), angle, 0.35 + random.nextDouble() * 0.3);
                }
            }

        }

        @Override
        public int getPreparingTime(){
            return 60;
        }

        @Override
        public int getAttackInterval(){
            return 350;
        }

        @Override
        public @Nullable SoundEvent getPrepareSound(){
            return null;
        }

        @Override
        public AttackRegistry getAttack(){
            return EntityStatsRegistry.RADIAL;
        }
    }

    private void liftBlock(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        FallingBlockEntity fallingBlock = FallingBlockEntity.fall(level, pos, state);
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        fallingBlock.setHurtsEntities(4, 15);
        fallingBlock.hurtMarked = true;

        fallingBlock.setDeltaMovement(0, 0.5f, 0);
    }

    public class StompAttack extends AttackGoal{
        private final TargetingConditions targeting = TargetingConditions.forCombat().range(8).ignoreLineOfSight().ignoreInvisibilityTesting();

        @Override
        public boolean canUse(){
            List<LivingEntity> entities = DryadorEntity.this.level().getNearbyEntities(LivingEntity.class, this.targeting, DryadorEntity.this, DryadorEntity.this.getBoundingBox().inflate(8));
            return super.canUse() && DryadorEntity.this.getTarget() != null && !entities.isEmpty() && flag;
        }

        @Override
        public void onPrepare(){
            DryadorEntity.this.getNavigation().stop();
            DryadorEntity.this.level().broadcastEntityEvent(DryadorEntity.this, (byte)62);
            DryadorEntity.this.playSound(SoundsRegistry.STOMP.get());
        }

        public void liftBlocksAround(Level level, BlockPos center, int radius){
            if(!(level instanceof ServerLevel serverLevel)) return;
            int currentTick = serverLevel.getServer().getTickCount();
            int radiusSqr = radius * radius;
            for(int x = -radius; x <= radius; x++){
                for(int z = -radius; z <= radius; z++){
                    if(x * x + z * z >= radiusSqr) continue;
                    for(int y = -2; y <= 0; y++){
                        BlockPos pos = center.offset(x, y, z);
                        BlockState state = level.getBlockState(pos);
                        if(state.getBlock().defaultDestroyTime() == -1) continue;
                        if(pos == center || state.isAir() || level.getBlockState(pos.above()).isSolid() || !state.isSolid()) continue;

                        serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), pos.getX(), pos.getY() + 0.5, pos.getZ(), 4, random.nextDouble(), random.nextDouble(), random.nextDouble(), 0);
                        int delay = (int)Math.sqrt(pos.distSqr(center)) * 5;
                        int scheduledTick = currentTick + delay;
                        scheduledLifts.computeIfAbsent(scheduledTick, k -> new ArrayList<>()).add(pos);
                    }
                }
            }
        }

        @Override
        protected void performAttack(){
            liftBlocksAround(DryadorEntity.this.level(), DryadorEntity.this.getOnPos(), 8);
            ScreenshakeHandler.add(new PositionedScreenshakeInstance(60, pro.komaru.tridot.util.phys.Vec3.from(position()), 0, 30).interp(Interp.bounce).intensity(0.8f));

            Vector3d pos = new Vector3d(DryadorEntity.this.getX(), DryadorEntity.this.getY(), DryadorEntity.this.getZ());
            ValoriaUtils.stunNearby(DryadorEntity.this.level(), DryadorEntity.this, pos,0, DryadorEntity.this.getRotationVector().y, 3);
        }

        @Override
        public int getPreparingTime(){
            return 40;
        }

        @Override
        public int getAttackInterval(){
            return 600;
        }

        @Override
        public @Nullable SoundEvent getPrepareSound(){
            return null;
        }

        @Override
        public AttackRegistry getAttack(){
            return DRYADOR_RADIAL; // special attack
        }
    }
}
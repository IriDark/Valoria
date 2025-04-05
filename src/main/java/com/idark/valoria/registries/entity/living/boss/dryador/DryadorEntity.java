package com.idark.valoria.registries.entity.living.boss.dryador;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
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
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
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

public class DryadorEntity extends AbstractBoss{
    public final ServerBossBarEvent bossEvent = (ServerBossBarEvent)(new ServerBossBarEvent(this.getName(), "Dryador")).setDarkenScreen(true);
    private int spawnTime = 0;
    public final AnimationState idleAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;
    public AnimationState spawnAnimationState = new AnimationState();
    public AnimationState phaseTransitionAnimationState = new AnimationState();
    public AnimationState rangedAttackAnimationState = new AnimationState();

    public IBossPhase currentPhase = new BossPhase(this, () -> DryadorEntity.this.getHealth() <= 500).setSound(SoundEvents.ANVIL_PLACE);
    public static final AttackRegistry DRYADOR_RADIAL = new AttackRegistry(Valoria.ID, "dryador_radial");
    public boolean flag = !(phaseTransitionAnimationState.isStarted() || rangedAttackAnimationState.isStarted());

    public DryadorEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public void handleEntityEvent(byte pId){
        if(pId == 62){
            this.idleAnimationState.stop();
            this.rangedAttackAnimationState.start(this.tickCount);
        }

        if(pId == 61){
            this.idleAnimationState.stop();
            this.phaseTransitionAnimationState.start(this.tickCount);
        }

        super.handleEntityEvent(pId);
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
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
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
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.2));
        this.goalSelector.addGoal(0, new StompAttack());
        this.goalSelector.addGoal(0, new RadialAcornAttack());
        this.goalSelector.addGoal(0, new PixieSummonGoal());

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
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
            DryadorEntity.this.level().broadcastEntityEvent(DryadorEntity.this, (byte)61);
        }

        @Override
        public boolean canUse(){
            int i = DryadorEntity.this.level().getNearbyEntities(PixieEntity.class, this.pixiesCount, DryadorEntity.this, DryadorEntity.this.getBoundingBox().inflate(16.0D)).size();
            return super.canUse() && i < 8 && flag;
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
            return 60;
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

    public class RadialAcornAttack extends AttackGoal {

        @Override
        public void onPrepare(){
            DryadorEntity.this.getNavigation().stop();
            DryadorEntity.this.level().broadcastEntityEvent(DryadorEntity.this, (byte)61);
        }

        @Override
        public boolean canUse(){
            return super.canUse() && flag;
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

        @Override
        public boolean canUse(){
            return super.canUse() && DryadorEntity.this.distanceToSqr(DryadorEntity.this.getTarget()) < 8.0D && flag;
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
                        if(state.getBlock().defaultDestroyTime() == -1) return;
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
            ScreenshakeHandler.add(new ScreenshakeInstance(60).interp(Interp.bounce).intensity(0.8f));

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
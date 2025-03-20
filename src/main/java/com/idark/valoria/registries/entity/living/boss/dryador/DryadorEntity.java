package com.idark.valoria.registries.entity.living.boss.dryador;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.living.boss.*;
import com.idark.valoria.registries.entity.living.boss.dryador.phases.*;
import com.mojang.logging.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.gameevent.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.api.render.bossbars.*;
import pro.komaru.tridot.common.registry.entity.*;

import java.util.*;

public class DryadorEntity extends AbstractBoss{
    public final ServerBossBarEvent bossEvent = (ServerBossBarEvent)(new ServerBossBarEvent(this.getName(), "Dryador")).setDarkenScreen(true);
    private int spawnTime = 0;
    public final AnimationState idleAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;
    public AnimationState spawnAnimationState = new AnimationState();
    public AnimationState deathAnimationState = new AnimationState();
    public int deathTime = 0;
    private IBossPhase currentPhase = new FirstPhase(this, this.getHealth() < 500);

    public DryadorEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = 80;
            this.idleAnimationState.start(this.tickCount);
        }else{
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick(){
        super.tick();
        setupAnimationStates();
        if (this.deathTime > 0) {
            if (this.level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 2; i++) {
                    double offsetX = (random.nextDouble() - 0.5) * 0.6;
                    double offsetY = random.nextDouble() * 0.4;
                    double offsetZ = (random.nextDouble() - 0.5) * 0.6;

                    serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, BlockRegistry.wickedAmethystBlock.get().defaultBlockState()), this.getX() + offsetX, this.getY() + 0.5 + offsetY, this.getZ() + offsetZ,
                    1, 0, 0.5, 0, 0.05);
                }
            }
        }

        if(this.spawnTime < 10){
            this.spawnTime++;
            this.spawnAnimationState.start(tickCount);
        }
    }

    @Override
    protected void tickDeath(){
        ++this.deathTime;
        if (this.deathTime >= 60 && !this.level().isClientSide() && !this.isRemoved()) {
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    public void die(DamageSource pDamageSource){
        if(net.minecraftforge.common.ForgeHooks.onLivingDeath(this, pDamageSource)) return;
        if(!this.isRemoved() && !this.dead){
            Entity entity = pDamageSource.getEntity();
            LivingEntity livingentity = this.getKillCredit();
            if(this.deathScore >= 0 && livingentity != null){
                livingentity.awardKillScore(this, this.deathScore, pDamageSource);
            }

            if(this.isSleeping()){
                this.stopSleeping();
            }

            if(!this.level().isClientSide && this.hasCustomName()){
                LogUtils.getLogger().info("Named entity {} died: {}", this, this.getCombatTracker().getDeathMessage().getString());
            }

            this.dead = true;
            this.getCombatTracker().recheckStatus();
            Level level = this.level();
            if(level instanceof ServerLevel serverlevel){
                if(entity == null || entity.killedEntity(serverlevel, this)){
                    this.gameEvent(GameEvent.ENTITY_DIE);
                    this.dropAllDeathLoot(pDamageSource);
                }

                this.level().broadcastEntityEvent(this, (byte)3);
            }
        }

        this.deathAnimationState.start(tickCount);
    }

    public void checkPhaseTransition() {
        if (currentPhase != null && currentPhase.shouldTransition()) {
            currentPhase.onEnter();
        }
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

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
}
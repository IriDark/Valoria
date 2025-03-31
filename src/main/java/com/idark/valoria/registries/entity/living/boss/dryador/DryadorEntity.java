package com.idark.valoria.registries.entity.living.boss.dryador;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.living.boss.*;
import com.idark.valoria.registries.entity.living.boss.dryador.phases.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
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
    public AnimationState phaseTransitionAnimationState = new AnimationState();
    public IBossPhase currentPhase = new BossPhase(this, "1") {{
        event = SoundEvents.ANVIL_PLACE;
    }

        @Override
        public boolean shouldTransition(){
            return DryadorEntity.this.getHealth() <= 500;
        }
    };

    public IBossPhase secondPhase = new BossPhase(this, "2") {{
        event = SoundEvents.ANVIL_PLACE;
    }

        @Override
        public boolean shouldTransition(){
            return DryadorEntity.this.getHealth() <= 250;
        }
    };

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
        checkPhaseTransition();
        if(this.spawnTime < 10){
            this.spawnTime++;
            this.spawnAnimationState.start(tickCount);
        }
    }

    public int animationTicks = 0;
    public void checkPhaseTransition() {
        if (currentPhase.shouldTransition() && !currentPhase.playedSound()) {
            animationTicks = 90;
            this.navigation.stop();
            phaseTransitionAnimationState.start(tickCount);
            currentPhase.onEnter();
            currentPhase = secondPhase;
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

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
}
package com.idark.valoria.registries.entity.living;

import com.idark.valoria.client.particle.*;
import com.idark.valoria.registries.*;
import net.minecraft.nbt.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.pathfinder.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.util.*;

import javax.annotation.*;

public class Corrupted extends Monster{
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public Corrupted(EntityType<? extends Monster> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        this.xpReward = 3;
        this.setPathfindingMalus(BlockPathTypes.LAVA, 2.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 4.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_CAUTIOUS, 4.0F);
    }

    @Override
    public void tick(){
        super.tick();
        if(this.level().isClientSide()){
            setupAnimationStates();
            if(this.walkAnimation.isMoving() && Tmp.rnd.chance(0.65)) {
                ParticleBuilder.create(ParticleRegistry.FLESH)
                .randomOffset(0.5f)
                .setGravity(1)
                .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
                .setScaleData(GenericParticleData.create(0.15f).build())
                .repeat(this.level(), this.position(), 3);
            }
        }
    }

    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = 60;
            this.idleAnimationState.start(this.tickCount);
        }else{
            --this.idleAnimationTimeout;
        }
    }

    public boolean doHurtTarget(Entity pEntity){
        this.level().broadcastEntityEvent(this, (byte)4);
        return super.doHurtTarget(pEntity);
    }

    public float getVoicePitch(){
        return this.isBaby() ? (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.5F : (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.25F;
    }

    protected SoundEvent getAmbientSound(){
        return SoundsRegistry.TROLL_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource){
        return SoundsRegistry.TROLL_HURT.get();
    }

    protected SoundEvent getDeathSound(){
        return SoundsRegistry.TROLL_DEATH.get();
    }

    @Override
    protected void registerGoals(){
        super.registerGoals();
        // attack
        this.targetSelector.addGoal(0, new MeleeAttackGoal(this, 2, false));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));

        // ai
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 0.8D));
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag){
        RandomSource randomsource = pLevel.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, pDifficulty);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }
}

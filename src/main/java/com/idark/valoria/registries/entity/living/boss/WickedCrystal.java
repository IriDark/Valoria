package com.idark.valoria.registries.entity.living.boss;

import com.idark.valoria.client.ui.bossbars.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.living.minions.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.targeting.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class WickedCrystal extends AbstractBoss{
    public final ServerBossBarEvent bossEvent = (ServerBossBarEvent)(new ServerBossBarEvent(this.getName(), "Wicked Crystal")).setDarkenScreen(true);
    public WickedCrystal(EntityType<? extends PathfinderMob> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound){
        super.readAdditionalSaveData(pCompound);
        if(this.hasCustomName()){
            this.bossEvent.setName(this.getDisplayName());
        }
    }

    public void setCustomName(@javax.annotation.Nullable Component pName){
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
    public boolean hurt(DamageSource source, float amount){
        TargetingConditions shields = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight().ignoreInvisibilityTesting();
        int shieldCount = WickedCrystal.this.level().getNearbyEntities(WickedShield.class, shields, WickedCrystal.this, WickedCrystal.this.getBoundingBox().inflate(16.0D)).size();
        float damage = amount;
        for (int i = 0; i < shieldCount; i++) {
            damage /= 2.0f;
        }

        return super.hurt(source, damage);
    }

    @Override
    protected void registerGoals(){
        super.registerGoals();
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));

        this.goalSelector.addGoal(1, new SummonShieldsGoal());
    }

    public class SummonShieldsGoal extends AttackGoal {
        private final TargetingConditions shieldCount = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight().ignoreInvisibilityTesting();

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            int i = WickedCrystal.this.level().getNearbyEntities(WickedShield.class, this.shieldCount, WickedCrystal.this, WickedCrystal.this.getBoundingBox().inflate(16.0D)).size();
            return super.canUse() && i < 8;
        }

        private void summonShield(ServerLevel serverLevel, BlockPos blockpos, float angle){
            WickedShield shield = EntityTypeRegistry.WICKED_SHIELD.get().create(WickedCrystal.this.level());
            if(shield != null && serverLevel.isEmptyBlock(blockpos)){
                shield.moveTo(blockpos, 0.0F, 0.0F);
                shield.finalizeSpawn(serverLevel, WickedCrystal.this.level().getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);
                shield.setOwner(WickedCrystal.this);
                shield.setRadius(2.5f);
                shield.setAngle(angle);
                serverLevel.addFreshEntityWithPassengers(shield);
            }
        }

        @Override
        public void onPrepare(){

        }

        @Override
        protected void performAttack(){
            WickedCrystal entity = WickedCrystal.this;
            boolean flag = entity.level().isClientSide || !entity.hasTarget();
            if(flag){
                return;
            }

            if(entity.level() instanceof ServerLevel serv){
                for (int i = 0; i < 4; i++) {
                    float initialAngle = (float)((2 * Math.PI / 4) * i);
                    summonShield(serv, WickedCrystal.this.blockPosition().above(), initialAngle);
                }
            }
        }

        public boolean requiresUpdateEveryTick(){
            return true;
        }

        @Override
        public int getPreparingTime(){
            return 25;
        }

        @Override
        public int getAttackInterval(){
            return 1200;
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
}
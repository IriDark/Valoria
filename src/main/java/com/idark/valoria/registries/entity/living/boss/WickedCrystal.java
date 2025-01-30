package com.idark.valoria.registries.entity.living.boss;

import com.idark.valoria.client.ui.bossbars.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.ai.movements.*;
import com.idark.valoria.registries.entity.living.minions.*;
import com.idark.valoria.registries.entity.projectile.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.targeting.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class WickedCrystal extends AbstractBoss{
    public final ServerBossBarEvent bossEvent = (ServerBossBarEvent)(new ServerBossBarEvent(this.getName(), "Wicked Crystal")).setDarkenScreen(true);
    public WickedCrystal(EntityType<? extends PathfinderMob> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public void checkPhaseTransition() {
        if (this.getHealth() <= 1000 && phase == 1) {
            this.phase = 2;
            ((ServerLevel)this.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, BlockRegistry.wickedAmethystBlock.get().defaultBlockState()), this.getX(), this.getY() + 5d, this.getZ(), 25, 0, 0, 0, 0);
            this.playSound(SoundEvents.ENDER_DRAGON_GROWL, 1.0F, 1.0F);
        }
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
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));

        this.goalSelector.addGoal(1, new SummonShieldsGoal());
        this.goalSelector.addGoal(1, new SummonSpellGoal());
        this.goalSelector.addGoal(1, new RadialAttack());
    }

    public class SummonSpellGoal extends AttackGoal{
        @Override
        public void onPrepare(){

        }

        @Override
        protected void performAttack(){
            if(WickedCrystal.this.level() instanceof ServerLevel serv) spawn(serv);
        }

        @Override
        public int getPreparingTime(){
            return 20;
        }

        @Override
        public int getAttackInterval(){
            return 425;
        }

        @Override
        public @Nullable SoundEvent getPrepareSound(){
            return SoundEvents.EVOKER_PREPARE_ATTACK;
        }

        @Override
        public AttackRegistry getAttack(){
            return EntityStatsRegistry.SUMMON;
        }

        private void spawn(ServerLevel serverLevel, BlockPos blockpos, float angle){
            CrystalEntity crystal = EntityTypeRegistry.CRYSTAL.get().create(WickedCrystal.this.level());
            if(crystal != null && serverLevel.isEmptyBlock(blockpos)){
                crystal.moveTo(blockpos, 0.0F, 0.0F);
                crystal.finalizeSpawn(serverLevel, WickedCrystal.this.level().getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);
                crystal.setOwner(WickedCrystal.this);
                crystal.movement = new FlyingAroundMovement(crystal, WickedCrystal.this);
                crystal.movement.setRadius(4f);
                crystal.movement.setAngle(angle);
                crystal.setBoundOrigin(blockpos);
                crystal.setLimitedLife(325);
                serverLevel.addFreshEntityWithPassengers(crystal);
            }
        }

        public void spawn(ServerLevel serv){
            for(int i = 0; i < 4; ++i){
                float initialAngle = (float)((2 * Math.PI / 4) * i);
                spawn(serv, WickedCrystal.this.blockPosition().above(), initialAngle);
            }
        }
    }

    public class RadialAttack extends AttackGoal {

        @Override
        public void onPrepare(){

        }

        @Override
        public boolean canUse(){
            return super.canUse() && WickedCrystal.this.distanceToSqr(WickedCrystal.this.getTarget()) < 9.0D;
        }

        protected void performSpellCasting(Level level, WickedCrystal crystal){
            double d0 = Math.min(crystal.getYRot(), crystal.getY());
            double d1 = Math.max(crystal.getYRot(), crystal.getY()) + 1.0D;
            float playerYaw = crystal.getYRot() * (float)(Math.PI / 180);
            for(int i = 0; i < 6; ++i){
                float angle = playerYaw + (i * (float)Math.PI / 2);
                double spellX = crystal.getX() + Math.cos(angle) * 1.5D;
                double spellZ = crystal.getZ() + Math.sin(angle) * 1.5D;
                float yaw = (float)(Math.atan2(crystal.getZ() - spellZ, crystal.getX() - spellX) * (180F * Math.PI));
                this.createSpellEntity(level, crystal.getX() + Math.cos(angle) * 1.5D, crystal.getZ() + Math.sin(angle) * 1.5D, d0, d1, yaw, 0);
            }

            for(int k = 0; k < 10; ++k){
                float angle = playerYaw + (k * (float)Math.PI / 4) + 1.2566371F;
                double spellX = crystal.getX() + Math.cos(angle) * 3D;
                double spellZ = crystal.getZ() + Math.sin(angle) * 3D;
                float yaw = (float)(Math.atan2(crystal.getZ() - spellZ, crystal.getX() - spellX) * (90F * Math.PI));
                this.createSpellEntity(level, crystal.getX() + Math.cos(angle) * 3D, crystal.getZ() + Math.sin(angle) * 3D, d0, d1, yaw, 6);
            }
        }

        private void createSpellEntity(Level level, double pX, double pZ, double pMinY, double pMaxY, float pYRot, int pWarmupDelay){
            BlockPos blockpos = BlockPos.containing(pX, pMaxY, pZ);
            boolean flag = false;
            double d0 = 0.0D;
            do{
                BlockPos blockpos1 = blockpos.below();
                BlockState blockstate = level.getBlockState(blockpos1);
                if(blockstate.isFaceSturdy(level, blockpos1, Direction.UP)){
                    if(!level.isEmptyBlock(blockpos)){
                        BlockState blockstate1 = level.getBlockState(blockpos);
                        VoxelShape voxelshape = blockstate1.getCollisionShape(level, blockpos);
                        if(!voxelshape.isEmpty()){
                            d0 = voxelshape.max(Direction.Axis.Y);
                        }
                    }

                    flag = true;
                    break;
                }

                blockpos = blockpos.below();
            }while(blockpos.getY() >= Mth.floor(pMinY) - 1);
            if(flag){
                if(level instanceof ServerLevel server){
                    PacketHandler.sendToTracking(server, blockpos, new BeastAttackParticlePacket(pX, (double)blockpos.getY() + d0, pZ, Pal.verySoftPink));
                    server.addFreshEntity(new CrystalSpikes(server, pX, (double)blockpos.getY() + d0, pZ, pYRot, pWarmupDelay, null));
                }
            }
        }

        @Override
        protected void performAttack(){
            performSpellCasting(WickedCrystal.this.level(), WickedCrystal.this);
        }

        @Override
        public int getPreparingTime(){
            return 20;
        }

        @Override
        public int getAttackInterval(){
            return 160;
        }

        @Override
        public @Nullable SoundEvent getPrepareSound(){
            return SoundEvents.AMETHYST_BLOCK_CHIME;
        }

        @Override
        public AttackRegistry getAttack(){
            return EntityStatsRegistry.RADIAL;
        }
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
                shield.movement = new FlyingAroundMovement(shield, WickedCrystal.this);
                shield.movement.setRadius(2f);
                shield.movement.setAngle(angle);
                serverLevel.addFreshEntity(shield);
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
                for (int i = 0; i < (WickedCrystal.this.phase == 1 ? 2 : 4); i++) {
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
            return EntityStatsRegistry.BLOCK;
        }
    }
}
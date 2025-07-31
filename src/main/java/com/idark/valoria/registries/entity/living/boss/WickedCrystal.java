package com.idark.valoria.registries.entity.living.boss;

import com.idark.valoria.core.config.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.ai.movements.*;
import com.idark.valoria.registries.entity.living.minions.*;
import com.idark.valoria.registries.entity.projectile.*;
import com.idark.valoria.util.*;
import com.mojang.logging.*;
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
import net.minecraft.world.level.gameevent.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.api.entity.*;
import pro.komaru.tridot.api.render.bossbars.*;
import pro.komaru.tridot.common.registry.entity.*;
import pro.komaru.tridot.util.*;

import java.util.*;

public class WickedCrystal extends AbstractBoss{
    public final ServerBossBarEvent bossEvent = (ServerBossBarEvent)(new ServerBossBarEvent(this.getName(), "Wicked Crystal")).setDarkenScreen(true);
    private int spawnTime = 0;
    public AnimationState spawnAnimationState = new AnimationState();
    public AnimationState deathAnimationState = new AnimationState();
    public int deathTime = 0;
    public int phase = 1;

    public WickedCrystal(EntityType<? extends PathfinderMob> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        this.xpReward = 200;
    }

    @Override
    public void tick(){
        super.tick();
        checkPhaseTransition();
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
            this.remove(Entity.RemovalReason.KILLED);
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
        if(this.getHealth() <= (this.getMaxHealth() / 2) && phase == 1){
            if(!level().isClientSide()){
                this.phase = 2;
                ((ServerLevel)this.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, BlockRegistry.wickedAmethystBlock.get().defaultBlockState()), this.getX(), this.getY() + 5d, this.getZ(), 25, 0, 0, 0, 0);
                this.playSound(SoundsRegistry.WICKED_CRYSTAL_TRANSFORM.get(), 1.0F, 1.0F);
            }
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
        if(source.getEntity() != null && source.getEntity().getY() > this.getY() + 6) return false;

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

        this.goalSelector.addGoal(1, new PullTowardsGoal());
        this.goalSelector.addGoal(1, new SummonShieldsGoal());
        this.goalSelector.addGoal(1, new SummonSpellGoal());
        this.goalSelector.addGoal(1, new RadialAttack());
        this.goalSelector.addGoal(1, new CrystalStorm());
    }

    public class PullTowardsGoal extends AttackGoal{
        Vec3 lastPos;
        int idleTicks = 0;

        @Override
        public void onPrepare(){
            if(WickedCrystal.this.getTarget() instanceof Player player){
                lastPos = player.position();
            }
        }

        @Override
        public void tick(){
            super.tick();
            if(WickedCrystal.this.getTarget() instanceof Player player){
                Vec3 currentPos = player.position();
                if (currentPos.distanceToSqr(lastPos) < 0.01) {
                    idleTicks++;
                } else {
                    idleTicks = 0;
                    lastPos = currentPos;
                }

                if (idleTicks > 40) {
                    pullTowardsBoss(player);
                }
            }
        }

        public void pullTowardsBoss(Player player) {
            Vec3 dir = WickedCrystal.this.position().subtract(player.position()).normalize().scale(2);
            player.setDeltaMovement(dir);
            player.jumpFromGround();
            player.hurtMarked = true;
            Utils.Particles.line(WickedCrystal.this.level(), WickedCrystal.this.position(), player.position(), ParticleTypes.ANGRY_VILLAGER);
        }

        @Override
        protected void performAttack(){
            WickedCrystal.this.playSound(SoundEvents.EVOKER_PREPARE_ATTACK);
        }

        @Override
        public int getPreparingTime(){
            return 40;
        }

        @Override
        public int getAttackInterval(){
            return 125;
        }

        @Override
        public @Nullable SoundEvent getPrepareSound(){
            return SoundEvents.EMPTY;
        }

        @Override
        public AttackRegistry getAttack(){
            return EntityStatsRegistry.MAGIC;
        }
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
            return CommonConfig.SUMMON_WICKED_CRYSTAL_CASTING_TIME.get();
        }

        @Override
        public int getAttackInterval(){
            return CommonConfig.SUMMON_WICKED_CRYSTAL_CASTING_INTERVAL.get();
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
                crystal.setLimitedLife(375);
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
            return super.canUse() && WickedCrystal.this.distanceToSqr(WickedCrystal.this.getTarget()) < 6.0D;
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

            for(int k = 0; k < 12; ++k){
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
                    PacketHandler.sendToTracking(server, blockpos, new BeastAttackParticlePacket(pX, (double)blockpos.getY() + d0, pZ, Pal.verySoftPink.toJava()));
                    server.addFreshEntity(new CrystalSpikes(server, pX, (double)blockpos.getY() + d0, pZ, pYRot, pWarmupDelay, CommonConfig.RADIAL_WICKED_CRYSTAL_DAMAGE.get(),null));
                }
            }
        }

        @Override
        protected void performAttack(){
            performSpellCasting(WickedCrystal.this.level(), WickedCrystal.this);
        }

        @Override
        public int getPreparingTime(){
            return CommonConfig.RADIAL_WICKED_CRYSTAL_CASTING_TIME.get();
        }

        @Override
        public int getAttackInterval(){
            return CommonConfig.RADIAL_WICKED_CRYSTAL_CASTING_INTERVAL.get();
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

    public class CrystalStorm extends AttackGoal {

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            return super.canUse() && WickedCrystal.this.phase > 1;
        }

        private void summonRainCrystal(ServerLevel serverLevel, BlockPos center) {
            CrystalShard crystalShard = EntityTypeRegistry.CRYSTAL_SHARD.get().create(WickedCrystal.this.level());
            if (crystalShard != null) {
                double spread = 6.0;
                double offsetX = (random.nextDouble() * spread * 2) - spread;
                double offsetZ = (random.nextDouble() * spread * 2) - spread;
                crystalShard.moveTo(center.getX() + 0.5 + offsetX, center.getY() + 1, center.getZ() + 0.5 + offsetZ, 0.0F, 0.0F);
                crystalShard.setOwner(WickedCrystal.this);
                crystalShard.setDeltaMovement(0, 1.0 + random.nextDouble() * 0.5, 0);
                serverLevel.addFreshEntity(crystalShard);
            }
        }

        private void summonStormCrystal(ServerLevel serverLevel, BlockPos spawnPos, float angle, double speed) {
            CrystalShard crystalShard = EntityTypeRegistry.CRYSTAL_SHARD.get().create(WickedCrystal.this.level());
            if (crystalShard != null) {
                crystalShard.moveTo(spawnPos.getX() + 0.5, spawnPos.getY() + 2, spawnPos.getZ() + 0.5, 0.0F, 0.0F);
                crystalShard.setOwner(WickedCrystal.this);
                double vx = Math.cos(angle) * speed;
                double vz = Math.sin(angle) * speed;
                crystalShard.setDeltaMovement(vx, 0.3, vz);
                serverLevel.addFreshEntity(crystalShard);
            }
        }

        @Override
        protected void performAttack() {
            if (level().isClientSide()) return;
            ServerLevel serv = (ServerLevel) level();
            BlockPos center = WickedCrystal.this.blockPosition();
            for(int i = 0; i < 12; i++){
                float angle = (float)((2 * Math.PI / 12) * i);
                summonStormCrystal(serv, center, angle, 0.25);
            }

            if(Tmp.rnd.fiftyFifty()){
                for (int i = 0; i < 6; i++) {
                    float angle = (float) ((2 * Math.PI / 6) * i);
                    summonStormCrystal(serv, center.above(2), angle, 0.35 + random.nextDouble() * 0.3);
                }
            } else{
                for(int i = 0; i < 45; i++){
                    summonRainCrystal(serv, center);
                }
            }
        }

        @Override
        public void onPrepare(){
        }

        public boolean requiresUpdateEveryTick(){
            return true;
        }

        @Override
        public int getPreparingTime(){
            return CommonConfig.CRYSTAL_STORM_WICKED_CRYSTAL_CASTING_TIME.get();
        }

        @Override
        public int getAttackInterval(){
            return CommonConfig.CRYSTAL_STORM_WICKED_CRYSTAL_CASTING_INTERVAL.get();
        }

        @Override
        public @Nullable SoundEvent getPrepareSound(){
            return SoundsRegistry.CRYSTAL_FALL.get();
        }

        @Override
        public AttackRegistry getAttack(){
            return EntityStatsRegistry.THROW;
        }
    }

    public class SummonShieldsGoal extends AttackGoal {
        private final TargetingConditions shieldCount = TargetingConditions.forNonCombat().range(8).ignoreLineOfSight().ignoreInvisibilityTesting();

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            int i = WickedCrystal.this.level().getNearbyEntities(WickedShield.class, this.shieldCount, WickedCrystal.this, WickedCrystal.this.getBoundingBox().inflate(16.0D)).size();
            return super.canUse() && i < CommonConfig.SHIELDS_WICKED_CRYSTAL_LIMIT.get();
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
                int phase1 = CommonConfig.SHIELDS_WICKED_CRYSTAL_COUNT_PHASE1.get();
                int phase2 = CommonConfig.SHIELDS_WICKED_CRYSTAL_COUNT_PHASE2.get();
                for (int i = 0; i < (WickedCrystal.this.phase == 1 ? phase1 : phase2); i++) {
                    float initialAngle = (float)((2 * Math.PI / (WickedCrystal.this.phase == 1 ? phase1 : phase2)) * i);
                    summonShield(serv, WickedCrystal.this.blockPosition().above(), initialAngle);
                }
            }
        }

        @Override
        public int getPreparingTime(){
            return CommonConfig.SHIELDS_WICKED_CRYSTAL_CASTING_TIME.get();
        }

        @Override
        public int getAttackInterval(){
            return CommonConfig.SHIELDS_WICKED_CRYSTAL_CASTING_INTERVAL.get();
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
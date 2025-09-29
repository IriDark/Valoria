package com.idark.valoria.registries.entity.living.boss;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.ai.movements.*;
import com.idark.valoria.registries.entity.living.*;
import com.idark.valoria.registries.entity.living.minions.*;
import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.targeting.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.api.render.bossbars.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import javax.annotation.Nullable;
import java.lang.Math;
import java.util.*;

public class NecromancerEntity extends AbstractNecromancer implements BossEntity{
    public final List<UUID> nearbyPlayers = new ArrayList<>();
    public final Map<UUID, Float> damageMap = new HashMap<>();
    public ArcRandom arcRandom = Tmp.rnd;
    public SkeletonMovement movement = new SkeletonMovement(this);
    public final ServerBossBar bossEvent = new ServerBossBar(this.getDisplayName(), Valoria.loc("basic")).setTexture(Valoria.loc("textures/gui/bossbars/necromancer.png")).setBossMusic(SoundsRegistry.MUSIC_NECROMANCER.get()).setDarkenScreen(true);
    private int spawnTime = 0;

    @Override
    public void tick(){
        super.tick();
        movement.setupMovement();
        if(this.spawnTime < 10){
            this.spawnTime++;
        }
    }

    public float getSpawnProgress(float partialTicks){
        return Math.min(1.0f, (this.spawnTime + partialTicks) / 10f);
    }

    @Nullable
    private Skeleton wololoTarget;

    public NecromancerEntity(EntityType<? extends NecromancerEntity> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        this.xpReward = 100;
    }

    public void readAdditionalSaveData(CompoundTag pCompound){
        super.readAdditionalSaveData(pCompound);
        readBossData(pCompound);
        if(this.hasCustomName()){
            this.bossEvent.setName(this.getDisplayName());
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound){
        super.addAdditionalSaveData(pCompound);
        saveBossData(pCompound);
    }

    public void setCustomName(@Nullable Component pName){
        super.setCustomName(pName);
        this.bossEvent.setName(this.getDisplayName());
    }

    public void startSeenByPlayer(ServerPlayer pPlayer){
        super.startSeenByPlayer(pPlayer);
        this.bossEvent.addPlayer(pPlayer);
        PacketHandler.sendTo(pPlayer, new MusicToastPacket(pPlayer, SoundsRegistry.MUSIC_NECROMANCER.get()));
    }

    public void stopSeenByPlayer(ServerPlayer pPlayer){
        super.stopSeenByPlayer(pPlayer);
        this.bossEvent.removePlayer(pPlayer);
    }

    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel){
        return 0.0F;
    }

    @Override
    public void onAddedToWorld(){
        super.onAddedToWorld();
        CompoundTag data = this.getPersistentData();
        if(!data.getBoolean("NearbyPlayerHealthBonus")){
            initializeNearbyPlayers(this.level(), this);
            applyBonusHealth(this);
        }
    }

    protected void customServerAiStep(){
        super.customServerAiStep();
        this.bossEvent.setHealth(this.getHealth(), this.getMaxHealth());
    }

    @Override
    public boolean hurt(DamageSource source, float amount){
        if(source.getDirectEntity() instanceof Player player){
            UUID playerUUID = player.getUUID();
            getDamageMap().put(playerUUID, getDamageMap().getOrDefault(playerUUID, 0.0f) + amount);
        }

        return super.hurt(source, amount);
    }

    @Nullable
    public ItemEntity spawnAtLocation(ItemStack stack, float offsetY){
        if(stack.isEmpty() || this.level().isClientSide) return null;
        initializeLoot(this.level(), stack, this.getOnPos().above(), offsetY);
        return null;
    }

    protected void registerGoals(){
        //attack
        this.goalSelector.addGoal(0, new NecromancerEntity.AttackSpellGoal());
        this.goalSelector.addGoal(1, new NecromancerEntity.SummonMobsSpellGoal());
        this.goalSelector.addGoal(1, new NecromancerEntity.KnockbackEntitiesGoal(true, new MobEffectInstance(MobEffects.WEAKNESS, 120, 0), new MobEffectInstance(MobEffects.BLINDNESS, 45, 0)));
        this.goalSelector.addGoal(1, new NecromancerEntity.KnockbackEntitiesGoal(false, new MobEffectInstance(MobEffects.WEAKNESS, 120, 0), new MobEffectInstance(MobEffects.BLINDNESS, 45, 0)));
        this.goalSelector.addGoal(2, new NecromancerEntity.KnockbackEntitiesGoal(false));
        // misc
        this.goalSelector.addGoal(1, new NecromancerEntity.HealSelfSpellGoal());
        this.goalSelector.addGoal(2, new NecromancerEntity.HealTargetSpellGoal());
        this.goalSelector.addGoal(2, new NecromancerEntity.ApplyEffectSpellGoal(new MobEffectInstance(EffectsRegistry.STUN.get(), 60, 0)));
        this.goalSelector.addGoal(2, new NecromancerEntity.ApplyEffectSpellGoal(new MobEffectInstance(MobEffects.WEAKNESS, 145, 0)));
        this.goalSelector.addGoal(2, new NecromancerEntity.ApplyEffectSpellGoal(new MobEffectInstance(MobEffects.WEAKNESS, 165, 1)));
        this.goalSelector.addGoal(3, new NecromancerEntity.WololoSpellGoal());

        // ai
        this.goalSelector.addGoal(0, new NecromancerEntity.CastingSpellGoal());
        this.goalSelector.addGoal(1, new RestrictSunGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Wolf.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    public @NotNull MobType getMobType(){
        return MobType.UNDEAD;
    }

    public boolean isAlliedTo(Entity pEntity){
        if(super.isAlliedTo(pEntity)){
            return true;
        }else if(pEntity instanceof LivingEntity && ((LivingEntity)pEntity).getMobType() == MobType.UNDEAD){
            return this.getTeam() == null && pEntity.getTeam() == null;
        }else{
            return false;
        }
    }

    public void rideTick(){
        super.rideTick();
        Entity entity = this.getControlledVehicle();
        if(entity instanceof PathfinderMob pathfindermob){
            this.yBodyRot = pathfindermob.yBodyRot;
        }
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag){
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        RandomSource randomsource = pLevel.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, pDifficulty);
        this.populateDefaultEquipmentEnchantments(randomsource, pDifficulty);
        this.setCanPickUpLoot(randomsource.nextFloat() < 0.55F * pDifficulty.getSpecialMultiplier());
        return pSpawnData;
    }

    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack){
        super.setItemSlot(pSlot, pStack);
    }

    protected void playStepSound(BlockPos pPos, BlockState pBlock){
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound(){
        return SoundEvents.SKELETON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource){
        return SoundEvents.SKELETON_HURT;
    }

    protected SoundEvent getDeathSound(){
        return SoundEvents.SKELETON_DEATH;
    }

    protected SoundEvent getStepSound(){
        return SoundEvents.SKELETON_STEP;
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize){
        return 1.74F;
    }

    public double getMyRidingOffset(){
        return -0.6;
    }

    void setWololoTarget(@Nullable Skeleton pWololoTarget){
        this.wololoTarget = pWololoTarget;
    }

    @Nullable
    Skeleton getWololoTarget(){
        return this.wololoTarget;
    }

    @Override
    public List<UUID> getNearbyPlayers(){
        return nearbyPlayers;
    }

    @Override
    public Map<UUID, Float> getDamageMap(){
        return damageMap;
    }

    class AttackSpellGoal extends AbstractNecromancer.SpellcasterUseSpellGoal{
        public int getCastingTime(){
            return CommonConfig.ATTACK_NECROMANCER_CASTING_TIME.get();
        }

        public int getCastingInterval(){
            return CommonConfig.ATTACK_NECROMANCER_CASTING_INTERVAL.get();
        }

        protected void performSpellCasting(){
            LivingEntity livingentity = NecromancerEntity.this.getTarget();
            if(livingentity != null){
                double d0 = Math.min(livingentity.getY(), NecromancerEntity.this.getY());
                double d1 = Math.max(livingentity.getY(), NecromancerEntity.this.getY()) + 1.0D;
                float f = (float)Mth.atan2(livingentity.getZ() - NecromancerEntity.this.getZ(), livingentity.getX() - NecromancerEntity.this.getX());
                if(NecromancerEntity.this.distanceToSqr(livingentity) < 9.0D){
                    for(int i = 0; i < 5; ++i){
                        float f1 = f + (float)i * (float)Math.PI * 0.4F;
                        this.createSpellEntity(NecromancerEntity.this.getX() + (double)Mth.cos(f1) * 1.5D, NecromancerEntity.this.getZ() + (double)Mth.sin(f1) * 1.5D, d0, d1, f1, 0);
                    }

                    for(int k = 0; k < 8; ++k){
                        float f2 = f + (float)k * (float)Math.PI * 2.0F / 8.0F + 1.2566371F;
                        this.createSpellEntity(NecromancerEntity.this.getX() + (double)Mth.cos(f2) * 2.5D, NecromancerEntity.this.getZ() + (double)Mth.sin(f2) * 2.5D, d0, d1, f2, 3);
                    }
                }else{
                    for(int l = 0; l < 16; ++l){
                        double d2 = 1.25D * (double)(l + 1);
                        this.createSpellEntity(NecromancerEntity.this.getX() + (double)Mth.cos(f) * d2, NecromancerEntity.this.getZ() + (double)Mth.sin(f) * d2, d0, d1, f, l);
                    }
                }
            }
        }

        private void createSpellEntity(double pX, double pZ, double pMinY, double pMaxY, float pYRot, int pWarmupDelay){
            BlockPos blockpos = BlockPos.containing(pX, pMaxY, pZ);
            boolean flag = false;
            double d0 = 0.0D;
            do{
                BlockPos blockpos1 = blockpos.below();
                BlockState blockstate = NecromancerEntity.this.level().getBlockState(blockpos1);
                if(blockstate.isFaceSturdy(NecromancerEntity.this.level(), blockpos1, Direction.UP)){
                    if(!NecromancerEntity.this.level().isEmptyBlock(blockpos)){
                        BlockState blockstate1 = NecromancerEntity.this.level().getBlockState(blockpos);
                        VoxelShape voxelshape = blockstate1.getCollisionShape(NecromancerEntity.this.level(), blockpos);
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
                NecromancerEntity.this.level().addFreshEntity(new Devourer(NecromancerEntity.this.level(), pX, (double)blockpos.getY() + d0, pZ, pYRot, pWarmupDelay, CommonConfig.ATTACK_NECROMANCER_DAMAGE.get(), NecromancerEntity.this));
            }
        }

        public SoundEvent getSpellPrepareSound(){
            return SoundEvents.EVOKER_PREPARE_ATTACK;
        }

        public NecromancerSpells getSpell(){
            return NecromancerSpells.FANGS;
        }
    }

    class CastingSpellGoal extends AbstractNecromancer.SpellcasterCastingSpellGoal{

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick(){
            if(NecromancerEntity.this.hasTarget()){
                NecromancerEntity.this.getLookControl().setLookAt(NecromancerEntity.this.getTarget(), (float)NecromancerEntity.this.getMaxHeadYRot(), (float)NecromancerEntity.this.getMaxHeadXRot());
            }else if(NecromancerEntity.this.getWololoTarget() != null){
                NecromancerEntity.this.getLookControl().setLookAt(NecromancerEntity.this.getWololoTarget(), (float)NecromancerEntity.this.getMaxHeadYRot(), (float)NecromancerEntity.this.getMaxHeadXRot());
            }
        }
    }

    class SummonMobsSpellGoal extends AbstractNecromancer.SpellcasterUseSpellGoal{
        private final TargetingConditions vexCountTargeting = TargetingConditions.forNonCombat().range(16.0D).ignoreLineOfSight().ignoreInvisibilityTesting();

        public void start(){
            NecromancerEntity.this.setIsCastingSpell(this.getSpell());
            this.attackWarmupDelay = this.adjustedTickDelay(this.getCastingTime());
            NecromancerEntity.this.spellCastingTickCount = this.getCastingTime();
            this.nextAttackTickCount = NecromancerEntity.this.tickCount + this.getCastingInterval();
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            int i = NecromancerEntity.this.level().getNearbyEntities(Zombie.class, this.vexCountTargeting, NecromancerEntity.this, NecromancerEntity.this.getBoundingBox().inflate(16.0D)).size();
            return super.canUse() && NecromancerEntity.this.random.nextInt(8) + 1 > i;
        }

        public int getCastingTime(){
            return CommonConfig.SUMMON_NECROMANCER_CASTING_TIME.get();
        }

        public int getCastingInterval(){
            return CommonConfig.SUMMON_NECROMANCER_CASTING_INTERVAL.get();
        }

        @Override
        public @Nullable SoundEvent getSpellPrepareSound(){
            return null;
        }

        private void spawnZombie(ServerLevel serverLevel, BlockPos blockpos){
            Zombie zombie = EntityType.ZOMBIE.create(NecromancerEntity.this.level());
            if(zombie != null && serverLevel.isEmptyBlock(blockpos) && serverLevel.isEmptyBlock(blockpos.above())){
                zombie.moveTo(blockpos, 0.0F, 0.0F);
                zombie.finalizeSpawn(serverLevel, NecromancerEntity.this.level().getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);
                zombie.setHealth(zombie.getMaxHealth() / 2);
                zombie.setTarget(NecromancerEntity.this.getTarget());
                serverLevel.addFreshEntityWithPassengers(zombie);
            }else{
                spawnUndead(serverLevel, blockpos.above());
            }
        }

        private void spawnSkeletons(ServerLevel serverLevel, BlockPos blockpos){
            Skeleton skeleton = EntityType.SKELETON.create(NecromancerEntity.this.level());
            if(skeleton != null && serverLevel.isEmptyBlock(blockpos) && serverLevel.isEmptyBlock(blockpos.above())){
                skeleton.moveTo(blockpos, 0.0F, 0.0F);
                skeleton.finalizeSpawn(serverLevel, NecromancerEntity.this.level().getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);
                skeleton.setHealth(skeleton.getMaxHealth() / 2);
                skeleton.setTarget(NecromancerEntity.this.getTarget());
                serverLevel.addFreshEntityWithPassengers(skeleton);
            }else{
                spawnUndead(serverLevel, blockpos.above());
            }
        }

        private void spawnSorcerers(ServerLevel serverLevel, BlockPos blockpos){
            SorcererEntity sorcerer = EntityTypeRegistry.SORCERER.get().create(NecromancerEntity.this.level());
            if(sorcerer != null && serverLevel.isEmptyBlock(blockpos) && serverLevel.isEmptyBlock(blockpos.above())){
                sorcerer.moveTo(blockpos, 0.0F, 0.0F);
                sorcerer.finalizeSpawn(serverLevel, NecromancerEntity.this.level().getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);
                serverLevel.addFreshEntityWithPassengers(sorcerer);
            }else{
                spawnUndead(serverLevel, blockpos.above());
            }
        }

        private void spawnUndead(ServerLevel serverLevel, BlockPos blockpos){
            UndeadEntity undead = EntityTypeRegistry.UNDEAD.get().create(NecromancerEntity.this.level());
            if(undead != null && serverLevel.isEmptyBlock(blockpos)){
                undead.moveTo(blockpos, 0.0F, 0.0F);
                undead.finalizeSpawn(serverLevel, NecromancerEntity.this.level().getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);
                undead.setOwner(NecromancerEntity.this);
                undead.setBoundOrigin(blockpos);
                undead.setLimitedLife(20 + NecromancerEntity.this.random.nextInt(140));
                serverLevel.addFreshEntityWithPassengers(undead);
            }
        }

        public void spawnUndead(ServerLevel serv){
            for(int i = 0; i < 3; ++i){
                BlockPos undeadSpawnPos = NecromancerEntity.this.blockPosition().offset(-2 + NecromancerEntity.this.random.nextInt(5), 1, -2 + NecromancerEntity.this.random.nextInt(5));
                spawnUndead(serv, undeadSpawnPos);
            }
        }

        public void spawnMobs(ServerLevel serv){
            for(int i = 0; i < 3; ++i){
                BlockPos blockpos = NecromancerEntity.this.blockPosition().offset(-2 + NecromancerEntity.this.random.nextInt(5), 0, -2 + NecromancerEntity.this.random.nextInt(5));
                if(arcRandom.fiftyFifty()){
                    spawnSkeletons(serv, blockpos);
                }else{
                    spawnZombie(serv, blockpos);
                }
            }

            if(arcRandom.chance(45)){
                BlockPos blockpos = NecromancerEntity.this.blockPosition().offset(-2 + NecromancerEntity.this.random.nextInt(5), 0, -2 + NecromancerEntity.this.random.nextInt(5));
                spawnSorcerers(serv, blockpos);
            }

            if(arcRandom.chance(5)){
                spawnUndead(serv);
            }

            serv.playSound(null, NecromancerEntity.this.blockPosition(), getSpellPrepareSound(false), SoundSource.HOSTILE);
        }

        protected void performSpellCasting(){
            NecromancerEntity entity = NecromancerEntity.this;
            boolean flag = entity.level().isClientSide || !entity.hasTarget();
            if(flag){
                return;
            }

            if(entity.level() instanceof ServerLevel serv){
                spawnMobs(serv);
                double posX = entity.getOnPos().getCenter().x;
                double posY = entity.getOnPos().above().getCenter().y;
                double posZ = entity.getOnPos().getCenter().z;

                PacketHandler.sendToTracking(serv, entity.getOnPos(), new SmokeParticlePacket(60, posX, posY - 0.5f, posZ, 0.125f, 0, 0.125f, 255, 255, 255));
            }
        }

        public SoundEvent getSpellPrepareSound(boolean isAir){
            return isAir ? SoundsRegistry.NECROMANCER_SUMMON_AIR.get() : SoundsRegistry.NECROMANCER_SUMMON_GROUND.get();
        }

        public NecromancerSpells getSpell(){
            return NecromancerSpells.SUMMON_MOBS;
        }
    }

    class KnockbackEntitiesGoal extends AbstractNecromancer.SpellcasterUseSpellGoal{
        private float range;
        private final TargetingConditions targeting = TargetingConditions.forCombat().range(range).ignoreLineOfSight().ignoreInvisibilityTesting();
        private final boolean strong;
        public final ImmutableList<MobEffectInstance> effects;

        public KnockbackEntitiesGoal(boolean strong, float range, MobEffectInstance... pEffect){
            this.range = range;
            this.effects = ImmutableList.copyOf(pEffect);
            this.strong = strong;
        }

        public KnockbackEntitiesGoal(boolean strong, MobEffectInstance... pEffect){
            this.range = NecromancerEntity.this.getHealth() < 100 ? CommonConfig.KNOCKBACK_NECROMANCER_RADIUS_STRONG.get() : CommonConfig.KNOCKBACK_NECROMANCER_RADIUS.get();
            this.effects = ImmutableList.copyOf(pEffect);
            this.strong = strong;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            List<LivingEntity> entities = NecromancerEntity.this.level().getNearbyEntities(LivingEntity.class, this.targeting, NecromancerEntity.this, NecromancerEntity.this.getBoundingBox().inflate(range));
            return super.canUse() && NecromancerEntity.this.getTarget() != null && !NecromancerEntity.this.isCastingSpell() && !entities.isEmpty();
        }

        public int getCastingTime(){
            return CommonConfig.KNOCKBACK_NECROMANCER_CASTING_TIME.get();
        }

        public int getCastingInterval(){
            return CommonConfig.KNOCKBACK_NECROMANCER_CASTING_INTERVAL.get();
        }

        protected void performSpellCasting(){
            if(NecromancerEntity.this.hasTarget()){
                Vec3 vec3 = new Vec3(NecromancerEntity.this.getX(), NecromancerEntity.this.getY(), NecromancerEntity.this.getZ());
                List<LivingEntity> entities = NecromancerEntity.this.level().getNearbyEntities(LivingEntity.class, this.targeting, NecromancerEntity.this, NecromancerEntity.this.getBoundingBox().inflate(range));
                for(LivingEntity entity : entities){
                    double distance = Math.sqrt(entity.distanceToSqr(vec3)) / range;
                    double dX = entity.getX() - NecromancerEntity.this.getX();
                    double dY = entity.getEyeY() - NecromancerEntity.this.getY();
                    double dZ = entity.getZ() - NecromancerEntity.this.getZ();
                    double sqrt = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
                    if(sqrt != 0.0D){
                        dX /= sqrt;
                        dY /= sqrt;
                        dZ /= sqrt;
                        double seenPercent = Utils.Hit.seenPercent(vec3, entity, 2);
                        double power = (1.0D - distance) * seenPercent;
                        double powerAfterDamp = ProtectionEnchantment.getExplosionKnockbackAfterDampener(entity, power);
                        dX *= powerAfterDamp;
                        dY *= powerAfterDamp;
                        dZ *= powerAfterDamp;
                        Vec3 vec31 = new Vec3(dX * 2, dY * 0.5f, dZ * 2);
                        if(this.strong){
                            NecromancerEntity.this.heal(NecromancerEntity.this.getHealth() * 0.05f);
                            entity.hurt(NecromancerEntity.this.level().damageSources().generic(), entity.getHealth() * 0.5f);
                        }

                        for(MobEffectInstance effectInstance : effects){
                            NecromancerEntity.this.getTarget().addEffect(effectInstance);
                        }

                        entity.hurtMarked = true; //Sync movements
                        entity.setDeltaMovement(entity.getDeltaMovement().add(vec31));
                    }
                }
            }
        }

        public boolean isInterruptable(){
            return false;
        }

        public SoundEvent getSpellPrepareSound(){
            return SoundEvents.EVOKER_PREPARE_SUMMON;
        }

        public NecromancerSpells getSpell(){
            return NecromancerSpells.KNOCKBACK;
        }
    }

    public class HealTargetSpellGoal extends AbstractNecromancer.SpellcasterUseSpellGoal{
        private final TargetingConditions targeting = TargetingConditions.forCombat().range(4.0D);

        public boolean canUse(){
            if(!super.canUse()){
                return false;
            }else{
                List<Monster> targets = NecromancerEntity.this.level().getNearbyEntities(Monster.class, this.targeting, NecromancerEntity.this, NecromancerEntity.this.getBoundingBox().inflate(4.0D));
                return !targets.isEmpty();
            }
        }

        public boolean isInterruptable(){
            return false;
        }

        public int getCastingTime(){
            return CommonConfig.TARGET_HEAL_NECROMANCER_CASTING_TIME.get();
        }

        public int getCastingInterval(){
            return CommonConfig.TARGET_HEAL_NECROMANCER_CASTING_INTERVAL.get();
        }

        protected void performSpellCasting(){
            ServerLevel serverLevel = (ServerLevel)NecromancerEntity.this.level();
            List<Monster> targets = serverLevel.getNearbyEntities(Monster.class, this.targeting, NecromancerEntity.this, NecromancerEntity.this.getBoundingBox().inflate(4.0D));
            List<LivingEntity> toHeal = new ArrayList<>();
            for(Monster target : targets){
                if(!(target instanceof NecromancerEntity) && target.getHealth() < target.getMaxHealth()){
                    Vector3d pos = new Vector3d(NecromancerEntity.this.getX(), NecromancerEntity.this.getY(), NecromancerEntity.this.getZ());
                    Utils.Particles.inRadius(serverLevel, null, ParticleTypes.HAPPY_VILLAGER, pos, 0, NecromancerEntity.this.getRotationVector().y, 4);
                    Utils.Hit.healNearbyMobs(MobCategory.MONSTER, CommonConfig.TARGET_HEAL_NECROMANCER_AMOUNT.get(), serverLevel, NecromancerEntity.this, toHeal, pos, 0, NecromancerEntity.this.getRotationVector().y, 4);
                    serverLevel.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.EVOKER_CAST_SPELL, target.getSoundSource(), 0.42F, 1.23F);
                    break;
                }
            }
        }

        public SoundEvent getSpellPrepareSound(){
            return SoundEvents.EVOKER_PREPARE_SUMMON;
        }

        public NecromancerSpells getSpell(){
            return NecromancerSpells.HEAL;
        }
    }

    public class ApplyEffectSpellGoal extends AbstractNecromancer.SpellcasterUseSpellGoal{

        public final ImmutableList<MobEffectInstance> effects;

        public ApplyEffectSpellGoal(MobEffectInstance... pEffect){
            this.effects = ImmutableList.copyOf(pEffect);
        }

        public int getCastingTime(){
            return CommonConfig.EFFECT_NECROMANCER_CASTING_TIME.get();
        }

        public int getCastingInterval(){
            return CommonConfig.EFFECT_NECROMANCER_CASTING_INTERVAL.get();
        }

        protected void performSpellCasting(){
            if(NecromancerEntity.this.hasTarget()){
                for(MobEffectInstance effectInstance : effects){
                    NecromancerEntity.this.getTarget().addEffect(effectInstance);
                }
            }
        }

        public SoundEvent getSpellPrepareSound(){
            return SoundEvents.EVOKER_PREPARE_SUMMON;
        }

        public NecromancerSpells getSpell(){
            return NecromancerSpells.EFFECT;
        }
    }

    public class HealSelfSpellGoal extends AbstractNecromancer.SpellcasterUseSpellGoal{
        public boolean canUse(){
            if(!super.canUse()){
                return false;
            }else{
                return NecromancerEntity.this.getHealth() < NecromancerEntity.this.getMaxHealth();
            }
        }

        public boolean isInterruptable(){
            return false;
        }

        public int getCastingTime(){
            return CommonConfig.SELF_HEAL_NECROMANCER_CASTING_TIME.get();
        }

        public int getCastingInterval(){
            return CommonConfig.SELF_HEAL_NECROMANCER_CASTING_INTERVAL.get();
        }

        protected void performSpellCasting(){
            ServerLevel serverLevel = (ServerLevel)NecromancerEntity.this.level();
            if(NecromancerEntity.this.getHealth() < NecromancerEntity.this.getMaxHealth()){
                Vector3d pos = new Vector3d(NecromancerEntity.this.getX(), NecromancerEntity.this.getY(), NecromancerEntity.this.getZ());
                Utils.Particles.around(pos, 2, 1, serverLevel, ParticleTypes.HAPPY_VILLAGER);
                NecromancerEntity.this.heal(CommonConfig.SELF_HEAL_NECROMANCER_AMOUNT.get());
                serverLevel.playSound(null, NecromancerEntity.this.getX(), NecromancerEntity.this.getY(), NecromancerEntity.this.getZ(), SoundEvents.EVOKER_CAST_SPELL, NecromancerEntity.this.getSoundSource(), 0.42F, 1.23F);
            }
        }

        public SoundEvent getSpellPrepareSound(){
            return SoundEvents.EVOKER_PREPARE_SUMMON;
        }

        public NecromancerSpells getSpell(){
            return NecromancerSpells.HEAL;
        }
    }

    public class WololoSpellGoal extends AbstractNecromancer.SpellcasterUseSpellGoal{
        private final TargetingConditions wololoTargeting = TargetingConditions.forCombat().range(8.0D);

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            if(NecromancerEntity.this.hasTarget()){
                return false;
            }else if(NecromancerEntity.this.isCastingSpell()){
                return false;
            }else if(NecromancerEntity.this.tickCount < this.nextAttackTickCount){
                return false;
            }else{
                List<Skeleton> list = NecromancerEntity.this.level().getNearbyEntities(Skeleton.class, this.wololoTargeting, NecromancerEntity.this, NecromancerEntity.this.getBoundingBox().inflate(16.0D, 4.0D, 16.0D));
                if(list.isEmpty()){
                    return false;
                }else{
                    NecromancerEntity.this.setWololoTarget(list.get(NecromancerEntity.this.random.nextInt(list.size())));
                    return true;
                }
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse(){
            return NecromancerEntity.this.getWololoTarget() != null && this.attackWarmupDelay > 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop(){
            super.stop();
            NecromancerEntity.this.setWololoTarget(null);
        }

        protected void performSpellCasting(){
            Skeleton target = NecromancerEntity.this.getWololoTarget();
            ServerLevel serverlevel = (ServerLevel)NecromancerEntity.this.level();
            if(target != null && target.isAlive()){
                DraugrEntity mob = EntityTypeRegistry.DRAUGR.get().create(serverlevel);
                if(mob != null){
                    serverlevel.addFreshEntity(mob);
                    BlockPos pos = new BlockPos(target.getBlockX(), target.getBlockY(), target.getBlockZ());
                    PacketHandler.sendToTracking(serverlevel, target.getOnPos(), new CircleShapedParticlePacket(target.getBlockX() + 0.5f, target.getBlockY(), target.getBlockZ() + 0.5f, target.getRotationVector().y, 46, 51, 60));
                    if(!target.getMainHandItem().isEmpty()){
                        mob.setItemInHand(InteractionHand.MAIN_HAND, Items.BOW.getDefaultInstance());
                    }else if(!target.getOffhandItem().isEmpty()){
                        mob.setItemInHand(InteractionHand.OFF_HAND, Items.BOW.getDefaultInstance());
                    }

                    mob.moveTo(pos, target.getYRot(), target.getXRot());
                    mob.getLookControl().setLookAt(target.getLookControl().getWantedX(), target.getLookControl().getWantedY(), target.getLookControl().getWantedZ());
                    mob.setHealth(target.getHealth());
                    target.discard();
                }
            }
        }

        public int getCastingTime(){
            return 60;
        }

        public int getCastingInterval(){
            return 180;
        }

        public SoundEvent getSpellPrepareSound(){
            return SoundEvents.WARDEN_SONIC_CHARGE;
        }

        public NecromancerSpells getSpell(){
            return NecromancerSpells.WOLOLO;
        }
    }
}
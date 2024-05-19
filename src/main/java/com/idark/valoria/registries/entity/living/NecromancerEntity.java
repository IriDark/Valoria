package com.idark.valoria.registries.entity.living;

import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.projectile.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.targeting.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.horse.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.*;
import org.joml.*;

import javax.annotation.Nullable;
import java.time.*;
import java.util.Random;
import java.util.*;
import java.lang.Math;

public class NecromancerEntity extends AbstractNecromancer{

    @Nullable
    private Skeleton wololoTarget;

    @Nullable
    private Horse wololoTargetHorse;

    public NecromancerEntity(EntityType<? extends NecromancerEntity> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    protected void registerGoals(){
        this.goalSelector.addGoal(1, new NecromancerEntity.CastingSpellGoal());
        this.goalSelector.addGoal(1, new NecromancerEntity.HealSelfSpellGoal());
        this.goalSelector.addGoal(2, new NecromancerEntity.HealTargetSpellGoal());
        this.goalSelector.addGoal(3, new NecromancerEntity.AttackSpellGoal());
        this.goalSelector.addGoal(4, new NecromancerEntity.SummonMobsSpellGoal());
        this.goalSelector.addGoal(6, new NecromancerEntity.WololoSpellGoal());
        this.goalSelector.addGoal(11, new NecromancerEntity.WololoHorseSpellGoal());

        this.goalSelector.addGoal(1, new RestrictSunGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 14.0F, 1.15, 1.4));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Wolf.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Monster.createMonsterAttributes()
        .add(Attributes.MOVEMENT_SPEED, 0.25)
        .add(Attributes.MAX_HEALTH, 50.0)
        .add(Attributes.ATTACK_DAMAGE, 2.0)
        .add(Attributes.FOLLOW_RANGE, 18.0)
        .add(Attributes.ARMOR, 2.5)
        .add(Attributes.ARMOR_TOUGHNESS, 1);

    }

    public @NotNull MobType getMobType(){
        return MobType.UNDEAD;
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
        if(this.getItemBySlot(EquipmentSlot.HEAD).isEmpty()){
            LocalDate localdate = LocalDate.now();
            int i = localdate.getDayOfMonth();
            int j = localdate.getMonth().getValue();
            if(j == 10 && i == 31 && randomsource.nextFloat() < 0.25F){
                this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(randomsource.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
                this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0.0F;
            }
        }

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

    void setHorseWololoTarget(@Nullable Horse pWololoTarget){
        this.wololoTargetHorse = pWololoTarget;
    }

    @Nullable
    Skeleton getWololoTarget(){
        return this.wololoTarget;
    }

    @Nullable
    Horse getWololoHorseTarget(){
        return this.wololoTargetHorse;
    }

    class AttackSpellGoal extends AbstractNecromancer.SpellcasterUseSpellGoal{
        public int getCastingTime(){
            return 45;
        }

        public int getCastingInterval(){
            return 160;
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
                NecromancerEntity.this.level().addFreshEntity(new NecromancerFangs(NecromancerEntity.this.level(), pX, (double)blockpos.getY() + d0, pZ, pYRot, pWarmupDelay, NecromancerEntity.this));
            }

        }

        public SoundEvent getSpellPrepareSound(){
            return SoundEvents.EVOKER_PREPARE_ATTACK;
        }

        public AbstractNecromancer.necromancerSpell getSpell(){
            return AbstractNecromancer.necromancerSpell.FANGS;
        }
    }

    class CastingSpellGoal extends AbstractNecromancer.SpellcasterCastingSpellGoal{

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick(){
            if(NecromancerEntity.this.getTarget() != null){
                NecromancerEntity.this.getLookControl().setLookAt(NecromancerEntity.this.getTarget(), (float)NecromancerEntity.this.getMaxHeadYRot(), (float)NecromancerEntity.this.getMaxHeadXRot());
            }else if(NecromancerEntity.this.getWololoTarget() != null){
                NecromancerEntity.this.getLookControl().setLookAt(NecromancerEntity.this.getWololoTarget(), (float)NecromancerEntity.this.getMaxHeadYRot(), (float)NecromancerEntity.this.getMaxHeadXRot());
            }
        }
    }

    class SummonMobsSpellGoal extends AbstractNecromancer.SpellcasterUseSpellGoal{
        private final TargetingConditions vexCountTargeting = TargetingConditions.forNonCombat().range(16.0D).ignoreLineOfSight().ignoreInvisibilityTesting();

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            if(!super.canUse()){
                return false;
            }else{
                int i = NecromancerEntity.this.level().getNearbyEntities(Zombie.class, this.vexCountTargeting, NecromancerEntity.this, NecromancerEntity.this.getBoundingBox().inflate(16.0D)).size();
                return NecromancerEntity.this.random.nextInt(8) + 1 > i;
            }
        }

        public int getCastWarmupTime(){
            return 75;
        }

        public int getCastingTime(){
            return 125;
        }

        public int getCastingInterval(){
            return 320;
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

        private void spawnUndead(ServerLevel serverLevel, BlockPos blockpos){
            UndeadEntity undead = EntityTypeRegistry.UNDEAD.get().create(NecromancerEntity.this.level());
            if(undead != null && serverLevel.isEmptyBlock(blockpos)){
                undead.moveTo(blockpos, 0.0F, 0.0F);
                undead.finalizeSpawn(serverLevel, NecromancerEntity.this.level().getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);
                undead.setOwner(NecromancerEntity.this);
                undead.setBoundOrigin(blockpos);
                undead.setLimitedLife(20 + NecromancerEntity.this.random.nextInt(60));
                serverLevel.addFreshEntityWithPassengers(undead);
            }
        }

        protected void performSpellCasting(){
            ServerLevel serverlevel = (ServerLevel)NecromancerEntity.this.level();
            if(NecromancerEntity.this.getTarget() != null){
                if(!serverlevel.isDay() || serverlevel.isRaining()){
                    for(int i = 0; i < 3; ++i){
                        BlockPos blockpos = NecromancerEntity.this.blockPosition().offset(-2 + NecromancerEntity.this.random.nextInt(5), 0, -2 + NecromancerEntity.this.random.nextInt(5));
                        BlockPos undeadSpawnPos = NecromancerEntity.this.blockPosition().offset(-2 + NecromancerEntity.this.random.nextInt(5), 1, -2 + NecromancerEntity.this.random.nextInt(5));
                        if(RandomUtil.fiftyFifty()){
                            spawnSkeletons(serverlevel, blockpos);
                        }else{
                            spawnZombie(serverlevel, blockpos);
                        }

                        if(RandomUtil.percentChance(5)){
                            spawnUndead(serverlevel, undeadSpawnPos);
                        }
                    }
                }else{
                    for(int i = 0; i < 3; ++i){
                        BlockPos undeadSpawnPos = NecromancerEntity.this.blockPosition().offset(-2 + NecromancerEntity.this.random.nextInt(5), 1, -2 + NecromancerEntity.this.random.nextInt(5));
                        spawnUndead(serverlevel, undeadSpawnPos);
                    }
                }

                PacketHandler.sendToTracking(serverlevel, NecromancerEntity.this.getOnPos(), new NecromancerSummonParticlePacket((float)NecromancerEntity.this.getOnPos().getX(), (float)NecromancerEntity.this.getOnPos().getY() + 1.2f, (float)NecromancerEntity.this.getOnPos().getZ(), (float)((new Random().nextDouble() - 0.5D) / 64), (float)0, (float)((new Random().nextDouble() - 0.5D) / 64), 30, 35, 75));
            }
        }

        public SoundEvent getSpellPrepareSound(){
            return SoundEvents.EVOKER_PREPARE_SUMMON;
        }

        public AbstractNecromancer.necromancerSpell getSpell(){
            return necromancerSpell.SUMMON_MOBS;
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

        public int getCastWarmupTime(){
            return 45;
        }

        public int getCastingTime(){
            return 55;
        }

        public int getCastingInterval(){
            return 320;
        }

        protected void performSpellCasting(){
            ServerLevel serverLevel = (ServerLevel)NecromancerEntity.this.level();
            List<Monster> targets = serverLevel.getNearbyEntities(Monster.class, this.targeting, NecromancerEntity.this, NecromancerEntity.this.getBoundingBox().inflate(4.0D));
            List<LivingEntity> toHeal = new ArrayList<>();
            for(Monster target : targets){
                if(!(target instanceof NecromancerEntity) && target.getHealth() < target.getMaxHealth()){
                    Vector3d pos = new Vector3d(NecromancerEntity.this.getX(), NecromancerEntity.this.getY(), NecromancerEntity.this.getZ());
                    ValoriaUtils.spawnParticlesInRadius(serverLevel, null, ParticleTypes.HAPPY_VILLAGER, pos, 0, NecromancerEntity.this.getRotationVector().y, 4);
                    ValoriaUtils.healNearbyTypedMobs(MobCategory.MONSTER, 5.0F, serverLevel, NecromancerEntity.this, toHeal, pos, 0, NecromancerEntity.this.getRotationVector().y, 4);
                    serverLevel.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.EVOKER_CAST_SPELL, target.getSoundSource(), 0.42F, 1.23F);
                    break;
                }
            }
        }

        public SoundEvent getSpellPrepareSound(){
            return SoundEvents.EVOKER_PREPARE_SUMMON;
        }

        public AbstractNecromancer.necromancerSpell getSpell(){
            return necromancerSpell.HEAL;
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

        public int getCastWarmupTime(){
            return 65;
        }

        public int getCastingTime(){
            return 75;
        }

        public int getCastingInterval(){
            return 400;
        }

        protected void performSpellCasting(){
            ServerLevel serverLevel = (ServerLevel)NecromancerEntity.this.level();
            if(NecromancerEntity.this.getHealth() < NecromancerEntity.this.getMaxHealth()){
                Vector3d pos = new Vector3d(NecromancerEntity.this.getX(), NecromancerEntity.this.getY(), NecromancerEntity.this.getZ());
                ValoriaUtils.spawnParticlesAroundPosition(pos, 2, 1, serverLevel, ParticleTypes.HAPPY_VILLAGER);
                NecromancerEntity.this.heal(25);
                serverLevel.playSound(null, NecromancerEntity.this.getX(), NecromancerEntity.this.getY(), NecromancerEntity.this.getZ(), SoundEvents.EVOKER_CAST_SPELL, NecromancerEntity.this.getSoundSource(), 0.42F, 1.23F);
            }
        }

        public SoundEvent getSpellPrepareSound(){
            return SoundEvents.EVOKER_PREPARE_SUMMON;
        }

        public AbstractNecromancer.necromancerSpell getSpell(){
            return necromancerSpell.HEAL;
        }
    }

    public class WololoSpellGoal extends AbstractNecromancer.SpellcasterUseSpellGoal{
        private final TargetingConditions wololoTargeting = TargetingConditions.forCombat().range(8.0D);

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            if(NecromancerEntity.this.getTarget() != null){
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
                    PacketHandler.sendToTracking(serverlevel, target.getOnPos(), new CircleShapedParticlePacket(target.getBlockX() + 0.5f, target.getBlockY(), target.getBlockZ() + 0.5f, target.getRotationVector().y, 0, 0.2f, 0, 46, 51, 60));
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

        public int getCastWarmupTime(){
            return 40;
        }

        public int getCastingTime(){
            return 60;
        }

        public int getCastingInterval(){
            return 140;
        }

        public SoundEvent getSpellPrepareSound(){
            return SoundEvents.WARDEN_SONIC_CHARGE;
        }

        public AbstractNecromancer.necromancerSpell getSpell(){
            return AbstractNecromancer.necromancerSpell.WOLOLO;
        }
    }

    public class WololoHorseSpellGoal extends AbstractNecromancer.SpellcasterUseSpellGoal{
        private final TargetingConditions wololoTargeting = TargetingConditions.forNonCombat().range(8.0D);

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            if(NecromancerEntity.this.getTarget() != null){
                return false;
            }else if(NecromancerEntity.this.isCastingSpell()){
                return false;
            }else if(NecromancerEntity.this.tickCount < this.nextAttackTickCount){
                return false;
            }else{
                List<Horse> list = NecromancerEntity.this.level().getNearbyEntities(Horse.class, this.wololoTargeting, NecromancerEntity.this, NecromancerEntity.this.getBoundingBox().inflate(16.0D, 4.0D, 16.0D));
                if(list.isEmpty()){
                    return false;
                }else{
                    NecromancerEntity.this.setHorseWololoTarget(list.get(NecromancerEntity.this.random.nextInt(list.size())));
                    return true;
                }
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse(){
            return NecromancerEntity.this.getWololoHorseTarget() != null && this.attackWarmupDelay > 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop(){
            super.stop();
            NecromancerEntity.this.setHorseWololoTarget(null);
        }

        protected void performSpellCasting(){
            Horse target = NecromancerEntity.this.getWololoHorseTarget();
            ServerLevel serverlevel = (ServerLevel)NecromancerEntity.this.level();
            if(target != null && target.isAlive()){
                SkeletonHorse mob = EntityType.SKELETON_HORSE.create(serverlevel);
                if(mob != null){
                    serverlevel.addFreshEntity(mob);
                    BlockPos pos = new BlockPos(target.getBlockX(), target.getBlockY(), target.getBlockZ());
                    mob.setItemInHand(InteractionHand.MAIN_HAND, Items.BOW.getDefaultInstance());
                    mob.moveTo(pos, 0.0F, 0.0F);
                    PacketHandler.sendToTracking(serverlevel, target.getOnPos(), new NecromancerSummonParticlePacket((float)target.getOnPos().getX(), target.getOnPos().getY() + 1.2f, target.getOnPos().getZ(), (float)((new Random().nextDouble() - 0.5D) / 64), (float)0, (float)((new Random().nextDouble() - 0.5D) / 64), 30, 35, 75));
                    target.discard();
                }
            }
        }

        public int getCastWarmupTime(){
            return 40;
        }

        public int getCastingTime(){
            return 60;
        }

        public int getCastingInterval(){
            return 140;
        }

        public SoundEvent getSpellPrepareSound(){
            return SoundEvents.WARDEN_SONIC_CHARGE;
        }

        public AbstractNecromancer.necromancerSpell getSpell(){
            return AbstractNecromancer.necromancerSpell.WOLOLO;
        }
    }
}
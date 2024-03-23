package com.idark.valoria.registries.world.entity.living;

import com.idark.valoria.registries.world.item.ModItems;
import com.idark.valoria.util.math.RandomUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class GoblinEntity extends PathfinderMob implements NeutralMob, Enemy {
    public static List<Item> goblinCanSpawnWith = new ArrayList<>();
    private final SimpleContainer inventory = new SimpleContainer(8);
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(GoblinEntity.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(GoblinEntity.class, EntityDataSerializers.BOOLEAN);
    private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2, false) {
        public void stop() {
            super.stop();
            GoblinEntity.this.setAggressive(false);
        }

        public void start() {
            super.start();
            GoblinEntity.this.setAggressive(true);
        }
    };

    @Nullable
    private UUID persistentAngerTarget;
    private int ticksSinceEaten;
    static final Predicate<ItemEntity> ALLOWED_ITEMS = (p_289438_) -> !p_289438_.hasPickUpDelay() && p_289438_.isAlive() || p_289438_.getItem().isEdible() || p_289438_.getItem() == Items.GOLD_INGOT.getDefaultInstance() || p_289438_.getItem() == Items.GOLD_BLOCK.getDefaultInstance() || p_289438_.getItem() == Items.GOLD_NUGGET.getDefaultInstance() || p_289438_.getItem() == ModItems.SAMURAI_KUNAI.get().getDefaultInstance() || p_289438_.getItem() == ModItems.SAMURAI_POISONED_KUNAI.get().getDefaultInstance() || p_289438_.getItem().getItem() instanceof SwordItem;

    public GoblinEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.setCanPickUpLoot(true);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
    }

    @VisibleForDebug
    public SimpleContainer getInventory() {
        return this.inventory;
    }

    public void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
        this.inventory.removeAllItems().forEach(this::spawnAtLocation);
    }

    public boolean shouldDespawnInPeaceful() {
        return false;
    }

    private boolean canEat(ItemStack pStack) {
        return pStack.getItem().isEdible() && this.getTarget() == null && this.onGround() && !this.isSleeping();
    }

    public boolean wantsToPickUp(ItemStack pStack) {
        Item item = pStack.getItem();
        return item.isEdible() || item == ModItems.SAMURAI_KUNAI.get() || item == ModItems.SAMURAI_POISONED_KUNAI.get() || item == Items.GOLD_INGOT || item == Items.GOLD_BLOCK || item == Items.GOLD_NUGGET || item instanceof SwordItem && this.getInventory().canAddItem(pStack);
    }

    public void aiStep() {
        if (!this.level().isClientSide && this.isAlive() && this.isEffectiveAi()) {
            ++this.ticksSinceEaten;
            ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (this.canEat(itemstack)) {
                if (this.ticksSinceEaten > 600) {
                    ItemStack itemstack1 = itemstack.finishUsingItem(this.level(), this);
                    if (!itemstack1.isEmpty()) {
                        this.setItemSlot(EquipmentSlot.MAINHAND, itemstack1);
                    }

                    this.ticksSinceEaten = 0;
                } else if (this.ticksSinceEaten > 560 && this.random.nextFloat() < 0.1F) {
                    this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
                    this.heal(1.25F);
                    this.level().broadcastEntityEvent(this, (byte) 45);
                }
            }
        }

        if (!this.level().isClientSide) {
            this.updatePersistentAnger((ServerLevel) this.level(), true);
        }

        if (this.getHealth() < 12) {
            this.stopBeingAngry();
        }

        super.aiStep();
    }

    public static boolean isBrightEnoughToSpawn(BlockAndTintGetter pLevel, BlockPos pPos) {
        return pLevel.getRawBrightness(pPos, 0) > 8;
    }

    public static boolean checkGoblinSpawnRules(EntityType<GoblinEntity> pGoblin, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        return pLevel.getBlockState(pPos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && isBrightEnoughToSpawn(pLevel, pPos);
    }

    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
        if (RandomUtil.percentChance(0.3f)) {
            this.setItemSlot(EquipmentSlot.MAINHAND, goblinCanSpawnWith.get(pRandom.nextInt(0, goblinCanSpawnWith.size())).getDefaultInstance());
        }
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        RandomSource randomsource = pLevel.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, pDifficulty);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 45) {
            ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (!itemstack.isEmpty()) {
                for (int i = 0; i < 8; ++i) {
                    Vec3 vec3 = (new Vec3(((double) this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D)).xRot(-this.getXRot() * ((float) Math.PI / 180F)).yRot(-this.getYRot() * ((float) Math.PI / 180F));
                    this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, itemstack), this.getX() + this.getLookAngle().x / 2.0D, this.getY(), this.getZ() + this.getLookAngle().z / 2.0D, vec3.x, vec3.y + 0.05D, vec3.z);
                }
            }
        } else {
            super.handleEntityEvent(pId);
        }
    }

    public boolean canHoldItem(ItemStack pStack) {
        return super.canHoldItem(pStack);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.18D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Animal.class, 10, true, true, (p_28604_) -> p_28604_ instanceof Chicken || p_28604_ instanceof Rabbit || p_28604_ instanceof Pig));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractFish.class, 20, true, true, (p_28600_) -> p_28600_ instanceof AbstractSchoolingFish));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true, true));
        this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, true));
        this.goalSelector.addGoal(3, new GoblinAvoidEntityGoal<>(this, Player.class, 16, 1.6, 1.8));

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.3));
        this.goalSelector.addGoal(0, new GoblinPanicGoal(1.85));
        this.goalSelector.addGoal(0, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.5D, false));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.3));
        this.goalSelector.addGoal(6, new ResetUniversalAngerTargetGoal<>(this, true));
        this.goalSelector.addGoal(3, new CollectBerriesGoal(1.3F, 12, 4));
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Wolf.class, 8, 1.6, 1.4));
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Creeper.class, 12, 1.8, 1.4));
        this.goalSelector.addGoal(0, new SearchForItemsGoal());
        this.goalSelector.addGoal(6, new RemoveDirtGoal(Blocks.FARMLAND, this, 1.4, 10));
        this.goalSelector.addGoal(6, new RemoveCropsGoal(this, 1.4, 10));
        this.goalSelector.addGoal(3, new FollowMobGoal(this, 1.4, 6, 10));
        this.goalSelector.addGoal(0, new SeekShelterGoal(1.8));
    }

    public void reassessWeaponGoal() {
        this.level();
        if (!this.level().isClientSide) {
            this.goalSelector.removeGoal(this.meleeGoal);
        }
    }

    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {
        super.setItemSlot(pSlot, pStack);
        if (!this.level().isClientSide) {
            this.reassessWeaponGoal();
        }

    }

    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int pTime) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, pTime);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@Nullable UUID pTarget) {
        this.persistentAngerTarget = pTarget;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_BABY_ID, false);
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    public void setBaby(boolean Baby) {
        this.getEntityData().set(DATA_BABY_ID, Baby);
    }

    @Override
    public float getScale() {
        return this.isBaby() ? 1f : 1.45F;
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_BABY_ID.equals(pKey)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(pKey);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        this.addPersistentAngerSaveData(pCompound);
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.readPersistentAngerSaveData(this.level(), pCompound);
    }

    public boolean isBaby() {
        return this.getEntityData().get(DATA_BABY_ID);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity entity = source.getEntity();
        if (entity instanceof Player player) {
            if (player.getAttribute(Attributes.ATTACK_DAMAGE).getValue() > 10f) {
                this.goalSelector.addGoal(1, new GoblinPanicGoal(1.5));
                this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 15, 1.2, 1.8));
            } else if (this.getHealth() < 12) {
                this.goalSelector.addGoal(1, new GoblinPanicGoal(1.5));
                this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 15, 1.2, 1.8));
            } else {
                if (!player.getAbilities().instabuild)
                    this.setTarget((Player) entity);
            }
        }

        return super.hurt(source, amount);
    }

    class GoblinAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
        private final GoblinEntity pGoblin;

        public GoblinAvoidEntityGoal(GoblinEntity pGoblin, Class<T> pEntityClassToAvoid, float pMaxDist, double pWalkSpeedModifier, double pSprintSpeedModifier) {
            super(pGoblin, pEntityClassToAvoid, pMaxDist, pWalkSpeedModifier, pSprintSpeedModifier);
            this.pGoblin = pGoblin;
        }

        public boolean canUse() {
            if (super.canUse() && this.toAvoid instanceof Player) {
                return this.avoidStrongPlayer((Player) this.toAvoid);
            } else {
                return false;
            }
        }

        private boolean avoidStrongPlayer(Player player) {
            return player.getAttributeValue(Attributes.ATTACK_DAMAGE) >= this.mob.getAttributeValue(Attributes.ATTACK_DAMAGE);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            GoblinEntity.this.setTarget((LivingEntity) null);
            super.start();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            GoblinEntity.this.setTarget((LivingEntity) null);
            super.tick();
        }
    }

    class GoblinPanicGoal extends PanicGoal {
        public GoblinPanicGoal(double pSpeedModifier) {
            super(GoblinEntity.this, pSpeedModifier);
        }

        protected boolean shouldPanic() {
            return this.mob.isFreezing() || this.mob.isOnFire() || this.mob.getHealth() < 12;
        }
    }

    public class CollectBerriesGoal extends MoveToBlockGoal {
        private static final int WAIT_TICKS = 40;
        protected int ticksWaited;

        public CollectBerriesGoal(double pSpeedModifier, int pSearchRange, int pVerticalSearchRange) {
            super(GoblinEntity.this, pSpeedModifier, pSearchRange, pVerticalSearchRange);
        }

        public double acceptedDistance() {
            return 2.0D;
        }

        public boolean shouldRecalculatePath() {
            return this.tryTicks % 100 == 0;
        }

        protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
            BlockState blockstate = pLevel.getBlockState(pPos);
            return blockstate.is(Blocks.SWEET_BERRY_BUSH) && blockstate.getValue(SweetBerryBushBlock.AGE) >= 2 || CaveVines.hasGlowBerries(blockstate);
        }

        public void tick() {
            if (this.isReachedTarget()) {
                if (this.ticksWaited >= 40) {
                    this.onReachedTarget();
                } else {
                    ++this.ticksWaited;
                }
            }

            super.tick();
        }

        protected void onReachedTarget() {
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(GoblinEntity.this.level(), GoblinEntity.this)) {
                BlockState blockstate = GoblinEntity.this.level().getBlockState(this.blockPos);
                if (blockstate.is(Blocks.SWEET_BERRY_BUSH)) {
                    this.pickSweetBerries(blockstate);
                } else if (CaveVines.hasGlowBerries(blockstate)) {
                    this.pickGlowBerry(blockstate);
                }

            }
        }

        private void pickGlowBerry(BlockState pState) {
            CaveVines.use(GoblinEntity.this, pState, GoblinEntity.this.level(), this.blockPos);
        }

        private void pickSweetBerries(BlockState pState) {
            int i = pState.getValue(SweetBerryBushBlock.AGE);
            pState.setValue(SweetBerryBushBlock.AGE, Integer.valueOf(1));
            int j = 1 + GoblinEntity.this.level().random.nextInt(2) + (i == 3 ? 1 : 0);
            ItemStack itemstack = GoblinEntity.this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (itemstack.isEmpty()) {
                GoblinEntity.this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.SWEET_BERRIES));
                --j;
            }

            if (j > 0) {
                Block.popResource(GoblinEntity.this.level(), this.blockPos, new ItemStack(Items.SWEET_BERRIES, j));
            }

            GoblinEntity.this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);
            GoblinEntity.this.level().setBlock(this.blockPos, pState.setValue(SweetBerryBushBlock.AGE, Integer.valueOf(1)), 2);
        }

        public boolean canUse() {
            return super.canUse();
        }

        public void start() {
            this.ticksWaited = 0;
            super.start();
        }
    }

    class SearchForItemsGoal extends Goal {
        public SearchForItemsGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            List<ItemEntity> list = GoblinEntity.this.level().getEntitiesOfClass(ItemEntity.class, GoblinEntity.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), GoblinEntity.ALLOWED_ITEMS);
            if (!GoblinEntity.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
                return false;
            } else if (GoblinEntity.this.getTarget() == null && GoblinEntity.this.getLastHurtByMob() == null) {
                if (GoblinEntity.this.getRandom().nextInt(reducedTickDelay(2)) != 0) {
                    return false;
                } else {
                    return !list.isEmpty() && GoblinEntity.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
                }
            } else {
                return false;
            }
        }

        public void tick() {
            List<ItemEntity> list = GoblinEntity.this.level().getEntitiesOfClass(ItemEntity.class, GoblinEntity.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), GoblinEntity.ALLOWED_ITEMS);
            ItemStack itemstack = GoblinEntity.this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (itemstack.isEmpty() && !list.isEmpty()) {
                GoblinEntity.this.getNavigation().moveTo(list.get(0), (double) 1.2F);
            }

        }

        public void start() {
            List<ItemEntity> list = GoblinEntity.this.level().getEntitiesOfClass(ItemEntity.class, GoblinEntity.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), GoblinEntity.ALLOWED_ITEMS);
            if (!list.isEmpty()) {
                GoblinEntity.this.getNavigation().moveTo(list.get(0), (double) 1.2F);
            }

        }
    }

    static class RemoveDirtGoal extends MoveToBlockGoal {
        private final Block blockToRemove;
        private final Mob removerMob;
        private int ticksSinceReachedGoal;
        private static final int WAIT_AFTER_BLOCK_FOUND = 20;

        public RemoveDirtGoal(Block pBlockToRemove, PathfinderMob pRemoverMob, double pSpeedModifier, int pSearchRange) {
            super(pRemoverMob, pSpeedModifier, 24, pSearchRange);
            this.blockToRemove = pBlockToRemove;
            this.removerMob = pRemoverMob;
        }

        public boolean canUse() {
            if (!ForgeEventFactory.getMobGriefingEvent(this.removerMob.level(), this.removerMob)) {
                return false;
            } else if (this.nextStartTick > 0) {
                --this.nextStartTick;
                return false;
            } else if (this.findNearestBlock()) {
                this.nextStartTick = reducedTickDelay(20);
                return true;
            } else {
                this.nextStartTick = this.nextStartTick(this.mob);
                return false;
            }
        }

        public void stop() {
            super.stop();
            this.removerMob.fallDistance = 1.0F;
        }

        public void start() {
            super.start();
            this.ticksSinceReachedGoal = 0;
        }

        public void playDestroyProgressSound(LevelAccessor pLevel, BlockPos pPos) {
        }

        public void playBreakSound(Level pLevel, BlockPos pPos) {
        }

        public void tick() {
            super.tick();
            Level level = this.removerMob.level();
            BlockPos blockpos = this.removerMob.blockPosition();
            BlockPos blockpos1 = this.getPosWithBlock(blockpos, level);
            RandomSource randomsource = this.removerMob.getRandom();
            if (this.isReachedTarget() && blockpos1 != null) {
                Vec3 vec31;
                double d3;
                if (this.ticksSinceReachedGoal > 0) {
                    vec31 = this.removerMob.getDeltaMovement();
                    this.removerMob.setDeltaMovement(vec31.x, 0.3, vec31.z);
                    if (!level.isClientSide) {
                        ((ServerLevel) level).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.DIRT)), (double) blockpos1.getX() + 0.5, (double) blockpos1.getY() + 0.7, (double) blockpos1.getZ() + 0.5, 3, ((double) randomsource.nextFloat() - 0.5) * 0.08, ((double) randomsource.nextFloat() - 0.5) * 0.08, ((double) randomsource.nextFloat() - 0.5) * 0.08, 0.15000000596046448);
                    }
                }

                if (this.ticksSinceReachedGoal % 2 == 0) {
                    vec31 = this.removerMob.getDeltaMovement();
                    this.removerMob.setDeltaMovement(vec31.x, -0.3, vec31.z);
                    if (this.ticksSinceReachedGoal % 6 == 0) {
                        this.playDestroyProgressSound(level, this.blockPos);
                    }
                }

                if (this.ticksSinceReachedGoal > 60) {
                    level.setBlockAndUpdate(blockpos1, Blocks.DIRT.defaultBlockState());
                    if (!level.isClientSide) {
                        for (int i = 0; i < 20; ++i) {
                            d3 = randomsource.nextGaussian() * 0.02;
                            double d1 = randomsource.nextGaussian() * 0.02;
                            double d2 = randomsource.nextGaussian() * 0.02;
                            ((ServerLevel) level).sendParticles(ParticleTypes.POOF, (double) blockpos1.getX() + 0.5, (double) blockpos1.getY(), (double) blockpos1.getZ() + 0.5, 1, d3, d1, d2, 0.15000000596046448);
                        }

                        this.playBreakSound(level, blockpos1);
                    }
                }

                ++this.ticksSinceReachedGoal;
            }

        }

        @Nullable
        private BlockPos getPosWithBlock(BlockPos pPos, BlockGetter pLevel) {
            if (pLevel.getBlockState(pPos).is(this.blockToRemove)) {
                return pPos;
            } else {
                BlockPos[] ablockpos = new BlockPos[]{pPos.below(), pPos.west(), pPos.east(), pPos.north(), pPos.south(), pPos.below().below()};
                int var5 = ablockpos.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    BlockPos blockpos = ablockpos[var6];
                    if (pLevel.getBlockState(blockpos).is(this.blockToRemove)) {
                        return blockpos;
                    }
                }

                return null;
            }
        }

        protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
            ChunkAccess chunkaccess = pLevel.getChunk(SectionPos.blockToSectionCoord(pPos.getX()), SectionPos.blockToSectionCoord(pPos.getZ()), ChunkStatus.FULL, false);
            if (chunkaccess == null) {
                return false;
            } else if (!chunkaccess.getBlockState(pPos).canEntityDestroy(pLevel, pPos, this.removerMob)) {
                return false;
            } else {
                return chunkaccess.getBlockState(pPos).is(this.blockToRemove) && chunkaccess.getBlockState(pPos.above()).isAir() && chunkaccess.getBlockState(pPos.above(2)).isAir();
            }
        }
    }

    static class RemoveCropsGoal extends MoveToBlockGoal {
        private final Mob removerMob;
        private int ticksSinceReachedGoal;
        private static final int WAIT_AFTER_BLOCK_FOUND = 20;

        public RemoveCropsGoal(PathfinderMob pRemoverMob, double pSpeedModifier, int pSearchRange) {
            super(pRemoverMob, pSpeedModifier, 24, pSearchRange);
            this.removerMob = pRemoverMob;
        }

        public boolean canUse() {
            if (!ForgeEventFactory.getMobGriefingEvent(this.removerMob.level(), this.removerMob)) {
                return false;
            } else if (this.nextStartTick > 0) {
                --this.nextStartTick;
                return false;
            } else if (this.findNearestBlock()) {
                this.nextStartTick = reducedTickDelay(20);
                return true;
            } else {
                this.nextStartTick = this.nextStartTick(this.mob);
                return false;
            }
        }

        public void stop() {
            super.stop();
            this.removerMob.fallDistance = 1.0F;
        }

        public void start() {
            super.start();
            this.ticksSinceReachedGoal = 0;
        }

        public void playDestroyProgressSound(LevelAccessor pLevel, BlockPos pPos) {
        }

        public void playBreakSound(Level pLevel, BlockPos pPos) {
        }

        public void tick() {
            super.tick();
            Level level = this.removerMob.level();
            BlockPos blockpos = this.removerMob.blockPosition();
            BlockPos blockpos1 = this.getPosWithBlock(blockpos, level);
            RandomSource randomsource = this.removerMob.getRandom();
            if (this.isReachedTarget() && blockpos1 != null) {
                Vec3 vec31;
                double d3;
                if (this.ticksSinceReachedGoal > 0) {
                    vec31 = this.removerMob.getDeltaMovement();
                    this.removerMob.setDeltaMovement(vec31.x, 0.3, vec31.z);
                    if (!level.isClientSide) {
                        ((ServerLevel) level).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.DIRT)), (double) blockpos1.getX() + 0.5, (double) blockpos1.getY() + 0.7, (double) blockpos1.getZ() + 0.5, 3, ((double) randomsource.nextFloat() - 0.5) * 0.08, ((double) randomsource.nextFloat() - 0.5) * 0.08, ((double) randomsource.nextFloat() - 0.5) * 0.08, 0.15000000596046448);
                    }
                }

                if (this.ticksSinceReachedGoal % 2 == 0) {
                    vec31 = this.removerMob.getDeltaMovement();
                    this.removerMob.setDeltaMovement(vec31.x, -0.3, vec31.z);
                    if (this.ticksSinceReachedGoal % 6 == 0) {
                        this.playDestroyProgressSound(level, this.blockPos);
                    }
                }

                if (this.ticksSinceReachedGoal > 60) {
                    level.removeBlock(blockpos1, false);
                    if (!level.isClientSide) {
                        for (int i = 0; i < 20; ++i) {
                            d3 = randomsource.nextGaussian() * 0.02;
                            double d1 = randomsource.nextGaussian() * 0.02;
                            double d2 = randomsource.nextGaussian() * 0.02;
                            ((ServerLevel) level).sendParticles(ParticleTypes.POOF, (double) blockpos1.getX() + 0.5, (double) blockpos1.getY(), (double) blockpos1.getZ() + 0.5, 1, d3, d1, d2, 0.15000000596046448);
                        }

                        this.playBreakSound(level, blockpos1);
                    }
                }

                ++this.ticksSinceReachedGoal;
            }

        }

        @Nullable
        private BlockPos getPosWithBlock(BlockPos pPos, BlockGetter pLevel) {
            if (pLevel.getBlockState(pPos).is(BlockTags.CROPS)) {
                return pPos;
            } else {
                BlockPos[] ablockpos = new BlockPos[]{pPos.below(), pPos.west(), pPos.east(), pPos.north(), pPos.south(), pPos.below().below()};
                int var5 = ablockpos.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    BlockPos blockpos = ablockpos[var6];
                    if (pLevel.getBlockState(blockpos).is(BlockTags.CROPS)) {
                        return blockpos;
                    }
                }

                return null;
            }
        }

        protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
            ChunkAccess chunkaccess = pLevel.getChunk(SectionPos.blockToSectionCoord(pPos.getX()), SectionPos.blockToSectionCoord(pPos.getZ()), ChunkStatus.FULL, false);
            if (chunkaccess == null) {
                return false;
            } else if (!chunkaccess.getBlockState(pPos).canEntityDestroy(pLevel, pPos, this.removerMob)) {
                return false;
            } else {
                return chunkaccess.getBlockState(pPos).is(BlockTags.CROPS) && chunkaccess.getBlockState(pPos.above()).isAir() && chunkaccess.getBlockState(pPos.above(2)).isAir();
            }
        }
    }

    class SeekShelterGoal extends FleeSunGoal {
        private int interval = reducedTickDelay(100);

        public SeekShelterGoal(double pSpeedModifier) {
            super(GoblinEntity.this, pSpeedModifier);
        }

        public boolean canUse() {
            if (this.mob.getTarget() == null) {
                if (GoblinEntity.this.level().isThundering() && GoblinEntity.this.level().canSeeSky(this.mob.blockPosition())) {
                    return this.setWantedPos();
                } else if (this.interval > 0) {
                    --this.interval;
                    return false;
                } else {
                    this.interval = 100;
                    BlockPos blockpos = this.mob.blockPosition();
                    return GoblinEntity.this.level().isDay() && GoblinEntity.this.level().canSeeSky(blockpos) && !((ServerLevel) GoblinEntity.this.level()).isVillage(blockpos) && this.setWantedPos();
                }
            } else {
                return false;
            }
        }

        public void start() {
            super.start();
        }
    }
}
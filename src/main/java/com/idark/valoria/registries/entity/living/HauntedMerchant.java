package com.idark.valoria.registries.entity.living;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.core.trades.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.ai.brains.*;
import com.mojang.serialization.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.gossip.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.ai.navigation.*;
import net.minecraft.world.entity.ai.sensing.*;
import net.minecraft.world.entity.ai.village.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.schedule.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.trading.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.pathfinder.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class HauntedMerchant extends Monster implements NeutralMob, Enemy, InventoryCarrier, ReputationEventHandler, Merchant {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState meleeAttackAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    @Nullable
    public UUID persistentAngerTarget;
    @Nullable
    private Player tradingPlayer;
    private Player lastTradedPlayer;
    public static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(HauntedMerchant.class, EntityDataSerializers.INT);
    public static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    @Nullable
    protected MerchantOffers offers;
    private final SimpleContainer inventory = new SimpleContainer(8);
    private final GossipContainer gossips = new GossipContainer();
    private long lastGossipTime;
    private long lastGossipDecayTime;
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.PATH, MemoryModuleType.DOORS_TO_CLOSE, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ANGRY_AT, MemoryModuleType.NEAREST_HOSTILE, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
    private static final ImmutableList<SensorType<? extends Sensor<? super HauntedMerchant>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY);
    public HauntedMerchant(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 5;
        ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
        this.getNavigation().setCanFloat(true);

        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 12.0D);
    }

    public Brain<HauntedMerchant> getBrain() {
        return (Brain<HauntedMerchant>) super.getBrain();
    }

    protected Brain.Provider<HauntedMerchant> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    protected Brain<?> makeBrain(Dynamic<?> pDynamic) {
        Brain<HauntedMerchant> brain = this.brainProvider().makeBrain(pDynamic);
        this.registerBrainGoals(brain);
        return brain;
    }

    public void refreshBrain(ServerLevel pServerLevel) {
        Brain<HauntedMerchant> brain = this.getBrain();
        brain.stopAll(pServerLevel, this);
        this.brain = brain.copyWithoutBehaviors();
        this.registerBrainGoals(this.getBrain());
    }

    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPackets.sendEntityBrain(this);
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 4) this.meleeAttackAnimationState.start(this.tickCount);
        if (pId == 13) this.addParticlesAroundSelf(ParticleTypes.FLAME);
        if (pId == 14) this.addParticlesAroundSelf(ParticleTypes.HAPPY_VILLAGER);
        super.handleEntityEvent(pId);
    }

    //TODO ranged attacks, attacks, better pathfinding, try to figure out how the new ai system even works...
    private void registerBrainGoals(Brain<HauntedMerchant> pBrain) {
        HauntedMerchantAI.initFightActivity(this, pBrain);
        pBrain.setDefaultActivity(Activity.IDLE);
        pBrain.setActiveActivityIfPossible(Activity.IDLE);
        pBrain.setCoreActivities(ImmutableSet.of(Activity.CORE));

        pBrain.addActivity(Activity.CORE, HauntedMerchantAI.getCorePackage(0.5F));
        pBrain.addActivity(Activity.IDLE, HauntedMerchantAI.getIdlePackage(0.5F));
        pBrain.addActivity(Activity.HIDE, HauntedMerchantAI.getHidePackage(0.5F));
        pBrain.updateActivityFromSchedule(this.level().getDayTime(), this.level().getGameTime());
        pBrain.useDefaultActivity();
    }

    public boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    protected void customServerAiStep() {
        ServerLevel serverlevel = (ServerLevel) this.level();
        serverlevel.getProfiler().push("hauntedMerchantBrain");
        this.getBrain().tick(serverlevel, this);
        this.level().getProfiler().pop();
        HauntedMerchantAI.updateActivity(this);
        if (this.lastTradedPlayer != null) {
            serverlevel.onReputationEvent(ReputationEventType.TRADE, this.lastTradedPlayer, this);
            this.level().broadcastEntityEvent(this, (byte) 14);
            this.lastTradedPlayer = null;
        }

        super.customServerAiStep();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }

        this.maybeDecayGossip();
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 60;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag) {
        RandomSource randomsource = pLevel.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, pDifficulty);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public void setAttackTarget(LivingEntity pAttackTarget) {
        this.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, pAttackTarget);
        this.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        boolean flag = super.hurt(pSource, pAmount);
        if (!this.level().isClientSide && !this.isNoAi()) {
            Entity entity = pSource.getEntity();
            if(entity instanceof LivingEntity living) {
                HauntedMerchantAI.wasHurtBy(this, living);
                if (this.brain.getMemory(MemoryModuleType.ATTACK_TARGET).isEmpty()) {
                    if (!pSource.isIndirect() || this.closerThan(living, 5.0D)) {
                        this.setAttackTarget(living);
                    }
                }
            }
        }

        return flag;
    }

    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        boolean isClientside = this.level().isClientSide;
        if (!itemstack.is(ItemsRegistry.HAUNTED_MERCHANT_SPAWN_EGG.get()) && this.isAlive() && !this.isTrading()) {
            if (!this.getOffers().isEmpty() && !isClientside) {
                this.startTrading(pPlayer);
            }

            return InteractionResult.sidedSuccess(isClientside);
        } else {
            return super.mobInteract(pPlayer, pHand);
        }
    }

    protected void addParticlesAroundSelf(ParticleOptions pParticleOption) {
        for (int i = 0; i < 4; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level().addParticle(pParticleOption, this.getRandomX(1.0D), this.getRandomY() + 1.0D, this.getRandomZ(1.0D), d0, d1, d2);
        }
    }

    public boolean doHurtTarget(Entity pEntity) {
        this.level().broadcastEntityEvent(this, (byte) 4);
        this.playSound(SoundsRegistry.HAUNTED_MERCHANT_MELEE.get(), 1.0F, this.getVoicePitch());
        return super.doHurtTarget(pEntity);
    }

    @Nullable
    public LivingEntity getTarget() {
        return this.brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
    }

    public SoundEvent getNotifyTradeSound() {
        return SoundsRegistry.HAUNTED_MERCHANT_YES.get();
    }

    protected SoundEvent getTradeUpdatedSound(boolean pIsYesSound) {
        return pIsYesSound ? SoundsRegistry.HAUNTED_MERCHANT_YES.get() : SoundsRegistry.HAUNTED_MERCHANT_NO.get();
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return this.isTrading() ? SoundsRegistry.HAUNTED_MERCHANT_YES.get() : SoundsRegistry.HAUNTED_MERCHANT_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundsRegistry.HAUNTED_MERCHANT_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return SoundsRegistry.HAUNTED_MERCHANT_DEATH.get();
    }

    public SimpleContainer getInventory() {
        return this.inventory;
    }

    public SlotAccess getSlot(int pSlot) {
        int i = pSlot - 300;
        return i >= 0 && i < this.inventory.getContainerSize() ? SlotAccess.forContainer(this.inventory, i) : super.getSlot(pSlot);
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
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        this.addPersistentAngerSaveData(pCompound);
        MerchantOffers merchantoffers = this.getOffers();
        if (!merchantoffers.isEmpty()) {
            pCompound.put("Offers", merchantoffers.createTag());
        }

        pCompound.put("Gossips", this.gossips.store(NbtOps.INSTANCE));
        this.writeInventoryToTag(pCompound);
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.readPersistentAngerSaveData(this.level(), pCompound);
        if (pCompound.contains("Offers", 10)) {
            this.offers = new MerchantOffers(pCompound.getCompound("Offers"));
        }

        if (this.level() instanceof ServerLevel level) {
            this.refreshBrain(level);
        }

        ListTag listtag = pCompound.getList("Gossips", 10);
        this.gossips.update(new Dynamic<>(NbtOps.INSTANCE, listtag));
        this.readInventoryFromTag(pCompound);
    }

    public void setTradingPlayer(@Nullable Player pPlayer) {
        boolean flag = this.getTradingPlayer() != null && pPlayer == null;
        this.tradingPlayer = pPlayer;
        if (flag) {
            this.stopTrading();
        }
    }

    public void die(DamageSource pCause) {
        Valoria.LOGGER.info("Merchant: {} died, message: '{}'", this, pCause.getLocalizedDeathMessage(this).getString());
        Entity entity = pCause.getEntity();
        if (entity != null) {
            this.tellWitnessesThatIWasMurdered(entity);
        }

        super.die(pCause);
    }

    private void tellWitnessesThatIWasMurdered(Entity pMurderer) {
        Level $$3 = this.level();
        if ($$3 instanceof ServerLevel serverlevel) {
            Optional<NearestVisibleLivingEntities> optional = this.brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
            optional.ifPresent(nearestVisibleLivingEntities -> nearestVisibleLivingEntities.findAll(ReputationEventHandler.class::isInstance).forEach((p_186297_) -> serverlevel.onReputationEvent(ReputationTypes.MERCHANT_KILLED, pMurderer, (ReputationEventHandler) p_186297_)));
        }
    }

    public void gossip(ServerLevel pServerLevel, HauntedMerchant pTarget, long pGameTime) {
        if ((pGameTime < this.lastGossipTime || pGameTime >= this.lastGossipTime + 1200L) && (pGameTime < pTarget.lastGossipTime || pGameTime >= pTarget.lastGossipTime + 1200L)) {
            this.gossips.transferFrom(pTarget.gossips, this.random, 10);
            this.lastGossipTime = pGameTime;
            pTarget.lastGossipTime = pGameTime;
        }
    }

    private void maybeDecayGossip() {
        long i = this.level().getGameTime();
        if (this.lastGossipDecayTime == 0L) {
            this.lastGossipDecayTime = i;
        } else if (i >= this.lastGossipDecayTime + 24000L) {
            this.gossips.decay();
            this.lastGossipDecayTime = i;
        }
    }

    @Nullable
    public Player getTradingPlayer() {
        return this.tradingPlayer;
    }

    private void startTrading(Player pPlayer) {
        this.updateSpecialPrices(pPlayer);
        this.setTradingPlayer(pPlayer);
        this.openTradingScreen(pPlayer, this.getDisplayName(), 1);
    }

    public int getPlayerReputation(Player pPlayer) {
        return this.gossips.getReputation(pPlayer.getUUID(), (p_186302_) -> true);
    }

    private void updateSpecialPrices(Player pPlayer) {
        int i = this.getPlayerReputation(pPlayer);
        if (i != 0) {
            for (MerchantOffer merchantoffer : this.getOffers()) {
                merchantoffer.addToSpecialPriceDiff(-Mth.floor((float) i * merchantoffer.getPriceMultiplier()));
            }
        }
    }

    public boolean isTrading() {
        return this.tradingPlayer != null;
    }

    public MerchantOffers getOffers() {
        if (this.offers == null) {
            this.offers = new MerchantOffers();
            this.updateTrades();
        }

        return this.offers;
    }

    protected void updateTrades() {
        VillagerTrades.ItemListing[] itemListings = MerchantTrades.HAUNTED_MERCHANT_TRADES.get(1);
        if (itemListings != null) {
            MerchantOffers merchantoffers = this.getOffers();
            this.addOffersFromItemListings(merchantoffers, itemListings, 5);
        }
    }

    public void overrideOffers(@Nullable MerchantOffers pOffers) {
    }

    public void overrideXp(int pXp) {
    }

    protected void stopTrading() {
        this.setTradingPlayer((Player) null);
        this.resetSpecialPrices();
    }

    private void resetSpecialPrices() {
        for (MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.resetSpecialPriceDiff();
        }
    }

    public void notifyTrade(MerchantOffer pOffer) {
        pOffer.increaseUses();
        this.ambientSoundTime = -this.getAmbientSoundInterval();
        this.rewardTradeXp(pOffer);
    }

    protected void rewardTradeXp(MerchantOffer pOffer) {
        if (pOffer.shouldRewardExp()) {
            int i = 3 + this.random.nextInt(4);
            this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY() + 0.5D, this.getZ(), i));
        }
    }

    public void notifyTradeUpdated(ItemStack pStack) {
        if (!this.level().isClientSide && this.ambientSoundTime > -this.getAmbientSoundInterval() + 20) {
            this.ambientSoundTime = -this.getAmbientSoundInterval();
            this.playSound(this.getTradeUpdatedSound(!pStack.isEmpty()), this.getSoundVolume(), this.getVoicePitch());
        }

    }

    @Override
    public int getVillagerXp() {
        return 0;
    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public boolean isClientSide() {
        return this.level().isClientSide;
    }

    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    public void setLastHurtByMob(@Nullable LivingEntity pLivingBase) {
        if (pLivingBase != null && this.level() instanceof ServerLevel) {
            ((ServerLevel) this.level()).onReputationEvent(ReputationTypes.MERCHANT_HURT, pLivingBase, this);
            if (this.isAlive() && pLivingBase instanceof Player) {
                this.level().broadcastEntityEvent(this, (byte) 13);
            }
        }

        super.setLastHurtByMob(pLivingBase);
    }

    public void onReputationEventFrom(ReputationEventType pType, Entity pTarget) {
        if (pType == ReputationTypes.MONSTER_KILLED) {
            this.gossips.add(pTarget.getUUID(), GossipType.MINOR_POSITIVE, 25);
        } else if (pType == ReputationEventType.TRADE) {
            this.gossips.add(pTarget.getUUID(), GossipType.TRADING, 2);
        } else if (pType == ReputationTypes.MERCHANT_HURT) {
            this.gossips.add(pTarget.getUUID(), GossipType.MINOR_NEGATIVE, 25);
        } else if (pType == ReputationTypes.MERCHANT_KILLED) {
            this.gossips.add(pTarget.getUUID(), GossipType.MAJOR_NEGATIVE, 25);
        }

    }

    protected void addOffersFromItemListings(MerchantOffers pGivenMerchantOffers, VillagerTrades.ItemListing[] pNewTrades, int pMaxNumbers) {
        Set<Integer> set = Sets.newHashSet();
        if (pNewTrades.length > pMaxNumbers) {
            while (set.size() < pMaxNumbers) {
                set.add(this.random.nextInt(pNewTrades.length));
            }
        } else {
            for (int i = 0; i < pNewTrades.length; ++i) {
                set.add(i);
            }
        }

        for (Integer integer : set) {
            VillagerTrades.ItemListing villagertrades$itemlisting = pNewTrades[integer];
            MerchantOffer merchantoffer = villagertrades$itemlisting.getOffer(this, this.random);
            if (merchantoffer != null) {
                pGivenMerchantOffers.add(merchantoffer);
            }
        }

    }
}
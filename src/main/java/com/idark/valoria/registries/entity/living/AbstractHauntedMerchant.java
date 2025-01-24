package com.idark.valoria.registries.entity.living;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.idark.valoria.Valoria;
import com.idark.valoria.core.interfaces.ReputationTypes;
import com.idark.valoria.core.trades.MerchantTrades;
import com.idark.valoria.registries.ItemsRegistry;
import com.idark.valoria.registries.SoundsRegistry;
import com.idark.valoria.registries.entity.ai.brains.HauntedMerchantAI;
import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.gossip.GossipContainer;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public abstract class AbstractHauntedMerchant extends Monster implements NeutralMob, InventoryCarrier, ReputationEventHandler, Merchant{
    @Nullable
    public UUID persistentAngerTarget;
    @Nullable
    private Player tradingPlayer;
    private Player lastTradedPlayer;
    public static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(AbstractHauntedMerchant.class, EntityDataSerializers.INT);
    public static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    @Nullable
    protected MerchantOffers offers;
    private final SimpleContainer inventory = new SimpleContainer(8);
    private final GossipContainer gossips = new GossipContainer();
    private long lastGossipTime;
    private long lastGossipDecayTime;
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.PATH, MemoryModuleType.NEAREST_HOSTILE, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
    private static final ImmutableList<SensorType<? extends Sensor<? super AbstractHauntedMerchant>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS);

    protected AbstractHauntedMerchant(EntityType<? extends Monster> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public Brain<AbstractHauntedMerchant> getBrain(){
        return (Brain<AbstractHauntedMerchant>)super.getBrain();
    }

    protected Brain.Provider<AbstractHauntedMerchant> brainProvider(){
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    protected Brain<?> makeBrain(Dynamic<?> pDynamic){
        Brain<AbstractHauntedMerchant> brain = this.brainProvider().makeBrain(pDynamic);
        this.registerBrainGoals(brain);
        return brain;
    }

    private void registerBrainGoals(Brain<AbstractHauntedMerchant> pBrain){
        pBrain.setDefaultActivity(Activity.IDLE);
        pBrain.setActiveActivityIfPossible(Activity.IDLE);
        pBrain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        pBrain.addActivity(Activity.CORE, HauntedMerchantAI.getCorePackage(1.0F));
        if(AbstractHauntedMerchant.this.getTarget() == null){
            pBrain.addActivity(Activity.IDLE, HauntedMerchantAI.getIdlePackage(1.0F));
        }

        pBrain.updateActivityFromSchedule(this.level().getDayTime(), this.level().getGameTime());
        pBrain.useDefaultActivity();
    }

    protected void sendDebugPackets(){
        super.sendDebugPackets();
        DebugPackets.sendEntityBrain(this);
    }

    @Override
    protected void customServerAiStep(){
        ServerLevel serverlevel = (ServerLevel)this.level();
        serverlevel.getProfiler().push("hauntedMerchantBrain");
        this.getBrain().tick(serverlevel, this);
        this.level().getProfiler().pop();
        HauntedMerchantAI.updateActivity(this);
        if(this.lastTradedPlayer != null){
            serverlevel.onReputationEvent(ReputationEventType.TRADE, this.lastTradedPlayer, this);
            this.level().broadcastEntityEvent(this, (byte)14);
            this.lastTradedPlayer = null;
        }

        super.customServerAiStep();
    }

    @Override
    public void tick(){
        super.tick();
        if(this.level().isClientSide()){
            setupAnimationStates();
        }

        this.maybeDecayGossip();
    }

    public void setupAnimationStates(){
    }

    public MobType getMobType(){
        return MobType.UNDEAD;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag){
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public boolean shouldDespawnInPeaceful(){
        return false;
    }

    public SoundEvent getNotifyTradeSound(){
        return SoundsRegistry.HAUNTED_MERCHANT_YES.get();
    }

    protected SoundEvent getTradeUpdatedSound(boolean pIsYesSound){
        return pIsYesSound ? SoundsRegistry.HAUNTED_MERCHANT_YES.get() : SoundsRegistry.HAUNTED_MERCHANT_NO.get();
    }

    @Nullable
    public SoundEvent getAmbientSound(){
        return this.isTrading() ? SoundsRegistry.HAUNTED_MERCHANT_YES.get() : SoundsRegistry.HAUNTED_MERCHANT_IDLE.get();
    }

    public SoundEvent getHurtSound(DamageSource pDamageSource){
        return SoundsRegistry.HAUNTED_MERCHANT_HURT.get();
    }

    public SoundEvent getDeathSound(){
        return SoundsRegistry.HAUNTED_MERCHANT_DEATH.get();
    }

    public SimpleContainer getInventory(){
        return this.inventory;
    }

    public SlotAccess getSlot(int pSlot){
        int i = pSlot - 300;
        return i >= 0 && i < this.inventory.getContainerSize() ? SlotAccess.forContainer(this.inventory, i) : super.getSlot(pSlot);
    }

    public int getRemainingPersistentAngerTime(){
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int pTime){
        this.entityData.set(DATA_REMAINING_ANGER_TIME, pTime);
    }

    public void startPersistentAngerTimer(){
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Nullable
    public UUID getPersistentAngerTarget(){
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@Nullable UUID pTarget){
        this.persistentAngerTarget = pTarget;
    }

    public void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    public void addAdditionalSaveData(CompoundTag pCompound){
        super.addAdditionalSaveData(pCompound);
        this.addPersistentAngerSaveData(pCompound);
        MerchantOffers merchantoffers = this.getOffers();
        if(!merchantoffers.isEmpty()){
            pCompound.put("Offers", merchantoffers.createTag());
        }

        pCompound.put("Gossips", this.gossips.store(NbtOps.INSTANCE));
        this.writeInventoryToTag(pCompound);
    }

    public void readAdditionalSaveData(CompoundTag pCompound){
        super.readAdditionalSaveData(pCompound);
        this.readPersistentAngerSaveData(this.level(), pCompound);
        if(pCompound.contains("Offers", 10)){
            this.offers = new MerchantOffers(pCompound.getCompound("Offers"));
        }

        ListTag listtag = pCompound.getList("Gossips", 10);
        this.gossips.update(new Dynamic<>(NbtOps.INSTANCE, listtag));
        this.readInventoryFromTag(pCompound);
    }

    public void setTradingPlayer(@Nullable Player pPlayer){
        boolean flag = this.getTradingPlayer() != null && pPlayer == null;
        this.tradingPlayer = pPlayer;
        if(flag){
            this.stopTrading();
        }
    }

    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand){
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        boolean isClientside = this.level().isClientSide;
        if(!itemstack.is(ItemsRegistry.pumpkinContract.get()) && this.isAlive() && !this.isTrading()){
            if(!this.getOffers().isEmpty() && !isClientside && this.getTarget() != pPlayer){
                this.startTrading(pPlayer);
                return InteractionResult.sidedSuccess(false);
            }
        }

        return super.mobInteract(pPlayer, pHand);
    }

    public void die(DamageSource pCause){
        Valoria.LOGGER.info("Merchant: {} died, message: '{}'", this, pCause.getLocalizedDeathMessage(this).getString());
        Entity entity = pCause.getEntity();
        if(entity != null){
            this.tellWitnessesThatIWasMurdered(entity);
        }

        super.die(pCause);
    }

    public void tellWitnessesThatIWasMurdered(Entity pMurderer){
        Level $$3 = this.level();
        if($$3 instanceof ServerLevel serverlevel){
            Optional<NearestVisibleLivingEntities> optional = this.brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
            optional.ifPresent(nearestVisibleLivingEntities -> nearestVisibleLivingEntities.findAll(ReputationEventHandler.class::isInstance).forEach((p_186297_) -> serverlevel.onReputationEvent(ReputationTypes.MERCHANT_KILLED, pMurderer, (ReputationEventHandler)p_186297_)));
        }
    }

    public void gossip(ServerLevel pServerLevel, AbstractHauntedMerchant pTarget, long pGameTime){
        if((pGameTime < this.lastGossipTime || pGameTime >= this.lastGossipTime + 1200L) && (pGameTime < pTarget.lastGossipTime || pGameTime >= pTarget.lastGossipTime + 1200L)){
            this.gossips.transferFrom(pTarget.gossips, this.random, 10);
            this.lastGossipTime = pGameTime;
            pTarget.lastGossipTime = pGameTime;
        }
    }

    public void maybeDecayGossip(){
        long i = this.level().getGameTime();
        if(this.lastGossipDecayTime == 0L){
            this.lastGossipDecayTime = i;
        }else if(i >= this.lastGossipDecayTime + 24000L){
            this.gossips.decay();
            this.lastGossipDecayTime = i;
        }
    }

    @Nullable
    public Player getTradingPlayer(){
        return this.tradingPlayer;
    }

    public void startTrading(Player pPlayer){
        this.updateSpecialPrices(pPlayer);
        this.setTradingPlayer(pPlayer);
        this.openTradingScreen(pPlayer, this.getDisplayName(), 1);
    }

    public int getPlayerReputation(Player pPlayer){
        return this.gossips.getReputation(pPlayer.getUUID(), (p_186302_) -> true);
    }

    private void updateSpecialPrices(Player pPlayer){
        int i = this.getPlayerReputation(pPlayer);
        if(i != 0){
            for(MerchantOffer merchantoffer : this.getOffers()){
                merchantoffer.addToSpecialPriceDiff(-Mth.floor((float)i * merchantoffer.getPriceMultiplier()));
            }
        }
    }

    public boolean isTrading(){
        return this.tradingPlayer != null;
    }

    public MerchantOffers getOffers(){
        if(this.offers == null){
            this.offers = new MerchantOffers();
            this.updateTrades();
        }

        return this.offers;
    }

    public void updateTrades(){
        VillagerTrades.ItemListing[] itemListings = MerchantTrades.HAUNTED_MERCHANT_TRADES.get(1);
        if(itemListings != null){
            MerchantOffers merchantoffers = this.getOffers();
            this.addOffersFromItemListings(merchantoffers, itemListings, 5);
        }
    }

    public void overrideOffers(@Nullable MerchantOffers pOffers){
    }

    public void overrideXp(int pXp){
    }

    public void stopTrading(){
        this.setTradingPlayer((Player)null);
        this.resetSpecialPrices();
    }

    public void resetSpecialPrices(){
        for(MerchantOffer merchantoffer : this.getOffers()){
            merchantoffer.resetSpecialPriceDiff();
        }
    }

    public void notifyTrade(MerchantOffer pOffer){
        pOffer.increaseUses();
        this.ambientSoundTime = -this.getAmbientSoundInterval();
        this.rewardTradeXp(pOffer);
    }

    public void rewardTradeXp(MerchantOffer pOffer){
        if(pOffer.shouldRewardExp()){
            int i = 3 + this.random.nextInt(4);
            this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY() + 0.5D, this.getZ(), i));
        }
    }

    public void notifyTradeUpdated(ItemStack pStack){
        if(!this.level().isClientSide && this.ambientSoundTime > -this.getAmbientSoundInterval() + 20){
            this.ambientSoundTime = -this.getAmbientSoundInterval();
            this.playSound(this.getTradeUpdatedSound(!pStack.isEmpty()), this.getSoundVolume(), this.getVoicePitch());
        }

    }

    @Override
    public int getVillagerXp(){
        return 0;
    }

    @Override
    public boolean showProgressBar(){
        return false;
    }

    @Override
    public boolean isClientSide(){
        return this.level().isClientSide;
    }

    public boolean removeWhenFarAway(double pDistanceToClosestPlayer){
        return false;
    }

    public void setLastHurtByMob(@Nullable LivingEntity pLivingBase){
        if(pLivingBase != null && this.level() instanceof ServerLevel){
            ((ServerLevel)this.level()).onReputationEvent(ReputationTypes.MERCHANT_HURT, pLivingBase, this);
            if(this.isAlive() && pLivingBase instanceof Player){
                this.level().broadcastEntityEvent(this, (byte)13);
            }
        }

        super.setLastHurtByMob(pLivingBase);
    }

    public void onReputationEventFrom(ReputationEventType pType, Entity pTarget){
        if(pType == ReputationTypes.MONSTER_KILLED){
            this.gossips.add(pTarget.getUUID(), GossipType.MINOR_POSITIVE, 25);
        }else if(pType == ReputationEventType.TRADE){
            this.gossips.add(pTarget.getUUID(), GossipType.TRADING, 2);
        }else if(pType == ReputationTypes.MERCHANT_HURT){
            this.gossips.add(pTarget.getUUID(), GossipType.MINOR_NEGATIVE, 25);
        }else if(pType == ReputationTypes.MERCHANT_KILLED){
            this.gossips.add(pTarget.getUUID(), GossipType.MAJOR_NEGATIVE, 25);
        }

    }

    public void addOffersFromItemListings(MerchantOffers pGivenMerchantOffers, VillagerTrades.ItemListing[] pNewTrades, int pMaxNumbers){
        Set<Integer> set = Sets.newHashSet();
        if(pNewTrades.length > pMaxNumbers){
            while(set.size() < pMaxNumbers){
                set.add(this.random.nextInt(pNewTrades.length));
            }
        }else{
            for(int i = 0; i < pNewTrades.length; ++i){
                set.add(i);
            }
        }

        for(Integer integer : set){
            VillagerTrades.ItemListing villagertrades$itemlisting = pNewTrades[integer];
            MerchantOffer merchantoffer = villagertrades$itemlisting.getOffer(this, this.random);
            if(merchantoffer != null){
                pGivenMerchantOffers.add(merchantoffer);
            }
        }
    }
}
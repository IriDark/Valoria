package com.idark.valoria.registries.entity.living.boss.firron;

import com.idark.valoria.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.living.boss.*;
import com.idark.valoria.registries.entity.living.boss.dryador.phases.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.api.render.bossbars.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.gfx.particle.options.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.client.render.screenshake.*;
import pro.komaru.tridot.common.registry.entity.system.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.comps.phys.*;
import pro.komaru.tridot.util.math.*;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.core.animatable.instance.*;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.*;
import software.bernie.geckolib.util.*;

import javax.annotation.Nullable;
import java.util.*;

public class Firron extends Monster implements Enemy, BossEntity, Allied, AttackSystemMob, GeoEntity{
    public final ServerBossBar bossEvent = new ServerBossBar(this.getDisplayName(), Valoria.loc("basic")).setTexture(Valoria.loc("textures/gui/bossbars/firron.png")).setDarkenScreen(true);
    public final List<UUID> nearbyPlayers = new ArrayList<>();
    public final Map<UUID, Float> damageMap = new HashMap<>();

    private final AttackSelector selector = new AttackSelector();
    private AttackInstance currentAttack;
    public IBossPhase currentPhase = new BossPhase(this, () -> Firron.this.getHealth() <= Firron.this.getMaxHealth() / 2).setSound(SoundEvents.ANVIL_PLACE);

    public int stunTicks;
    public boolean isStunned;

    private Vec3 rushDirection;
    private int animationTicks = 0;
    private int rushPrepareTicks;
    private int rushTicks;
    private int prepareDuration = 80;
    private int rushDuration = 80;
    private boolean rushing;

    public static final float sweepAttackRange = 1.5f;
    public static final float tripleSweepAttackRange = 3;
    public static final float pierceAttackRange = 4;

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    public static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("WALK");
    public static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("IDLE");
    public static final RawAnimation ATTACK_1 = RawAnimation.begin().thenPlay("ATTACK1");
    public static final RawAnimation ATTACK_2 = RawAnimation.begin().thenPlay("ATTACK2");
    public static final RawAnimation ATTACK_3 = RawAnimation.begin().thenPlay("ATTACK3");
    public static final RawAnimation RUSH_PREPARE = RawAnimation.begin().thenPlay("RUSH_PREPARE").thenLoop("RUSH_RAM");
    public static final RawAnimation RUSH_RAM = RawAnimation.begin().thenLoop("RUSH_RAM");
    public static final RawAnimation RUSH_BASH = RawAnimation.begin().thenPlay("RUSH_BASH");
    public static final RawAnimation RUSH_STUN_BEGIN = RawAnimation.begin().thenPlay("RUSH_STUN_BEGIN").thenLoop("STUN_LOOP");
    public static final RawAnimation STUN_LOOP = RawAnimation.begin().thenPlay("STUN_LOOP");
    public static final RawAnimation STUN_END = RawAnimation.begin().thenPlay("STUN_END");
    public static final RawAnimation SPAWN = RawAnimation.begin().thenPlay("SPAWN");
    public static final RawAnimation SUMMON_START = RawAnimation.begin().thenPlay("SUMMON_START");
    public static final RawAnimation SUMMON_HOLD = RawAnimation.begin().thenPlay("SUMMON_HOLD");
    public static final RawAnimation SUMMON_END = RawAnimation.begin().thenPlay("SUMMON_END");

    public Firron(EntityType<? extends Monster> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);

        this.initAttacks();
    }

    public void checkPhaseTransition() {
        if (currentPhase.shouldTransition() && !currentPhase.playedSound()) {
            animationTicks = 90;
            this.navigation.stop();
            currentPhase.onEnter();
            amplifyStats();
        }

        if(animationTicks > 0) animationTicks--;
    }

    private void amplifyStats(){
        this.getAttribute(Attributes.ARMOR).addTransientModifier(new AttributeModifier("modifier", this.level().getDifficulty().getId() * 0.5f, Operation.MULTIPLY_TOTAL));
        this.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier("modifier", this.level().getDifficulty().getId() * 2f, Operation.ADDITION));
        this.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier("modifier", 0.025f, Operation.MULTIPLY_TOTAL));
    }

    @Override
    public void tick(){
        super.tick();
        checkPhaseTransition();
        if(rushing && rushPrepareTicks <= prepareDuration){
            rushPrepareTicks++;
        }else if(rushDirection != null && !this.level().isClientSide){
            performRush();
        }

        if(rushing) spawnRushParticles();
    }

    private void performRush() {
        rushTicks++;

        //better to end the rush rather than crash the game
        if (hitWall() || rushTicks > rushDuration || rushDirection == null) {
            endRush(!hitWall());
            return;
        }

        Vec3 dir = rushDirection;
        this.setDeltaMovement(dir.scale(0.7f));
        this.hurtMarked = true;
        this.hasImpulse = true;

        AABB hitBox = this.getBoundingBox().inflate(2d);
        List<LivingEntity> hitEntities = this.level().getEntitiesOfClass(LivingEntity.class, hitBox, e -> e != this);
        for (LivingEntity e : hitEntities) {
            if (e.isAlive() && !(e instanceof Allied)) {
                this.swing(InteractionHand.MAIN_HAND);
                this.doHurtTarget(e);
                double d2 = e.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                double d1 = Math.max(0.0D, 1.0D - d2);
                ItemStack useStack = e.getUseItem();
                if(!(useStack.getItem() instanceof ShieldItem shieldItem)){
                    e.addEffect(new MobEffectInstance(EffectsRegistry.STUN.get(), 160, 0));
                    endRush(true);
                } else {
                    useStack.hurtAndBreak(15, e, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                    if(e instanceof Player plr) plr.getCooldowns().addCooldown(shieldItem, 300);
                    endRush(false);
                }

                e.setDeltaMovement(e.getDeltaMovement().add(dir.x, (double)0.5F * d1, dir.z));
                break;
            }
        }
    }

    private void spawnRushParticles(){
        if(this.level().isClientSide()){
            Player player = ClientUtils.getClientPlayer();

            ParticleBuilder.create(getBlockParticleOptions())
            .setRenderType(TridotRenderTypes.TRANSLUCENT_BLOCK_PARTICLE)
            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
            .setScaleData(GenericParticleData.create(0.15f, 0.02f, 0).build())
            .setSpriteData(SpriteParticleData.CRUMBS_RANDOM)
            .setLifetime(30)
            .randomVelocity(0.35, 0.35, 0.35)
            .randomOffset(0.125, 0.125)
            .setGravity(0.75f)
            .repeat(player.level(), this.blockPosition().getCenter(), 12);
        }
    }

    private void spawnCrushParticles(){
        if(level() instanceof ServerLevel level){
            PacketHandler.sendToTracking(level, this.blockPosition(), new CrushParticlePacket(this.blockPosition().below(), this.getBlockX(), this.getBlockY(), this.getBlockZ()));
        }
    }

    private boolean hitWall() {
        if(rushDirection == null) return false;
        return this.horizontalCollision;
    }

    public void startRush(LivingEntity target) {
        this.rushDirection = target.position().subtract(this.position()).normalize();
        this.rushing = true;
        this.rushTicks = 0;
        this.rushPrepareTicks = 0;
        this.level().broadcastEntityEvent(this, (byte)92);
        this.getNavigation().stop();
        this.getNavigation().moveTo(target, 1);
        this.hurtMarked = true;
        this.hasImpulse = true;
    }

    public void stopRush(){
        this.level().broadcastEntityEvent(this, (byte)93);
        this.rushing = false;
        this.rushDirection = null;
        this.rushTicks = 0;
        this.setAggressive(false);
    }

    private void endRush(boolean hitEntity) {
        rushing = false;
        rushPrepareTicks = 0;
        if (hitEntity){
            this.triggerAnim("AttackController", "rush_bash");
        }else{
            this.setStunned(160);
            this.spawnCrushParticles();
            ScreenshakeHandler.add(new PositionedScreenshakeInstance(25, Pos3.init(this.getBlockX(), this.getBlockY(), this.getBlockZ()), 0, 30).intensity(1.25f).interp(Interp.bounce));
            this.playSound(SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR, 0.5f, 0.75f);
            this.triggerAnim("AttackController", "rush_stun_begin");
        }

        this.stopRush();
    }

    @Override
    public boolean isNoAi(){
        return super.isNoAi() || this.isStunned || this.tickCount < 140 || animationTicks > 0;
    }

    @Override
    public void die(DamageSource pDamageSource){
        bossEvent.setAboutToDie(true);
        super.die(pDamageSource);
    }

    @Override
    public int getMaxFallDistance() {
        return 12;
    }

    @Override
    public double getVisibilityPercent(Entity entityIn) {
        return 1.0D;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "SpawnController", 0, state -> {
            if (this.tickCount < 140)
                return state.setAndContinue(SPAWN);

            return PlayState.STOP;
        })
        .setCustomInstructionKeyframeHandler((e) -> {
            String data = e.getKeyframeData().getInstructions();
            if(data.equals("spawned;")){
                Player player = ClientUtils.getClientPlayer();

                ParticleBuilder.create(TridotParticles.WISP)
                .setHasPhysics(false)
                .randomVelocity(1, 0, 1)
                .setLifetime(30)
                .setScaleData(GenericParticleData.create(1.55f, 0).build())
                .setColorData(ColorParticleData.create(Pal.lightishGray, Col.white).build())
                .repeat(player.level(), this.getBlockX(), this.getBlockY(), this.getBlockZ(), 64);

                BlockParticleOptions options = getBlockParticleOptions();
                ParticleBuilder.create(options)
                .setRenderType(TridotRenderTypes.TRANSLUCENT_BLOCK_PARTICLE)
                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.15f).build())
                .setScaleData(GenericParticleData.create(0.65f, 0).build())
                .setSpriteData(SpriteParticleData.CRUMBS_RANDOM)
                .setLifetime(50)
                .randomVelocity(0.345, 0.85f, 0.345)
                .randomOffset(0.045, 0.045)
                .setGravity(0.75f)
                .repeat(player.level(), this.getBlockX(), this.getBlockY(), this.getBlockZ(), 128);

                player.playSound(SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR, 0.5f, 0.75f);
                ScreenshakeHandler.add(new PositionedScreenshakeInstance(10, Pos3.init(this.getBlockX(), this.getBlockY(), this.getBlockZ()), 0, 15).intensity(1.25f).interp(Interp.bounce));
            }
        }));

        controllers.add(new AnimationController<>(this, "AnimationController", 15, this::animationController));
        controllers.add(new AnimationController<>(this, "AttackController", 5, state -> PlayState.STOP)
        .triggerableAnim("triple_sweep", ATTACK_1)
        .triggerableAnim("sweep", ATTACK_2)
        .triggerableAnim("pierce", ATTACK_3)
        .triggerableAnim("rush_prepare", RUSH_PREPARE)
        .triggerableAnim("rush_bash", RUSH_BASH)
        .triggerableAnim("rush_ram", RUSH_RAM)
        .triggerableAnim("rush_stun_begin", RUSH_STUN_BEGIN)

        .setCustomInstructionKeyframeHandler((e) -> {
            String data = e.getKeyframeData().getInstructions();
            Player player = ClientUtils.getClientPlayer();
            if(this.isStunned || rushing || animationTicks > 0 || this.tickCount < 140) return;

            if(data.equals("sweep1;")) {
                PacketHandler.sendToServer(new FirronKeyframePacket(e.getAnimatable().getUUID(), "sweep1"));
                if (player != null)
                    player.playSound(SoundsRegistry.SWIFTSLICE.get(), 1, 1);
            }

            if(data.equals("sweep2;")) {
                PacketHandler.sendToServer(new FirronKeyframePacket(e.getAnimatable().getUUID(), "sweep2"));
                if (player != null)
                    player.playSound(SoundsRegistry.SWIFTSLICE.get(), 1, 1);
            }

            if(data.equals("pierce;")) {
                PacketHandler.sendToServer(new FirronKeyframePacket(e.getAnimatable().getUUID(), "pierce"));
                if (player != null)
                    player.playSound(SoundsRegistry.SWIFTSLICE_LEGACY.get(), 1, 1);
            }
        })
        );

        controllers.add(new AnimationController<>(this, "StatusController", state -> {
            if(isStunned) {
                return state.setAndContinue(STUN_LOOP);
            }

            return PlayState.STOP;
        })
        .triggerableAnim("stun_end", STUN_END).receiveTriggeredAnimations()
        );
    }

    @OnlyIn(Dist.CLIENT)
    private BlockParticleOptions getBlockParticleOptions(){
        return new BlockParticleOptions(TridotParticles.BLOCK.get(), this.level().getBlockState(this.blockPosition().below()));
    }

    public void setStunned(int duration) {
        this.stunTicks = duration;
        this.level().broadcastEntityEvent(this, (byte)90);
        this.addEffect(new MobEffectInstance(EffectsRegistry.STUN.get(), duration, 0));
    }

    @Override
    public boolean isEffectiveAi(){
        return super.isEffectiveAi() || this.tickCount < 140 || animationTicks > 0;
    }

    @Override
    protected boolean isImmobile(){
        return super.isImmobile() || isStunned || this.tickCount < 140 || animationTicks > 0;
    }

    public void handleKeyframe(String keyframe) {
        if ("sweep1".equals(keyframe)) {
            performAttackPhase(1);
        } else if ("sweep2".equals(keyframe)) {
            performAttackPhase(2);
        } else if ("pierce".equals(keyframe)) {
            Vec3 mobPosition = this.position();
            Vec3 target;
            float distance = 2.5f;
            if(this.getTarget() != null){
                target = this.getTarget().position();
            } else {
                target = this.getViewVector(0.0F).scale(distance);
            }

            Vec3 leapVector = target.subtract(mobPosition).normalize().scale(distance);
            this.setDeltaMovement(this.getDeltaMovement().add(leapVector));
            this.hurtMarked = true;
            this.hasImpulse = true;
            performAttackPhase(3);
        }
    }

    private void performAttackPhase(int phase) {
        float range = (phase == 1 || phase == 2) ? sweepAttackRange : tripleSweepAttackRange;
        LivingEntity target = this.getTarget();
        if (target != null && target.isAlive() && isWithinMeleeAttackRange(target, range)) {
            this.swing(InteractionHand.MAIN_HAND);
            this.doHurtTarget(target);
        }
    }

    public double getMeleeAttackRangeSqr(LivingEntity pEntity, float range) {
        return this.getBbWidth() * range * this.getBbWidth() * range + pEntity.getBbWidth();
    }

    public boolean isWithinMeleeAttackRange(LivingEntity pEntity, float range) {
        double d0 = this.getPerceivedTargetDistanceSquareForMeleeAttack(pEntity);
        return d0 <= this.getMeleeAttackRangeSqr(pEntity, range);
    }

    protected <E extends Firron> PlayState animationController(final AnimationState<E> event){
        if(this.isStunned) return PlayState.STOP;
        if (this.tickCount < 140) return PlayState.STOP;

        if(event.isMoving() && !event.isCurrentAnimation(RUSH_RAM)) {
            event.getController().setCustomInstructionKeyframeHandler((e) -> {
                String data = e.getKeyframeData().getInstructions();
                if(data.equals("step;")) {
                    ScreenshakeHandler.add(new PositionedScreenshakeInstance(5, Pos3.init(this.getBlockX(), this.getBlockY(), this.getBlockZ()), 0, 10).intensity(0.5f).interp(Interp.bounce));
                }
            });

            return event.setAndContinue(WALK_ANIM);
        } else if (!event.isMoving()){
            return event.setAndContinue(IDLE_ANIM);
        }

        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    public void initAttacks() {
        this.selector.addAttack(new RushAttack(this, 0, 60, 400));
        this.selector.addAttack(new SweepAttack(this,  6,1, 0, 60, 20));
        this.selector.addAttack(new PiercingDashAttack(this,  8,1, 0, 40, 60));
        this.selector.addAttack(new TripleSweepAttack(this, 6, 1, 0, 60, 40));
    }

    @Override
    public void handleEntityEvent(byte pId){
        switch(pId) {
            case 90: {
                this.isStunned = true;
                break;
            }

            case 91: {
                this.isStunned = false;
                this.triggerAnim("StatusController", "stun_end");
                break;
            }

            case 92: {
                this.rushing = true;
                this.triggerAnim("AttackController", "rush_prepare");
                break;
            }

            case 93: {
                this.rushing = false;
                break;
            }

            default: super.handleEntityEvent(pId);
        }
    }

    public void aiStep(){
        this.tickSystem();
        super.aiStep();
        if(this.level().getServer() != null && this.tickCount < 140) {
            this.serverAiStep();
        }

        var effect = EffectsRegistry.STUN.get();
        if(this.hasEffect(effect) && stunTicks == 0 && !this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte)90);
            this.stunTicks = this.getEffect(effect).getDuration();
        }

        if (stunTicks > 0) {
            if(--stunTicks <= 0) {
                this.level().broadcastEntityEvent(this, (byte)91);
            }
        }
    }

    @Override
    protected float nextStep(){
        return this.moveDist + 0.55F;
    }

    public boolean canSpawnSprintParticle() {
        return this.getDeltaMovement().horizontalDistanceSqr() > (double)2.5000003E-7F && this.random.nextInt(5) == 0;
    }

    public void addAdditionalSaveData(CompoundTag pCompound){
        super.addAdditionalSaveData(pCompound);
        this.writeAttackInfo(pCompound);
        this.saveBossData(pCompound);
    }

    public void readAdditionalSaveData(CompoundTag pCompound){
        super.readAdditionalSaveData(pCompound);
        this.readAttackInfo(pCompound);
        this.readBossData(pCompound);
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
        this.bossEvent.setHealth(this.getHealth(), this.getMaxHealth());
    }

    @Override
    protected boolean canRide(Entity pVehicle){
        return false;
    }

    public boolean isIgnoringBlockTriggers(){
        return true;
    }

    public @NotNull PushReaction getPistonPushReaction(){
        return PushReaction.IGNORE;
    }

    @Override
    public boolean isPushedByFluid(){
        return false;
    }

    @Override
    public boolean canBreatheUnderwater(){
        return true;
    }

    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel) {
        return 0.0F;
    }

    protected void doPush(@NotNull Entity pEntity){
    }

    @Override
    public void knockback(double strength, double x, double z){
    }

    @Override
    public boolean isAlliedTo(Entity pEntity){
        return super.isAlliedTo(pEntity) || pEntity instanceof Allied;
    }

    public boolean canAttack(LivingEntity pTarget){
        return super.canAttack(pTarget) && !isAlliedTo(pTarget);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource pSource){
        return super.isInvulnerableTo(pSource) && !(animationTicks > 0 || pSource.getEntity() instanceof Allied || this.tickCount < 140);
    }

    @Override
    public boolean hurt(DamageSource source, float amount){
        if(source.getDirectEntity() instanceof Player player){
            UUID playerUUID = player.getUUID();
            getDamageMap().put(playerUUID, getDamageMap().getOrDefault(playerUUID, 0.0f) + amount);
        }

        return super.hurt(source, amount);
    }

    @Override
    protected void registerGoals(){
        super.registerGoals();
        this.goalSelector.addGoal(0, new ExecuteAttackGoal(this));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, LivingEntity.class, 20));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers());
    }

    @Override
    public AttackSelector getAttackSelector(){
        return selector;
    }

    @Override
    public AttackInstance getActiveAttack(){
        return currentAttack;
    }

    @Override
    public void setActiveAttack(AttackInstance attackInstance){
        currentAttack = attackInstance;
    }

    public void onAddedToWorld() {
        super.onAddedToWorld();
        getNearbyPlayers().clear();
        initializeNearbyPlayers(this.level(), this);
        applyBonusHealth(this);
    }

    @Override
    public List<UUID> getNearbyPlayers(){
        return nearbyPlayers;
    }

    public boolean isPushable(){
        return false;
    }

    @Override
    public Map<UUID, Float> getDamageMap(){
        return damageMap;
    }

    @Nullable
    public ItemEntity spawnAtLocation(ItemStack stack, float offsetY){
        if(stack.isEmpty() || this.level().isClientSide) return null;
        initializeLoot(this.level(), stack, this.getOnPos().above(), offsetY);
        return null;
    }
}

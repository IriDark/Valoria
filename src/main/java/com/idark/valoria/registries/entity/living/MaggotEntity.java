package com.idark.valoria.registries.entity.living;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.util.ByIdMap.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;

import java.util.function.*;

public class MaggotEntity extends Monster{
    private static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(MaggotEntity.class, EntityDataSerializers.INT);
    public Variant variant;

    public MaggotEntity(Level pLevel) {
        super(EntityTypeRegistry.MAGGOT.get(), pLevel);
        if(!pLevel.isClientSide){
            this.setType(Variant.byId(this.random.nextInt(Variant.values().length)));
        }
    }

    public MaggotEntity(EntityType<? extends MaggotEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        if(!pLevel.isClientSide){
            this.setType(Variant.byId(this.random.nextInt(Variant.values().length)));
        }
    }

    protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(1, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
      this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
      this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
   }

    public boolean doHurtTarget(Entity pEntity) {
        if(pEntity instanceof LivingEntity target){
            switch(this.getVariant()){
                case MAGGOT_ROT -> target.addEffect(new MobEffectInstance(MobEffects.POISON, 30));
                case MAGGOT_ROTTEN -> target.addEffect(new MobEffectInstance(MobEffects.POISON, 50));
            }
        }

        return super.doHurtTarget(pEntity);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TYPE, 0);
    }

    public void setType(Variant variant) {
        this.variant = variant;
        this.entityData.set(TYPE, variant.getId());
    }

    public Variant getVariant() {
        return Variant.byId(this.entityData.get(TYPE));
    }

    public enum Variant{
        MAGGOT(0, "Maggot"), MAGGOT_ROT(1, "MaggotRot"), MAGGOT_ROTTEN(2, "MaggotRotten");

        private static final IntFunction<MaggotEntity.Variant> BY_ID = ByIdMap.continuous((p_263091_) -> p_263091_.id, values(), OutOfBoundsStrategy.ZERO);
        public String type;
        public final int id;

        Variant(int id, String type){
            this.id = id;
            this.type = type;
        }

        public String getString(){
            return type;
        }

        public int getId(){
            return id;
        }

        public static MaggotEntity.Variant byId(int pId){
            return BY_ID.apply(pId);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound){
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Variant", variant.getId());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound){
        super.readAdditionalSaveData(pCompound);
        this.variant = Variant.byId(pCompound.getInt("Variant"));
    }

    /**
    * Returns the Y Offset of this entity.
    */
   public double getMyRidingOffset() {
      return 0.1D;
   }

   protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
      return 0.13F;
   }

   protected Entity.MovementEmission getMovementEmission() {
      return Entity.MovementEmission.EVENTS;
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.SILVERFISH_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource pDamageSource) {
      return SoundEvents.SILVERFISH_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.SILVERFISH_DEATH;
   }

   protected void playStepSound(BlockPos pPos, BlockState pBlock) {
      this.playSound(SoundEvents.SILVERFISH_STEP, 0.15F, 1.0F);
   }

   /**
    * Called to update the entity's position/logic.
    */
   public void tick() {
      this.yBodyRot = this.getYRot();
      super.tick();
   }

   /**
    * Set the render yaw offset
    */
   public void setYBodyRot(float pOffset) {
      this.setYRot(pOffset);
      super.setYBodyRot(pOffset);
   }

   public MobType getMobType() {
      return MobType.ARTHROPOD;
   }
}

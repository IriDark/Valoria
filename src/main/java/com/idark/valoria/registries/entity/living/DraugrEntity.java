package com.idark.valoria.registries.entity.living;

import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

import javax.annotation.*;
import java.time.*;
import java.util.*;

public class DraugrEntity extends Monster implements RangedAttackMob {
    public static List<Item> draugrCanSpawnWith = new ArrayList<>();
    private final RangedBowAttackGoal<net.minecraft.world.entity.monster.AbstractSkeleton> bowGoal = new RangedBowAttackGoal<>(this, 1.0, 20, 15.0F);
    private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2, false) {
        public void stop() {
            super.stop();
            DraugrEntity.this.setAggressive(false);
        }

        public void start() {
            super.start();
            DraugrEntity.this.setAggressive(true);
        }
    };

    public DraugrEntity(EntityType<? extends DraugrEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.reassessWeaponGoal();
        this.xpReward = 5;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RestrictSunGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Wolf.class, 6.0F, 1.0, 1.2));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public void rideTick() {
        super.rideTick();
        Entity entity = this.getControlledVehicle();

        if (entity instanceof PathfinderMob pathfindermob) {
            this.yBodyRot = pathfindermob.yBodyRot;
        }
    }

    public static void spawnable(Item... T) {
        Collections.addAll(draugrCanSpawnWith, T);
    }

    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
        this.setItemSlot(EquipmentSlot.MAINHAND, draugrCanSpawnWith.get(pRandom.nextInt(0, draugrCanSpawnWith.size())).getDefaultInstance());
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        RandomSource randomsource = pLevel.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, pDifficulty);
        this.populateDefaultEquipmentEnchantments(randomsource, pDifficulty);
        this.reassessWeaponGoal();
        this.setCanPickUpLoot(randomsource.nextFloat() < 0.55F * pDifficulty.getSpecialMultiplier());
        if (this.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
            LocalDate localdate = LocalDate.now();
            int i = localdate.getDayOfMonth();
            int j = localdate.getMonth().getValue();
            if (j == 10 && i == 31 && randomsource.nextFloat() < 0.25F) {
                this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(randomsource.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
                this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0.0F;
            }
        }

        return pSpawnData;
    }

    public void reassessWeaponGoal() {
        this.level();
        if (!this.level().isClientSide) {
            this.goalSelector.removeGoal(this.meleeGoal);
            this.goalSelector.removeGoal(this.bowGoal);
            ItemStack itemstack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, (item) -> item instanceof BowItem));
            if (itemstack.is(Items.BOW)) {
                int i = 20;
                if (this.level().getDifficulty() != Difficulty.HARD) {
                    i = 40;
                }

                this.bowGoal.setMinAttackInterval(i);
                this.goalSelector.addGoal(4, this.bowGoal);
            } else {
                this.goalSelector.addGoal(4, this.meleeGoal);
            }
        }

    }

    public void performRangedAttack(LivingEntity pTarget, float pDistanceFactor) {
        ItemStack itemstack = this.getProjectile(this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, (item) -> item instanceof BowItem)));
        AbstractArrow abstractarrow = this.getArrow(itemstack, pDistanceFactor);
        if (this.getMainHandItem().getItem() instanceof BowItem) {
            abstractarrow = ((BowItem) this.getMainHandItem().getItem()).customArrow(abstractarrow);
        }

        double d0 = pTarget.getX() - this.getX();
        double d1 = pTarget.getY(0.3333333333333333) - abstractarrow.getY();
        double d2 = pTarget.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        abstractarrow.shoot(d0, d1 + d3 * 0.20000000298023224, d2, 1.6F, (float) (14 - this.level().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(abstractarrow);
    }

    protected AbstractArrow getArrow(ItemStack pArrowStack, float pVelocity) {
        return ProjectileUtil.getMobArrow(this, pArrowStack, pVelocity);
    }

    public boolean canFireProjectileWeapon(ProjectileWeaponItem pProjectileWeapon) {
        return pProjectileWeapon == Items.BOW;
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.reassessWeaponGoal();

    }

    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {
        super.setItemSlot(pSlot, pStack);
        if (!this.level().isClientSide) {
            this.reassessWeaponGoal();
        }

    }

    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SKELETON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return 1.74F;
    }

    public double getMyRidingOffset() {
        return -0.6;
    }

    public boolean isShaking() {
        return this.isFullyFrozen();
    }
}
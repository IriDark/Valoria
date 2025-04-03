package com.idark.valoria.registries.entity.projectile;

import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.alchemy.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.Level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.gameevent.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;

import java.util.*;

public class AcornProjectile extends ThrowableProjectile{
    private static final EntityDataAccessor<Integer> DATA_SWELL_DIR = SynchedEntityData.defineId(AcornProjectile.class, EntityDataSerializers.INT);
    public final Set<MobEffectInstance> effects = Sets.newHashSet();
    private int oldSwell;
    private int swell;
    private int maxSwell = 35;
    public float rotationVelocity = 0;
    public boolean inGround;

    public AcornProjectile(EntityType<? extends ThrowableProjectile> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public AcornProjectile(LivingEntity pShooter, Level pLevel){
        super(EntityTypeRegistry.ACORN.get(), pShooter, pLevel);
        this.setPos(pShooter.getX(), pShooter.getEyeY() - (double)0.1F, pShooter.getZ());
        this.setOwner(pShooter);
    }

    public void addEffect(MobEffectInstance pEffectInstance){
        this.effects.add(pEffectInstance);
    }

    public void setEffectsFromList(ImmutableList<MobEffectInstance> effects){
        this.effects.addAll(effects);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if(!this.effects.isEmpty()){
            ListTag listtag = new ListTag();
            for(MobEffectInstance mobeffectinstance : this.effects){
                listtag.add(mobeffectinstance.save(new CompoundTag()));
            }

            pCompound.put("CustomPotionEffects", listtag);
        }

        pCompound.putShort("Fuse", (short)this.maxSwell);
    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_SWELL_DIR, 1);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag pCompound){
        super.readAdditionalSaveData(pCompound);
        for(MobEffectInstance mobeffectinstance : PotionUtils.getCustomEffects(pCompound)){
            this.addEffect(mobeffectinstance);
        }

        if(pCompound.contains("Fuse", 99)){
            this.maxSwell = pCompound.getShort("Fuse");
        }
    }

        /**
         * Called to update the entity's position/logic.
         */
    public void tick() {
        if (this.isAlive()) {
            this.oldSwell = this.swell;
            int i = this.getSwellDir();
            if (i > 0 && this.swell == 1) {
                this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
                this.gameEvent(GameEvent.PRIME_FUSE);
            }

            this.swell += i;
            if (this.swell >= this.maxSwell) {
                this.swell = this.maxSwell;
                this.explode();
            }
        }

        Vec3 vec3 = this.getDeltaMovement();
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d0 = vec3.horizontalDistance();
            this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
            this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }

        BlockPos blockpos = this.blockPosition();
        BlockState blockstate = this.level().getBlockState(blockpos);
        if (!blockstate.isAir()) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.level(), blockpos);
            if (!voxelshape.isEmpty()) {
                Vec3 vec31 = this.position();

                for(AABB aabb : voxelshape.toAabbs()) {
                    if (aabb.move(blockpos).contains(vec31)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        super.tick();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult){
        super.onHitEntity(pResult);
        this.explode();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult){
        Level level = level();
        BlockPos pos = pResult.getBlockPos();
        BlockState blockState = level.getBlockState(pos);
        if(blockState.isSolid()){
            Direction d = pResult.getDirection();
            Vec3 mot = getDeltaMovement();
            double x = mot.x();
            double y = mot.y();
            double z = mot.z();

            if(d.getStepX() != 0){
                x *= -1D;
            }else if(d.getStepY() != 0){
                y *= -1D;
            }else if(d.getStepZ() != 0){
                z *= -1D;
            }

            Vec3 newMot = new Vec3(x, y, z).scale(0.325f);
            setPos(pResult.getLocation());
            setDeltaMovement(newMot);
        }

        this.inGround = true;
        super.onHitBlock(pResult);
    }

    /**
     * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
     */
    public float getSwelling(float pPartialTicks){
        return Mth.lerp(pPartialTicks, (float)this.oldSwell, (float)this.swell) / (float)(this.maxSwell - 2);
    }

    /**
     * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
     */
    public int getSwellDir(){
        return this.entityData.get(DATA_SWELL_DIR);
    }

    /**
     * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
     */
    public void setSwellDir(int pState){
        this.entityData.set(DATA_SWELL_DIR, pState);
    }

    /**
     * Creates an explosion as determined by this creeper's power and explosion radius.
     */
    private void explode(){
        if(!this.level().isClientSide){
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 1.75f, ExplosionInteraction.NONE);
            this.spawnLingeringCloud();
            this.discard();
        }
    }

    private void spawnLingeringCloud() {
        Collection<MobEffectInstance> collection = effects;
        if (!collection.isEmpty()) {
            AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
            areaeffectcloud.setRadius(2.5F);
            areaeffectcloud.setRadiusOnUse(-0.5F);
            areaeffectcloud.setWaitTime(10);
            areaeffectcloud.setDuration(areaeffectcloud.getDuration() / 2);
            areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float)areaeffectcloud.getDuration());

            for(MobEffectInstance mobeffectinstance : collection) {
                areaeffectcloud.addEffect(new MobEffectInstance(mobeffectinstance));
            }

            this.level().addFreshEntity(areaeffectcloud);
        }

    }
}

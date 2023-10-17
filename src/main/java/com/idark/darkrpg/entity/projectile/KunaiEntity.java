package com.idark.darkrpg.entity.projectile;

import com.idark.darkrpg.entity.ModEntityTypes;
import com.idark.darkrpg.item.ModItems;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.network.IPacket;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;

import java.util.List;
import javax.annotation.Nullable;

public class KunaiEntity extends AbstractArrowEntity {
	public static final DataParameter<Byte> LOYALTY_LEVEL = EntityDataManager.defineId(KunaiEntity.class, DataSerializers.BYTE);
	public static final DataParameter<Byte> PIERCE_LEVEL = EntityDataManager.defineId(KunaiEntity.class, DataSerializers.BYTE);
	public static final DataParameter<Boolean> ID_FOIL = EntityDataManager.defineId(KunaiEntity.class, DataSerializers.BOOLEAN);
	public ItemStack thrownStack = new ItemStack(ModItems.SAMURAI_KUNAI.get());
	public boolean dealtDamage;
	public boolean notRenderable;
	public boolean piercing;	
	public IntOpenHashSet piercedEntities;
	public List<Entity> hitEntities;

	public float rotationVelocity = 0;
	public int returningTicks;

	public KunaiEntity(EntityType<? extends KunaiEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public KunaiEntity(World worldIn, LivingEntity thrower, ItemStack thrownStackIn) {
		super(ModEntityTypes.KUNAI.get(), thrower, worldIn);
		this.thrownStack = thrownStackIn.copy();
		this.entityData.set(LOYALTY_LEVEL, (byte)EnchantmentHelper.getLoyalty(thrownStackIn));
		this.entityData.set(PIERCE_LEVEL, (byte)EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, thrownStackIn));
		this.entityData.set(ID_FOIL, thrownStackIn.hasFoil());
	}

	@OnlyIn(Dist.CLIENT)
	public KunaiEntity(World worldIn, double x, double y, double z) {
		super(ModEntityTypes.KUNAI.get(), x, y, z, worldIn);
	}

	public void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(LOYALTY_LEVEL, (byte)0);
		this.entityData.define(PIERCE_LEVEL, (byte)0);		
		this.entityData.define(ID_FOIL, false);
	}

	@OnlyIn(Dist.CLIENT)
	public boolean shouldRenderAtSqrDistance(double distance) {
		double d0 = this.getBoundingBox().getSize() * 10.0D;
		if (Double.isNaN(d0)) {
			d0 = 1.0D;
		}
	
		d0 = d0 * 64.0D * getViewScale();
		this.notRenderable = distance < d0 * d0;	
		return this.notRenderable;
	}

	public void tick() {
		if (this.inGroundTime > 4) {
			this.dealtDamage = true;
		}
		
		Entity entity = this.getOwner();
		if ((this.dealtDamage || this.isNoPhysics()) && entity != null) {
			int i = this.entityData.get(LOYALTY_LEVEL);
			int p = this.entityData.get(PIERCE_LEVEL);
			if (p > 0) {
				this.piercing = true;
			}
			
			if (i > 0 && !this.shouldReturnToThrower()) {
				if (!this.level.isClientSide && this.pickup == AbstractArrowEntity.PickupStatus.ALLOWED) {
				this.spawnAtLocation(this.getPickupItem(), 0.1F);
				}
				
			this.remove();
		} else if (i > 0) {
            this.setNoPhysics(true);
            Vector3d vector3d = new Vector3d(entity.getX() - this.getX(), entity.getEyeY() - this.getY(), entity.getZ() - this.getZ());
            this.setPosRaw(this.getX(), this.getY() + vector3d.y * 0.015D * (double)i, this.getZ());
            if (this.level.isClientSide) {
               this.yOld = this.getY();
            }

            double d0 = 0.05D * (double)i;
            this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vector3d.normalize().scale(d0)));
            if (this.returningTicks == 0) {
               this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
            }

            ++this.returningTicks;
			}
		}

		if (this.notRenderable == false && !this.inGround) {
			Vector3d vector3d = this.getDeltaMovement();
			double a3 = vector3d.x;
			double a4 = vector3d.y;
			double a0 = vector3d.z;		
			for(int a = 0; a < 3; ++a) {
				this.level.addParticle(ParticleTypes.WHITE_ASH, this.getX() + a3 * (double)a / 4.0D, this.getY() + a4 * (double)a / 4.0D, this.getZ() + a0 * (double)a / 4.0D, -a3, -a4 + 0.2D, -a0);		
			}
		}
	
		super.tick();
	}

	private boolean shouldReturnToThrower() {
		Entity entity = this.getOwner();
		if (entity != null && entity.isAlive()) {
			return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
		} else {
			return false;
		}
	}

	public ItemStack getPickupItem() {
		return this.thrownStack.copy();
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isFoil() {
		return this.entityData.get(ID_FOIL);
	}

	@Nullable
	public EntityRayTraceResult findHitEntity(Vector3d startVec, Vector3d endVec) {
		return this.dealtDamage ? null : super.findHitEntity(startVec, endVec);
	}

	@Override
	public void onHitEntity(EntityRayTraceResult result) {
		Entity entity = result.getEntity();
        int e = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SHARPNESS, this.thrownStack);		
		float f = 5.5f + (((float)e)-1.5f);
		if (entity instanceof LivingEntity) {
			LivingEntity livingentity = (LivingEntity)entity;
			f += EnchantmentHelper.getDamageBonus(this.thrownStack, livingentity.getMobType());
		}

		Entity shooter = this.getOwner();
		DamageSource damagesource = DamageSource.trident(this, (Entity)(shooter == null ? this : shooter));
		this.dealtDamage = true;
		SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
		if (entity.hurt(damagesource, f)) {
			if (entity.getType() == EntityType.ENDERMAN) {
				return;
			}

			if (entity instanceof LivingEntity) {
				LivingEntity livingentity1 = (LivingEntity)entity;
				if (shooter instanceof LivingEntity) {
					EnchantmentHelper.doPostHurtEffects(livingentity1, shooter);
					EnchantmentHelper.doPostDamageEffects((LivingEntity)shooter, livingentity1);
				}

				this.doPostHurtEffects(livingentity1);
			}
		}

		if (this.getPierceLevel() > 0) {
			if (this.piercedEntities == null) {
				this.piercedEntities = new IntOpenHashSet(5);
			}

			if (this.hitEntities == null) {
				this.hitEntities = Lists.newArrayListWithCapacity(5);
			}

			if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
				this.remove();
				return;
			}

			this.piercedEntities.add(entity.getId());
		}
		
        if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entity;
            if (!entity.isAlive() && this.hitEntities != null) {
				this.hitEntities.add(livingentity);		
			}
		}
	}

	public SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.TRIDENT_HIT_GROUND;
	}

	@Override
	public SoundEvent getHitGroundSoundEvent() {
		return SoundEvents.TRIDENT_HIT_GROUND;
	}
	
	public void playerTouch(PlayerEntity entityIn) {
		Entity entity = this.getOwner();
		if (entity == null || entity.getUUID() == entityIn.getUUID()) {
			super.playerTouch(entityIn);
		}
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Kunai", 10)) {
			this.thrownStack = ItemStack.of(compound.getCompound("Kunai"));
		}

		this.dealtDamage = compound.getBoolean("DealtDamage");
		this.setPierceLevel(compound.getByte("PierceLevel"));		
		this.entityData.set(LOYALTY_LEVEL, (byte)EnchantmentHelper.getLoyalty(this.thrownStack));
		this.entityData.set(PIERCE_LEVEL, (byte)EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, this.thrownStack));
	}

	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.put("Kunai", this.thrownStack.save(new CompoundNBT()));
		compound.putBoolean("DealtDamage", this.dealtDamage);
		compound.putByte("PierceLevel", this.getPierceLevel());
	}

	public byte getPierceLevel() {
		return this.entityData.get(PIERCE_LEVEL);
	}

	public void tickDespawn() {
		int i = this.entityData.get(LOYALTY_LEVEL);
		if (this.pickup != AbstractArrowEntity.PickupStatus.ALLOWED || i <= 0) {
			super.tickDespawn();
		}
	}
	
	public void resetPiercedEntities() {
		if (this.hitEntities != null) {
			this.hitEntities.clear();
		}

		if (this.piercedEntities != null) {
			this.piercedEntities.clear();
		}
	}
	
	public float getWaterInertia() {
		return 0.5F;
	}

	@OnlyIn(Dist.CLIENT)
	public boolean shouldRender(double x, double y, double z) {
		return true;
	}
}
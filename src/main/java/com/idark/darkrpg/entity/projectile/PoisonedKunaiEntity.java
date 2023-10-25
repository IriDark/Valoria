package com.idark.darkrpg.entity.projectile;

import com.idark.darkrpg.entity.ModEntityTypes;
import com.idark.darkrpg.item.ModItems;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;

import java.util.List;

@SuppressWarnings("SillyAssignment")
public class PoisonedKunaiEntity extends AbstractArrow {
	public static final EntityDataAccessor<Byte> LOYALTY_LEVEL = SynchedEntityData.defineId(PoisonedKunaiEntity.class, EntityDataSerializers.BYTE);
	public static final EntityDataAccessor<Byte> PIERCE_LEVEL = SynchedEntityData.defineId(PoisonedKunaiEntity.class, EntityDataSerializers.BYTE);
	public static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(PoisonedKunaiEntity.class, EntityDataSerializers.BOOLEAN);
	public ItemStack thrownStack = new ItemStack(ModItems.SAMURAI_KUNAI.get());
	public boolean dealtDamage;
	public boolean notRenderable;	
	public boolean piercing;
	public IntOpenHashSet piercedEntities;
	public List<Entity> hitEntities;

	public float rotationVelocity = 0;
	public int returningTicks;

	public PoisonedKunaiEntity(EntityType<? extends PoisonedKunaiEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	public PoisonedKunaiEntity(Level worldIn, LivingEntity thrower, ItemStack thrownStackIn) {
		super(ModEntityTypes.POISONED_KUNAI.get(), thrower, worldIn);
		this.thrownStack = this.thrownStack;
		this.entityData.set(LOYALTY_LEVEL, (byte) EnchantmentHelper.getLoyalty(thrownStackIn));
		this.entityData.set(PIERCE_LEVEL, (byte)EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, thrownStackIn));
		this.entityData.set(ID_FOIL, thrownStackIn.hasFoil());
	}

	@OnlyIn(Dist.CLIENT)
	public PoisonedKunaiEntity(Level worldIn, double x, double y, double z) {
		super(ModEntityTypes.POISONED_KUNAI.get(), x, y, z, worldIn);
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
			this.piercing = true;
		}
		
		/*Entity entity = this.getOwner();
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
				this.level.addParticle(ParticleTypes.ENCHANTED_HIT, this.getX() + a3 * (double)a / 4.0D, this.getY() + a4 * (double)a / 4.0D, this.getZ() + a0 * (double)a / 4.0D, -a3, -a4 + 0.2D, -a0);		
			}
		}*/
	
		super.tick();
	}

	private boolean shouldReturnToThrower() {
		Entity entity = this.getOwner();
		if (entity != null && entity.isAlive()) {
			return !(entity instanceof ServerPlayer) || !entity.isSpectator();
		} else {
			return false;
		}
	}

	public ItemStack getPickupItem() {
		return this.thrownStack;
	}

	/*@OnlyIn(Dist.CLIENT)
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
			LivingEntity livingentity = (LivingEntity) entity;
			f += EnchantmentHelper.getDamageBonus(this.thrownStack, livingentity.getMobType());
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
		
		if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entity;
            if (!entity.isAlive() && this.hitEntities != null) {
               this.hitEntities.add(livingentity);
            }			
		}
		
		Entity entity1 = this.getOwner();
		DamageSource damagesource = DamageSource.trident(this, (Entity)(entity1 == null ? this : entity1));
		this.dealtDamage = true;
		SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
		if (entity.hurt(damagesource, f)) {
			if (entity.getType() == EntityType.ENDERMAN) {
				return;
			}

			if (entity instanceof LivingEntity) {
				LivingEntity livingentity1 = (LivingEntity)entity;
				((LivingEntity)entity).addEffect(new EffectInstance(Effects.POISON, 170, 0));
				if (entity1 instanceof LivingEntity) {
					EnchantmentHelper.doPostHurtEffects(livingentity1, entity1);
					EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity1);
				}

				this.doPostHurtEffects(livingentity1);
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
	}*/
}
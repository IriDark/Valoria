package com.idark.darkrpg.entity.projectile;

import com.idark.darkrpg.entity.ModEntityTypes;
import com.idark.darkrpg.item.ModItems;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.network.IPacket;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;

public class KunaiEntity extends AbstractArrowEntity {
	public static final DataParameter<Byte> LOYALTY_LEVEL = EntityDataManager.createKey(KunaiEntity.class, DataSerializers.BYTE);
	public static final DataParameter<Boolean> field_226571_aq_ = EntityDataManager.createKey(KunaiEntity.class, DataSerializers.BOOLEAN);
	public ItemStack thrownStack = new ItemStack(ModItems.SAMURAI_KUNAI.get());
	public boolean dealtDamage;
	public boolean notRenderable;	
	public IntOpenHashSet piercedEntities;
	public List<Entity> hitEntities;

	public int returningTicks;

	public KunaiEntity(EntityType<? extends KunaiEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public KunaiEntity(World worldIn, LivingEntity thrower, ItemStack thrownStackIn) {
		super(ModEntityTypes.KUNAI.get(), thrower, worldIn);
		this.thrownStack = thrownStackIn.copy();
		this.dataManager.set(LOYALTY_LEVEL, (byte)EnchantmentHelper.getLoyaltyModifier(thrownStackIn));
		this.dataManager.set(field_226571_aq_, thrownStackIn.hasEffect());
	}

	@OnlyIn(Dist.CLIENT)
	public KunaiEntity(World worldIn, double x, double y, double z) {
		super(ModEntityTypes.KUNAI.get(), x, y, z, worldIn);
	}

	public void registerData() {
		super.registerData();
		this.dataManager.register(LOYALTY_LEVEL, (byte)0);
		this.dataManager.register(field_226571_aq_, false);
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		double d0 = this.getBoundingBox().getAverageEdgeLength() * 10.0D;
		if (Double.isNaN(d0)) {
			d0 = 1.0D;
		}
	
		d0 = d0 * 64.0D * getRenderDistanceWeight();
		this.notRenderable = distance < d0 * d0;	
		return this.notRenderable;
	}

	public void tick() {
		if (this.timeInGround > 4) {
			this.dealtDamage = true;
		}
		
		Entity entity = this.getShooter();
		if ((this.dealtDamage || this.getNoClip()) && entity != null) {
			int i = this.dataManager.get(LOYALTY_LEVEL);
			if (i > 0 && !this.shouldReturnToThrower()) {
				if (!this.world.isRemote && this.pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED) {
				this.entityDropItem(this.getArrowStack(), 0.1F);
				}
				
			this.remove();
		} else if (i > 0) {
            this.setNoClip(true);
            Vector3d vector3d = new Vector3d(entity.getPosX() - this.getPosX(), entity.getPosYEye() - this.getPosY(), entity.getPosZ() - this.getPosZ());
            this.setRawPosition(this.getPosX(), this.getPosY() + vector3d.y * 0.015D * (double)i, this.getPosZ());
            if (this.world.isRemote) {
               this.lastTickPosY = this.getPosY();
            }

            double d0 = 0.05D * (double)i;
            this.setMotion(this.getMotion().scale(0.95D).add(vector3d.normalize().scale(d0)));
            if (this.returningTicks == 0) {
               this.playSound(SoundEvents.ITEM_TRIDENT_RETURN, 10.0F, 1.0F);
            }

            ++this.returningTicks;
			}
		}

		if (this.notRenderable == false && !this.inGround) {
			Vector3d vector3d = this.getMotion();
			double a3 = vector3d.x;
			double a4 = vector3d.y;
			double a0 = vector3d.z;		
			for(int a = 0; a < 3; ++a) {
				this.world.addParticle(ParticleTypes.WHITE_ASH, this.getPosX() + a3 * (double)a / 4.0D, this.getPosY() + a4 * (double)a / 4.0D, this.getPosZ() + a0 * (double)a / 4.0D, -a3, -a4 + 0.2D, -a0);		
			}
		}
	
		super.tick();
	}

	private boolean shouldReturnToThrower() {
		Entity entity = this.getShooter();
		if (entity != null && entity.isAlive()) {
			return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
		} else {
			return false;
		}
	}

	public ItemStack getArrowStack() {
		return this.thrownStack.copy();
	}

	@OnlyIn(Dist.CLIENT)
	public boolean func_226572_w_() {
		return this.dataManager.get(field_226571_aq_);
	}

	@Nullable
	public EntityRayTraceResult rayTraceEntities(Vector3d startVec, Vector3d endVec) {
		return this.dealtDamage ? null : super.rayTraceEntities(startVec, endVec);
	}

	public void onEntityHit(EntityRayTraceResult result) {
		Entity entity = result.getEntity();
        int e = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, this.thrownStack);		
		float f = 5.5f + (((float)e)-1.5f);
		if (entity instanceof LivingEntity) {
			LivingEntity livingentity = (LivingEntity)entity;
			f += EnchantmentHelper.getModifierForCreature(this.thrownStack, livingentity.getCreatureAttribute());
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

        this.piercedEntities.add(entity.getEntityId());
		}
		
        if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entity;
            if (!this.world.isRemote && this.getPierceLevel() <= 0) {
               livingentity.setArrowCountInEntity(livingentity.getArrowCountInEntity() + 1);
            }
			
            if (!entity.isAlive() && this.hitEntities != null) {
               this.hitEntities.add(livingentity);
            }			
		}
		
		Entity entity1 = this.getShooter();
		DamageSource damagesource = DamageSource.causeTridentDamage(this, (Entity)(entity1 == null ? this : entity1));
		this.dealtDamage = true;
		SoundEvent soundevent = SoundEvents.ITEM_TRIDENT_HIT;
		if (entity.attackEntityFrom(damagesource, f)) {
			if (entity.getType() == EntityType.ENDERMAN) {
				return;
			}

			if (entity instanceof LivingEntity) {
				LivingEntity livingentity1 = (LivingEntity)entity;
				if (entity1 instanceof LivingEntity) {
					EnchantmentHelper.applyThornEnchantments(livingentity1, entity1);
					EnchantmentHelper.applyArthropodEnchantments((LivingEntity)entity1, livingentity1);
				}

				this.arrowHit(livingentity1);
			}
		}
	}

	public SoundEvent getHitEntitySound() {
		return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
	}

	@Override
	public SoundEvent getHitGroundSound() {
		return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
	}
	
	public void onCollideWithPlayer(PlayerEntity entityIn) {
		Entity entity = this.getShooter();
		if (entity == null || entity.getUniqueID() == entityIn.getUniqueID()) {
			super.onCollideWithPlayer(entityIn);
		}
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("Kunai", 10)) {
			this.thrownStack = ItemStack.read(compound.getCompound("Kunai"));
		}

		this.dealtDamage = compound.getBoolean("DealtDamage");
		this.dataManager.set(LOYALTY_LEVEL, (byte)EnchantmentHelper.getLoyaltyModifier(this.thrownStack));
	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.put("Kunai", this.thrownStack.write(new CompoundNBT()));
		compound.putBoolean("DealtDamage", this.dealtDamage);
	}

	public void func_225516_i_() {
		int i = this.dataManager.get(LOYALTY_LEVEL);
		if (this.pickupStatus != AbstractArrowEntity.PickupStatus.ALLOWED || i <= 0) {
			super.func_225516_i_();
		}
	}

	public float getWaterDrag() {
		return 0.6F;
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isInRangeToRender3d(double x, double y, double z) {
		return true;
	}
}
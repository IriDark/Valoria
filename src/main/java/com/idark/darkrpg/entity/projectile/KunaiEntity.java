package com.idark.darkrpg.entity.projectile;

import com.idark.darkrpg.entity.ModEntityTypes;
import com.idark.darkrpg.item.ModItems;
import javax.annotation.Nullable;
import net.minecraft.enchantment.EnchantmentHelper;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class KunaiEntity extends AbstractArrowEntity {
	private static final DataParameter<Byte> LOYALTY_LEVEL = EntityDataManager.createKey(KunaiEntity.class, DataSerializers.BYTE);
	private static final DataParameter<Boolean> field_226571_aq_ = EntityDataManager.createKey(KunaiEntity.class, DataSerializers.BOOLEAN);
	private ItemStack thrownStack = new ItemStack(ModItems.SAMURAI_KUNAI.get());
	private boolean dealtDamage;
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

	protected void registerData() {
		super.registerData();
		this.dataManager.register(LOYALTY_LEVEL, (byte)0);
		this.dataManager.register(field_226571_aq_, false);
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

	protected ItemStack getArrowStack() {
		return this.thrownStack.copy();
	}

	@OnlyIn(Dist.CLIENT)
	public boolean func_226572_w_() {
		return this.dataManager.get(field_226571_aq_);
	}

	@Nullable
	protected EntityRayTraceResult rayTraceEntities(Vector3d startVec, Vector3d endVec) {
		return this.dealtDamage ? null : super.rayTraceEntities(startVec, endVec);
	}

	protected void onEntityHit(EntityRayTraceResult result) {
		Entity entity = result.getEntity();
		float f = 8.0F;
		if (entity instanceof LivingEntity) {
			LivingEntity livingentity = (LivingEntity)entity;
			f += EnchantmentHelper.getModifierForCreature(this.thrownStack, livingentity.getCreatureAttribute());
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

	protected SoundEvent getHitEntitySound() {
		return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
	}

	public void onCollideWithPlayer(PlayerEntity entityIn) {
		Entity entity = this.getShooter();
		if (entity == null || entity.getUniqueID() == entityIn.getUniqueID()) {
			super.onCollideWithPlayer(entityIn);
		}
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

	protected float getWaterDrag() {
		return 0.99F;
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isInRangeToRender3d(double x, double y, double z) {
		return true;
	}
}
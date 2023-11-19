package com.idark.darkrpg.entity.custom;

import com.idark.darkrpg.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeEntity;

public class MannequinEntity extends Mob implements IForgeEntity {

    private static final EntityDataAccessor<Float> LAST_DAMAGE = SynchedEntityData.defineId(MannequinEntity.class, EntityDataSerializers.FLOAT);

    public float lastDamageOffset = 0;
    public float lastDamageOffsetPrev = 0;

    public MannequinEntity(EntityType<? extends Mob> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(LAST_DAMAGE,0f);
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        Level level = pPlayer.level();
        if (pPlayer.isShiftKeyDown()) {
            if(!level.isClientSide()) {
                if(!pPlayer.isCreative()) {
                    level.addFreshEntity(new ItemEntity(level, this.getX(), this.getY() + 0.7D, this.getZ(), ModItems.MANNEQUIN_SPAWN_EGG.get().getDefaultInstance()));
                }

                this.removeAfterChangingDimensions();
            } else {
                for (int i = 0; i < 15; i++) {
                    level.addParticle(ParticleTypes.POOF, this.getX(), this.getY() + 0.7D, this.getZ(), 0d, 0.02d, 0d);
                }
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
        .add(Attributes.MAX_HEALTH, 1000000)
        .add(Attributes.MOVEMENT_SPEED, 0.0D)
        .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
        .add(Attributes.FOLLOW_RANGE, 0.0D)
        .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (hurtTime == 0) {
            entityData.set(LAST_DAMAGE, 0f);
            entityData.set(LAST_DAMAGE, amount);
        }

	    return super.hurt(source, amount);
	}

    public float getLastDamage(){
        return entityData.get(LAST_DAMAGE);
    }

}
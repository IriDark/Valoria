package com.idark.valoria.registries.entity.living.decoration;

import com.idark.valoria.registries.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.syncher.*;
import net.minecraft.tags.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraftforge.common.extensions.*;
import org.jetbrains.annotations.*;

public class MannequinEntity extends AbstractDecorationMob implements IForgeEntity {
    private static final EntityDataAccessor<Float> LAST_DAMAGE = SynchedEntityData.defineId(MannequinEntity.class, EntityDataSerializers.FLOAT);

    public float lastDamageOffset = 0;
    public float lastDamageOffsetPrev = 0;

    public MannequinEntity(EntityType<? extends Mob> type, Level worldIn) {
        super(type, worldIn);
        this.setMaxUpStep(0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(LAST_DAMAGE, 0f);
    }

    public void tick() {
        super.tick();
        if (this.isInWall()) {
            this.spawnAtLocation(ItemsRegistry.mannequin.get());
            this.discard();
        }
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player pPlayer, @NotNull InteractionHand pHand) {
        Level level = pPlayer.level();
        if (pPlayer.isShiftKeyDown()) {
            if (!level.isClientSide()) {
                if (!pPlayer.isCreative()) {
                    level.addFreshEntity(new ItemEntity(level, this.getX(), this.getY() + 0.7D, this.getZ(), ItemsRegistry.mannequin.get().getDefaultInstance()));
                }

                this.removeAfterChangingDimensions();
            } else {
                for (int i = 0; i < 4; i++) {
                    level.addParticle(ParticleTypes.POOF, this.getX(), this.getY() + 0.7D, this.getZ(), 0d, 0.02d, 0d);
                }

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    /**
     * Actually damages the entity
     */
    public void kill() {
        this.actuallyHurt(this.level().damageSources().genericKill(), Float.MAX_VALUE);
    }

    /**
     * Only sends a packet of damage without damaging, so it's some sorta of invisibility
     */
    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (source == this.damageSources().fellOutOfWorld()) {
            this.remove(RemovalReason.KILLED);
            return false;
        }

        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            kill();
            return false;
        } else {
            BlockState state = Blocks.HAY_BLOCK.defaultBlockState();
            for (int i = 0; i < 3; i++) {
                this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), this.getX(), this.getY() + 0.7D, this.getZ(), 0d, 0.02d, 0d);
            }

            // Reset of LAST_DAMAGE value to prevent visual bugs
            entityData.set(LAST_DAMAGE, 0f);
            if (hurtTime == 0) {
                entityData.set(LAST_DAMAGE, amount);
            }

            this.markHurt();
            this.level().broadcastDamageEvent(this, source);
            return true;
        }
    }

    public float getLastDamage() {
        return entityData.get(LAST_DAMAGE);
    }
}
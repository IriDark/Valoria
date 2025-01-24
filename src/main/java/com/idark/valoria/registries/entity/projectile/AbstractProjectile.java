package com.idark.valoria.registries.entity.projectile;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public abstract class AbstractProjectile extends AbstractValoriaArrow{
    public boolean velocityBased;
    public boolean discardOnHit;

    public AbstractProjectile(EntityType<? extends AbstractArrow> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public AbstractProjectile(EntityType<? extends AbstractArrow> pEntityType, Level worldIn, LivingEntity thrower, int baseDamage){
        super(pEntityType, worldIn, thrower, baseDamage);
    }

    public AbstractProjectile(EntityType<? extends AbstractArrow> pEntityType, Level worldIn, LivingEntity thrower, ItemStack thrownStackIn, int baseDamage){
        super(pEntityType, worldIn, thrower, thrownStackIn, baseDamage);
    }

    public void setVelocityBasedDamage(double baseDamage){
        this.baseDamage = baseDamage;
        this.velocityBased = true;
    }

    public void setDiscardOnHit(){
        this.discardOnHit = true;
    }

    public boolean isDiscardOnHit(){
        return discardOnHit;
    }

    public boolean isVelocityBased(){
        return velocityBased;
    }

    public void processVelocityDamage(LivingEntity thrower, Entity entity, DamageSource damagesource){
        double velocity = this.getDeltaMovement().length();
        int damage = Mth.ceil(Mth.clamp(velocity * this.baseDamage, 0, Integer.MAX_VALUE));
        if(this.isCritArrow()){
            long j = this.random.nextInt(damage / 2 + 2);
            damage = (int)Math.min(j + (long)damage, 2147483647L);
        }

        hurt(thrower, entity, damagesource, damage);
    }

    @Override
    public void onHitEntity(EntityHitResult result){
        Entity entity = result.getEntity();
        Entity shooter = this.getOwner();
        if(this.getPierceLevel() > 0){
            if(this.piercingIgnoreEntityIds == null){
                this.piercingIgnoreEntityIds = new IntOpenHashSet(5);
            }

            if(this.piercedAndKilledEntities == null){
                this.piercedAndKilledEntities = Lists.newArrayListWithCapacity(5);
            }

            if(this.piercingIgnoreEntityIds.size() >= this.getPierceLevel() + 1){
                this.discard();
                return;
            }

            this.piercingIgnoreEntityIds.add(entity.getId());
        }

        DamageSource damagesource;
        if(shooter == null){
            damagesource = this.damageSources().arrow(this, this);
        }else{
            damagesource = this.damageSources().arrow(this, shooter);
            if(shooter instanceof LivingEntity){
                ((LivingEntity)shooter).setLastHurtMob(entity);
            }
        }

        if(shooter instanceof LivingEntity thrower){
            boolean flag = entity.getType() == EntityType.ENDERMAN;
            if(this.isOnFire() && !flag){
                entity.setSecondsOnFire(5);
            }

            processVelocityDamage(thrower, entity, damagesource);
        }
    }

    @Override
    protected void onHit(HitResult pResult){
        super.onHit(pResult);
        if(discardOnHit) discard();
    }

    /**
     * Custom damage processing here
     */
    public void hurt(LivingEntity thrower, Entity entity, DamageSource source, float damage){
        int k = entity.getRemainingFireTicks();
        if(entity.hurt(source, damage)){
            boolean flag = entity.getType() == EntityType.ENDERMAN;
            if(flag){
                return;
            }

            if(entity instanceof LivingEntity livingentity){
                EnchantmentHelper.doPostHurtEffects(livingentity, thrower);
                EnchantmentHelper.doPostDamageEffects(thrower, livingentity);
                this.doPostHurtEffects(livingentity);
                if(this.knockback > 0){
                    double d0 = Math.max(0.0D, 1.0D - livingentity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                    Vec3 vec3 = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockback * 0.6D * d0);
                    if(vec3.lengthSqr() > 0.0D){
                        livingentity.push(vec3.x, 0.1D, vec3.z);
                    }
                }

                if(livingentity != thrower && livingentity instanceof Player && thrower instanceof ServerPlayer serv && !this.isSilent()){
                    serv.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }

                if(!entity.isAlive() && this.piercedAndKilledEntities != null){
                    this.piercedAndKilledEntities.add(livingentity);
                }

                if(!this.level().isClientSide && thrower instanceof ServerPlayer serverplayer){
                    if(this.piercedAndKilledEntities != null && this.shotFromCrossbow()){
                        CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverplayer, this.piercedAndKilledEntities);
                    }else if(!entity.isAlive() && this.shotFromCrossbow()){
                        CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverplayer, List.of(entity));
                    }
                }
            }
        }else{
            entity.setRemainingFireTicks(k);
            this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
            this.setYRot(this.getYRot() + 180.0F);
            this.yRotO += 180.0F;
            if(!this.level().isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D){
                if(this.pickup == AbstractArrow.Pickup.ALLOWED){
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.discard();
            }
        }
    }
}

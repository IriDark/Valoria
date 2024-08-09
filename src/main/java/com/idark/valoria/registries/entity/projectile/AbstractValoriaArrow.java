package com.idark.valoria.registries.entity.projectile;

import com.google.common.collect.*;
import it.unimi.dsi.fastutil.ints.*;
import net.minecraft.advancements.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;

import java.util.*;

public class AbstractValoriaArrow extends AbstractArrow{

    public ItemStack arrowItem = ItemStack.EMPTY;
    public int minDamage;

    public AbstractValoriaArrow(EntityType<? extends AbstractArrow> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public AbstractValoriaArrow(EntityType<? extends AbstractArrow> pEntityType, Level worldIn, LivingEntity thrower, ItemStack thrownStackIn, int minDamage, int baseDamage){
        super(pEntityType, thrower, worldIn);
        arrowItem = new ItemStack(thrownStackIn.getItem());
        this.minDamage = minDamage;
        this.baseDamage = baseDamage;
    }

    public void tick(){
        super.tick();
        if(this.level().isClientSide){
            this.spawnParticlesTrail();
        }
    }

    public void spawnParticlesTrail(){
    }

    @Override
    public ItemStack getPickupItem(){
        return arrowItem;
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult){
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        double velocity = this.getDeltaMovement().length();
        int damage = Mth.ceil(Mth.clamp(velocity * this.baseDamage, 0.0D, Integer.MAX_VALUE));
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

        if(this.isCritArrow()){
            long j = this.random.nextInt(damage / 2 + 2);
            damage = (int)Math.min(j + (long)damage, 2147483647L);
        }

        Entity entity1 = this.getOwner();
        DamageSource damagesource;
        if(entity1 == null){
            damagesource = this.damageSources().arrow(this, this);
        }else{
            damagesource = this.damageSources().arrow(this, entity1);
            if(entity1 instanceof LivingEntity){
                ((LivingEntity)entity1).setLastHurtMob(entity);
            }
        }

        boolean flag = entity.getType() == EntityType.ENDERMAN;
        int k = entity.getRemainingFireTicks();
        if(this.isOnFire() && !flag){
            entity.setSecondsOnFire(5);
        }

        if(entity.hurt(damagesource, (float)damage)){
            if(flag){
                return;
            }

            if(entity instanceof LivingEntity livingentity){
                if(!this.level().isClientSide && this.getPierceLevel() <= 0){
                    livingentity.setArrowCount(livingentity.getArrowCount() + 1);
                }

                if(this.knockback > 0){
                    double d0 = Math.max(0.0D, 1.0D - livingentity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                    Vec3 vec3 = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockback * 0.6D * d0);
                    if(vec3.lengthSqr() > 0.0D){
                        livingentity.push(vec3.x, 0.1D, vec3.z);
                    }
                }

                if(!this.level().isClientSide && entity1 instanceof LivingEntity){
                    EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity);
                }

                this.doPostHurtEffects(livingentity);
                if(livingentity != entity1 && livingentity instanceof Player && entity1 instanceof ServerPlayer && !this.isSilent()){
                    ((ServerPlayer)entity1).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }

                if(!entity.isAlive() && this.piercedAndKilledEntities != null){
                    this.piercedAndKilledEntities.add(livingentity);
                }

                if(!this.level().isClientSide && entity1 instanceof ServerPlayer serverplayer){
                    if(this.piercedAndKilledEntities != null && this.shotFromCrossbow()){
                        CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverplayer, this.piercedAndKilledEntities);
                    }else if(!entity.isAlive() && this.shotFromCrossbow()){
                        CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverplayer, List.of(entity));
                    }
                }
            }

            this.playSound(this.soundEvent, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            if(this.getPierceLevel() <= 0){
                this.discard();
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
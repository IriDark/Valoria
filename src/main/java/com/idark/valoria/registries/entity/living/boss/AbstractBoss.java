package com.idark.valoria.registries.entity.living.boss;

import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.entity.living.*;
import net.minecraft.nbt.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;

import javax.annotation.*;
import java.util.*;

public abstract class AbstractBoss extends MultiAttackMob implements Enemy, BossEntity, Allied{
    public final List<UUID> nearbyPlayers = new ArrayList<>();
    public final Map<UUID, Float> damageMap = new HashMap<>();
    public int phase = 1;

    public AbstractBoss(EntityType<? extends PathfinderMob> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    @Override
    public void tick(){
        super.tick();
        checkPhaseTransition();
    }

    /**
     * Both sided
     */
    public abstract void checkPhaseTransition();

    @Override
    public boolean isAlliedTo(Entity pEntity){
        return super.isAlliedTo(pEntity) || pEntity instanceof Allied;
    }

    public boolean canAttack(LivingEntity pTarget){
        return super.canAttack(pTarget) && !isAlliedTo(pTarget);
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super. readAdditionalSaveData(pCompound);
        readBossData(pCompound);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super. addAdditionalSaveData(pCompound);
        saveBossData(pCompound);
    }

    @Override
    public boolean hurt(DamageSource source, float amount){
        if(source.getEntity() instanceof Allied) return false;
        if(source.getDirectEntity() instanceof Player player){
            UUID playerUUID = player.getUUID();
            getDamageMap().put(playerUUID, getDamageMap().getOrDefault(playerUUID, 0.0f) + amount);
        }

        return super.hurt(source, amount);
    }

    @Override
    public void onAddedToWorld(){
        super.onAddedToWorld();
        CompoundTag data = this.getPersistentData();
        if(!data.getBoolean("NearbyPlayerHealthBonus")){
            initializeNearbyPlayers(this.level(), this);
            applyBonusHealth(this);
        }
    }

    @Override
    public List<UUID> getNearbyPlayers(){
        return nearbyPlayers;
    }

    public boolean isPushable(){
        return false;
    }

    @Override
    public Map<UUID, Float> getDamageMap(){
        return damageMap;
    }

    @Nullable
    public ItemEntity spawnAtLocation(ItemStack stack, float offsetY){
        if(stack.isEmpty() || this.level().isClientSide) return null;
        initializeLoot(this.level(), stack, this.getOnPos().above(), offsetY);
        return null;
    }
}

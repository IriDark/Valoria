package com.idark.valoria.registries.entity.living.boss;

import com.idark.valoria.client.ui.bossbars.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.entity.living.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;

import javax.annotation.*;
import java.util.*;

public abstract class AbstractBoss extends MultiAttackMob implements Enemy, BossEntity{
    public final ServerBossBarEvent bossEvent;
    public final List<UUID> nearbyPlayers = new ArrayList<>();
    public final Map<UUID, Float> damageMap = new HashMap<>();
    public AbstractBoss(EntityType<? extends PathfinderMob> pEntityType, Level pLevel, ServerBossBarEvent bossEvent){
        super(pEntityType, pLevel);
        this.bossEvent = bossEvent;
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
        if(!data.getBoolean("NearbyPlayerHealthBoost")){
            initializeNearbyPlayers(this.level(), this);
            applyBonusHealth(this);
        }
    }

    @Override
    public List<UUID> getNearbyPlayers(){
        return nearbyPlayers;
    }

    @Override
    public Map<UUID, Float> getDamageMap(){
        return damageMap;
    }

    public void setCustomName(@Nullable Component pName){
        super.setCustomName(pName);
        this.bossEvent.setName(this.getDisplayName());
    }

    public void startSeenByPlayer(ServerPlayer pPlayer){
        super.startSeenByPlayer(pPlayer);
        this.bossEvent.addPlayer(pPlayer);
    }

    public void stopSeenByPlayer(ServerPlayer pPlayer){
        super.stopSeenByPlayer(pPlayer);
        this.bossEvent.removePlayer(pPlayer);
    }

    protected void customServerAiStep(){
        super.customServerAiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Nullable
    public ItemEntity spawnAtLocation(ItemStack stack, float offsetY){
        if(stack.isEmpty() || this.level().isClientSide) return null;
        initializeLoot(this.level(), stack, this.getOnPos().above(), offsetY);
        return null;
    }
}

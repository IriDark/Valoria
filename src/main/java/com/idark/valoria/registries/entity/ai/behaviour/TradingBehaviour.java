package com.idark.valoria.registries.entity.ai.behaviour;

import com.google.common.collect.*;
import com.idark.valoria.registries.entity.living.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.ai.*;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.player.*;

public class TradingBehaviour extends Behavior<AbstractHauntedMerchant>{
    private final float speedModifier;

    public TradingBehaviour(float pSpeedModifier){
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED), Integer.MAX_VALUE);
        this.speedModifier = pSpeedModifier;
    }

    protected boolean checkExtraStartConditions(ServerLevel pLevel, AbstractHauntedMerchant pOwner){
        Player player = pOwner.getTradingPlayer();
        return pOwner.isAlive() && player != null && !pOwner.isInWater() && !pOwner.hurtMarked && pOwner.distanceToSqr(player) <= 16.0D && player.containerMenu != null;
    }

    protected boolean canStillUse(ServerLevel pLevel, AbstractHauntedMerchant pEntity, long pGameTime){
        return this.checkExtraStartConditions(pLevel, pEntity);
    }

    protected void start(ServerLevel pLevel, AbstractHauntedMerchant pEntity, long pGameTime){
        this.followPlayer(pEntity);
    }

    protected void stop(ServerLevel pLevel, AbstractHauntedMerchant pEntity, long pGameTime){
        Brain<?> brain = pEntity.getBrain();
        brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
    }

    protected void tick(ServerLevel pLevel, AbstractHauntedMerchant pOwner, long pGameTime){
        this.followPlayer(pOwner);
    }

    protected boolean timedOut(long pGameTime){
        return false;
    }

    private void followPlayer(AbstractHauntedMerchant pOwner){
        Brain<?> brain = pOwner.getBrain();
        brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(pOwner.getTradingPlayer(), false), this.speedModifier, 2));
        brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(pOwner.getTradingPlayer(), true));
    }
}
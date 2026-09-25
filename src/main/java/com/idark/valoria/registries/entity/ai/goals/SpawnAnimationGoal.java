package com.idark.valoria.registries.entity.ai.goals;

import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import pro.komaru.tridot.client.cinema.*;

import java.util.*;

public class SpawnAnimationGoal extends Goal {
    private final PathfinderMob entity;
    private final ISpawnAnimated spawnAnimated;

    public SpawnAnimationGoal(PathfinderMob entity) {
        this.entity = entity;
        if (!(entity instanceof ISpawnAnimated)) {
            throw new IllegalArgumentException("Entity must implement ISpawnAnimated");
        }
        this.spawnAnimated = (ISpawnAnimated) entity;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP, Goal.Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        return !spawnAnimated.hasSpawned();
    }

    @Override
    public boolean canContinueToUse() {
        return !spawnAnimated.hasSpawned();
    }

    @Override
    public void start() {
        this.entity.getNavigation().stop();
        if (!this.entity.level().isClientSide) {
            if (spawnAnimated.shouldPlayCutscene()) {
                CutsceneHelper.init(this.entity.level(), this.entity.getBoundingBox().inflate(16.0), spawnAnimated.getMaxSpawnAnimationTicks());
            }

            PacketHandler.sendEntity(this.entity, new PlaySpawnCutscenePacket(this.entity));
        }
    }

    @Override
    public void tick() {
        int currentTicks = spawnAnimated.getSpawnAnimationTicks();
        if (currentTicks < spawnAnimated.getMaxSpawnAnimationTicks()) {
            spawnAnimated.setSpawnAnimationTicks(currentTicks + 1);
        } else {
            spawnAnimated.setSpawned(true);
        }
    }
}

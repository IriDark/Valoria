package com.idark.valoria.registries.entity.ai.goals;

import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.player.*;

import java.util.*;

public class FocusOwnerTargetGoal extends TargetGoal {
    private final Mob minion;
    private LivingEntity target;

    public FocusOwnerTargetGoal(Mob pMob) {
        super(pMob, false);
        this.minion = pMob;
    }

    @Override
    public boolean canUse() {
        if (this.minion instanceof OwnableEntity ownable && ownable.getOwner() instanceof Player player) {
            if (player.getPersistentData().contains("ValoriaSummonFocus")) {
                UUID targetUuid = player.getPersistentData().getUUID("ValoriaSummonFocus");
                if (this.minion.level() instanceof ServerLevel serverLevel) {
                    if (serverLevel.getEntity(targetUuid) instanceof LivingEntity livingTarget) {
                        if (livingTarget.isAlive() && !livingTarget.is(player)) {
                            this.target = livingTarget;
                            return true;
                        } else {
                            player.getPersistentData().remove("ValoriaSummonFocus");
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void start() {
        this.minion.setTarget(this.target);
        super.start();
    }
}

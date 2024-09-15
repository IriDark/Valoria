package com.idark.valoria.core.trades;

import net.minecraft.world.entity.ai.village.ReputationEventType;

public interface ReputationTypes extends ReputationEventType {
    ReputationEventType MONSTER_KILLED = ReputationEventType.register("monster_killed");
    ReputationEventType MERCHANT_HURT = ReputationEventType.register("merchant_hurt");
    ReputationEventType MERCHANT_KILLED = ReputationEventType.register("merchant_killed");
}

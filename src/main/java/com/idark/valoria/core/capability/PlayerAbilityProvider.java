package com.idark.valoria.core.capability;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;

import javax.annotation.*;
import java.util.*;

public final class PlayerAbilityProvider {
    private PlayerAbilityProvider(){}

    public static Optional<PlayerAbilityTracker> of(@Nullable Entity entity){
        return entity instanceof Player player ? Optional.of(player.getData(ValoriaAttachments.ABILITIES)) : Optional.empty();
    }
}

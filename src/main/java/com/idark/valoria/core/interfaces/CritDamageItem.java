package com.idark.valoria.core.interfaces;

import net.minecraftforge.event.entity.player.*;

public interface CritDamageItem{
    /**
     * Called when a critical hit is successfully triggered by the player and the attack is fully charged, that means weak attack will be ignored.
     * <p>
     * Implementations can modify the damage, cancel the event, or apply
     * custom effects such as particles, sounds, or debuffs.
     */
    void critDamage(CriticalHitEvent event);
}

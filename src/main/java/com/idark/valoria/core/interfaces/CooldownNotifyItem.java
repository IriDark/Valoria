package com.idark.valoria.core.interfaces;

import net.minecraft.sounds.*;

// todo move to lib
/**
 * Implement this Interface to play a SoundEvent after the Item cooldown ending
 */
@FunctionalInterface
public interface CooldownNotifyItem {
    SoundEvent getSoundEvent();
}
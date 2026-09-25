package com.idark.valoria.core.interfaces;

public interface ISpawnAnimated {
    boolean hasSpawned();
    void setSpawned(boolean spawned);
    int getSpawnAnimationTicks();
    void setSpawnAnimationTicks(int ticks);
    int getMaxSpawnAnimationTicks();
    boolean shouldPlayCutscene();
    
    default void playSpawnCutscene() {}
}

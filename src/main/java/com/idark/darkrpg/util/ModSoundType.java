package com.idark.darkrpg.util;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;

import java.util.function.Supplier;

public class ModSoundType extends SoundType {

    private final Supplier<SoundEvent> breakSoundIn;
    private final Supplier<SoundEvent> stepSoundIn;
    private final Supplier<SoundEvent> placeSoundIn;
    private final Supplier<SoundEvent> hitSoundIn;
    private final Supplier<SoundEvent> fallSoundIn;

    public ModSoundType(float volumeIn, float pitchIn, Supplier<SoundEvent> breakSoundIn, Supplier<SoundEvent> stepSoundIn, Supplier<SoundEvent> placeSoundIn, Supplier<SoundEvent> hitSoundIn, Supplier<SoundEvent> fallSoundIn) {
        super(volumeIn, pitchIn,null,null,null,null,null);
        this.placeSoundIn = placeSoundIn;
        this.breakSoundIn = breakSoundIn;
        this.stepSoundIn = stepSoundIn;
        this.hitSoundIn = hitSoundIn;
        this.fallSoundIn = fallSoundIn;
    }

    @Override
    public SoundEvent getBreakSound() {
        return breakSoundIn.get();
    }

    @Override
    public SoundEvent getStepSound() {
        return stepSoundIn.get();
    }

    @Override
    public SoundEvent getPlaceSound() {
        return placeSoundIn.get();
    }

    @Override
    public SoundEvent getHitSound() {
        return hitSoundIn.get();
    }

    @Override
    public SoundEvent getFallSound() {
        return fallSoundIn.get();
    }
}
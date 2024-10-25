package com.idark.valoria.client.sounds;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.entity.*;
import mod.maxbogomol.fluffy_fur.client.sound.*;
import net.minecraft.client.*;

public class ElementalManipulatorSoundInstance extends BlockEntitySoundInstance<ManipulatorBlockEntity>{
    public ElementalManipulatorSoundInstance(ManipulatorBlockEntity blockEntity, float volume, float pitch) {
        super(blockEntity, SoundsRegistry.MANIPULATOR_LOOP.get(), volume, pitch);
        this.x = blockEntity.getBlockPos().getX() + 0.5f;
        this.y = blockEntity.getBlockPos().getY() + 0.5f;
        this.z = blockEntity.getBlockPos().getZ() + 0.5f;
    }

    public final void stopSound() {
        stop();
    }

    public static ElementalManipulatorSoundInstance getSound(ManipulatorBlockEntity blockEntity) {
        return new ElementalManipulatorSoundInstance(blockEntity, 1, 1);
    }

    public void playSound() {
        Minecraft.getInstance().getSoundManager().queueTickingSound(this);
    }
}
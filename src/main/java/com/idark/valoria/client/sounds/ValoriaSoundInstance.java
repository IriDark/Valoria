package com.idark.valoria.client.sounds;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ValoriaSoundInstance extends AbstractTickableSoundInstance{
    public final LocalPlayer player;

    public ValoriaSoundInstance(SoundEvent sound, LocalPlayer pPlayer){
        super(sound, SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
        this.player = pPlayer;
        this.looping = false;
        this.delay = 0;
        this.volume = 1.10F;
    }

    public ValoriaSoundInstance(SoundEvent sound, SoundSource source, LocalPlayer pPlayer){
        super(sound, source, SoundInstance.createUnseededRandom());
        this.player = pPlayer;
        this.looping = false;
        this.delay = 0;
        this.volume = 1.10F;
    }

    public void tick(){
        if(!this.player.isRemoved()){
            this.x = (float)this.player.getX();
            this.y = (float)this.player.getY();
            this.z = (float)this.player.getZ();
        }else{
            this.stop();
        }
    }
}
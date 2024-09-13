package com.idark.valoria.registries.sounds;

import com.idark.valoria.registries.*;
import net.minecraft.client.player.*;
import net.minecraft.client.resources.sounds.*;
import net.minecraft.sounds.*;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class CooldownSoundInstance extends AbstractTickableSoundInstance{
    public final LocalPlayer player;
    public CooldownSoundInstance(LocalPlayer pPlayer){
        super(SoundsRegistry.RECHARGE.get(), SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
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
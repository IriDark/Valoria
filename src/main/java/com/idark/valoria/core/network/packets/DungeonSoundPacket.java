package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.client.sounds.*;
import net.minecraft.client.*;
import net.minecraft.client.sounds.*;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.network.*;
import net.minecraftforge.registries.*;

import java.util.function.*;

public class DungeonSoundPacket{
    private final double posX;
    private final double posY;
    private final double posZ;
    private final SoundEvent event;

    public DungeonSoundPacket(SoundEvent event, double posX, double posY, double posZ){
        this.event = event;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static DungeonSoundPacket decode(FriendlyByteBuf buf){
        ResourceLocation soundID = buf.readResourceLocation();
        SoundEvent event = ForgeRegistries.SOUND_EVENTS.getValue(soundID);
        return new DungeonSoundPacket(event, buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    @OnlyIn(Dist.CLIENT)
    public static void playSound(SoundEvent event){
        SoundManager soundManager = Minecraft.getInstance().getSoundManager();
        if(ValoriaClient.DUNGEON_MUSIC_INSTANCE != null && soundManager.isActive(ValoriaClient.DUNGEON_MUSIC_INSTANCE)){
            return;
        }

        ValoriaClient.DUNGEON_MUSIC_INSTANCE = new ValoriaSoundInstance(event, SoundSource.MUSIC, Minecraft.getInstance().player);
        soundManager.play(ValoriaClient.DUNGEON_MUSIC_INSTANCE);
        if(!soundManager.isActive(ValoriaClient.DUNGEON_MUSIC_INSTANCE)){
            ValoriaClient.DUNGEON_MUSIC_INSTANCE = null;
        }
    }

    public static void handle(DungeonSoundPacket msg, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;
            playSound(msg.event);
        });

        ctx.get().setPacketHandled(true);
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeResourceLocation(event.getLocation());
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }
}
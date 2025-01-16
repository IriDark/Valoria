package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import net.minecraft.network.*;
import net.minecraft.sounds.*;
import net.minecraftforge.network.NetworkEvent.*;

import java.util.*;
import java.util.function.*;

public class UpdateBossbarPacket {
    private final UUID bossBar;
    private final String id;
    private final SoundEvent bossMusic;
    public UpdateBossbarPacket(UUID bossBar, String id, SoundEvent bossMusic) {
        this.bossBar = bossBar;
        this.id = id;
        this.bossMusic = bossMusic;
    }

    public static UpdateBossbarPacket decode(FriendlyByteBuf buf) {
        return new UpdateBossbarPacket(buf.readUUID(), buf.readUtf(), SoundEvent.createFixedRangeEvent(buf.readResourceLocation(), 16));
    }

    public static void encode(UpdateBossbarPacket msg, FriendlyByteBuf buf) {
        buf.writeUUID(msg.bossBar);
        buf.writeUtf(msg.id);
        buf.writeResourceLocation(msg.bossMusic.getLocation());
    }

    public static void handle(UpdateBossbarPacket msg, Supplier<Context> context) {
        context.get().setPacketHandled(true);
        if(Objects.equals(msg.id, "empty")){
            Valoria.proxy.removeBossBarRender(msg.bossBar);
        }else{
            Valoria.proxy.setBossBarRender(msg.bossBar, msg.id, msg.bossMusic);
        }
    }
}
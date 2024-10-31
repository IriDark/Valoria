package com.idark.valoria.core.network.packets;

import com.idark.valoria.core.proxy.*;
import net.minecraft.network.*;
import net.minecraftforge.network.NetworkEvent.*;

import java.util.*;
import java.util.function.*;

public class UpdateBossbarPacket {
    private final UUID bossBar;
    private final String id;

    public UpdateBossbarPacket(UUID bossBar, String id) {
        this.bossBar = bossBar;
        this.id = id;
    }

    public static UpdateBossbarPacket decode(FriendlyByteBuf buf) {
        return new UpdateBossbarPacket(buf.readUUID(), buf.readUtf());
    }

    public static void encode(UpdateBossbarPacket msg, FriendlyByteBuf buf) {
        buf.writeUUID(msg.bossBar);
        buf.writeUtf(msg.id);
    }

    public static void handle(UpdateBossbarPacket msg, Supplier<Context> context) {
        context.get().setPacketHandled(true);
        if(Objects.equals(msg.id, "empty")){
            ClientProxy.removeBossBarRender(msg.bossBar);
        }else{
            ClientProxy.setBossBarRender(msg.bossBar, msg.id);
        }
    }
}
package com.idark.valoria.client.cinema;

import net.minecraft.network.*;
import net.minecraftforge.network.NetworkEvent.*;

import java.util.function.*;

public class CutsceneSkippedPacket{
    public CutsceneSkippedPacket(){}

    public static void encode(CutsceneSkippedPacket object, FriendlyByteBuf buffer){}

    public static CutsceneSkippedPacket decode(FriendlyByteBuf buffer){
        return new CutsceneSkippedPacket();
    }

    public void handle(Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var player = ctx.get().getSender();
            if(player == null) return;
            CutsceneHelper.stop(player);
        });

        ctx.get().setPacketHandled(true);
    }
}
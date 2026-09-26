package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.core.network.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.server.level.*;

public class ProgressionDisableCodexPacket extends RateLimitedPacket{
    public static final CustomPacketPayload.Type<ProgressionDisableCodexPacket> TYPE = new CustomPacketPayload.Type<>(Valoria.loc("progression_disable_codex_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ProgressionDisableCodexPacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> ProgressionDisableCodexPacket.encode(msg, buf), ProgressionDisableCodexPacket::decode);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
    public ProgressionDisableCodexPacket(){}

    public static void encode(ProgressionDisableCodexPacket packet, FriendlyByteBuf buf){}

    public static ProgressionDisableCodexPacket decode(FriendlyByteBuf buffer){
        return new ProgressionDisableCodexPacket();
    }

    @Override
    public void execute(ServerPlayer player){
        if(player.hasPermissions(2)) {
            var conf = ServerConfig.ENABLE_CODEX_PROGRESSION;
            boolean current = conf.get();
            conf.set(!current);
            conf.save();
        }
    }
}
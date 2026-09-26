package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.core.network.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.server.level.*;
import net.neoforged.neoforge.network.handling.*;

public class ReadCodexPacket implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<ReadCodexPacket> TYPE = new CustomPacketPayload.Type<>(Valoria.loc("read_codex_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ReadCodexPacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> msg.encode(buf), ReadCodexPacket::decode);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
    private final String unlockableId;

    public ReadCodexPacket(String unlockableId) {
        this.unlockableId = unlockableId;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(unlockableId);
    }

    public static ReadCodexPacket decode(FriendlyByteBuf buffer){
        return new ReadCodexPacket(buffer.readUtf());
    }

    public void handle(IPayloadContext ctx) {
        ServerPlayer player = ((ServerPlayer)ctx.player());
        Unlockable unlockable = Unlockables.unlockableMap.get(unlockableId);
        if (unlockable != null && player != null) {
            UnlockUtils.markViewed(player, unlockable);
            PacketHandler.sendTo(player, new UnlockableUpdatePacket(player));
        }
    }
}
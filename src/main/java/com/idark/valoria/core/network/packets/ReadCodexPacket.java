package com.idark.valoria.core.network.packets;

import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.core.network.*;
import net.minecraft.network.*;
import net.minecraft.server.level.*;
import net.minecraftforge.network.NetworkEvent.*;

import java.util.function.*;

public class ReadCodexPacket{
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

    public void handle(Supplier<Context> ctx) {
        ServerPlayer player = ctx.get().getSender();
        Unlockable unlockable = Unlockables.unlockableMap.get(unlockableId);
        if (unlockable != null && player != null) {
            UnlockUtils.markViewed(player, unlockable);
            PacketHandler.sendTo(player, new UnlockableUpdatePacket(player));
        }

        ctx.get().setPacketHandled(true);
    }
}
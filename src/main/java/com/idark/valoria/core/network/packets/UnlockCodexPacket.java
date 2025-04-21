package com.idark.valoria.core.network.packets;

import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.core.network.*;
import net.minecraft.network.*;
import net.minecraft.server.level.*;
import net.minecraftforge.network.NetworkEvent.*;

import java.util.function.*;

public class UnlockCodexPacket {
    private final String unlockableId;

    public UnlockCodexPacket(String unlockableId) {
        this.unlockableId = unlockableId;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(unlockableId);
    }

    public static UnlockCodexPacket decode(FriendlyByteBuf buffer){
        return new UnlockCodexPacket(buffer.readUtf());
    }

    public void handle(Supplier<Context> ctx) {
        ServerPlayer player = ctx.get().getSender();
        Unlockable unlockable = Unlockables.unlockableMap.get(unlockableId);
        if (unlockable != null && player != null) {
            UnlockUtils.claim(player, unlockable);
            unlockable.award(player);
            PacketHandler.sendTo(player, new UnlockableUpdatePacket(player));
        }

        ctx.get().setPacketHandled(true);
    }
}
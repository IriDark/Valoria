package com.idark.valoria.core.network.packets;

import com.idark.valoria.api.events.CodexEvent.*;
import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.core.network.*;
import net.minecraft.network.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.*;
import net.minecraftforge.common.*;

public class UnlockCodexPacket extends RateLimitedPacket {
    private final String unlockableId;

    public UnlockCodexPacket(String unlockableId) {
        this.unlockableId = unlockableId;
    }

    public static void encode(UnlockCodexPacket packet, FriendlyByteBuf buf) {
        buf.writeUtf(packet.unlockableId);
    }

    public static UnlockCodexPacket decode(FriendlyByteBuf buffer){
        return new UnlockCodexPacket(buffer.readUtf());
    }

    @Override
    public void execute(ServerPlayer player) {
        Unlockable unlockable = Unlockables.unlockableMap.get(unlockableId);
        if (unlockable != null && player != null) {
            if(!UnlockUtils.isUnlocked(player, unlockable) || UnlockUtils.isClaimed(player, unlockable)) return;

            UnlockUtils.claim(player, unlockable);
            if(!onClaim(player, unlockable)){
                unlockable.award(player);
            }

            PacketHandler.sendTo(player, new UnlockableUpdatePacket(player));
        }
    }

    public static boolean onClaim(Player player, Unlockable unlockable) {
        return MinecraftForge.EVENT_BUS.post(new OnRewardClaim(player, unlockable));
    }
}
package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.api.events.CodexEvent.*;
import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.core.network.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.*;
import net.neoforged.neoforge.common.*;

public class UnlockCodexPacket extends RateLimitedPacket{
    public static final CustomPacketPayload.Type<UnlockCodexPacket> TYPE = new CustomPacketPayload.Type<>(Valoria.loc("unlock_codex_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, UnlockCodexPacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> UnlockCodexPacket.encode(msg, buf), UnlockCodexPacket::decode);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
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
        return NeoForge.EVENT_BUS.post(new OnRewardClaim(player, unlockable)).isCanceled(); // PORT NOTE: post() returns the event in NeoForge
    }
}
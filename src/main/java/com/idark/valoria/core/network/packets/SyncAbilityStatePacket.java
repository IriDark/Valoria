package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.ability.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.neoforged.neoforge.network.handling.*;

public class SyncAbilityStatePacket implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<SyncAbilityStatePacket> TYPE = new CustomPacketPayload.Type<>(Valoria.loc("sync_ability_state_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncAbilityStatePacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> SyncAbilityStatePacket.encode(msg, buf), SyncAbilityStatePacket::decode);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
    private final String abilityId;
    private final long cooldownEndTime;
    private final int maxCooldownTicks;
    private final int usages;

    public SyncAbilityStatePacket(String abilityId, long cooldownEndTime, int maxCooldownTicks, int usages) {
        this.abilityId = abilityId;
        this.cooldownEndTime = cooldownEndTime;
        this.maxCooldownTicks = maxCooldownTicks;
        this.usages = usages;
    }

    public static void encode(SyncAbilityStatePacket msg, FriendlyByteBuf buffer) {
        buffer.writeUtf(msg.abilityId);
        buffer.writeLong(msg.cooldownEndTime);
        buffer.writeInt(msg.maxCooldownTicks);
        buffer.writeInt(msg.usages);
    }

    public static SyncAbilityStatePacket decode(FriendlyByteBuf buffer) {
        return new SyncAbilityStatePacket(
            buffer.readUtf(),
            buffer.readLong(),
            buffer.readInt(),
            buffer.readInt()
        );
    }

    public static void handle(SyncAbilityStatePacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            AbilityHelper.updateClientState(msg.abilityId, msg.cooldownEndTime, msg.maxCooldownTicks, msg.usages);
        });
    }
}

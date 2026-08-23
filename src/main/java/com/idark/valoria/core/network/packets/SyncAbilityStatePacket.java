package com.idark.valoria.core.network.packets;

import com.idark.valoria.registries.item.ability.*;
import net.minecraft.network.*;
import net.minecraftforge.network.*;

import java.util.function.*;

public class SyncAbilityStatePacket {
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

    public static void handle(SyncAbilityStatePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            AbilityHelper.updateClientState(msg.abilityId, msg.cooldownEndTime, msg.maxCooldownTicks, msg.usages);
        });
        ctx.get().setPacketHandled(true);
    }
}

package com.idark.valoria.core.network;

import com.idark.valoria.*;
import net.minecraft.server.level.*;
import net.minecraftforge.network.NetworkEvent.*;

import java.util.*;
import java.util.function.*;

public abstract class RateLimitedPacket{
    private static final Map<UUID, Long> SPAM_FILTER = new java.util.concurrent.ConcurrentHashMap<>();
    private static final long RATE_LIMIT_TICKS = 3L;

    public static <MSG extends RateLimitedPacket> void processPacket(MSG packet, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if(player == null) return;

            long now = player.level().getGameTime();
            long lastTime = SPAM_FILTER.getOrDefault(player.getUUID(), 0L);
            if (now - lastTime < RATE_LIMIT_TICKS) {
                Valoria.LOGGER.debug("Packet Spam prevented from Player {}", player.getName().getString());
                return;
            }

            SPAM_FILTER.put(player.getUUID(), now);
            packet.execute(player);
        });

        ctx.get().setPacketHandled(true);
    }

    public abstract void execute(ServerPlayer player);
}

package com.idark.valoria.core.network;

import com.google.common.cache.*;
import com.idark.valoria.*;
import net.minecraft.server.level.*;
import net.minecraftforge.network.NetworkEvent.*;

import java.util.concurrent.*;
import java.util.function.*;

public abstract class RateLimitedPacket{
    private static final Cache<String, Long> RATE_LIMITER = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.MINUTES).build();
    private static final long RATE_LIMIT_TICKS = 3L;

    public static <MSG extends RateLimitedPacket> void processPacket(MSG packet, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if(player == null) return;

            long now = player.level().getGameTime();
            String key = player.getStringUUID() + ":" + packet.getClass().getSimpleName();
            Long lastTime = RATE_LIMITER.getIfPresent(key);
            if (lastTime == null) lastTime = 0L;

            if (now - lastTime < RATE_LIMIT_TICKS) {
                Valoria.LOGGER.debug("Packet Spam prevented from Player {}", player.getName().getString());
                return;
            }

            RATE_LIMITER.put(key, now);
            packet.execute(player);
        });

        ctx.get().setPacketHandled(true);
    }

    public abstract void execute(ServerPlayer player);
}

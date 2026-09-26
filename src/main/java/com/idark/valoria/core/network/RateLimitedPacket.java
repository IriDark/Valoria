package com.idark.valoria.core.network;

import com.google.common.cache.*;
import com.idark.valoria.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.server.level.*;
import net.neoforged.neoforge.network.handling.*;

import java.util.concurrent.*;

public abstract class RateLimitedPacket implements CustomPacketPayload{
    private static final Cache<String, Long> RATE_LIMITER = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.MINUTES).build();
    private static final long RATE_LIMIT_TICKS = 3L;

    public static <MSG extends RateLimitedPacket> void processPacket(MSG packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if(!(ctx.player() instanceof ServerPlayer player)) return;

            long now = player.level().getGameTime();
            String key = player.getStringUUID() + ":" + packet.getClass().getSimpleName();
            Long lastTime = RATE_LIMITER.getIfPresent(key);
            if (lastTime == null) lastTime = 0L;

            if (now - lastTime < RATE_LIMIT_TICKS) {
                Valoria.LOGGER.debug("Tried to receive {}, ignoring packet spam from Player {}", packet.getClass().getSimpleName(), player.getName().getString());
                return;
            }

            RATE_LIMITER.put(key, now);
            packet.execute(player);
        });
    }

    public abstract void execute(ServerPlayer player);
}

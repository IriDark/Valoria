package com.idark.valoria.core.network.packets;

import com.idark.valoria.core.network.*;
import com.idark.valoria.registries.entity.living.boss.firron.*;
import net.minecraft.network.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;

import java.util.*;

public class FirronKeyframePacket extends RateLimitedPacket{
    private final UUID entityId;
    private final String keyframe;

    public FirronKeyframePacket(UUID entityId, String keyframe) {
        this.entityId = entityId;
        this.keyframe = keyframe;
    }

    public static void encode(FirronKeyframePacket msg, FriendlyByteBuf buf) {
        buf.writeUUID(msg.entityId);
        buf.writeUtf(msg.keyframe);
    }

    public static FirronKeyframePacket decode(FriendlyByteBuf buf) {
        return new FirronKeyframePacket(buf.readUUID(), buf.readUtf());
    }

    public void execute(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        Entity entity = level.getEntity(this.entityId);

        if (entity instanceof Firron firron) {
            firron.handleKeyframe(this.keyframe);
        }
    }
}
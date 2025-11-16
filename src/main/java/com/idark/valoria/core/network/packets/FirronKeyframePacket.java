package com.idark.valoria.core.network.packets;

import com.idark.valoria.registries.entity.living.boss.firron.*;
import net.minecraft.network.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraftforge.network.NetworkEvent.*;

import java.util.*;
import java.util.function.*;

public class FirronKeyframePacket {
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

    public static void handle(FirronKeyframePacket msg, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerLevel level = ctx.get().getSender().serverLevel();
            Entity entity = level.getEntity(msg.entityId);

            if (entity instanceof Firron firron) {
                firron.handleKeyframe(msg.keyframe);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
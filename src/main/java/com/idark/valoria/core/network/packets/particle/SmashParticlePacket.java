package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import net.minecraft.network.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.*;

import java.util.*;
import java.util.function.*;

public class SmashParticlePacket {
    private final UUID id;
    private final SmashType type;

    public SmashParticlePacket(UUID id) {
        this(id, SmashType.DEFAULT);
    }

    public SmashParticlePacket(UUID id, SmashType type) {
        this.id = id;
        this.type = type != null ? type : SmashType.DEFAULT;
    }

    public static SmashParticlePacket decode(FriendlyByteBuf buf) {
        return new SmashParticlePacket(buf.readUUID(), buf.readEnum(SmashType.class));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(id);
        buf.writeEnum(type);
    }

    public static void handle(SmashParticlePacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                Level level = Valoria.proxy.getLevel();
                Player player = level.getPlayerByUUID(msg.id);
                if (player != null) {
                    Vec3 pos = new Vec3(player.getX(), player.getY() + 1, player.getZ());
                    SmashFX.play(level, player, pos, msg.type);
                } else {
                    Valoria.LOGGER.error("Player with UUID {} not found for smash particle", msg.id);
                }
                ctx.get().setPacketHandled(true);
            });
        }
    }
}
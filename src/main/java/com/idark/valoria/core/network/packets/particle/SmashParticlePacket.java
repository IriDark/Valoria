package com.idark.valoria.core.network.packets.particle;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.network.handling.*;

import java.util.*;

public class SmashParticlePacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SmashParticlePacket> TYPE = new CustomPacketPayload.Type<>(Valoria.loc("smash_particle_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SmashParticlePacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> msg.encode(buf), SmashParticlePacket::decode);

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

    public static void handle(SmashParticlePacket msg, IPayloadContext ctx){
        if (ctx.flow().isClientbound()) {
            ctx.enqueueWork(() -> {
                Level level = Valoria.proxy.getLevel();
                Player player = level.getPlayerByUUID(msg.id);
                if (player != null) {
                    Vec3 pos = new Vec3(player.getX(), player.getY() + 1, player.getZ());
                    SmashFX.play(level, player, pos, msg.type);
                } else {
                    Valoria.LOGGER.error("Player with UUID {} not found for smash particle", msg.id);
                }
            });
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type(){
        return TYPE;
    }
}
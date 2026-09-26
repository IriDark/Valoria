package com.idark.valoria.core.network.packets;

import com.idark.valoria.*;
import com.idark.valoria.core.interfaces.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.world.entity.*;
import net.neoforged.neoforge.network.handling.*;

public class PlaySpawnCutscenePacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<PlaySpawnCutscenePacket> TYPE = new CustomPacketPayload.Type<>(Valoria.loc("play_spawn_cutscene_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PlaySpawnCutscenePacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> PlaySpawnCutscenePacket.encode(msg, buf), PlaySpawnCutscenePacket::decode);
    private final int entityId;

    public PlaySpawnCutscenePacket(int entityId) {
        this.entityId = entityId;
    }

    public PlaySpawnCutscenePacket(LivingEntity entity) {
        this.entityId = entity.getId();
    }

    public static void encode(PlaySpawnCutscenePacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
    }

    public static PlaySpawnCutscenePacket decode(FriendlyByteBuf buf) {
        return new PlaySpawnCutscenePacket(buf.readInt());
    }

    public static void handle(PlaySpawnCutscenePacket msg, IPayloadContext ctx){
        if(ctx.flow().isClientbound()){
            ctx.enqueueWork(() -> {
                if(Minecraft.getInstance().level != null){
                    Entity entity = Minecraft.getInstance().level.getEntity(msg.entityId);
                    if(entity instanceof ISpawnAnimated spawnAnimated && !spawnAnimated.hasSpawned()){
                        spawnAnimated.playSpawnCutscene();
                    }
                }
            });
        }
    }

    @Override
    public Type type() {
        return TYPE;
    }
}

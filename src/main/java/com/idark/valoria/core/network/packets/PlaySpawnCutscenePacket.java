package com.idark.valoria.core.network.packets;

import com.idark.valoria.core.interfaces.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.world.entity.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.network.*;

import java.util.function.*;

public class PlaySpawnCutscenePacket {
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

    public static void handle(PlaySpawnCutscenePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                if (Minecraft.getInstance().level != null) {
                    Entity entity = Minecraft.getInstance().level.getEntity(msg.entityId);
                    if (entity instanceof ISpawnAnimated spawnAnimated && !spawnAnimated.hasSpawned()) {
                        spawnAnimated.playSpawnCutscene();
                    }
                }
            });
        });
        ctx.get().setPacketHandled(true);
    }
}

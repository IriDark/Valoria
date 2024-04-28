package com.idark.valoria.core.network;

import com.idark.valoria.Valoria;
import com.idark.valoria.core.network.packets.*;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public final class PacketHandler {
    private static final String PROTOCOL = "10";
    public static final SimpleChannel HANDLER = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Valoria.ID, "network"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );

    public static void init() {
        int id = 0;
        HANDLER.registerMessage(id++, UnlockableUpdatePacket.class, UnlockableUpdatePacket::encode, UnlockableUpdatePacket::decode, UnlockableUpdatePacket::handle);
        HANDLER.registerMessage(id++, CooldownSoundPacket.class, CooldownSoundPacket::encode, CooldownSoundPacket::decode, CooldownSoundPacket::handle);
        HANDLER.registerMessage(id++, PageToastPacket.class, PageToastPacket::encode, PageToastPacket::decode, PageToastPacket::handle);
        HANDLER.registerMessage(id++, NecromancerSummonParticlePacket.class, NecromancerSummonParticlePacket::encode, NecromancerSummonParticlePacket::decode, NecromancerSummonParticlePacket::handle);
        HANDLER.registerMessage(id++, LineToNearbyMobsParticlePacket.class, LineToNearbyMobsParticlePacket::encode, LineToNearbyMobsParticlePacket::decode, LineToNearbyMobsParticlePacket::handle);
        HANDLER.registerMessage(id++, CircleShapedParticlePacket.class, CircleShapedParticlePacket::encode, CircleShapedParticlePacket::decode, CircleShapedParticlePacket::handle);
        HANDLER.registerMessage(id++, CubeShapedParticlePacket.class, CubeShapedParticlePacket::encode, CubeShapedParticlePacket::decode, CubeShapedParticlePacket::handle);
        HANDLER.registerMessage(id++, ManipulatorParticlePacket.class, ManipulatorParticlePacket::encode, ManipulatorParticlePacket::decode, ManipulatorParticlePacket::handle);
        HANDLER.registerMessage(id++, ManipulatorCraftParticlePacket.class, ManipulatorCraftParticlePacket::encode, ManipulatorCraftParticlePacket::decode, ManipulatorCraftParticlePacket::handle);
        HANDLER.registerMessage(id++, ManipulatorEmptyParticlePacket.class, ManipulatorEmptyParticlePacket::encode, ManipulatorEmptyParticlePacket::decode, ManipulatorEmptyParticlePacket::handle);
        HANDLER.registerMessage(id++, MurasamaParticlePacket.class, MurasamaParticlePacket::encode, MurasamaParticlePacket::decode, MurasamaParticlePacket::handle);
        HANDLER.registerMessage(id++, SarcophagusSummonPacket.class, SarcophagusSummonPacket::encode, SarcophagusSummonPacket::decode, SarcophagusSummonPacket::handle);
        HANDLER.registerMessage(id++, FireTrapParticlePacket.class, FireTrapParticlePacket::encode, FireTrapParticlePacket::decode, FireTrapParticlePacket::handle);
        HANDLER.registerMessage(id++, KeypadParticlePacket.class, KeypadParticlePacket::encode, KeypadParticlePacket::decode, KeypadParticlePacket::handle);
    }

    private static final PacketDistributor<Pair<Level, BlockPos>> TRACKING_CHUNK_AND_NEAR = new PacketDistributor<>(
            (_d, pairSupplier) -> {
                var pair = pairSupplier.get();
                var level = pair.getFirst();
                var blockpos = pair.getSecond();
                var chunkpos = new ChunkPos(blockpos);
                return packet -> {
                    var players = ((ServerChunkCache) level.getChunkSource()).chunkMap
                            .getPlayers(chunkpos, false);
                    for (var player : players) {
                        if (player.distanceToSqr(blockpos.getX(), blockpos.getY(), blockpos.getZ()) < 64 * 64) {
                            player.connection.send(packet);
                        }
                    }
                };
            },
            NetworkDirection.PLAY_TO_CLIENT
    );

    public static void sendTo(ServerPlayer playerMP, Object toSend) {
        HANDLER.sendTo(toSend, playerMP.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendNonLocal(ServerPlayer playerMP, Object toSend) {
        if (playerMP.server.isDedicatedServer() || !playerMP.getGameProfile().getName().equals(playerMP.server.getLocalIp())) {
            sendTo(playerMP, toSend);
        }
    }

    public static void sendToTracking(Level world, BlockPos pos, Object msg) {
        HANDLER.send(TRACKING_CHUNK_AND_NEAR.with(() -> Pair.of(world, pos)), msg);
    }

    public static void sendTo(Player entity, Object msg) {
        HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) entity), msg);
    }

    public static void sendToServer(Object msg) {
        HANDLER.sendToServer(msg);
    }

    private PacketHandler() {
    }
}
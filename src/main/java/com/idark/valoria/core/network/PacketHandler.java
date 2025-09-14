package com.idark.valoria.core.network;

import com.idark.valoria.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.mojang.datafixers.util.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraftforge.network.*;
import net.minecraftforge.network.simple.*;
import net.minecraftforge.server.*;

public final class PacketHandler{
    private static final String PROTOCOL = "10";
    public static final SimpleChannel HANDLER = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Valoria.ID, "network"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );
    private static final PacketDistributor<Pair<Level, BlockPos>> TRACKING_CHUNK_AND_NEAR = new PacketDistributor<>(
            (_d, pairSupplier) -> {
                var pair = pairSupplier.get();
                var level = pair.getFirst();
                var blockpos = pair.getSecond();
                var chunkpos = new ChunkPos(blockpos);
                return packet -> {
                    var players = ((ServerChunkCache)level.getChunkSource()).chunkMap
                            .getPlayers(chunkpos, false);
                    for(var player : players){
                        if(player.distanceToSqr(blockpos.getX(), blockpos.getY(), blockpos.getZ()) < 64 * 64){
                            player.connection.send(packet);
                        }
                    }
                };
            },
            NetworkDirection.PLAY_TO_CLIENT
    );

    private PacketHandler(){
    }

    public static void init(){
        int id = 0;
        HANDLER.registerMessage(id++, BeastAttackParticlePacket.class, BeastAttackParticlePacket::encode, BeastAttackParticlePacket::decode, BeastAttackParticlePacket::handle);
        HANDLER.registerMessage(id++, CystSummonParticlePacket.class, CystSummonParticlePacket::encode, CystSummonParticlePacket::decode, CystSummonParticlePacket::handle);
        HANDLER.registerMessage(id++, MinionSummonParticlePacket.class, MinionSummonParticlePacket::encode, MinionSummonParticlePacket::decode, MinionSummonParticlePacket::handle);
        HANDLER.registerMessage(id++, SoulCollectParticlePacket.class, SoulCollectParticlePacket::encode, SoulCollectParticlePacket::decode, SoulCollectParticlePacket::handle);
        HANDLER.registerMessage(id++, UnlockableUpdatePacket.class, UnlockableUpdatePacket::encode, UnlockableUpdatePacket::decode, UnlockableUpdatePacket::handle);
        HANDLER.registerMessage(id++, PageToastPacket.class, PageToastPacket::encode, PageToastPacket::decode, PageToastPacket::handle);
        HANDLER.registerMessage(id++, LineToNearbyMobsParticlePacket.class, LineToNearbyMobsParticlePacket::encode, LineToNearbyMobsParticlePacket::decode, LineToNearbyMobsParticlePacket::handle);
        HANDLER.registerMessage(id++, CircleShapedParticlePacket.class, CircleShapedParticlePacket::encode, CircleShapedParticlePacket::decode, CircleShapedParticlePacket::handle);
        HANDLER.registerMessage(id++, CubeShapedParticlePacket.class, CubeShapedParticlePacket::encode, CubeShapedParticlePacket::decode, CubeShapedParticlePacket::handle);
        HANDLER.registerMessage(id++, ManipulatorCraftParticlePacket.class, ManipulatorCraftParticlePacket::encode, ManipulatorCraftParticlePacket::decode, ManipulatorCraftParticlePacket::handle);
        HANDLER.registerMessage(id++, ManipulatorEmptyParticlePacket.class, ManipulatorEmptyParticlePacket::encode, ManipulatorEmptyParticlePacket::decode, ManipulatorEmptyParticlePacket::handle);
        HANDLER.registerMessage(id++, MurasamaParticlePacket.class, MurasamaParticlePacket::encode, MurasamaParticlePacket::decode, MurasamaParticlePacket::handle);
        HANDLER.registerMessage(id++, SmokeParticlePacket.class, SmokeParticlePacket::encode, SmokeParticlePacket::decode, SmokeParticlePacket::handle);
        HANDLER.registerMessage(id++, FireTrapParticlePacket.class, FireTrapParticlePacket::encode, FireTrapParticlePacket::decode, FireTrapParticlePacket::handle);
        HANDLER.registerMessage(id++, KeypadParticlePacket.class, KeypadParticlePacket::encode, KeypadParticlePacket::decode, KeypadParticlePacket::handle);
        HANDLER.registerMessage(id++, ParticleLinePacket.class, ParticleLinePacket::encode, ParticleLinePacket::decode, ParticleLinePacket::handle);
        HANDLER.registerMessage(id++, CuriosSetStackPacket.class, CuriosSetStackPacket::encode, CuriosSetStackPacket::decode, CuriosSetStackPacket::handle);
        HANDLER.registerMessage(id++, DashParticlePacket.class, DashParticlePacket::encode, DashParticlePacket::decode, DashParticlePacket::handle);
        HANDLER.registerMessage(id++, MusicToastPacket.class, MusicToastPacket::encode, MusicToastPacket::decode, MusicToastPacket::handle);
        HANDLER.registerMessage(id++, UnlockCodexPacket.class, UnlockCodexPacket::encode, UnlockCodexPacket::decode, UnlockCodexPacket::handle);
        HANDLER.registerMessage(id++, NihilityPacket.class, NihilityPacket::encode, NihilityPacket::decode, NihilityPacket::handle);
        HANDLER.registerMessage(id++, ManipulatorParticlePacket.class, ManipulatorParticlePacket::encode, ManipulatorParticlePacket::decode, ManipulatorParticlePacket::handle);
        HANDLER.registerMessage(id++, HeavyWorkbenchCraftPacket.class, HeavyWorkbenchCraftPacket::encode, HeavyWorkbenchCraftPacket::decode, HeavyWorkbenchCraftPacket::handle);
    }

    public static void sendTo(ServerPlayer playerMP, Object toSend){
        HANDLER.sendTo(toSend, playerMP.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToAll(Object message){
        for(ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()){
            sendNonLocal(message, player);
        }
    }

    public static void sendNonLocal(Object msg, ServerPlayer player){
        HANDLER.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendNonLocal(ServerPlayer playerMP, Object toSend){
        if(playerMP.server.isDedicatedServer() || !playerMP.getGameProfile().getName().equals(playerMP.server.getLocalIp())){
            sendTo(playerMP, toSend);
        }
    }

    public static void sendToTracking(Level world, BlockPos pos, Object msg){
        HANDLER.send(TRACKING_CHUNK_AND_NEAR.with(() -> Pair.of(world, pos)), msg);
    }

    public static void sendTo(Player entity, Object msg){
        HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)entity), msg);
    }

    public static void sendEntity(Player entity, Object msg){
        HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), msg);
    }

    public static void sendToServer(Object msg){
        HANDLER.sendToServer(msg);
    }
}
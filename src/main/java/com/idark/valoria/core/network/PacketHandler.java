package com.idark.valoria.core.network;

import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.core.network.packets.particle.*;
import net.minecraft.core.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.network.*;
import net.neoforged.neoforge.network.event.*;
import net.neoforged.neoforge.network.registration.*;

public final class PacketHandler{
    private static final String PROTOCOL = "10";

    private PacketHandler(){
    }

    public static void register(RegisterPayloadHandlersEvent event){
        PayloadRegistrar r = event.registrar(PROTOCOL);
        r.playToClient(BeastAttackParticlePacket.TYPE, BeastAttackParticlePacket.STREAM_CODEC, BeastAttackParticlePacket::handle);
        r.playToClient(CystSummonParticlePacket.TYPE, CystSummonParticlePacket.STREAM_CODEC, CystSummonParticlePacket::handle);
        r.playToClient(MinionSummonParticlePacket.TYPE, MinionSummonParticlePacket.STREAM_CODEC, MinionSummonParticlePacket::handle);
        r.playToClient(SoulCollectParticlePacket.TYPE, SoulCollectParticlePacket.STREAM_CODEC, SoulCollectParticlePacket::handle);
        r.playToClient(VampirismParticlePacket.TYPE, VampirismParticlePacket.STREAM_CODEC, VampirismParticlePacket::handle);
        r.playToClient(UnlockableUpdatePacket.TYPE, UnlockableUpdatePacket.STREAM_CODEC, UnlockableUpdatePacket::handle);
        r.playToClient(PageToastPacket.TYPE, PageToastPacket.STREAM_CODEC, PageToastPacket::handle);
        r.playToClient(LineToNearbyMobsParticlePacket.TYPE, LineToNearbyMobsParticlePacket.STREAM_CODEC, LineToNearbyMobsParticlePacket::handle);
        r.playToClient(CircleShapedParticlePacket.TYPE, CircleShapedParticlePacket.STREAM_CODEC, CircleShapedParticlePacket::handle);
        r.playToClient(CubeShapedParticlePacket.TYPE, CubeShapedParticlePacket.STREAM_CODEC, CubeShapedParticlePacket::handle);
        r.playToClient(ManipulatorCraftParticlePacket.TYPE, ManipulatorCraftParticlePacket.STREAM_CODEC, ManipulatorCraftParticlePacket::handle);
        r.playToClient(ManipulatorEmptyParticlePacket.TYPE, ManipulatorEmptyParticlePacket.STREAM_CODEC, ManipulatorEmptyParticlePacket::handle);
        r.playToClient(MurasamaParticlePacket.TYPE, MurasamaParticlePacket.STREAM_CODEC, MurasamaParticlePacket::handle);
        r.playToClient(SmokeParticlePacket.TYPE, SmokeParticlePacket.STREAM_CODEC, SmokeParticlePacket::handle);
        r.playToClient(FireTrapParticlePacket.TYPE, FireTrapParticlePacket.STREAM_CODEC, FireTrapParticlePacket::handle);
        r.playToClient(KeypadParticlePacket.TYPE, KeypadParticlePacket.STREAM_CODEC, KeypadParticlePacket::handle);
        r.playToClient(ParticleLinePacket.TYPE, ParticleLinePacket.STREAM_CODEC, ParticleLinePacket::handle);
        r.playToServer(CuriosSetStackPacket.TYPE, CuriosSetStackPacket.STREAM_CODEC, RateLimitedPacket::processPacket);
        r.playToClient(DashParticlePacket.TYPE, DashParticlePacket.STREAM_CODEC, DashParticlePacket::handle);
        r.playToClient(MusicToastPacket.TYPE, MusicToastPacket.STREAM_CODEC, MusicToastPacket::handle);
        r.playToServer(UnlockCodexPacket.TYPE, UnlockCodexPacket.STREAM_CODEC, RateLimitedPacket::processPacket);
        r.playToClient(NihilityPacket.TYPE, NihilityPacket.STREAM_CODEC, NihilityPacket::handle);
        r.playToClient(ManipulatorParticlePacket.TYPE, ManipulatorParticlePacket.STREAM_CODEC, ManipulatorParticlePacket::handle);
        r.playToServer(HeavyWorkbenchCraftPacket.TYPE, HeavyWorkbenchCraftPacket.STREAM_CODEC, RateLimitedPacket::processPacket);
        r.playToServer(AlchemyCraftPacket.TYPE, AlchemyCraftPacket.STREAM_CODEC, RateLimitedPacket::processPacket);
        r.playToServer(AlchemyUpgradeTryPacket.TYPE, AlchemyUpgradeTryPacket.STREAM_CODEC, RateLimitedPacket::processPacket);
        r.playToClient(AlchemyUpgradeParticlePacket.TYPE, AlchemyUpgradeParticlePacket.STREAM_CODEC, AlchemyUpgradeParticlePacket::handle);
        r.playToClient(CrusherParticlePacket.TYPE, CrusherParticlePacket.STREAM_CODEC, CrusherParticlePacket::handle);
        r.playToClient(CrushParticlePacket.TYPE, CrushParticlePacket.STREAM_CODEC, CrushParticlePacket::handle);
        r.playToServer(FirronKeyframePacket.TYPE, FirronKeyframePacket.STREAM_CODEC, RateLimitedPacket::processPacket);
        r.playToClient(MagmaPacket.TYPE, MagmaPacket.STREAM_CODEC, MagmaPacket::handle);
        r.playToServer(OnKeyInputPacket.TYPE, OnKeyInputPacket.STREAM_CODEC, RateLimitedPacket::processPacket);
        r.playToServer(CastAbilityPacket.TYPE, CastAbilityPacket.STREAM_CODEC, RateLimitedPacket::processPacket);
        r.playBidirectional(ReadCodexPacket.TYPE, ReadCodexPacket.STREAM_CODEC, ReadCodexPacket::handle);
        r.playToServer(ProgressionDisableCodexPacket.TYPE, ProgressionDisableCodexPacket.STREAM_CODEC, RateLimitedPacket::processPacket);
        r.playToClient(CrusherSyncPacket.TYPE, CrusherSyncPacket.STREAM_CODEC, CrusherSyncPacket::handle);
        r.playToClient(SyncAbilityStatePacket.TYPE, SyncAbilityStatePacket.STREAM_CODEC, SyncAbilityStatePacket::handle);
        r.playToClient(JumpParticlePacket.TYPE, JumpParticlePacket.STREAM_CODEC, JumpParticlePacket::handle);
        r.playToClient(SmashParticlePacket.TYPE, SmashParticlePacket.STREAM_CODEC, SmashParticlePacket::handle);
        r.playToClient(PlaySpawnCutscenePacket.TYPE, PlaySpawnCutscenePacket.STREAM_CODEC, PlaySpawnCutscenePacket::handle);
    }

    public static void sendTo(ServerPlayer playerMP, CustomPacketPayload toSend){
        PacketDistributor.sendToPlayer(playerMP, toSend);
    }

    public static void sendToAll(CustomPacketPayload message){
        PacketDistributor.sendToAllPlayers(message);
    }

    public static void sendNonLocal(CustomPacketPayload msg, ServerPlayer player){
        PacketDistributor.sendToPlayer(player, msg);
    }

    public static void sendToTracking(Level world, BlockPos pos, CustomPacketPayload msg){
        if(!(world instanceof ServerLevel level)) return;
        var chunkpos = new ChunkPos(pos);
        var players = level.getChunkSource().chunkMap.getPlayers(chunkpos, false);
        for(var player : players){
            if(player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) < 64 * 64){
                PacketDistributor.sendToPlayer(player, msg);
            }
        }
    }

    public static void sendTo(Player entity, CustomPacketPayload msg){
        PacketDistributor.sendToPlayer((ServerPlayer)entity, msg);
    }

    public static void sendEntity(Entity entity, CustomPacketPayload msg){
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, msg);
    }

    public static void sendToServer(CustomPacketPayload msg){
        PacketDistributor.sendToServer(msg);
    }
}
package com.idark.darkrpg.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.PacketDistributor;

public class PacketUtils {
    public static void SUpdateTileEntityPacket(BlockEntity tile) {
        if (tile.getLevel() instanceof ServerLevel) {
            tile.setChanged();
            tile.saveWithoutMetadata();
            PacketHandler.HANDLER.send(PacketDistributor.TRACKING_CHUNK.with(() -> tile.getLevel().getChunkAt(tile.getBlockPos())), new TESyncPacket(tile.getBlockPos(), tile.getUpdateTag()));
        }
    }
}

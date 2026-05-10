package com.idark.valoria.api.unlockable.types;

import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.levelgen.structure.*;

public interface OnDungeonVisitListener{
    void checkCondition(ServerPlayer player, ServerLevel serverLevel);

    default boolean isPlayerInStructure(Player player, ServerLevel serverLevel, ResourceKey<Structure> structureKey) {
        var structure = serverLevel.structureManager().getStructureWithPieceAt(player.blockPosition(), structureKey);
        return structure.getBoundingBox().isInside(player.getBlockX(), player.getBlockY(), player.getBlockZ());
    }

    default boolean isPlayerInStructure(Player player, ServerLevel serverLevel, TagKey<Structure> tag) {
        var structure = serverLevel.structureManager().getStructureWithPieceAt(player.blockPosition(), tag);
        return structure.getBoundingBox().isInside(player.getBlockX(), player.getBlockY(), player.getBlockZ());
    }
}
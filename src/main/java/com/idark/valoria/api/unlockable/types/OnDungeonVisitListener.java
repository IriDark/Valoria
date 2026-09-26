package com.idark.valoria.api.unlockable.types;

import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.levelgen.structure.*;

public interface OnDungeonVisitListener{
    void checkCondition(ServerPlayer player, ServerLevel serverLevel);

    default boolean isPlayerInStructure(Player player, ServerLevel serverLevel, ResourceKey<Structure> structureKey) {
        Structure structureType = serverLevel.registryAccess().registryOrThrow(Registries.STRUCTURE).get(structureKey);
        if(structureType == null) return false;
        var structure = serverLevel.structureManager().getStructureWithPieceAt(player.blockPosition(), structureType);
        if(!structure.isValid()) return false;
        return structure.getBoundingBox().isInside(player.getBlockX(), player.getBlockY(), player.getBlockZ());
    }

    default boolean isPlayerInStructure(Player player, ServerLevel serverLevel, TagKey<Structure> tag) {
        var structure = serverLevel.structureManager().getStructureWithPieceAt(player.blockPosition(), tag);
        if(!structure.isValid()) return false;
        return structure.getBoundingBox().isInside(player.getBlockX(), player.getBlockY(), player.getBlockZ());
    }
}

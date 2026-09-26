package com.idark.valoria.registries.level.events;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.level.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.server.level.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.event.entity.*;
import net.neoforged.neoforge.event.level.*;

public class StructureEvents{

    @SubscribeEvent
    public void onTeleportTry(EntityTeleportEvent.EnderPearl e){
        BlockPos pos = BlockPos.containing(e.getTarget());
        if (e.getEntity().level() instanceof ServerLevel level && level.dimension() == LevelGen.VALORIA_KEY && level.getGameRules().getBoolean(Valoria.DISABLE_BLOCK_BREAKING)) {
            if(isInStructure(pos, level)) e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onDestroyTry(BlockEvent.BreakEvent e){
        BlockPos pos = e.getPos();
        if(e.getLevel() instanceof ServerLevel level && level.dimension() == LevelGen.VALORIA_KEY && level.getGameRules().getBoolean(Valoria.DISABLE_BLOCK_BREAKING)){
            if(!level.getBlockState(pos).is(TagsRegistry.ALLOWED_TO_BREAK) && isInStructure(pos, level)) e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onExplosionStart(ExplosionEvent.Start e) {
        BlockPos pos = BlockPos.containing(e.getExplosion().center());
        if (e.getLevel() instanceof ServerLevel level && level.dimension() == LevelGen.VALORIA_KEY && level.getGameRules().getBoolean(Valoria.DISABLE_BLOCK_BREAKING)) {
            if(!level.getBlockState(pos).is(TagsRegistry.ALLOWED_TO_BREAK) && isInStructure(pos, level)) e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onFluidPlace(BlockEvent.FluidPlaceBlockEvent e) {
        if (e.getLevel() instanceof ServerLevel level && level.dimension() == LevelGen.VALORIA_KEY && level.getGameRules().getBoolean(Valoria.DISABLE_BLOCK_BREAKING)) {
            if(isInStructure(e.getPos(), level)) e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.EntityPlaceEvent e) {
        if (e.getLevel() instanceof ServerLevel level && level.dimension() == LevelGen.VALORIA_KEY && level.getGameRules().getBoolean(Valoria.DISABLE_BLOCK_BREAKING)) {
            if(isInStructure(e.getPos(), level)) e.setCanceled(true);
        }
    }

    public boolean isInStructure(BlockPos pos, ServerLevel serverLevel) {
        var structureType = serverLevel.registryAccess().registryOrThrow(Registries.STRUCTURE).get(LevelGen.VALORIA_FORTRESS);
        if(structureType == null) return false;
        var structure = serverLevel.structureManager().getStructureWithPieceAt(pos, structureType);
        return !structure.getPieces().isEmpty() && structure.getBoundingBox().isInside(pos);
    }

}

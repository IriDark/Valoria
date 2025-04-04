package com.idark.valoria;

import com.idark.valoria.registries.level.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraftforge.event.level.*;
import net.minecraftforge.eventbus.api.*;

public class StructureEvents{

    @SubscribeEvent
    public void onDestroyTry(BlockEvent.BreakEvent e){
        BlockPos pos = e.getPos();
        if (e.getLevel() instanceof ServerLevel level && level.dimension() == LevelGen.VALORIA_KEY && level.getGameRules().getBoolean(Valoria.DISABLE_BLOCK_BREAKING)) {
            if(isInStructure(pos, level)) e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onExplosionStart(ExplosionEvent.Start e) {
        if (e.getLevel() instanceof ServerLevel level && level.dimension() == LevelGen.VALORIA_KEY && level.getGameRules().getBoolean(Valoria.DISABLE_BLOCK_BREAKING)) {
            if(isInStructure(BlockPos.containing(e.getExplosion().getPosition()), level)) e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPistonMove(PistonEvent e) {
        if (e.getLevel() instanceof ServerLevel level && level.dimension() == LevelGen.VALORIA_KEY && level.getGameRules().getBoolean(Valoria.DISABLE_BLOCK_BREAKING)) {
            if(isInStructure(e.getPos(), level)) e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onFluidPlace(BlockEvent.FluidPlaceBlockEvent e) {
        if (e.getLevel() instanceof ServerLevel level && level.dimension() == LevelGen.VALORIA_KEY && level.getGameRules().getBoolean(Valoria.DISABLE_BLOCK_BREAKING)) {
            if(isInStructure(e.getPos(), level)) e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntityChangeBlock(BlockEvent.EntityPlaceEvent e) {
        if (e.getLevel() instanceof ServerLevel level && level.dimension() == LevelGen.VALORIA_KEY && level.getGameRules().getBoolean(Valoria.DISABLE_BLOCK_BREAKING)) {
            if(isInStructure(e.getPos(), level)) e.setCanceled(true);
        }
    }

    public boolean isInStructure(BlockPos pos, ServerLevel serverLevel) {
        var structure = serverLevel.structureManager().getStructureWithPieceAt(pos, LevelGen.VALORIA_FORTRESS);
        return !structure.getPieces().isEmpty() && structure.getBoundingBox().isInside(pos);
    }

}

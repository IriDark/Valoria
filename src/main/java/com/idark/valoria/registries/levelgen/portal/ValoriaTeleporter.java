package com.idark.valoria.registries.levelgen.portal;

import com.idark.valoria.registries.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.village.poi.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.level.portal.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.util.*;

import java.util.*;
import java.util.function.*;

public class ValoriaTeleporter extends BaseTeleporter implements ITeleporter{

    public ValoriaTeleporter(BlockPos pos, boolean insideDim){
        super(pos, insideDim, PoiRegistries.VALORIA_PORTAL.getKey());
    }

    public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo){
        entity.setPortalCooldown();
        return findOrCreatePortal(destWorld, entity);
    }

    private PortalInfo findOrCreatePortal(ServerLevel level, Entity entity){
        level.getPoiManager().ensureLoadedAndValid(level, entity.blockPosition(), 256);
        Optional<PoiRecord> portalPoi = level.getPoiManager().getInSquare((poiType) -> poiType.is(poi), entity.blockPosition(), 256, PoiManager.Occupancy.ANY).min(Comparator.<PoiRecord>comparingDouble((poi) -> poi.getPos().distSqr(entity.blockPosition())).thenComparingInt((poi) -> poi.getPos().getY()));
        BlockPos blockpos;
        if(portalPoi.isEmpty()){
            BlockUtil.FoundRectangle rectangle = createPortal(level, entity.blockPosition(), BlockRegistry.VALORIA_PORTAL.get().defaultBlockState(), BlockRegistry.VALORIA_PORTAL_FRAME.get().defaultBlockState()).get();
            blockpos = rectangle.minCorner.offset(2, 0, 2);
            level.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(blockpos), 3, blockpos);
            return new PortalInfo(new Vec3(rectangle.minCorner.getX() + 2, rectangle.minCorner.getY(), rectangle.minCorner.getZ() + 2), Vec3.ZERO, entity.getXRot(), entity.getYRot());
        }

        blockpos = portalPoi.get().getPos();
        return new PortalInfo(new Vec3(blockpos.getX(), blockpos.getY(), blockpos.getZ()), Vec3.ZERO, entity.getXRot(), entity.getYRot());
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destinationWorld, float yaw, Function<Boolean, Entity> repositionEntity){
        entity = repositionEntity.apply(false);
        int y = 61;
        if(!insideDimension){
            y = thisPos.getY();
        }

        BlockPos destinationPos = new BlockPos(thisPos.getX(), y, thisPos.getZ());
        int tries = 0;
        while((destinationWorld.getBlockState(destinationPos).getBlock() != Blocks.AIR) &&
        !destinationWorld.getBlockState(destinationPos).canBeReplaced(Fluids.WATER) &&
        (destinationWorld.getBlockState(destinationPos.above()).getBlock() != Blocks.AIR) &&
        !destinationWorld.getBlockState(destinationPos.above()).canBeReplaced(Fluids.WATER) && (tries < 25)){
            destinationPos = destinationPos.above(2);
            tries++;
        }

        entity.setPos(destinationPos.getX(), destinationPos.getY(), destinationPos.getZ());
        if(insideDimension){
            findOrCreatePortal(destinationWorld, entity);
        }

        return entity;
    }

    public Optional<BlockUtil.FoundRectangle> createPortal(ServerLevel world, BlockPos pos, BlockState portal, BlockState frame){
        pos = new BlockPos(pos.getX(), getHeight(world, 90, pos.getX(), pos.getZ(), BlockRegistry.VOID_GRASS.get()), pos.getZ());
        for(int x = -2; x < 3; x++){
            for(int z = -2; z < 3; z++){
                if(Math.abs(x) < 2 && Math.abs(z) < 2){
                    world.setBlock(pos.offset(x, 0, z), portal, 3);
                }else if(Math.abs(z) < 2){
                    if(x == -2){
                        world.setBlock(pos.offset(x, 0, z), frame.setValue(HorizontalDirectionalBlock.FACING, Direction.EAST), 3);
                    }else if(x == 2){
                        world.setBlock(pos.offset(x, 0, z), frame.setValue(HorizontalDirectionalBlock.FACING, Direction.WEST), 3);
                    }
                }else if(Math.abs(x) < 2){
                    if(z == -2){
                        world.setBlock(pos.offset(x, 0, z), frame.setValue(HorizontalDirectionalBlock.FACING, Direction.SOUTH), 3);
                    }else{
                        world.setBlock(pos.offset(x, 0, z), frame.setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH), 3);
                    }
                }
            }
        }

        return Optional.of(new BlockUtil.FoundRectangle(pos, 3, 3));
    }
}
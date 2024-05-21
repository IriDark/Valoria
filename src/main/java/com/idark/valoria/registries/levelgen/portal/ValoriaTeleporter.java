package com.idark.valoria.registries.levelgen.portal;

import com.idark.valoria.registries.BlockRegistry;
import com.idark.valoria.registries.PoiRegistries;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

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
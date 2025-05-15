package com.idark.valoria.registries.level.portal;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import com.idark.valoria.registries.level.*;
import net.minecraft.*;
import net.minecraft.BlockUtil.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.village.poi.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.portal.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.util.*;

import java.util.*;
import java.util.function.*;

public class ValoriaTeleporter extends BaseTeleporter implements ITeleporter{
    protected final ServerLevel level;

    public ValoriaTeleporter(ServerLevel pLevel, BlockPos pos, boolean insideDim){
        super(pos, insideDim, MiscRegistry.VALORIA_PORTAL.getKey());
        this.level = pLevel;
    }

    @Override
    public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo){
        entity.setPortalCooldown();
        return findPlayerMadePortal(destWorld, entity);
    }

    private PortalInfo findPlayerMadePortal(ServerLevel level, Entity entity){
        BlockPos pos = entity.blockPosition();
        PoiManager poiManager = level.getPoiManager();
        poiManager.ensureLoadedAndValid(level, pos, 256);
        Optional<PoiRecord> optional = poiManager.getInSquare((poiType) ->
                poiType.is(poi), pos, 256, PoiManager.Occupancy.ANY).sorted(Comparator.<PoiRecord>comparingDouble((poi) ->
                poi.getPos().distSqr(pos)).thenComparingInt((poi) ->
                poi.getPos().getY())).findFirst();

        BlockPos blockpos;
        if(optional.isEmpty()){
            FoundRectangle rectangle = createPortal(level, entity);
            blockpos = rectangle.minCorner;
            level.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(blockpos), 3, blockpos);
            return new PortalInfo(new Vec3(rectangle.minCorner.getX() + 2, rectangle.minCorner.getY(), rectangle.minCorner.getZ() + 2), Vec3.ZERO, entity.getXRot(), entity.getYRot());
        }

        // cringe
        blockpos = optional.get().getPos();
        if(level.dimension() == LevelGen.VALORIA_KEY){
            return new PortalInfo(new Vec3(blockpos.getX() + 1.5f, blockpos.getY(), blockpos.getZ() + 1.5f), Vec3.ZERO, entity.getXRot(), entity.getYRot());
        } else {
            return new PortalInfo(new Vec3(blockpos.getX() - 0.5f, blockpos.getY(), blockpos.getZ() - 0.5f), Vec3.ZERO, entity.getXRot(), entity.getYRot());
        }
    }

    private FoundRectangle createPortal(ServerLevel level, Entity entity){
        PoiManager poimanager = level.getPoiManager();
        poimanager.ensureLoadedAndValid(level, entity.blockPosition(), 256);
        BlockUtil.FoundRectangle rectangle = createPortal(level, entity.blockPosition(), BlockRegistry.valoriaPortal.get().defaultBlockState(), BlockRegistry.valoriaPortalFrame.get().defaultBlockState().setValue(ValoriaPortalFrame.GENERATED, true)).get();
        BlockPos blockpos = rectangle.minCorner.offset(0, 0, 0);
        level.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(blockpos), 3, blockpos);
        return new BlockUtil.FoundRectangle(blockpos, 3, 3);
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destinationWorld, float yaw, Function<Boolean, Entity> repositionEntity){
        entity = repositionEntity.apply(true);
        return entity;
    }

    public Optional<BlockUtil.FoundRectangle> createPortal(ServerLevel world, BlockPos pos, BlockState portal, BlockState frame){
        pos = new BlockPos(pos.getX(), getHeight(world, 90, pos.getX(), pos.getZ(), BlockRegistry.voidGrass.get()), pos.getZ());
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
package com.idark.valoria.registries.levelgen.portal;

import com.idark.valoria.registries.*;
import net.minecraft.*;
import net.minecraft.BlockUtil.*;
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

public class ValoriaTeleporter extends BaseTeleporter implements ITeleporter {
    protected final ServerLevel level;
    public ValoriaTeleporter(ServerLevel pLevel, BlockPos pos, boolean insideDim) {
        super(pos, insideDim, MiscRegistry.VALORIA_PORTAL.getKey());
        this.level = pLevel;
    }

    @Override
    public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        entity.setPortalCooldown();
        return findPlayerMadePortal(destWorld, entity);
    }

    private PortalInfo findPlayerMadePortal(ServerLevel level, Entity entity) {
        BlockPos pos = entity.blockPosition();
        PoiManager poiManager = level.getPoiManager();
        poiManager.ensureLoadedAndValid(level, pos, 256);
        Optional<PoiRecord> optional = poiManager.getInSquare((poiType) ->
        poiType.is(poi), pos, 256, PoiManager.Occupancy.ANY).sorted(Comparator.<PoiRecord>comparingDouble((poi) ->
        poi.getPos().distSqr(pos)).thenComparingInt((poi) ->
        poi.getPos().getY())).findFirst();
        BlockPos blockpos;
        if(optional.isEmpty()) {
            FoundRectangle rectangle = createPortal(level, entity);
            blockpos = rectangle.minCorner;
            level.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(blockpos), 3, blockpos);
            return new PortalInfo(new Vec3(rectangle.minCorner.getX(), rectangle.minCorner.getY(), rectangle.minCorner.getZ()), Vec3.ZERO, entity.getXRot(), entity.getYRot());
        }

        blockpos = optional.get().getPos();
        return new PortalInfo(new Vec3(blockpos.getX(), blockpos.getY() - 1, blockpos.getZ()), Vec3.ZERO, entity.getXRot(), entity.getYRot());
    }

    private FoundRectangle createPortal(ServerLevel level, Entity entity) {
        PoiManager poimanager = level.getPoiManager();
        poimanager.ensureLoadedAndValid(level, entity.blockPosition(), 256);
        BlockUtil.FoundRectangle rectangle = createPortal(level, entity.blockPosition(), BlockRegistry.VALORIA_PORTAL.get().defaultBlockState(), BlockRegistry.VALORIA_PORTAL_FRAME.get().defaultBlockState()).get();
        BlockPos blockpos = rectangle.minCorner.offset(0, 0, 0);;
        level.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(blockpos), 3, blockpos);
        return new BlockUtil.FoundRectangle(blockpos, 3, 3);
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destinationWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        entity = repositionEntity.apply(false);
        int y = 64;
        if (!insideDimension) {
            y = thisPos.getY();
        }

        BlockPos destinationPos = new BlockPos(thisPos.getX(), y, thisPos.getZ());
        int tries = 0;
        while ((destinationWorld.getBlockState(destinationPos).getBlock() != Blocks.AIR) && !destinationWorld.getBlockState(destinationPos).canBeReplaced(Fluids.WATER) && (destinationWorld.getBlockState(destinationPos.above()).getBlock() != Blocks.AIR) && !destinationWorld.getBlockState(destinationPos.above()).canBeReplaced(Fluids.WATER) && (tries < 25)) {
            destinationPos = destinationPos.above(4);
            tries++;
        }

        entity.setPos(destinationPos.getX() - 2, destinationPos.getY(), destinationPos.getZ());
        return entity;
    }

    public Optional<BlockUtil.FoundRectangle> createPortal(ServerLevel world, BlockPos pos, BlockState portal, BlockState frame) {
        pos = new BlockPos(pos.getX(), getHeight(world, 90, pos.getX(), pos.getZ(), BlockRegistry.VOID_GRASS.get()), pos.getZ());
        for (int x = -2; x < 3; x++) {
            for (int z = -2; z < 3; z++) {
                if (Math.abs(x) < 2 && Math.abs(z) < 2) {
                    world.setBlock(pos.offset(x, 0, z), portal, 3);
                } else if (Math.abs(z) < 2) {
                    if (x == -2) {
                        world.setBlock(pos.offset(x, 0, z), frame.setValue(HorizontalDirectionalBlock.FACING, Direction.EAST), 3);
                    } else if (x == 2) {
                        world.setBlock(pos.offset(x, 0, z), frame.setValue(HorizontalDirectionalBlock.FACING, Direction.WEST), 3);
                    }
                } else if (Math.abs(x) < 2) {
                    if (z == -2) {
                        world.setBlock(pos.offset(x, 0, z), frame.setValue(HorizontalDirectionalBlock.FACING, Direction.SOUTH), 3);
                    } else {
                        world.setBlock(pos.offset(x, 0, z), frame.setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH), 3);
                    }
                }
            }
        }

        return Optional.of(new BlockUtil.FoundRectangle(pos, 3, 3));
    }
}
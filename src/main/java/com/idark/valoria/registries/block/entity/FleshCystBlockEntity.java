package com.idark.valoria.registries.block.entity;

import com.idark.valoria.client.render.tile.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.living.minions.FleshSentinel;
import com.idark.valoria.registries.level.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;

import javax.annotation.*;

public class FleshCystBlockEntity extends BlockEntity implements TickableBlockEntity{
    private final BaseCystSpawner spawner = new BaseCystSpawner() {
        public void broadcastEvent(Level p_155767_, BlockPos p_155768_, int p_155769_) {
            p_155767_.blockEvent(p_155768_, BlockRegistry.FLESH_CYST.get(), p_155769_, 0);
        }

        protected void setNextSpawnData(@Nullable Level pLevel, BlockPos pPos, SpawnData pNextSpawnData) {
            super.setNextSpawnData(pLevel, pPos, pNextSpawnData);
            if (pLevel != null){
                BlockState blockstate = pLevel.getBlockState(pPos);
                pLevel.sendBlockUpdated(pPos, blockstate, blockstate, 4);

                Entity entity = pLevel.getEntity(pNextSpawnData.getEntityToSpawn().getId());
                if(entity instanceof FleshSentinel sentinel){
                    sentinel.setBoundOrigin(pPos);
                }
            }
        }

        public BlockEntity getSpawnerBlockEntity(){
            return FleshCystBlockEntity.this;
        }
    };

    public FleshCystBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntitiesRegistry.FLESH_CYST.get(), pPos, pBlockState);
        setEntityId(EntityTypeRegistry.FLESH_SENTINEL.get(), RandomSource.create());
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.spawner.load(this.level, this.worldPosition, pTag);
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        this.spawner.save(pTag);
    }

    @Override
    public void tick(){
        if (this.level.isClientSide()) {
            this.getSpawner().clientTick(this.level, this.getBlockPos());
        } else {
            this.getSpawner().serverTick((ServerLevel)this.level, this.getBlockPos());
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket(){
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt){
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
    }

    @Override
    public final CompoundTag getUpdateTag(){
        CompoundTag compoundtag = this.saveWithoutMetadata();
        this.saveAdditional(compoundtag);
        compoundtag.remove("SpawnPotentials");
        return compoundtag;
    }

    public boolean triggerEvent(int pId, int pType) {
        return this.spawner.onEventTriggered(this.level, pId) || super.triggerEvent(pId, pType);
    }

    public boolean onlyOpCanSetNbt() {
        return true;
    }

    public void setEntityId(EntityType<?> pType, RandomSource pRandom) {
        this.spawner.setEntityId(pType, this.level, pRandom, this.worldPosition);
    }

    public BaseCystSpawner getSpawner() {
        return this.spawner;
    }
}

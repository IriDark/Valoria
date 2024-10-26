package com.idark.valoria.registries.block.entity;

import com.idark.valoria.client.render.tile.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.living.minions.*;
import com.idark.valoria.registries.level.BaseSpawner;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;

public class FleshCystBlockEntity extends BlockEntity implements TickableBlockEntity{
    private final BaseSpawner spawner = new BaseSpawner() {
        public EntityType<?> getEntityType(){
            return EntityTypeRegistry.FLESH_SENTINEL.get();
        }

        public void onEntityConfiguration(Entity entity, BlockPos pPos) {
            if(entity instanceof FleshSentinel fleshSentinel){
                fleshSentinel.setBoundOrigin(pPos);
                fleshSentinel.cystSpawned = true;
            }
        }

        public void onEntitySpawn(ServerLevel pServerLevel, Entity entity, BlockPos pPos) {
            PacketHandler.sendToTracking(pServerLevel, pPos, new CystSummonParticlePacket(entity.getId(), pPos));
        }

        public void broadcastEvent(Level p_155767_, BlockPos p_155768_, int p_155769_) {
            p_155767_.blockEvent(p_155768_, BlockRegistry.FLESH_CYST.get(), p_155769_, 0);
        }
    };

    public FleshCystBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntitiesRegistry.FLESH_CYST.get(), pPos, pBlockState);
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
        if (!this.level.isClientSide()) {
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

    public BaseSpawner getSpawner() {
        return this.spawner;
    }
}

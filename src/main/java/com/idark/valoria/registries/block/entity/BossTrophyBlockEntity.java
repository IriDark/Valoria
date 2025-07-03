package com.idark.valoria.registries.block.entity;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraftforge.registries.*;

import java.util.function.*;

public class BossTrophyBlockEntity extends BlockEntity{
    public EntityType<?> entity;
    public Entity instance;
    public CompoundTag nbt;
    public BossTrophyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state){
        super(type, pos, state);
    }

    public BossTrophyBlockEntity(BlockPos pos, BlockState state){
        this(BlockEntitiesRegistry.BOSS_TROPHY_ENTITIES.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag){
        super.saveAdditional(pTag);
        EntityType<?> type = null;
        var sup = ((BossTrophyBlock)this.getBlockState().getBlock()).getEntity();

        if(sup != null) type = sup.get();
        if(entity != null) type = entity; // higher priority
        if(type != null){
            if(nbt != null && !nbt.isEmpty()){
                pTag.put("nbt", nbt.copy());
            }else{
                CompoundTag tag = new CompoundTag();
                tag.putString("id", ForgeRegistries.ENTITY_TYPES.getKey(type).toString());
                pTag.put("nbt", tag);
            }

            pTag.putString("pEntityID", ForgeRegistries.ENTITY_TYPES.getKey(type).toString());
        }
    }

    @Override
    public void load(CompoundTag pTag){
        super.load(pTag);
        ResourceLocation entityLocation = new ResourceLocation(pTag.getString("pEntityID"));
        entity = ForgeRegistries.ENTITY_TYPES.getValue(entityLocation);
        nbt = pTag.getCompound("nbt");
        if (entity != null && level != null) {
            instance = EntityType.loadEntityRecursive(nbt, level, Function.identity()); // creates static entity
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

    public void setEntity(Entity entity) {
        this.entity = entity.getType();
        this.nbt = null;
        setChanged();
    }

    @Override
    public final CompoundTag getUpdateTag(){
        CompoundTag compoundtag = this.saveWithoutMetadata();
        saveAdditional(compoundtag);
        return compoundtag;
    }

    @Override
    public void setChanged(){
        super.setChanged();
        if(level != null && !level.isClientSide){
            ValoriaUtils.SUpdateTileEntityPacket(this);
        }
    }
}
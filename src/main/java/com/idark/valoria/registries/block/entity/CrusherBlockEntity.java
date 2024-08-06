package com.idark.valoria.registries.block.entity;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.recipe.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class CrusherBlockEntity extends BlockSimpleInventory{
    public CrusherBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state){
        super(type, pos, state);
    }

    public CrusherBlockEntity(BlockPos pos, BlockState state){
        this(BlockEntitiesRegistry.CRUSHER_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    protected SimpleContainer createItemHandler(){
        return new SimpleContainer(64);
    }

    public void craftItem(ServerPlayer plr){
        Optional<CrusherRecipe> recipe = getCurrentRecipe();
        this.getItemHandler().removeItem(0, 1);
        this.getItemHandler().setChanged();
        Vec3 block = new Vec3(this.getBlockPos().getX() + 0.5f, this.getBlockPos().getY() + 1.5f, this.getBlockPos().getZ() + 0.5f);
        LootUtil.SpawnLoot(this.level, this.getBlockPos().above(), LootUtil.createLoot(recipe.get().getOutput(), LootUtil.getGiftParameters((ServerLevel)this.level, block, plr)));
        this.level.playSound(null, this.getBlockPos(), SoundEvents.CALCITE_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    public Optional<CrusherRecipe> getCurrentRecipe(){
        return this.level.getRecipeManager().getRecipeFor(CrusherRecipe.Type.INSTANCE, this.getItemHandler(), level);
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

    @NotNull
    @Override
    public final CompoundTag getUpdateTag(){
        var tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void setChanged(){
        super.setChanged();
        if(level != null && !level.isClientSide){
            ValoriaUtils.tileEntity.SUpdateTileEntityPacket(this);
        }
    }
}
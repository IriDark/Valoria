package com.idark.valoria.registries.block.entity;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.world.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.common.registry.block.entity.*;
import pro.komaru.tridot.common.registry.book.*;

public class PedestalBlockEntity extends BlockSimpleInventory implements TickableBlockEntity{
    public BookComponent bookComponent = null;
    public PedestalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state){
        super(type, pos, state);
    }

    public PedestalBlockEntity(BlockPos pos, BlockState state){
        this(BlockEntitiesRegistry.PEDESTAL_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void tick(){
        if (getLevel().isClientSide()) {
            Book book = getBook();
            if (book != null) {
                if (bookComponent == null) bookComponent = book.getComponent();
                bookTick(book);
            } else {
                bookComponent = null;
            }
        }
    }

    public void bookTick(Book book) {
        Container container = getItemHandler();
        book.tick(getLevel(), getBlockPos().getCenter(), container.getItem(0), bookComponent, 3);
    }

    public Book getBook() {
        Container container = getItemHandler();
        if (!container.isEmpty()) {
            for (Book book : BookHandler.getBooks()) {
                if (book.hasBook(container.getItem(0))) {
                    return book;
                }
            }
        }

        return null;
    }

    @Override
    protected SimpleContainer createItemHandler(){
        return new SimpleContainer(1){
            @Override
            public int getMaxStackSize(){
                return 1;
            }
        };
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket(){
        return ClientboundBlockEntityDataPacket.create(this, (e) -> e.getUpdateTag());
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
            ValoriaUtils.SUpdateTileEntityPacket(this);
        }
    }
}
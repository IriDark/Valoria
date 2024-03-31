package com.idark.valoria.registries.world.block.entity.types;

import com.idark.valoria.registries.recipe.CrusherRecipe;
import com.idark.valoria.registries.world.block.entity.ModBlockEntities;
import com.idark.valoria.util.LootUtil;
import com.idark.valoria.util.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class CrusherBlockEntity extends BlockSimpleInventory {
    public CrusherBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public CrusherBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntities.CRUSHER_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    protected SimpleContainer createItemHandler() {
        return new SimpleContainer(1) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };
    }

    public void craftItem(ServerPlayer plr) {
        Optional<CrusherRecipe> recipe = getCurrentRecipe();
        this.getItemHandler().removeItem(0, 1);
        Vec3 block = new Vec3(this.getBlockPos().getX() + 0.5f, this.getBlockPos().getY() + 1.5f, this.getBlockPos().getZ() + 0.5f);
        LootUtil.SpawnLoot(this.level, this.getBlockPos().above(), LootUtil.createLoot(recipe.get().getOutput(), LootUtil.getGiftParameters((ServerLevel) this.level, block, plr)));
        this.level.playSound(null, this.getBlockPos(), SoundEvents.CALCITE_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    public Optional<CrusherRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.getItemHandler().getContainerSize());
        for (int i = 0; i < this.getItemHandler().getContainerSize(); i++) {
            inventory.setItem(i, this.getItemHandler().getItem(i));
        }

        return this.level.getRecipeManager().getRecipeFor(CrusherRecipe.Type.INSTANCE, inventory, level);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, (e) -> e.getUpdateTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
    }

    @NotNull
    @Override
    public final CompoundTag getUpdateTag() {
        var tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null && !level.isClientSide) {
            PacketUtils.SUpdateTileEntityPacket(this);
        }
    }
}
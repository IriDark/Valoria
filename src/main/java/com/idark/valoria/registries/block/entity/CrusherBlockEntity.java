package com.idark.valoria.registries.block.entity;

import com.idark.valoria.registries.BlockEntitiesRegistry;
import com.idark.valoria.registries.item.recipe.CrusherRecipe;
import com.idark.valoria.util.LootUtil;
import com.idark.valoria.util.ValoriaUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
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
        this(BlockEntitiesRegistry.CRUSHER_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    protected SimpleContainer createItemHandler() {
        return new SimpleContainer(64);
    }

    public void craftItem(ServerPlayer plr) {
        Optional<CrusherRecipe> recipe = getCurrentRecipe();
        this.getItemHandler().removeItem(0, 1);
        this.getItemHandler().setChanged();
        Vec3 block = new Vec3(this.getBlockPos().getX() + 0.5f, this.getBlockPos().getY() + 1.5f, this.getBlockPos().getZ() + 0.5f);
        LootUtil.SpawnLoot(this.level, this.getBlockPos().above(), LootUtil.createLoot(recipe.get().getOutput(), LootUtil.getGiftParameters((ServerLevel) this.level, block, plr)));
        this.level.playSound(null, this.getBlockPos(), SoundEvents.CALCITE_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    public Optional<CrusherRecipe> getCurrentRecipe() {
        return this.level.getRecipeManager().getRecipeFor(CrusherRecipe.Type.INSTANCE, this.getItemHandler(), level);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
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
            ValoriaUtils.tileEntity.SUpdateTileEntityPacket(this);
        }
    }
}
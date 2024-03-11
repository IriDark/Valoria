package com.idark.valoria.registries.world.block.entity;

import com.idark.valoria.registries.world.block.types.KegBlock;
import com.idark.valoria.client.render.model.blockentity.TickableBlockEntity;
import com.idark.valoria.registries.recipe.KegRecipe;
import com.idark.valoria.util.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class KegBlockEntity extends BlockSimpleInventory implements TickableBlockEntity {

    public int progress = 0;
    public int progressMax = 0;
    public boolean startCraft = false;

    public KegBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public KegBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntities.KEG_BLOCK_ENTITY.get(), pos, state);
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

    @Override
    public void tick() {
        if (!level.isClientSide) {
            Optional<KegRecipe> recipe = getCurrentRecipe();
            if (recipe.isPresent()) {
                increaseCraftingProgress();
                startCraft = true;
                setMaxProgress();
                setChanged(level, getBlockPos(), getBlockState());
                if (hasProgressFinished()) {
                    craftItem();
                    resetProgress();
                }

                PacketUtils.SUpdateTileEntityPacket(this);
            } else {
                resetProgress();
            }
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("startCraft", startCraft);
        tag.putInt("progress", progress);
        tag.putInt("progressMax", progressMax);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        startCraft = tag.getBoolean("startCraft");
        progress = tag.getInt("progress");
        progressMax = tag.getInt("progressMax");
    }

    private void resetProgress() {
        progress = 0;
        startCraft = false;
        KegBlock.setBrewing(this.getLevel(), this.getBlockPos(), this.getBlockState(), false);
    }

    private void craftItem() {
        Optional<KegRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(RegistryAccess.EMPTY);

        this.getItemHandler().removeItem(0, 1);
        this.getItemHandler().setItem(0, new ItemStack(result.getItem(), result.getCount()));
    }

    private Optional<KegRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.getItemHandler().getContainerSize());
        for(int i = 0; i < this.getItemHandler().getContainerSize(); i++) {
            inventory.setItem(i, this.getItemHandler().getItem(i));
        }

        return this.level.getRecipeManager().getRecipeFor(KegRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean hasProgressFinished() {
        Optional<KegRecipe> recipe = getCurrentRecipe();
        return progress >= recipe.get().getTime();
    }

    private void increaseCraftingProgress() {
        Optional<KegRecipe> recipe = getCurrentRecipe();
        if (progress < recipe.get().getTime()) {
            progress++;
        }
    }

    private void setMaxProgress() {
        Optional<KegRecipe> recipe = getCurrentRecipe();
        if (progressMax <= 0) {
            progressMax = recipe.map(KegRecipe::getTime).orElse(200);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
    }

    public float getBlockRotate() {
        BlockState state = this.getBlockState();

        if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

            return switch (direction) {
                case EAST -> 90F;
                case SOUTH -> 360F;
                case WEST -> -90F;
                default -> 180F;
            };
        }

        return 0F;
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
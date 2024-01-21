package com.idark.valoria.tileentity;

import com.idark.valoria.block.types.KegBlock;
import com.idark.valoria.client.render.model.tileentity.KegBlockRenderer;
import com.idark.valoria.client.render.model.tileentity.TickableTileEntity;
import com.idark.valoria.recipe.KegRecipe;
import com.idark.valoria.util.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class KegBlockEntity extends TileSimpleInventory implements TickableTileEntity {

    private int progress = 0;

    public KegBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public KegBlockEntity(BlockPos pos, BlockState state) {
        this(ModTileEntities.KEG_TILE_ENTITY.get(), pos, state);
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
            if (!recipe.isEmpty()) {
                increaseCraftingProgress();
                KegBlockRenderer.setBrewingForBlock(getBlockPos(), true);
                setChanged(level, getBlockPos(), getBlockState());
                if (hasProgressFinished()) {
                    craftItem();
                    resetProgress();
                }
            } else {
                resetProgress();
            }
        }
    }

    private void resetProgress() {
        progress = 0;
        KegBlockRenderer.setBrewingForBlock(getBlockPos(), false);
        KegBlock.setBrewing(this.getLevel(), this.getBlockPos(), this.getBlockState(), false);
    }

    private void craftItem() {
        Optional<KegRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);

        this.getItemHandler().removeItem(0, 1);
        this.getItemHandler().setItem(0, new ItemStack(result.getItem(),
                this.getItemHandler().getItem(0).getCount() + result.getCount()));
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

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, (e) -> e.getUpdateTag());
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
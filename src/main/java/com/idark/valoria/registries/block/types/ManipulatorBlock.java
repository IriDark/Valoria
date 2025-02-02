package com.idark.valoria.registries.block.types;

import com.idark.valoria.client.render.tile.*;
import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.registries.block.entity.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.network.*;
import org.jetbrains.annotations.*;

import javax.annotation.Nullable;
import javax.annotation.*;

public class ManipulatorBlock extends Block implements EntityBlock{

    public ManipulatorBlock(BlockBehaviour.Properties properties){
        super(properties);
    }

    public VoxelShape makeShape(){
        // Voxel shapes sucks
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.1875, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.1875, 0.25, 0.75, 0.3125, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.3125, 0, 1, 0.56, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0.5, 0.6875, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.5, 0.6875, 0.3125, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.5, 0, 0.3125, 1, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0.5, 0, 1, 1, 0.3125), BooleanOp.OR);
        return shape;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
        return makeShape();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState stateIn, Level pLevel, BlockPos pos, RandomSource rand){
        float chance = 0.35f;
        if(chance < rand.nextFloat()){
            pLevel.addParticle(ParticleTypes.PORTAL, pos.getX() + rand.nextDouble(),
                    pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(),
                    0d, 0.05d, 0d);
        }

        super.animateTick(stateIn, pLevel, pos, rand);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param){
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity tile = world.getBlockEntity(pos);
        return tile != null && tile.triggerEvent(id, param);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving){
        if(state.getBlock() != newState.getBlock()){
            BlockEntity tile = world.getBlockEntity(pos);
            if(tile instanceof ManipulatorBlockEntity table){
                SimpleContainer inv = new SimpleContainer(table.itemHandler.getSlots() + 1);
                for(int i = 0; i < table.itemHandler.getSlots(); i++){
                    inv.setItem(i, table.itemHandler.getStackInSlot(i));
                }

                inv.setItem(table.itemHandler.getSlots(), table.itemOutputHandler.getStackInSlot(0));
                Containers.dropContents(world, pos, inv);
            }

            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
        if(world.isClientSide){
            return InteractionResult.SUCCESS;
        }

        BlockEntity tileEntity = world.getBlockEntity(pos);
        if(!(tileEntity instanceof ManipulatorBlockEntity coreBlock)){
            return InteractionResult.FAIL;
        }

        ItemStack core = player.getItemInHand(hand);
        boolean coreUpdated = false;
        if(core.getItem() instanceof CoreItem builder && coreBlock.getCoreNBT(builder.getCoreName()) != 8){
            if(!player.getAbilities().instabuild){
                core.shrink(1);
            }

            coreBlock.addCharge(builder.getCoreName(), builder.getGivenCores());
            coreUpdated = true;
        }

        if(coreUpdated){
            ValoriaUtils.SUpdateTileEntityPacket(coreBlock);
        }else{
            MenuProvider containerProvider = createContainerProvider(world, pos);
            NetworkHooks.openScreen(((ServerPlayer)player), containerProvider, tileEntity.getBlockPos());
        }

        return InteractionResult.CONSUME;
    }

    private MenuProvider createContainerProvider(Level worldIn, BlockPos pos){
        return new MenuProvider(){

            @Override
            public Component getDisplayName(){
                return Component.translatable("menu.valoria.manipulator");
            }

            @Override
            public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity){
                return new ManipulatorMenu(i, worldIn, pos, playerInventory, playerEntity);
            }
        };
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState){
        return new ManipulatorBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type){
        return TickableBlockEntity.getTickerHelper();
    }
}
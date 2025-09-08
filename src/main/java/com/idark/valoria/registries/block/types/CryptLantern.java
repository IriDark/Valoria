package com.idark.valoria.registries.block.types;

import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.shapes.*;

public class CryptLantern extends LanternBlock{
    public CryptLantern(Properties pProperties){
        super(pProperties);
    }

    private static VoxelShape makeHangingShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.1875, 0.3125, 0.6875, 0.5, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.4375, 0.125, 0.875, 0.75, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.11875000000000002, 0.05625, 0.11875000000000002, 0.88125, 0.25625000000000003, 0.88125), BooleanOp.OR);

        return shape;
    }

    private static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.125, 0.3125, 0.6875, 0.4375, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.375, 0.125, 0.875, 0.6875, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.11875000000000002, -0.006249999999999999, 0.11875000000000002, 0.88125, 0.19375000000000003, 0.88125), BooleanOp.OR);

        return shape;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
        return pState.getValue(HANGING) ? makeHangingShape() : makeShape();
    }
}

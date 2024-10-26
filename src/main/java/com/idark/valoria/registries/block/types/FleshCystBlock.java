package com.idark.valoria.registries.block.types;

import com.idark.valoria.client.render.tile.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.entity.*;
import com.idark.valoria.registries.entity.living.minions.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;

import javax.annotation.*;

public class FleshCystBlock extends BaseEntityBlock{
    public FleshCystBlock(Properties pProperties){
        super(pProperties);
    }

    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FleshCystBlockEntity(pPos, pState);
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    private void spawnSentinels(ServerLevel world, BlockPos pos) {
        FleshSentinel sentinel = EntityTypeRegistry.FLESH_SENTINEL.get().create(world);
        if (sentinel != null) {
            RandomSource randomsource = world.getRandom();
            double d0 = (double)pos.getX() + (randomsource.nextDouble() - randomsource.nextDouble()) * 6 + 0.5D;
            double d1 = pos.getY() + randomsource.nextInt(3) - 1;
            double d2 = (double)pos.getZ() + (randomsource.nextDouble() - randomsource.nextDouble()) * 6 + 0.5D;
            if(world.noCollision(sentinel.getType().getAABB(d0, d1, d2))){
                sentinel.moveTo(d0, d1, d2, 0.0F, 0.0F);
                sentinel.setBoundOrigin(pos);
                world.addFreshEntity(sentinel);
            }
        }
    }

    public VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125625, 0.125625, 0.125625, 0.874375, 0.561875, 0.874375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.125, 0, 1, 0.375, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.125, 0, 0.25, 0.375, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.125, 0.6875, 0.25, 0.375, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.125, 0.6875, 1, 0.375, 1), BooleanOp.OR);

        return shape;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
        return makeShape();
    }

    @Override
    public void onProjectileHit(Level pLevel, BlockState pState, BlockHitResult pHit, Projectile pProjectile) {
        pLevel.destroyBlock(pHit.getBlockPos(), true);
        if (!pLevel.isClientSide()) {
            for(int i = 0; i < 4; i++){
                this.spawnSentinels((ServerLevel)pLevel, pHit.getBlockPos());
            }
        }
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        if (pFallDistance > 1f) {
            pLevel.destroyBlock(pPos, false);
            if (!pLevel.isClientSide()) {
                for(int i = 0; i < 4; i++){
                    this.spawnSentinels((ServerLevel)pLevel, pPos);
                }
            }
        }
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pos, BlockState state) {
        if (!pLevel.isClientSide()) {
            for(int i = 0; i < 4; i++){
                this.spawnSentinels((ServerLevel)pLevel, pos);
            }
        }
    }

    @Override
    public void wasExploded(Level pLevel, BlockPos pos, Explosion explosionIn) {
        if (!pLevel.isClientSide()) {
            for(int i = 0; i < 4; i++){
                this.spawnSentinels((ServerLevel)pLevel, pos);
            }
        }
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return TickableBlockEntity.getTickerHelper();
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.level.LevelReader world, net.minecraft.util.RandomSource randomSource, BlockPos pos, int fortune, int silktouch) {
        return 15 + randomSource.nextInt(15) + randomSource.nextInt(15);
    }
}


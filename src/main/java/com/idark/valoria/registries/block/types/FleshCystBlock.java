package com.idark.valoria.registries.block.types;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.entity.*;
import com.idark.valoria.registries.entity.living.minions.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.common.registry.block.entity.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import javax.annotation.*;

public class FleshCystBlock extends BaseEntityBlock implements SimpleWaterloggedBlock{
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public FleshCystBlock(Properties pProperties){
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, Boolean.FALSE));
    }

    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState){
        return new FleshCystBlockEntity(pPos, pState);
    }

    public RenderShape getRenderShape(BlockState pState){
        return RenderShape.MODEL;
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos){
        if(pState.getValue(WATERLOGGED)){
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(WATERLOGGED);
    }

    public FluidState getFluidState(BlockState pState){
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    private void spawnSentinels(ServerLevel world, BlockPos pos){
        FleshSentinel sentinel = EntityTypeRegistry.FLESH_SENTINEL.get().create(world);
        if(sentinel != null){
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

    public void onDestroy(Level pLevel, BlockPos pPos){
        if(!pLevel.isClientSide()){
            for(int i = 0; i < 3; i++){
                this.spawnSentinels((ServerLevel)pLevel, pPos);
            }
        }

        if(pLevel.isClientSide()){
            spawnDestroyParticles(pLevel, pPos);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void spawnDestroyParticles(Level pLevel, BlockPos pPos){
        ParticleBuilder.create(TridotParticles.WISP)
        .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
        .setColorData(ColorParticleData.create(Pal.kiwi, Pal.mindaro).build())
        .setScaleData(GenericParticleData.create(0.425f, 0.075f, 0).setEasing(Interp.bounceOut).build())
        .setLifetime(35)
        .randomOffset(0.5f)
        .setGravity(0.0125f)
        .flatRandomVelocity(0.025, Tmp.rnd.randomValueUpTo(0.055), 0.025)
        .repeat(pLevel, pPos.getCenter().x, pPos.getCenter().y + 0.2, pPos.getCenter().z, 5);
    }

    @Override
    public void onProjectileHit(Level pLevel, BlockState pState, BlockHitResult pHit, Projectile pProjectile){
        pLevel.destroyBlock(pHit.getBlockPos(), true);
        onDestroy(pLevel, pHit.getBlockPos());
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance){
        if(pFallDistance > 1f){
            pLevel.destroyBlock(pPos, false);
            onDestroy(pLevel, pPos);
        }
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pos, BlockState state){
        if(pLevel instanceof Level level){
            onDestroy(level, pos);
        }
    }

    @Override
    public void wasExploded(Level pLevel, BlockPos pos, Explosion explosionIn){
        onDestroy(pLevel, pos);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType){
        return TickableBlockEntity.getTickerHelper();
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.level.LevelReader world, net.minecraft.util.RandomSource randomSource, BlockPos pos, int fortune, int silktouch){
        return 15 + randomSource.nextInt(25);
    }
}


package com.idark.valoria.registries.block.types.plants;

import com.idark.valoria.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TallVoidFlowerBlock extends DoublePlantBlock{
    public TallVoidFlowerBlock(BlockBehaviour.Properties properties){
        super(properties);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos){
        Block block = state.getBlock();
        return block == BlockRegistry.voidStone.get() || block == BlockRegistry.voidGrass.get();
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext){
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand){
        VoxelShape voxelshape = this.getShape(stateIn, worldIn, pos, CollisionContext.empty());
        Vec3 vector3d = voxelshape.bounds().getCenter();
        double d0 = (double)pos.getX() + vector3d.x;
        double d1 = (double)pos.getZ() + vector3d.z;
        for(int i = 0; i < 3; ++i){
            if(rand.nextBoolean()){
                worldIn.addParticle(ParticleTypes.SMOKE, d0 + rand.nextDouble() / 5.0D, (double)pos.getY() + (0.5D - rand.nextDouble()), d1 + rand.nextDouble() / 5.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
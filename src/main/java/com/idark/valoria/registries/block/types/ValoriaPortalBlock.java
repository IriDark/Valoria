package com.idark.valoria.registries.block.types;

import com.idark.valoria.registries.block.entity.*;
import com.idark.valoria.registries.level.*;
import com.idark.valoria.registries.level.portal.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.server.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.pattern.*;
import net.minecraft.world.phys.shapes.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;
import org.joml.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.behavior.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.gfx.postprocess.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

public class ValoriaPortalBlock extends Block implements EntityBlock{
    private static final VoxelShape shape = Block.box(0.0D, 6.0D, 0.0D, 16.0D, 8.5D, 16.0D);
    public ValoriaPortalBlock(Properties pProperties){
        super(pProperties);
    }

    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity){
        if(pEntity.canChangeDimensions() && Shapes.joinIsNotEmpty(Shapes.create(pEntity.getBoundingBox().move(-pPos.getX(), -pPos.getY(), -pPos.getZ())), pState.getShape(pLevel, pPos), BooleanOp.AND)){
            handlePortal(pEntity, pPos);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
        return shape;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom){
        if(pRandom.nextInt(100) == 0){
            pLevel.playLocalSound((double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5F, -pRandom.nextFloat() * 0.5F, false);
        }

        var random = Tmp.rnd;
        ParticleBuilder.create(TridotParticles.SQUARE)
                .setBehavior(SparkParticleBehavior.create().build())
                .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.25f, 0f).build())
                .setScaleData(GenericParticleData.create(0.01f, 0.06f, 0).setEasing(Interp.bounce).build())
                .flatRandomOffset(0.25f, 0f, 0.25f)
                .setLifetime(24)
                .setColorData(ColorParticleData.create(Col.black).setEasing(Interp.bounce).build())
                .setVelocity(0, (random.nextDouble() + 0.2D) / 6, 0)
                .spawn(pLevel, pPos.getX() + pRandom.nextDouble(), pPos.getY() + pRandom.nextDouble(), pPos.getZ() + pRandom.nextDouble());

        ParticleBuilder.create(TridotParticles.SQUARE)
                .setBehavior(SparkParticleBehavior.create().build())
                .setTransparencyData(GenericParticleData.create(0.25f, 0f).build())
                .setScaleData(GenericParticleData.create(0.01f, 0.06f, 0).setEasing(Interp.bounce).build())
                .flatRandomOffset(0.45f, 0f, 0.45f)
                .setLifetime(24)
                .setColorData(ColorParticleData.create(random.fiftyFifty() ? Col.fromHex("1a101f") : Col.fromHex("451f3f")).setEasing(Interp.bounce).build())
                .setVelocity(0, (random.nextDouble() + 0.2D) / 6, 0)
                .spawn(pLevel, pPos.getX() + pRandom.nextDouble(), pPos.getY() + pRandom.nextDouble(), pPos.getZ() + pRandom.nextDouble());
    }

    private void handlePortal(Entity player, BlockPos pPos){
        if(player.isOnPortalCooldown()){
            player.setPortalCooldown();
        }else if(player.level() instanceof ServerLevel serverlevel){
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = player.level().dimension() == LevelGen.VALORIA_KEY ? Level.OVERWORLD : LevelGen.VALORIA_KEY;
            ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
            if(portalDimension != null && !player.isPassenger()){
                if(resourcekey == LevelGen.VALORIA_KEY){
                    player.changeDimension(portalDimension, new ValoriaTeleporter(serverlevel, pPos, true));
                }else{
                    player.changeDimension(portalDimension, new ValoriaTeleporter(serverlevel, pPos, false));
                }

                player.setPortalCooldown();
            }
        }
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos){
        if(pLevel.isClientSide()) {
            GlowPostProcess.INSTANCE.addInstance(new GlowPostProcessInstance(pCurrentPos.getCenter().toVector3f(), new Vector3f((float)70 / 255, (float)31 / 255, (float)139 / 255)).setIntensity(0.25f).setFadeTime(45).setRadius(4));
        }

        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos pos2, boolean unknown){
        BlockPattern.BlockPatternMatch frame = ValoriaPortalFrame.getOrCreatePortalShape().find(world, pos);
        if(frame == null && world.dimension() != LevelGen.VALORIA_KEY) world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
    }

    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState){
        return new ValoriaPortalBlockEntity(pPos, pState);
    }
}
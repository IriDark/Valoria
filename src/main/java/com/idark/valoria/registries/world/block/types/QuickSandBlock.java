package com.idark.valoria.registries.world.block.types;

import com.idark.valoria.registries.world.block.ModBlocks;
import com.idark.valoria.registries.world.damage.ModDamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class QuickSandBlock extends Block {
    private static final float HORIZONTAL_PARTICLE_MOMENTUM_FACTOR = 0.083333336F;
    private static final float IN_BLOCK_HORIZONTAL_SPEED_MULTIPLIER = 0.9F;
    private static final float IN_BLOCK_VERTICAL_SPEED_MULTIPLIER = 1.5F;
    private static final float NUM_BLOCKS_TO_FALL_INTO_BLOCK = 2.5F;
    private static final VoxelShape FALLING_COLLISION_SHAPE = Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, (double) 0.9F, 1.0D);
    private static final double MINIMUM_FALL_DISTANCE_FOR_SOUND = 4.0D;
    private static final double MINIMUM_FALL_DISTANCE_FOR_BIG_SOUND = 7.0D;

    public QuickSandBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pDirection) {
        return pAdjacentBlockState.is(this) ? true : super.skipRendering(pState, pAdjacentBlockState, pDirection);
    }

    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return Shapes.empty();
    }

    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        RandomSource randomsource = pLevel.getRandom();
        if (!(pEntity instanceof LivingEntity) || pEntity.getFeetBlockState().is(this)) {
            pEntity.makeStuckInBlock(pState, new Vec3(0.9F, 1.5D, 0.9F));
            if (pLevel.isClientSide) {
                boolean flag = pEntity.xOld != pEntity.getX() || pEntity.zOld != pEntity.getZ();
                if (flag && randomsource.nextBoolean()) {
                    BlockState state = Blocks.SAND.defaultBlockState();
                    pLevel.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, state), pEntity.getX(), (double) (pPos.getY() + 1), pEntity.getZ(), (double) (Mth.randomBetween(randomsource, -1.0F, 1.0F) * 0.083333336F), (double) 0.05F, (double) (Mth.randomBetween(randomsource, -1.0F, 1.0F) * 0.083333336F));
                }

                if (pEntity.isOnFire() && (pLevel.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || pEntity instanceof Player) && pEntity.mayInteract(pLevel, pPos)) {
                    pLevel.destroyBlock(pPos, false);
                    BlockState state = ModBlocks.QUICKSAND.get().defaultBlockState();
                    pLevel.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), pEntity.getX(), (double) (pPos.getY() + 1), pEntity.getZ(), (double) (Mth.randomBetween(randomsource, -1.0F, 1.0F) * 0.083333336F), (double) 0.05F, (double) (Mth.randomBetween(randomsource, -1.0F, 1.0F) * 0.083333336F));
                }

                pEntity.setDeltaMovement(pEntity.getDeltaMovement().multiply(0.4, 0.05, 0.4));
            }

            pEntity.setSharedFlagOnFire(false);
        }

        if (randomsource.nextFloat() < 0.06f) {
            pEntity.hurt(new DamageSource(ModDamageSources.source(pEntity.level(), ModDamageSources.QUICKSAND_SUFFOCATING).typeHolder()), 1.0F);
        }
    }

    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        if (!((double) pFallDistance < 4.0D) && pEntity instanceof LivingEntity livingentity) {
            LivingEntity.Fallsounds $$7 = livingentity.getFallSounds();
            SoundEvent soundevent = (double) pFallDistance < 7.0D ? $$7.small() : $$7.big();
            pEntity.playSound(soundevent, 1.0F, 1.0F);
        }
    }

    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pContext instanceof EntityCollisionContext entitycollisioncontext) {
            Entity entity = entitycollisioncontext.getEntity();
            if (entity != null) {
                if (entity.fallDistance > 2.5F) {
                    return FALLING_COLLISION_SHAPE;
                }
            }
        }

        return Shapes.empty();
    }

    public VoxelShape getVisualShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return true;
    }
}

package com.idark.valoria.registries.entity.ai.goals;

import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.*;

import javax.annotation.*;

public class RemoveCropsGoal extends MoveToBlockGoal{
    private final Mob removerMob;
    private int ticksSinceReachedGoal;
    private static final int WAIT_AFTER_BLOCK_FOUND = 20;

    public RemoveCropsGoal(PathfinderMob pRemoverMob, double pSpeedModifier, int pSearchRange){
        super(pRemoverMob, pSpeedModifier, 24, pSearchRange);
        this.removerMob = pRemoverMob;
    }

    public boolean canUse(){
        if(!ForgeEventFactory.getMobGriefingEvent(this.removerMob.level(), this.removerMob)){
            return false;
        }else if(this.nextStartTick > 0){
            --this.nextStartTick;
            return false;
        }else if(this.findNearestBlock()){
            this.nextStartTick = reducedTickDelay(20);
            return true;
        }else{
            this.nextStartTick = this.nextStartTick(this.mob);
            return false;
        }
    }

    public void stop(){
        super.stop();
        this.removerMob.fallDistance = 1.0F;
    }

    public void start(){
        super.start();
        this.ticksSinceReachedGoal = 0;
    }

    public void playDestroyProgressSound(LevelAccessor pLevel, BlockPos pPos){
    }

    public void playBreakSound(Level pLevel, BlockPos pPos){
    }

    public void tick(){
        super.tick();
        Level level = this.removerMob.level();
        BlockPos blockpos = this.removerMob.blockPosition();
        BlockPos blockpos1 = this.getPosWithBlock(blockpos, level);
        RandomSource randomsource = this.removerMob.getRandom();
        if(this.isReachedTarget() && blockpos1 != null){
            Vec3 vec31;
            double d3;
            if(this.ticksSinceReachedGoal > 0){
                vec31 = this.removerMob.getDeltaMovement();
                this.removerMob.setDeltaMovement(vec31.x, 0.3, vec31.z);
                if(!level.isClientSide){
                    ((ServerLevel)level).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.DIRT)), (double)blockpos1.getX() + 0.5, (double)blockpos1.getY() + 0.7, (double)blockpos1.getZ() + 0.5, 3, ((double)randomsource.nextFloat() - 0.5) * 0.08, ((double)randomsource.nextFloat() - 0.5) * 0.08, ((double)randomsource.nextFloat() - 0.5) * 0.08, 0.15000000596046448);
                }
            }

            if(this.ticksSinceReachedGoal % 2 == 0){
                vec31 = this.removerMob.getDeltaMovement();
                this.removerMob.setDeltaMovement(vec31.x, -0.3, vec31.z);
                if(this.ticksSinceReachedGoal % 6 == 0){
                    this.playDestroyProgressSound(level, this.blockPos);
                }
            }

            if(this.ticksSinceReachedGoal > 60){
                level.removeBlock(blockpos1, false);
                if(!level.isClientSide){
                    for(int i = 0; i < 20; ++i){
                        d3 = randomsource.nextGaussian() * 0.02;
                        double d1 = randomsource.nextGaussian() * 0.02;
                        double d2 = randomsource.nextGaussian() * 0.02;
                        ((ServerLevel)level).sendParticles(ParticleTypes.POOF, (double)blockpos1.getX() + 0.5, (double)blockpos1.getY(), (double)blockpos1.getZ() + 0.5, 1, d3, d1, d2, 0.15000000596046448);
                    }

                    this.playBreakSound(level, blockpos1);
                }
            }

            ++this.ticksSinceReachedGoal;
        }

    }

    @Nullable
    private BlockPos getPosWithBlock(BlockPos pPos, BlockGetter pLevel){
        if(pLevel.getBlockState(pPos).is(BlockTags.CROPS)){
            return pPos;
        }else{
            BlockPos[] ablockpos = new BlockPos[]{pPos.below(), pPos.west(), pPos.east(), pPos.north(), pPos.south(), pPos.below().below()};
            for(BlockPos blockpos : ablockpos){
                if(pLevel.getBlockState(blockpos).is(BlockTags.CROPS)){
                    return blockpos;
                }
            }

            return null;
        }
    }

    protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos){
        ChunkAccess chunkaccess = pLevel.getChunk(SectionPos.blockToSectionCoord(pPos.getX()), SectionPos.blockToSectionCoord(pPos.getZ()), ChunkStatus.FULL, false);
        if(chunkaccess == null){
            return false;
        }else if(!chunkaccess.getBlockState(pPos).canEntityDestroy(pLevel, pPos, this.removerMob)){
            return false;
        }else{
            return chunkaccess.getBlockState(pPos).is(BlockTags.CROPS) && chunkaccess.getBlockState(pPos.above()).isAir() && chunkaccess.getBlockState(pPos.above(2)).isAir();
        }
    }
}
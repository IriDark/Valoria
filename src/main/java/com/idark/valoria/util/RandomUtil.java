package com.idark.valoria.util;

import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

public final class RandomUtil{

    public static final EasyRandom rand = new EasyRandom(new Random());

    public static boolean fiftyFifty(){
        return rand.fiftyFifty();
    }


    /**
     * @param percentChance from 0.00f to 1
     */
    public static boolean percentChance(float percentChance){
        return rand.percentChance(percentChance);
    }

    public static int randomNumberUpTo(int upperBound){
        return rand.randomNumberUpTo(upperBound);
    }

    public static float randomValueUpTo(float upperBound){
        return rand.randomValueUpTo(upperBound);
    }

    public static double randomValueUpTo(double upperBound){
        return rand.randomValueUpTo(upperBound);
    }

    public static double randomGaussianValue(){
        return rand.randomGaussianValue();
    }

    public static double randomScaledGaussianValue(double scale){
        return rand.randomScaledGaussianValue(scale);
    }

    public static int randomNumberBetween(int min, int max){
        return rand.randomNumberBetween(min, max);
    }

    public static double randomValueBetween(double min, double max){
        return rand.randomValueBetween(min, max);
    }

    public static <T> T getRandomSelection(@Nonnull T... options){
        return rand.getRandomSelection(options);
    }

    public static <T> T getRandomSelection(@Nonnull List<T> options){
        return rand.getRandomSelection(options);
    }

    public static BlockPos getRandomPositionWithinRange(BlockPos centerPos, int xRadius, int yRadius, int zRadius){
        return rand.getRandomPositionWithinRange(centerPos, xRadius, yRadius, zRadius);
    }

    public static BlockPos getRandomPositionWithinRange(BlockPos centerPos, int xRadius, int yRadius, int zRadius, boolean safeSurfacePlacement, Level world){
        return rand.getRandomPositionWithinRange(centerPos, xRadius, yRadius, zRadius, safeSurfacePlacement, world);
    }

    public static BlockPos getRandomPositionWithinRange(BlockPos centerPos, int xRadius, int yRadius, int zRadius, boolean safeSurfacePlacement, Level world, @Nullable Predicate<BlockState> statePredicate, int tries){
        return rand.getRandomPositionWithinRange(centerPos, xRadius, yRadius, zRadius, safeSurfacePlacement, world, statePredicate, tries);
    }

    public static final class EasyRandom{
        private final Random rand;

        public EasyRandom(@Nonnull Random rand){
            this.rand = rand;
        }

        public Random source(){
            return rand;
        }

        public boolean fiftyFifty(){
            return rand.nextBoolean();
        }

        public boolean percentChance(float percentChance){
            if(percentChance <= 0)
                return false;

            if(percentChance >= 1)
                return true;

            return rand.nextDouble() < percentChance;
        }

        public int randomNumberUpTo(int upperBound){
            return rand.nextInt(upperBound);
        }

        public float randomValueUpTo(float upperBound){
            return rand.nextFloat() * upperBound;
        }

        public double randomValueUpTo(double upperBound){
            return rand.nextDouble() * upperBound;
        }

        public double randomGaussianValue(){
            return rand.nextGaussian();
        }

        public double randomScaledGaussianValue(double scale){
            return rand.nextGaussian() * scale;
        }

        public int randomNumberBetween(int min, int max){
            return min + (int)Math.floor(rand.nextDouble() * (1 + max - min));
        }

        public double randomValueBetween(double min, double max){
            return min + rand.nextDouble() * (max - min);
        }

        public <T> T getRandomSelection(@Nonnull T... options){
            return options[rand.nextInt(options.length)];
        }

        public <T> T getRandomSelection(@Nonnull List<T> options){
            return options.get(rand.nextInt(options.size()));
        }

        public BlockPos getRandomPositionWithinRange(BlockPos centerPos, int xRadius, int yRadius, int zRadius){
            return getRandomPositionWithinRange(centerPos, xRadius, yRadius, zRadius, false, null);
        }

        public BlockPos getRandomPositionWithinRange(BlockPos centerPos, int xRadius, int yRadius, int zRadius, boolean safeSurfacePlacement, Level world){
            return getRandomPositionWithinRange(centerPos, xRadius, yRadius, zRadius, safeSurfacePlacement, world, null, 1);
        }

        public BlockPos getRandomPositionWithinRange(BlockPos centerPos, int xRadius, int yRadius, int zRadius, boolean safeSurfacePlacement, Level world, @Nullable Predicate<BlockState> statePredicate, int tries){
            BlockPos.MutableBlockPos mutablePos = centerPos.mutable();

            for(int i = 0; i < tries; i++){
                int newX = (int)Math.floor(mutablePos.getX() + rand.nextFloat() * xRadius * 2 - xRadius);
                int newY = (int)Math.floor(mutablePos.getY() + rand.nextFloat() * yRadius * 2 - yRadius);
                int newZ = (int)Math.floor(mutablePos.getZ() + rand.nextFloat() * zRadius * 2 - zRadius);

                mutablePos.set(newX, newY, newZ);

                if(safeSurfacePlacement && world != null)
                    mutablePos.set(world.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, mutablePos));

                if(statePredicate == null || statePredicate.test(world.getBlockState(mutablePos)))
                    return mutablePos.immutable();
            }

            return centerPos;
        }
    }
}
package com.idark.valoria.registries.level.feature;

import com.idark.valoria.registries.*;
import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import pro.komaru.tridot.util.*;

public class SuspiciousIcebergFeature extends Feature<NoneFeatureConfiguration>{
    public SuspiciousIcebergFeature(Codec<NoneFeatureConfiguration> pCodec){
        super(pCodec);
    }

    /**
     * Places the given feature at the given location.
     * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
     * that they can safely generate into.
     *
     * @param pContext A context object with a reference to the level and the position the feature is being placed at
     */
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext){
        BlockPos blockpos = pContext.origin();
        WorldGenLevel worldgenlevel = pContext.level();
        blockpos = new BlockPos(blockpos.getX(), pContext.chunkGenerator().getSeaLevel(), blockpos.getZ());
        RandomSource randomsource = pContext.random();
        boolean flag = randomsource.nextDouble() > 0.7D;
        BlockState blockstate = Blocks.ICE.defaultBlockState();
        double d0 = randomsource.nextDouble() * 2.0D * Math.PI;
        int i = 11 - randomsource.nextInt(5);
        int j = 3 + randomsource.nextInt(3);
        boolean flag1 = randomsource.nextDouble() > 0.7D;
        int l = flag1 ? randomsource.nextInt(6) + 6 : randomsource.nextInt(15) + 3;
        if(!flag1 && randomsource.nextDouble() > 0.9D){
            l += randomsource.nextInt(19) + 7;
        }

        int i1 = Math.min(l + randomsource.nextInt(11), 18);
        int j1 = Math.min(l + randomsource.nextInt(7) - randomsource.nextInt(5), 11);
        int k1 = flag1 ? i : 11;
        for(int l1 = -k1; l1 < k1; ++l1){
            for(int i2 = -k1; i2 < k1; ++i2){
                for(int j2 = 0; j2 < l; ++j2){
                    int k2 = flag1 ? this.heightDependentRadiusEllipse(j2, l, j1) : this.heightDependentRadiusRound(randomsource, j2, l, j1);
                    if(flag1 || l1 < k2){
                        this.generateIcebergBlock(worldgenlevel, randomsource, blockpos, l, l1, j2, i2, k2, k1, flag1, j, d0, flag, blockstate);
                    }
                }
            }
        }

        this.smooth(worldgenlevel, blockpos, j1, l, flag1, i);
        for(int i3 = -k1; i3 < k1; ++i3){
            for(int j3 = -k1; j3 < k1; ++j3){
                for(int k3 = -1; k3 > -i1; --k3){
                    int l3 = flag1 ? Mth.ceil((float)k1 * (1.0F - (float)Math.pow(k3, 2.0D) / ((float)i1 * 8.0F))) : k1;
                    int l2 = this.heightDependentRadiusSteep(randomsource, -k3, i1, j1);
                    if(i3 < l2){
                        this.generateIcebergBlock(worldgenlevel, randomsource, blockpos, i1, i3, k3, j3, l2, l3, flag1, j, d0, flag, blockstate);
                    }
                }
            }
        }

        boolean flag2 = flag1 ? randomsource.nextDouble() > 0.1D : randomsource.nextDouble() > 0.7D;
        if(flag2){
            this.generateCutOut(randomsource, worldgenlevel, j1, l, blockpos, flag1, i, d0, j);
        }

        return true;
    }

    private void generateCutOut(RandomSource pRandom, LevelAccessor pLevel, int pMajorAxis, int pHeight, BlockPos pPos, boolean pElliptical, int pEllipseRadius, double pAngle, int pMinorAxis){
        int i = pRandom.nextBoolean() ? -1 : 1;
        int j = pRandom.nextBoolean() ? -1 : 1;
        int k = pRandom.nextInt(Math.max(pMajorAxis / 2 - 2, 1));
        if(pRandom.nextBoolean()){
            k = pMajorAxis / 2 + 1 - pRandom.nextInt(Math.max(pMajorAxis - pMajorAxis / 2 - 1, 1));
        }

        int l = pRandom.nextInt(Math.max(pMajorAxis / 2 - 2, 1));
        if(pRandom.nextBoolean()){
            l = pMajorAxis / 2 + 1 - pRandom.nextInt(Math.max(pMajorAxis - pMajorAxis / 2 - 1, 1));
        }

        if(pElliptical){
            k = l = pRandom.nextInt(Math.max(pEllipseRadius - 5, 1));
        }

        BlockPos blockpos = new BlockPos(i * k, 0, j * l);
        double d0 = pElliptical ? pAngle + (Math.PI / 2D) : pRandom.nextDouble() * 2.0D * Math.PI;
        for(int i1 = 0; i1 < pHeight - 3; ++i1){
            int j1 = this.heightDependentRadiusRound(pRandom, i1, pHeight, pMajorAxis);
            this.carve(j1, i1, pPos, pLevel, false, d0, blockpos, pEllipseRadius, pMinorAxis);
        }

        for(int k1 = -1; k1 > -pHeight + pRandom.nextInt(5); --k1){
            int l1 = this.heightDependentRadiusSteep(pRandom, -k1, pHeight, pMajorAxis);
            this.carve(l1, k1, pPos, pLevel, true, d0, blockpos, pEllipseRadius, pMinorAxis);
        }

    }

    private void carve(int pRadius, int pLocalY, BlockPos pPos, LevelAccessor pLevel, boolean pPlaceWater, double pPerpendicularAngle, BlockPos pEllipseOrigin, int pMajorRadius, int pMinorRadius){
        int i = pRadius + 1 + pMajorRadius / 3;
        int j = Math.min(pRadius - 3, 3) + pMinorRadius / 2 - 1;
        for(int k = -i; k < i; ++k){
            for(int l = -i; l < i; ++l){
                double d0 = this.signedDistanceEllipse(k, l, pEllipseOrigin, i, j, pPerpendicularAngle);
                if(d0 < 0.0D){
                    BlockPos blockpos = pPos.offset(k, pLocalY, l);
                    BlockState blockstate = pLevel.getBlockState(blockpos);
                    if(isIcebergState(blockstate) || blockstate.is(Blocks.SNOW_BLOCK)){
                        if(pPlaceWater){
                            this.setBlock(pLevel, blockpos, Blocks.WATER.defaultBlockState());
                        }else{
                            this.setBlock(pLevel, blockpos, Blocks.AIR.defaultBlockState());
                            this.removeFloatingSnowLayer(pLevel, blockpos);
                        }
                    }
                }
            }
        }

    }

    private void removeFloatingSnowLayer(LevelAccessor pLevel, BlockPos pPos){
        if(pLevel.getBlockState(pPos.above()).is(Blocks.SNOW)){
            this.setBlock(pLevel, pPos.above(), Blocks.AIR.defaultBlockState());
        }

    }

    private void generateIcebergBlock(LevelAccessor pLevel, RandomSource pRandom, BlockPos pPos, int pHeight, int pLocalX, int pLocalY, int pLocalZ, int pRadius, int pMajorRadius, boolean pElliptical, int pMinorRadius, double pAngle, boolean pPlaceSnow, BlockState pState){
        double d0 = pElliptical ? this.signedDistanceEllipse(pLocalX, pLocalZ, BlockPos.ZERO, pMajorRadius, this.getEllipseC(pLocalY, pHeight, pMinorRadius), pAngle) : this.signedDistanceCircle(pLocalX, pLocalZ, pRadius, pRandom);
        if(d0 < 0.0D){
            BlockPos blockpos = pPos.offset(pLocalX, pLocalY, pLocalZ);
            double d1 = pElliptical ? -0.5D : (double)(-6 - pRandom.nextInt(3));
            if(d0 > d1 && pRandom.nextDouble() > 0.9D){
                return;
            }

            this.setIcebergBlock(blockpos, pLevel, pRandom, pHeight - pLocalY, pHeight, pElliptical, pPlaceSnow, pState);
        }

    }

    private void setIcebergBlock(BlockPos pPos, LevelAccessor pLevel, RandomSource pRandom, int pHeightRemaining, int pHeight, boolean pElliptical, boolean pPlaceSnow, BlockState pState){
        BlockState blockstate = pLevel.getBlockState(pPos);
        if(blockstate.isAir() || blockstate.is(Blocks.SNOW_BLOCK) || blockstate.is(Blocks.ICE) || blockstate.is(Blocks.WATER)){
            boolean flag = !pElliptical || pRandom.nextDouble() > 0.05D;
            int i = pElliptical ? 3 : 2;
            if(pPlaceSnow && !blockstate.is(Blocks.WATER) && (double)pHeightRemaining <= (double)pRandom.nextInt(Math.max(1, pHeight / i)) + (double)pHeight * 0.6D && flag){
                this.setBlock(pLevel, pPos, Blocks.SNOW_BLOCK.defaultBlockState());
            }else{
                if(Tmp.rnd.chance(0.025f)){
                    this.setBlock(pLevel, pPos, BlockRegistry.suspiciousIce.get().defaultBlockState());
                }else{
                    this.setBlock(pLevel, pPos, pState);
                }
            }
        }

    }

    private int getEllipseC(int pY, int pHeight, int pMinorAxis){
        int i = pMinorAxis;
        if(pY > 0 && pHeight - pY <= 3){
            i = pMinorAxis - (4 - (pHeight - pY));
        }

        return i;
    }

    private double signedDistanceCircle(int pX, int pZ, int pRadius, RandomSource pRandom){
        float f = 10.0F * Mth.clamp(pRandom.nextFloat(), 0.2F, 0.8F) / (float)pRadius;
        return (double)f + Math.pow(pX - BlockPos.ZERO.getX(), 2.0D) + Math.pow(pZ - BlockPos.ZERO.getZ(), 2.0D) - Math.pow(pRadius, 2.0D);
    }

    /**
     * Given an ellipse defined by the equation {@code (x/a)^2 + (y/b)^2 = 1}, where {@code a} and {@code b} are the
     * semi-major and semi-minor axes respectively, this computes the distance between an arbitrary point and the
     * ellipse.
     * The point (x, y) is within the ellipse if the return value is < 0, outside the ellipse if the return value is > 0
     * and exactly on the edge of the ellipse if the return value is 0.
     *
     * @param pX           The x position of the point to measure the distance to.
     * @param pZ           The z position of the point to measure distance to.
     * @param pCenter      The center point of the ellipse.
     * @param pMajorRadius The semi-major axis ({@code a}) of the ellipse.
     * @param pMinorRadius The semi-minor axis ({@code b}) of the ellipse
     * @param pAngle       The rotation angle of the ellipse (the angle from the positive horizontal axis to the ellipse's
     *                     major axis).
     */
    private double signedDistanceEllipse(int pX, int pZ, BlockPos pCenter, int pMajorRadius, int pMinorRadius, double pAngle){
        return Math.pow(((double)(pX - pCenter.getX()) * Math.cos(pAngle) - (double)(pZ - pCenter.getZ()) * Math.sin(pAngle)) / (double)pMajorRadius, 2.0D) + Math.pow(((double)(pX - pCenter.getX()) * Math.sin(pAngle) + (double)(pZ - pCenter.getZ()) * Math.cos(pAngle)) / (double)pMinorRadius, 2.0D) - 1.0D;
    }

    private int heightDependentRadiusRound(RandomSource pRandom, int pY, int pHeight, int pMajorAxis){
        float f = 3.5F - pRandom.nextFloat();
        float f1 = (1.0F - (float)Math.pow(pY, 2.0D) / ((float)pHeight * f)) * (float)pMajorAxis;
        if(pHeight > 15 + pRandom.nextInt(5)){
            int i = pY < 3 + pRandom.nextInt(6) ? pY / 2 : pY;
            f1 = (1.0F - (float)i / ((float)pHeight * f * 0.4F)) * (float)pMajorAxis;
        }

        return Mth.ceil(f1 / 2.0F);
    }

    /**
     * Given a horizontal projection of an iceberg, defines the three-dimensional extrusion by defining a radius at any
     * given y value.
     * The radius curve is a parabolic function, resulting in more rounded iceberg peaks.
     *
     * @param pY         The y value to calculate a radius at.
     * @param pHeight    The maximum height of the iceberg.
     * @param pMaxRadius The maximum radius of the iceberg, at the horizontal.
     */
    private int heightDependentRadiusEllipse(int pY, int pHeight, int pMaxRadius){
        float f1 = (1.0F - (float)Math.pow(pY, 2.0D) / ((float)pHeight)) * (float)pMaxRadius;
        return Mth.ceil(f1 / 2.0F);
    }

    /**
     * Given a horizontal projection of an iceberg, defines the three-dimensional extrusion by defining a radius at any
     * given y value.
     * The radius curve is a linear function, with a slope that is both dependent on the {@code majorAxis} and randomly
     * varies, which results in steep conical icebergs.
     *
     * @param pRandom    A random to use to vary the slope of the falloff curve.
     * @param pY         The y value to calculate a radius at.
     * @param pHeight    The maximum height of the iceberg.
     * @param pMaxRadius The maximum radius of the iceberg, at the horizontal.
     */
    private int heightDependentRadiusSteep(RandomSource pRandom, int pY, int pHeight, int pMaxRadius){
        float f = 1.0F + pRandom.nextFloat() / 2.0F;
        float f1 = (1.0F - (float)pY / ((float)pHeight * f)) * (float)pMaxRadius;
        return Mth.ceil(f1 / 2.0F);
    }

    private static boolean isIcebergState(BlockState pState){
        return pState.is(Blocks.PACKED_ICE) || pState.is(Blocks.SNOW_BLOCK) || pState.is(Blocks.BLUE_ICE);
    }

    private boolean belowIsAir(BlockGetter pLevel, BlockPos pPos){
        return pLevel.getBlockState(pPos.below()).isAir();
    }

    /**
     * Smooths out an iceberg by removing blocks which either have air below, or non-iceberg blocks on three or more
     * horizontal sides, with air.
     */
    private void smooth(LevelAccessor pLevel, BlockPos pPos, int pMajorRadius, int pHeight, boolean pElliptical, int pMinorRadius){
        int i = pElliptical ? pMinorRadius : pMajorRadius / 2;
        for(int j = -i; j <= i; ++j){
            for(int k = -i; k <= i; ++k){
                for(int l = 0; l <= pHeight; ++l){
                    BlockPos blockpos = pPos.offset(j, l, k);
                    BlockState blockstate = pLevel.getBlockState(blockpos);
                    if(isIcebergState(blockstate) || blockstate.is(Blocks.SNOW)){
                        if(this.belowIsAir(pLevel, blockpos)){
                            this.setBlock(pLevel, blockpos, Blocks.AIR.defaultBlockState());
                            this.setBlock(pLevel, blockpos.above(), Blocks.AIR.defaultBlockState());
                        }else if(isIcebergState(blockstate)){
                            BlockState[] ablockstate = new BlockState[]{pLevel.getBlockState(blockpos.west()), pLevel.getBlockState(blockpos.east()), pLevel.getBlockState(blockpos.north()), pLevel.getBlockState(blockpos.south())};
                            int i1 = 0;

                            for(BlockState blockstate1 : ablockstate){
                                if(!isIcebergState(blockstate1)){
                                    ++i1;
                                }
                            }

                            if(i1 >= 3){
                                this.setBlock(pLevel, blockpos, Blocks.AIR.defaultBlockState());
                            }
                        }
                    }
                }
            }
        }

    }
}
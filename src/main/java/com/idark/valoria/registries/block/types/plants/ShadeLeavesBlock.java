package com.idark.valoria.registries.block.types.plants;

import com.idark.valoria.client.particle.*;
import net.minecraft.client.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.client.graphics.particle.data.*;

import java.awt.*;

public class ShadeLeavesBlock extends LeavesBlock{

    public ShadeLeavesBlock(Properties properties){
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random){
        super.animateTick(state, world, pos, random);
        int i = Minecraft.getInstance().getBlockColors().getColor(state, world, pos, 0);
        if(world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).isAir()){
            if(random.nextFloat() < 0.015){
                double x = (double)pos.getX() + random.nextDouble();
                double y = (double)pos.getY() - 0.05D;
                double z = (double)pos.getZ() + random.nextDouble();

                Color color = new Color((i >> 16 & 255) / 255.0F, (i >> 8 & 255) / 255.0F, (i & 255) / 255.0F);
                Vec3 position = new Vec3(x, y, z);
                ParticleEffects.leafParticle(world, position, ColorParticleData.create(color.darker().darker(), color).build());
            }
        }
    }
}

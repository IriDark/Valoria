package com.idark.valoria.registries.block.types;

import com.idark.valoria.client.particle.*;
import net.minecraft.client.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import team.lodestar.lodestone.systems.particle.data.color.*;

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
                Color color = new Color((i >> 16 & 255) / 255.0F, (i >> 8 & 255) / 255.0F, (i & 255) / 255.0F);
                Vec3 position = new Vec3(pos.getX() + 0.5F + ((random.nextFloat() - 0.5f) * 0.9f), pos.getY() + 0.05, pos.getZ() + 0.5F + ((random.nextFloat() - 0.5f * 0.9f)));
                ParticleEffects.leafParticle(world, position, ColorParticleData.create(color, color.darker()).build()).spawnParticles();
            }
        }
    }
}

package com.idark.valoria.registries.world.block.types;

import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.registries.world.levelgen.LevelGen;
import com.idark.valoria.registries.world.levelgen.portal.ValoriaTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;

public class ValoriaPortalBlock extends Block {

    public ValoriaPortalBlock(Properties pProperties) {
        super(pProperties);
    }

    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity.canChangeDimensions()) {
            handlePortal(pEntity, pPos);
        }
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextInt(100) == 0) {
            pLevel.playLocalSound((double) pPos.getX() + 0.5D, (double) pPos.getY() + 0.5D, (double) pPos.getZ() + 0.5D, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5F, pRandom.nextFloat() * 0.4F - 3F, false);
        }

        for (int i = 0; i < 0.8f; ++i) {
            double d0 = (double) pPos.getX() + pRandom.nextDouble();
            double d1 = (double) pPos.getY() + pRandom.nextDouble();
            double d2 = (double) pPos.getZ() + pRandom.nextDouble();
            double d3 = 0.0D;
            double d4 = 0.07D;
            double d5 = 0.0D;

            pLevel.addParticle(ModParticles.VOID_GLITTER.get(), d0, d1, d2, d3, d4, d5);
        }
    }

    private void handlePortal(Entity player, BlockPos pPos) {
        if (player.isOnPortalCooldown()) {
            player.setPortalCooldown();
        } else if (player.level() instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = player.level().dimension() == LevelGen.VALORIA_KEY ? Level.OVERWORLD : LevelGen.VALORIA_KEY;
            ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
            if (portalDimension != null && !player.isPassenger()) {
                if (resourcekey == LevelGen.VALORIA_KEY) {
                    player.changeDimension(portalDimension, new ValoriaTeleporter(pPos, true));
                } else {
                    player.changeDimension(portalDimension, new ValoriaTeleporter(pPos, false));
                }

                player.setPortalCooldown();
            }
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos pos2, boolean unknown) {
        BlockPattern.BlockPatternMatch frame = ValoriaPortalFrame.getOrCreatePortalShape().find(world, pos);
        if (frame == null && world.dimension() != LevelGen.VALORIA_KEY) {
            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
    }
}
package com.idark.valoria.registries.item.types;

import com.idark.valoria.client.particle.*;
import com.idark.valoria.registries.entity.projectile.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.client.particle.*;
import mod.maxbogomol.fluffy_fur.client.particle.data.*;
import mod.maxbogomol.fluffy_fur.common.easing.*;
import mod.maxbogomol.fluffy_fur.registry.client.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.*;

/**
 * Used to debug some particle packets and other things
 */
public class DebugItem extends Item {

    public DebugItem(Properties pProperties) {
        super(pProperties);
    }

    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    protected void performSpellCasting(Level level, Player player) {
        double d0 = Math.min(player.getYRot(), player.getY());
        double d1 = Math.max(player.getYRot(), player.getY()) + 1.0D;
        float f = (float) Mth.atan2(player.getLookAngle().z - player.getZ(), 0.0);
        for(int i = 0; i < 5; ++i){
            float f1 = f + (float)i * (float)Math.PI * 0.5F;
            this.createSpellEntity(level, player.getX() + (double)Mth.cos(f1) * 1.5D, player.getZ() + (double)Mth.sin(f1) * 1.5D, d0, d1, f1, 0);
        }

        for(int k = 0; k < 8; ++k){
            float f2 = f + (float)k * (float)Math.PI / 4.0F + 1.2566371F;
            this.createSpellEntity(level, player.getX() + (double)Mth.cos(f2) * 3D, player.getZ() + (double)Mth.sin(f2) * 3D, d0, d1, f2, 6);
        }

        for(int k = 0; k < 16; ++k){
            float f2 = f + (float)k * (float)Math.PI / 7.0F + 1.2566371F;
            this.createSpellEntity(level, player.getX() + (double)Mth.cos(f2) * 6D, player.getZ() + (double)Mth.sin(f2) * 6D, d0, d1, f2, 12);
        }
    }

    private void createSpellEntity(Level level, double pX, double pZ, double pMinY, double pMaxY, float pYRot, int pWarmupDelay) {
        BlockPos blockpos = BlockPos.containing(pX, pMaxY, pZ);
        boolean flag = false;
        double d0 = 0.0D;
        do {
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = level.getBlockState(blockpos1);
            if (blockstate.isFaceSturdy(level, blockpos1, Direction.UP)) {
                if (!level.isEmptyBlock(blockpos)) {
                    BlockState blockstate1 = level.getBlockState(blockpos);
                    VoxelShape voxelshape = blockstate1.getCollisionShape(level, blockpos);
                    if (!voxelshape.isEmpty()) {
                        d0 = voxelshape.max(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            blockpos = blockpos.below();
        } while (blockpos.getY() >= Mth.floor(pMinY) - 1);
        if (flag) {
            ParticleBuilder.create(ParticleRegistry.SMOKE)
            .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
            .setColorData(ColorParticleData.create(ColorUtil.valueOf("66b4a3"), Pal.vividGreen).setEasing(Easing.BOUNCE_IN).build())
            .setTransparencyData(GenericParticleData.create().setRandomValue(1, 0.0f).setEasing(Easing.CUBIC_OUT).build())
            .setScaleData(GenericParticleData.create(0.7f, 0.4f, 0f).build())
            .setLifetime(95 + level.random.nextInt(100))
            .randomVelocity(0.05, 0.15, 0.05)
            .randomOffset(0.025f)
            .spawn(level, pX, (double) blockpos.getY() + d0, pZ);
            level.addFreshEntity(new Devourer(level, pX, (double) blockpos.getY() + d0, pZ, pYRot, pWarmupDelay, null));
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        playerIn.startUsingItem(handIn);
        performSpellCasting(worldIn, playerIn);
        return InteractionResultHolder.consume(itemstack);
    }
}
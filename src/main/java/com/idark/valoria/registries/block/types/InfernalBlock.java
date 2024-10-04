package com.idark.valoria.registries.block.types;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.*;

public class InfernalBlock extends Block {
    private static IntegerProperty STATE = IntegerProperty.create("awakened", 0, 1);

    public InfernalBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(STATE, 0));
    }

    private static boolean isValidFuel(ItemStack stack) {
        return stack.getItem() == ItemsRegistry.infernalStone.get();
    }

    private static boolean Infernal(Player player, RandomSource rand, Level worldIn, BlockPos pos, BlockState state) {
        if (state.getValue(STATE) == 0) {
            worldIn.playSound(player, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_AMBIENT, SoundSource.BLOCKS, 10f, 1f);
            for (int i = 0; i < 25; i++) {
                double d2 = rand.nextGaussian() * 0.02D;
                double d3 = rand.nextGaussian() * 0.02D;
                double d4 = rand.nextGaussian() * 0.02D;
                double d5 = 0.5D - 3.0D;
                double d6 = (double) pos.getX() + d5 + rand.nextDouble() * 3.0D * 2.0D;
                double d7 = (double) pos.getY() + rand.nextDouble() + 0.5D;
                double d8 = (double) pos.getZ() + d5 + rand.nextDouble() * 3.0D * 2.0D;
                worldIn.addParticle(ParticleTypes.LAVA, d6, d7, d8, d2, d3, d4);
            }
            worldIn.setBlockAndUpdate(pos, state.setValue(STATE, 1));
            return false;
        }

        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STATE);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack itemstack = player.getItemInHand(handIn);
        if (handIn == InteractionHand.MAIN_HAND && !isValidFuel(itemstack) && isValidFuel(player.getItemInHand(InteractionHand.OFF_HAND))) {
            return InteractionResult.PASS;
        } else if (isValidFuel(itemstack) && Infernal(player, level.getRandom(), level, pos, state)) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }
}
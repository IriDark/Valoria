package com.idark.valoria.registries.world.block.types;

import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.client.particle.types.Particles;
import com.idark.valoria.registries.TagsRegistry;
import com.idark.valoria.registries.world.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public class KeyPadBlock extends Block {
    private static final BooleanProperty STATE = BooleanProperty.create("key");
    Random rand = new Random();

    public KeyPadBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(STATE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STATE);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public InteractionResult use(BlockState state, Level pLevel, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (state.getValue(STATE)) {
            keyOpen(player, pLevel, pos);
            return InteractionResult.SUCCESS;
        }

        if (!state.getValue(STATE)) {
            ItemStack itemstack = player.getItemInHand(handIn);
            if (!isValidFuel(itemstack)) {
                return InteractionResult.PASS;
            } else if (isValidFuel(itemstack)) {
                itemKey(player, pLevel, pos, state);
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                    return InteractionResult.SUCCESS;
                }
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.SUCCESS;
    }

    private boolean isValidFuel(ItemStack stack) {
        return stack.getItem() == ModItems.VOID_KEY.get();
    }

    private void keyOpen(Player player, Level pLevel, BlockPos pos) {
        pLevel.playSound(player, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.BLOCKS, 0.8F, 1.2F);
        pLevel.playSound(player, player.blockPosition(), SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1.2F, 1.0F);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    BlockPos targetPos = pos.offset(i, j, k);
                    BlockState targetState = pLevel.getBlockState(targetPos);
                    if (targetState.is(TagsRegistry.KEY_BLOCKS)) {
                        pLevel.setBlockAndUpdate(targetPos, Blocks.AIR.defaultBlockState());
                        for (int a = 0; a < 12; a++) {
                            Particles.create(ModParticles.TRANSFORM_PARTICLE)
                                    .addVelocity(((rand.nextDouble() - 0.5D) / 30), ((rand.nextDouble() - 0.5D) / 30), ((rand.nextDouble() - 0.5D) / 30))
                                    .setAlpha(1.0f, 0)
                                    .setScale(0.3f, 0)
                                    .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                                    .setLifetime(36)
                                    .setSpin((0.5f * (float) ((rand.nextDouble() - 0.5D) * 2)))
                                    .spawn(pLevel, pos.getX() + (rand.nextDouble() * 1.25), pos.getY() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), pos.getZ() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25))
                                    .spawn(pLevel, targetPos.getX() + (rand.nextDouble() * 1.25), targetPos.getY() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), targetPos.getZ() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25));
                        }
                    }
                }
            }
        }

        pLevel.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
    }

    private void itemKey(Player player, Level pLevel, BlockPos pos, BlockState state) {
        pLevel.playSound(player, player.blockPosition(), SoundEvents.CHAIN_BREAK, SoundSource.BLOCKS, 1.0F, 0.95F);
        for (int i = 0; i < 25; i++) {
            double d2 = rand.nextGaussian() * 0.02D;
            double d3 = rand.nextGaussian() * 0.02D;
            double d4 = rand.nextGaussian() * 0.02D;
            double d5 = 0.5D - 2.0D;
            double d6 = (double) pos.getX() + d5 + rand.nextDouble() * 3.0D;
            double d7 = (double) pos.getY() + rand.nextDouble() + 0.5D;
            double d8 = (double) pos.getZ() + d5 + rand.nextDouble() * 3.0D;
            pLevel.addParticle(ParticleTypes.POOF, d6, d7, d8, d2, d3, d4);
        }

        pLevel.setBlockAndUpdate(pos, state.setValue(STATE, true));
    }
}
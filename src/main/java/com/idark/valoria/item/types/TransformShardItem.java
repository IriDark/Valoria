package com.idark.valoria.item.types;

import com.idark.valoria.block.ModBlocks;
import com.idark.valoria.particle.ModParticles;
import com.idark.valoria.particle.Particles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class TransformShardItem extends Item {
    Random rand = new Random();
    public TransformType type;

    public TransformShardItem(TransformType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        return InteractionResultHolder.success(stack);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level worldIn = context.getLevel();
        BlockState state = worldIn.getBlockState(context.getClickedPos());
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();

        rightClickOnCertainBlockState(stack, player, worldIn, state, pos);
        return super.onItemUseFirst(stack, context);
    }

    private void rightClickOnCertainBlockState(ItemStack stack, Player player, Level worldIn, BlockState state, BlockPos pos) {
        if (state.is(ModBlocks.VOID_PILLAR.get())) {
            worldIn.playSound(player, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_AMBIENT, SoundSource.BLOCKS, 10f, 1f);
            worldIn.playSound(player, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) {
                stack.shrink(1);
            }

            switch (type) {
                case WICKED:
                    for (int i = 0; i < 26; i++) {
                        Particles.create(ModParticles.TRANSFORM_PARTICLE)
                                .addVelocity(((rand.nextDouble() - 0.5D) / 30), ((rand.nextDouble() - 0.5D) / 30), ((rand.nextDouble() - 0.5D) / 30))
                                .setAlpha(1.0f, 0)
                                .setScale(0.3f, 0)
                                .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                                .setLifetime(36)
                                .setSpin((0.5f * (float) ((rand.nextDouble() - 0.5D) * 2)))
                                .spawn(worldIn, pos.getX() + (rand.nextDouble() * 1.25), pos.getY() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), pos.getZ() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25));
                    }

                    worldIn.setBlockAndUpdate(pos, ModBlocks.VOID_PILLAR_AMETHYST.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
                    break;
                case SOUL:
                    for (int i = 0; i < 10; i++) {
                        worldIn.addParticle(ParticleTypes.SOUL, pos.getX(), pos.getY() + 0.5F + rand.nextDouble() * 0.75, pos.getZ() + rand.nextDouble(), 0d, 0.05d, 0d);
                    }

                    worldIn.setBlockAndUpdate(pos, ModBlocks.CHARGED_VOID_PILLAR.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
                    break;
            }
        }
    }
}
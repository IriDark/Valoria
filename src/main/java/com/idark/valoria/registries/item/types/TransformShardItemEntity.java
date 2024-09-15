package com.idark.valoria.registries.item.types;

import com.idark.valoria.client.particle.ParticleEffects;
import com.idark.valoria.core.interfaces.IParticleItemEntity;
import com.idark.valoria.registries.BlockRegistry;
import com.idark.valoria.registries.ItemsRegistry;
import com.idark.valoria.util.Pal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;

//TODO: probably rework (maybe?)
public class TransformShardItemEntity extends Item implements IParticleItemEntity {
    public TransformShardItemEntity(Properties properties) {
        super(properties);
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
        var rand = worldIn.random;
        if (state.is(BlockRegistry.VOID_PILLAR.get())) {
            worldIn.playSound(player, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_AMBIENT, SoundSource.BLOCKS, 10f, 1f);
            worldIn.playSound(player, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (stack.is(ItemsRegistry.AMETHYST.get())) {
                worldIn.setBlockAndUpdate(pos, BlockRegistry.VOID_PILLAR_AMETHYST.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
                for (int i = 0; i < 26; i++) {
                    Vec3 position = new Vec3(pos.getX() + (rand.nextDouble() * 1.25), pos.getY() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), pos.getZ() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25));
                    ParticleEffects.transformParticle(worldIn, position, ColorParticleData.create(Pal.moderatePink, Pal.verySoftPink).build()).spawnParticles();
                }
            } else {
                worldIn.setBlockAndUpdate(pos, BlockRegistry.CHARGED_VOID_PILLAR.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
                for (int i = 0; i < 10; i++) {
                    worldIn.addParticle(ParticleTypes.SOUL, pos.getX() + (rand.nextDouble() * 1.25), pos.getY() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), pos.getZ() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), 0d, 0.05d, 0d);
                }
            }

            if (!player.isCreative()) {
                stack.shrink(1);
            }
        }

        return super.onItemUseFirst(stack, context);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnParticles(Level level, ItemEntity entity) {
        var rand = level.random;
        Vec3 pos = new Vec3(entity.getX() + (rand.nextDouble() - 0.5f) / 6, entity.getY() + 0.4F, entity.getZ());
        if (entity.getItem().is(ItemsRegistry.AMETHYST.get())) {
            ParticleEffects.itemParticles(level, pos, ColorParticleData.create(Pal.softMagenta, Pal.strongRed).build()).spawnParticles();
        } else {
            ParticleEffects.itemParticles(level, pos, ColorParticleData.create(Pal.oceanic, Pal.moderatePink).build()).spawnParticles();
        }
    }
}
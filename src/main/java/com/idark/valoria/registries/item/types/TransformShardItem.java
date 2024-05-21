package com.idark.valoria.registries.item.types;

import com.idark.valoria.client.particle.*;
import com.idark.valoria.client.particle.types.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.interfaces.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

import java.util.*;

//TODO: probably rework
public class TransformShardItem extends Item implements IParticleItem{
    Random rand = new Random();

    public TransformShardItem(Properties properties){
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand){
        ItemStack stack = player.getItemInHand(hand);
        return InteractionResultHolder.success(stack);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context){
        Level worldIn = context.getLevel();
        BlockState state = worldIn.getBlockState(context.getClickedPos());
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        if(state.is(BlockRegistry.VOID_PILLAR.get())){
            worldIn.playSound(player, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_AMBIENT, SoundSource.BLOCKS, 10f, 1f);
            worldIn.playSound(player, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if(stack.is(ItemsRegistry.AMETHYST.get())){
                worldIn.setBlockAndUpdate(pos, BlockRegistry.VOID_PILLAR_AMETHYST.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
                for(int i = 0; i < 26; i++){
                    Particles.create(ParticleRegistry.TRANSFORM_PARTICLE)
                    .addVelocity(((rand.nextDouble() - 0.5D) / 30), ((rand.nextDouble() - 0.5D) / 30), ((rand.nextDouble() - 0.5D) / 30))
                    .setAlpha(1.0f, 0)
                    .setScale(0.3f, 0)
                    .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                    .setLifetime(36)
                    .setSpin((0.5f * (float)((rand.nextDouble() - 0.5D) * 2)))
                    .spawn(worldIn, pos.getX() + (rand.nextDouble() * 1.25), pos.getY() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), pos.getZ() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25));
                }
            }else{
                worldIn.setBlockAndUpdate(pos, BlockRegistry.CHARGED_VOID_PILLAR.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
                for(int i = 0; i < 10; i++){
                    worldIn.addParticle(ParticleTypes.SOUL, pos.getX() + (rand.nextDouble() * 1.25), pos.getY() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), pos.getZ() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), 0d, 0.05d, 0d);
                }
            }

            if(!player.isCreative()){
                stack.shrink(1);
            }
        }

        return super.onItemUseFirst(stack, context);
    }

    @Override
    public void addParticles(Level level, ItemEntity entity){
        if(entity.getItem().is(ItemsRegistry.AMETHYST.get())){
            Particles.create(ParticleRegistry.GLOWING_SPHERE)
            .addVelocity((rand.nextDouble() / 30), 0.05f, (rand.nextDouble() / 30))
            .setAlpha(0.25f, 0)
            .setScale(0.1f, 0)
            .setColor(0.866f, 0.643f, 0.815f, 0.266f, 0.943f, 5.815f)
            .setLifetime(7)
            .setSpin((0.5f * (float)((rand.nextDouble() - 0.5D) * 2)))
            .spawn(level, entity.getX() + (rand.nextDouble() - 0.5f) / 2, entity.getY() + 0.4F, entity.getZ());
        }else{
            Particles.create(ParticleRegistry.GLOWING_SPHERE)
            .addVelocity((rand.nextDouble() / 30), 0.05f, (rand.nextDouble() / 30))
            .setAlpha(0.25f, 0)
            .setScale(0.1f, 0)
            .setColor(0.466f, 0.643f, 0.815f, 1.466f, 0.643f, 0.815f)
            .setLifetime(7)
            .setSpin((0.5f * (float)((rand.nextDouble() - 0.5D) * 2)))
            .spawn(level, entity.getX() + (rand.nextDouble() - 0.5f) / 2, entity.getY() + 0.4F, entity.getZ());
        }
    }
}
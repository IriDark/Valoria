package com.idark.valoria.registries.item.types;

import com.idark.valoria.client.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
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
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.client.gfx.particle.data.*;

//TODO: probably rework (maybe?)
public class TransformShardItem extends Item implements ParticleItemEntity{
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
        var rand = worldIn.random;
        if(state.is(BlockRegistry.voidPillar.get())){
            worldIn.playSound(player, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_AMBIENT, SoundSource.BLOCKS, 10f, 1f);
            worldIn.playSound(player, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if(stack.is(ItemsRegistry.wickedAmethyst.get())){
                worldIn.setBlockAndUpdate(pos, BlockRegistry.voidPillarAmethyst.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
                for(int i = 0; i < 26; i++){
                    Vec3 position = new Vec3(pos.getX() + (rand.nextDouble() * 1.25), pos.getY() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25), pos.getZ() + 0.5F + ((rand.nextDouble() - 0.5D) * 1.25));
                    ParticleEffects.transformParticle(worldIn, position, ColorParticleData.create(Pal.moderatePink, Pal.verySoftPink).build());
                }
            }else{
                worldIn.setBlockAndUpdate(pos, BlockRegistry.chargedVoidPillar.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
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

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnParticles(Level level, ItemEntity entity){
        var rand = level.random;
        Vec3 pos = new Vec3(entity.getX() + (rand.nextDouble() - 0.5f) / 6, entity.getY() + 0.4F, entity.getZ());
        if(entity.getItem().is(ItemsRegistry.wickedAmethyst.get())){
            ParticleEffects.itemParticles(level, pos, ColorParticleData.create(Pal.softMagenta, Pal.strongRed).build());
        }else{
            ParticleEffects.itemParticles(level, pos, ColorParticleData.create(Pal.oceanic, Pal.moderatePink).build());
        }
    }
}
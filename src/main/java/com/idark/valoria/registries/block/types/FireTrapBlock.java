package com.idark.valoria.registries.block.types;

import com.google.common.collect.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.gameevent.*;
import net.minecraft.world.level.material.*;
import pro.komaru.tridot.client.graphics.particle.data.*;

public class FireTrapBlock extends Block{
    public float damage;
    public int secondsOnFire;
    public BlockState state;
    public ColorParticleData color;
    public final ImmutableList<MobEffectInstance> effects;


    public FireTrapBlock(BlockState pState, float pDamage, int pSecondsOnFire, ColorParticleData pColor, BlockBehaviour.Properties properties){
        super(properties);
        this.damage = pDamage;
        this.secondsOnFire = pSecondsOnFire;
        this.state = pState;
        this.color = pColor;
        this.effects = ImmutableList.of();
    }

    public FireTrapBlock(BlockState pState, float pDamage, int pSecondsOnFire, ColorParticleData pColor, BlockBehaviour.Properties properties, MobEffectInstance... pEffects){
        super(properties);
        this.damage = pDamage;
        this.secondsOnFire = pSecondsOnFire;
        this.state = pState;
        this.color = pColor;
        this.effects = ImmutableList.copyOf(pEffects);
    }

    public static boolean isWaterNearby(Level world, BlockPos centerPos, int radius){
        for(int xOffset = -radius; xOffset <= radius; xOffset++){
            for(int yOffset = -radius; yOffset <= radius; yOffset++){
                for(int zOffset = -radius; zOffset <= radius; zOffset++){
                    BlockPos currentPos = centerPos.offset(xOffset, yOffset, zOffset);
                    FluidState fluidState = world.getFluidState(currentPos);

                    if(fluidState.getType() == Fluids.WATER){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entityIn){
        if(level.getDifficulty() != Difficulty.PEACEFUL){
            if(entityIn instanceof LivingEntity living && !EnchantmentHelper.hasFrostWalker((LivingEntity)entityIn)){
                int radius = 1;
                boolean isWaterNearby = isWaterNearby(level, pos, radius);
                if(isWaterNearby){
                    level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.05F, level.getRandom().nextFloat() * 0.5F + 0.5F);
                    level.addParticle(ParticleTypes.POOF, pos.getX() + level.getRandom().nextDouble(), pos.getY() + 0.7D, pos.getZ() + level.getRandom().nextDouble(), 0d, 0.05d, 0d);
                }else{
                    level.playSound(null, pos, SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.BLOCKS, 0.3F, level.getRandom().nextFloat() * 0.25F + 0.6F);
                    if(level instanceof ServerLevel serverLevel){
                        PacketHandler.sendToTracking(serverLevel, pos, new FireTrapParticlePacket(pos.getCenter().x, pos.getY(), pos.getCenter().z, (int)color.r1, (int)color.g1, (int)color.b1, (int)color.r2, (int)color.g2, (int)color.b2));
                        living.hurt(living.damageSources().inFire(), damage);
                        living.setSecondsOnFire(secondsOnFire);
                        if(!effects.isEmpty()){
                            for(MobEffectInstance effectInstance : effects){
                                living.addEffect(new MobEffectInstance(effectInstance));
                            }
                        }

                        living.gameEvent(GameEvent.BLOCK_ACTIVATE);
                        serverLevel.setBlockAndUpdate(pos, this.state);
                    }
                }
            }
        }
    }
}
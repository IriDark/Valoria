package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.entity.living.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;

public class SummonBook extends Item{
    private int lifetime;
    private int uses;
    private EntityType<AbstractMinionEntity> summonedEntity;
    public SummonBook(Properties pProperties){
        super(pProperties);
    }

    private void spawnMinions(ServerLevel serverLevel, Player player){
        BlockPos blockpos = player.getOnPos();
        AbstractMinionEntity summoned = summonedEntity.create(player.level());
        if(summoned != null && serverLevel.isEmptyBlock(blockpos)){
            summoned.moveTo(blockpos, 0.0F, 0.0F);
            summoned.finalizeSpawn(serverLevel, player.level().getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);
            summoned.setOwner(player);
            summoned.setBoundOrigin(blockpos);
            summoned.setLimitedLife(lifetime + serverLevel.random.nextInt(60));
            serverLevel.addFreshEntityWithPassengers(summoned);
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(!playerIn.isShiftKeyDown()){
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public UseAnim getUseAnimation(ItemStack pStack){
        return UseAnim.CUSTOM;
    }

    public int getUseDuration(ItemStack stack){
        return 7;
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving){
        Player player = (Player)entityLiving;
        if(level instanceof ServerLevel server) {
            spawnMinions(server, player);
        }

        return stack;
    }
}
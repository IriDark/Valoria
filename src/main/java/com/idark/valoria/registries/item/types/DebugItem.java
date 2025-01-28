package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.living.minions.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;

/**
 * Used to debug some particle packets and other things
 */
public class DebugItem extends Item{

    public DebugItem(Properties pProperties){
        super(pProperties);
    }

    public int getUseDuration(@NotNull ItemStack stack){
        return 72000;
    }

    private void spawn(ServerLevel level, BlockPos blockpos, Player player, float angle){
        CrystalEntity crystal = EntityTypeRegistry.CRYSTAL.get().create(player.level());
        if(crystal != null && level.isEmptyBlock(blockpos)){
            crystal.moveTo(blockpos, 0.0F, 0.0F);
            crystal.finalizeSpawn(level, player.level().getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);
            crystal.setOwner(player);
            crystal.setRadius(3f);
            crystal.setAngle(angle);
            crystal.setBoundOrigin(blockpos);
            crystal.setLimitedLife(325);
            level.addFreshEntity(crystal);
        }
    }

    protected void performSpellCasting(ServerLevel level, Player player){
        for(int i = 0; i < 4; ++i){
            float initialAngle = (float)((2 * Math.PI / 4) * i);
            spawn(level, player.blockPosition().above(), player, initialAngle);
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        playerIn.startUsingItem(handIn);
        if(worldIn instanceof ServerLevel level) {
            performSpellCasting(level, playerIn);
        }
        return InteractionResultHolder.consume(itemstack);
    }
}
package com.idark.darkrpg.block.types;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;
	
public class VariatedBlock extends Block {
    private static IntegerProperty STATE = IntegerProperty.create("variation", 0, 4);
	Random random = new Random();

    public VariatedBlock(AbstractBlock.Properties properties) {
		super(properties);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STATE);
        super.fillStateContainer(builder);
    }
	
   public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
   this.setDefaultState(this.stateContainer.getBaseState().with(STATE, random.nextInt(4)));
   }
}
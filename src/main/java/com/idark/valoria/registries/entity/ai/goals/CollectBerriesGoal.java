package com.idark.valoria.registries.entity.ai.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class CollectBerriesGoal extends MoveToBlockGoal {
    protected int ticksWaited;

    public CollectBerriesGoal(PathfinderMob pMob, double pSpeedModifier, int pSearchRange, int pVerticalSearchRange) {
        super(pMob, pSpeedModifier, pSearchRange, pVerticalSearchRange);
    }

    public double acceptedDistance() {
        return 2.0D;
    }

    public boolean shouldRecalculatePath() {
        return this.tryTicks % 100 == 0;
    }

    protected boolean isValidTarget(LevelReader pLevel, @NotNull BlockPos pPos) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        return blockstate.is(Blocks.SWEET_BERRY_BUSH) && blockstate.getValue(SweetBerryBushBlock.AGE) >= 2 || CaveVines.hasGlowBerries(blockstate);
    }

    public void tick() {
        if (this.isReachedTarget()) {
            if (this.ticksWaited >= 40) {
                this.onReachedTarget();
            } else {
                ++this.ticksWaited;
            }
        }

        super.tick();
    }

    protected void onReachedTarget() {
        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(mob.level(), mob)) {
            BlockState blockstate = mob.level().getBlockState(this.blockPos);
            if (blockstate.is(Blocks.SWEET_BERRY_BUSH)) {
                this.pickSweetBerries(blockstate);
            } else if (CaveVines.hasGlowBerries(blockstate)) {
                this.pickGlowBerry(blockstate);
            }

        }
    }

    private void pickGlowBerry(BlockState pState) {
        CaveVines.use(mob, pState, mob.level(), this.blockPos);
    }

    private void pickSweetBerries(BlockState pState) {
        int i = pState.getValue(SweetBerryBushBlock.AGE);
        pState.setValue(SweetBerryBushBlock.AGE, 1);
        int j = 1 + mob.level().random.nextInt(2) + (i == 3 ? 1 : 0);
        ItemStack itemstack = mob.getItemBySlot(EquipmentSlot.MAINHAND);
        if (itemstack.isEmpty()) {
            mob.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.SWEET_BERRIES));
            --j;
        }

        if (j > 0) {
            Block.popResource(mob.level(), this.blockPos, new ItemStack(Items.SWEET_BERRIES, j));
        }

        mob.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);
        mob.level().setBlock(this.blockPos, pState.setValue(SweetBerryBushBlock.AGE, 1), 2);
    }

    public boolean canUse() {
        return super.canUse();
    }

    public void start() {
        this.ticksWaited = 0;
        super.start();
    }
}

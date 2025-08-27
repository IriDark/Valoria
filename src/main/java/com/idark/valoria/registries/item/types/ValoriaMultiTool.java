package com.idark.valoria.registries.item.types;

import com.google.common.collect.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.*;
import com.mojang.datafixers.util.*;
import net.minecraft.*;
import net.minecraft.advancements.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.gameevent.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.*;
import org.jetbrains.annotations.*;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static net.minecraft.world.item.HoeItem.changeIntoState;
import static net.minecraftforge.common.ToolActions.*;

public class ValoriaMultiTool extends DiggerItem implements StoneCrushable{
    Tier tier;
    public ValoriaMultiTool(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties){
        super(pAttackDamageModifier, pAttackSpeedModifier, pTier, TagsRegistry.MINEABLE_WITH_MULTITOOL, pProperties);
        this.tier = pTier;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.valoria.efficiency", speed).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.valoria.harvest", getTier().getLevel()).withStyle(ChatFormatting.GRAY));
    }

    public boolean isCorrectTags(BlockState state) {
        return state.is(TagsRegistry.MINEABLE_WITH_MULTITOOL);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return isCorrectTags(state) && TierSortingRegistry.isCorrectTierForDrops(this.tier, state);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return isCorrectTags(state) ? speed : super.getDestroySpeed(stack, state);
    }

    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        Player player = pContext.getPlayer();
        BlockState blockstate = level.getBlockState(blockpos);

        BlockState toolModifiedState = level.getBlockState(blockpos).getToolModifiedState(pContext, net.minecraftforge.common.ToolActions.HOE_TILL, false);
        Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = toolModifiedState == null ? null : Pair.of(ctx -> true, changeIntoState(toolModifiedState));
        if (pair != null) return performHoeBehaviour(pContext, pair, level, player, blockpos);
        
        AxeResult result = getAxeResult(pContext, blockstate, level, player, blockpos);
        if (result.optional3().isPresent()) return performAxeBehaviour(pContext, player, blockpos, result, level);
        return InteractionResult.PASS;
    }

    private static @Nullable InteractionResult performHoeBehaviour(UseOnContext pContext, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair, Level level, Player player, BlockPos blockpos){
        Predicate<UseOnContext> predicate = pair.getFirst();
        Consumer<UseOnContext> consumer = pair.getSecond();
        if(predicate.test(pContext)){
            level.playSound(player, blockpos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            if(!level.isClientSide){
                consumer.accept(pContext);
                if(player != null){
                    pContext.getItemInHand().hurtAndBreak(1, player, (p_150845_) -> {
                        p_150845_.broadcastBreakEvent(pContext.getHand());
                    });
                }
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return null;
    }

    private static @NotNull InteractionResult performAxeBehaviour(UseOnContext pContext, Player player, BlockPos blockpos, AxeResult result, Level level){
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockpos, result.itemstack());
        }

        level.setBlock(blockpos, result.optional3().get(), 11);
        level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, result.optional3().get()));
        if (player != null) {
            result.itemstack().hurtAndBreak(1, player, (p_150686_) -> p_150686_.broadcastBreakEvent(pContext.getHand()));
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private static @NotNull ValoriaMultiTool.AxeResult getAxeResult(UseOnContext pContext, BlockState blockstate, Level level, Player player, BlockPos blockpos){
        Optional<BlockState> optional = Optional.ofNullable(blockstate.getToolModifiedState(pContext, ToolActions.AXE_STRIP, false));
        Optional<BlockState> optional1 = optional.isPresent() ? Optional.empty() : Optional.ofNullable(blockstate.getToolModifiedState(pContext, ToolActions.AXE_SCRAPE, false));
        Optional<BlockState> optional2 = optional.isPresent() || optional1.isPresent() ? Optional.empty() : Optional.ofNullable(blockstate.getToolModifiedState(pContext, ToolActions.AXE_WAX_OFF, false));
        ItemStack itemstack = pContext.getItemInHand();
        Optional<BlockState> optional3 = Optional.empty();
        if (optional.isPresent()) {
            level.playSound(player, blockpos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            optional3 = optional;
        } else if (optional1.isPresent()) {
            level.playSound(player, blockpos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, 3005, blockpos, 0);
            optional3 = optional1;
        } else if (optional2.isPresent()) {
            level.playSound(player, blockpos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, 3004, blockpos, 0);
            optional3 = optional2;
        }
        
        return new AxeResult(itemstack, optional3);
    }

    private record AxeResult(ItemStack itemstack, Optional<BlockState> optional3){ }
    private static final Set<ToolAction> TOOL_ACTIONS = Stream.of(AXE_DIG, AXE_STRIP, AXE_SCRAPE, AXE_WAX_OFF, PICKAXE_DIG, SHOVEL_DIG, HOE_DIG, HOE_TILL).collect(Collectors.toCollection(Sets::newIdentityHashSet));
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return TOOL_ACTIONS.contains(toolAction);
    }
}
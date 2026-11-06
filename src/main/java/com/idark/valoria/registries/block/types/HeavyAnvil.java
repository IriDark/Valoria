package com.idark.valoria.registries.block.types;

import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.entity.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;

import javax.annotation.*;
import java.util.*;

public class HeavyAnvil extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, EntityBlock{
    public static final EnumProperty<BedPart> PART = BlockStateProperties.BED_PART;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape shape = Block.box(0, 0, 0, 15, 15, 15);
    public final HeavyAnvilTier tier;

    public HeavyAnvil(Properties pProperties){
        this(pProperties, HeavyAnvilTier.BRONZE);
    }

    public HeavyAnvil(Properties pProperties, HeavyAnvilTier tier){
        super(pProperties);
        this.tier = tier;
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, BedPart.FOOT).setValue(WATERLOGGED, false));
    }

    private static Direction getNeighbourDirection(BedPart pPart, Direction pDirection){
        return pPart == BedPart.FOOT ? pDirection : pDirection.getOpposite();
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState){
        if(pState.getValue(PART) == BedPart.HEAD) {
            return new HeavyAnvilBlockEntity(pPos, pState);
        }

        return null;
    }

    public HeavyAnvilBlockEntity getAnvilBlockEntity(Level level, BlockPos pos, BlockState state) {
        if (state.getValue(PART) == BedPart.HEAD) {
            BlockEntity be = level.getBlockEntity(pos);
            return be instanceof HeavyAnvilBlockEntity ? (HeavyAnvilBlockEntity) be : null;
        } else {
            BlockPos headPos = pos.relative(state.getValue(FACING));
            BlockEntity be = level.getBlockEntity(headPos);
            return be instanceof HeavyAnvilBlockEntity ? (HeavyAnvilBlockEntity) be : null;
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit){
        if(pLevel.isClientSide) return InteractionResult.SUCCESS;

        HeavyAnvilBlockEntity anvil = getAnvilBlockEntity(pLevel, pPos, pState);
        if(anvil != null){
            ItemStack handItem = pPlayer.getItemInHand(pHand);
            if(!anvil.itemHandler.getStackInSlot(0).isEmpty() && (handItem.isEmpty() || pPlayer.isShiftKeyDown())){
                ItemStack anvilItem = anvil.itemHandler.getStackInSlot(0);
                if(handItem.isEmpty()){
                    pPlayer.setItemInHand(pHand, anvilItem);
                    anvil.itemHandler.setStackInSlot(0, ItemStack.EMPTY);
                } else if(!pPlayer.getInventory().add(anvilItem)){
                    pPlayer.drop(anvilItem, false);
                    anvil.itemHandler.setStackInSlot(0, ItemStack.EMPTY);
                } else {
                    anvil.itemHandler.setStackInSlot(0, ItemStack.EMPTY);
                }
                
                anvil.resetProgress();
                anvil.sync();
                return InteractionResult.SUCCESS;
            }else if(anvil.itemHandler.getStackInSlot(0).isEmpty() && !handItem.isEmpty()){
                anvil.itemHandler.insertItem(0, handItem.copy(), false);
                pPlayer.getInventory().removeItem(handItem);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    private double getHammerSweetSpotBonus(ItemStack hammer){
        if(hammer.is(ItemsRegistry.voidHammer.get())){
            return 0.12;
        } else if(hammer.is(ItemsRegistry.infernalHammer.get())){
            return 0.08;
        } else if(hammer.is(ItemsRegistry.blackGoldHammer.get())){
            return 0.04;
        }
        return 0.0;
    }

    private int getProgressGain(ItemStack hammer, RandomSource random){
        if(hammer.is(ItemsRegistry.voidHammer.get())){
            return 2;
        } else if(hammer.is(ItemsRegistry.infernalHammer.get())){
            return random.nextFloat() < 0.70f ? 2 : 1;
        } else if(hammer.is(ItemsRegistry.blackGoldHammer.get())){
            return random.nextFloat() < 0.35f ? 2 : 1;
        }
        return 1;
    }

    @Override
    public void attack(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer){
        if(pLevel.isClientSide) return;

        HeavyAnvilBlockEntity anvil = getAnvilBlockEntity(pLevel, pPos, pState);
        if(anvil != null){
            if(anvil.itemHandler.getStackInSlot(0).isEmpty()) return;

            ItemStack hammerStack = pPlayer.getMainHandItem();
            if(!hammerStack.is(TagsRegistry.HEAVY_ANVIL_TOOL)) return;
            Direction facing = pState.getValue(HeavyAnvil.FACING);
            double x = pState.getValue(HeavyAnvil.PART) == BedPart.HEAD ? 0.5 - facing.getStepX() * 0.5 : 0.5 + facing.getStepX() * 0.5;
            double z = pState.getValue(HeavyAnvil.PART) == BedPart.HEAD ? 0.5 - facing.getStepZ() * 0.5 : 0.5 + facing.getStepZ() * 0.5;

            if(pLevel instanceof ServerLevel server){
                if(anvil.getCurrentRecipe().isPresent()){
                    var recipe = anvil.getCurrentRecipe().get();
                    if(this.tier.getLevel() < recipe.getMinTier().getLevel()){
                        anvil.tierTooLow = true;
                        anvil.sync();
                        pLevel.playSound(null, pPos, SoundEvents.ANVIL_HIT, SoundSource.BLOCKS, 0.5f, 0.5f);
                        pPlayer.displayClientMessage(Component.translatable("hud.valoria.heavy_anvil.low_tier").withStyle(ChatFormatting.RED), true);
                        return;
                    }

                    int bonus = recipe.acceptsTierBonus() ? this.tier.getInstabilityBonus() : 0;
                    anvil.requiredHits = recipe.getRequiredHits();
                    anvil.maxInstability = recipe.getMaxInstability() + bonus;
                    anvil.cursorSpeed = recipe.getCursorSpeed();

                    if(pLevel.getGameTime() - anvil.lastHitTick < 6) return;
                    anvil.lastHitTick = pLevel.getGameTime();

                    double hammerSweetBonus = getHammerSweetSpotBonus(hammerStack);
                    double tolerance = 0.035 + (hammerSweetBonus * 0.25);
                    hammerStack.hurtAndBreak(1, pPlayer, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));

                    double normalized = anvil.getLinePosition(pLevel, 0);
                    if(normalized >= (anvil.sweetSpotMin - tolerance) && normalized <= (anvil.sweetSpotMax + tolerance)){
                        int progressGain = getProgressGain(hammerStack, pLevel.random);
                        anvil.progress += progressGain;

                        PacketHandler.sendToTracking(server, pPos, new CrusherParticlePacket(pPos.getX() + x, pPos.getY() + 1, pPos.getZ() + z, anvil.itemHandler.getStackInSlot(0)));
                        server.sendParticles(ParticleTypes.CRIT, pPos.getX() + x, pPos.getY() + 1.05, pPos.getZ() + z, 6, 0.15, 0.1, 0.15, 0.15);
                        server.sendParticles(ParticleTypes.ELECTRIC_SPARK, pPos.getX() + x, pPos.getY() + 1.05, pPos.getZ() + z, 3, 0.1, 0.05, 0.1, 0.05);
                        if(progressGain > 1){
                            server.sendParticles(ParticleTypes.ENCHANTED_HIT, pPos.getX() + x, pPos.getY() + 1.1, pPos.getZ() + z, 12, 0.2, 0.2, 0.2, 0.15);
                            pLevel.playSound(null, pPos, SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.BLOCKS, 0.7f, 1.3f);
                        }
                        pLevel.playSound(null, pPos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0f, 1.25f);

                        if(anvil.progress >= anvil.requiredHits){
                            ItemStack input = anvil.itemHandler.getStackInSlot(0);
                            ItemStack recipeResult = recipe.getResultItem(pLevel.registryAccess());

                            int recipeOut = recipeResult.getCount();
                            int maxCrafts = recipeResult.getMaxStackSize() / recipeOut;
                            if(maxCrafts == 0) maxCrafts = 1;
                            int crafts = Math.min(input.getCount(), maxCrafts);

                            ItemStack result = recipeResult.copy();
                            result.setCount(crafts * recipeOut);
                            input.shrink(crafts);

                            if(input.isEmpty()){
                                anvil.itemHandler.setStackInSlot(0, result);
                            }else{
                                Containers.dropItemStack(pLevel, pPos.getX() + 0.5, pPos.getY() + 1.0, pPos.getZ() + 0.5, result);
                            }

                            anvil.resetProgress();
                            pLevel.playSound(null, pPos, SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1.0f, 1.0f);
                        }else{
                            anvil.randomizeSweetSpot(hammerSweetBonus);
                        }
                    }else{
                        anvil.instability++;
                        server.sendParticles(ParticleTypes.LARGE_SMOKE, pPos.getX() + x, pPos.getY() + 1, pPos.getZ() + z, 12, 0, 0, 0, 0.5);
                        server.sendParticles(ParticleTypes.SMOKE, pPos.getX() + x, pPos.getY() + 1.05, pPos.getZ() + z, 8, 0.15, 0.05, 0.15, 0.05);
                        pLevel.playSound(null, pPos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1.0f, 0.5f);
                        pLevel.playSound(null, pPos, SoundEvents.ANVIL_HIT, SoundSource.BLOCKS, 0.8f, 0.5f);
                        if(anvil.instability >= anvil.maxInstability){
                            anvil.itemHandler.extractItem(0, 1, false);
                            anvil.resetProgress();
                            pLevel.explode(null, pPos.getX() + 0.5, pPos.getY() + 1.0, pPos.getZ() + 0.5, 2.0F, Level.ExplosionInteraction.NONE);
                        }
                    }

                    anvil.sync();
                }
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
        return shape;
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state){
        return PushReaction.BLOCK;
    }

    public RenderShape getRenderShape(BlockState pState){
        var part = pState.getValue(PART);
        if(part == BedPart.FOOT){
            return RenderShape.INVISIBLE;
        }

        return RenderShape.MODEL;
    }

    public FluidState getFluidState(BlockState pState){
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    public boolean placeLiquid(LevelAccessor pLevel, BlockPos pPos, BlockState pState, FluidState pFluidState){
        if(!pState.getValue(WATERLOGGED) && pFluidState.getType() == Fluids.WATER){
            BlockState blockstate = pState.setValue(WATERLOGGED, true);
            pLevel.setBlock(pPos, blockstate, 3);
            pLevel.scheduleTick(pPos, pFluidState.getType(), pFluidState.getType().getTickDelay(pLevel));
            return true;
        }else{
            return false;
        }
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos){
        if(pDirection == getNeighbourDirection(pState.getValue(PART), pState.getValue(FACING))){
            return pNeighborState.is(this) && pNeighborState.getValue(PART) != pState.getValue(PART) ? pState : Blocks.AIR.defaultBlockState();
        }else if(pState.getValue(WATERLOGGED)){
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer){
        if(!pLevel.isClientSide && pPlayer.isCreative()){
            BedPart part = pState.getValue(PART);
            BlockPos pNeighborPos = pPos.relative(getNeighbourDirection(part, pState.getValue(FACING)));
            BlockState pNeighborState = pLevel.getBlockState(pNeighborPos);
            if(part == BedPart.FOOT){
                if(!pNeighborState.is(this) || pNeighborState.getValue(PART) != pState.getValue(PART)){
                    pLevel.levelEvent(null, 2001, pNeighborPos, Block.getId(pNeighborState));
                    pLevel.setBlock(pNeighborPos, Blocks.AIR.defaultBlockState(), 35);
                }
            }
        }

        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext){
        Direction $$1 = pContext.getHorizontalDirection();
        BlockPos $$2 = pContext.getClickedPos();
        BlockPos $$3 = $$2.relative($$1);
        Level $$4 = pContext.getLevel();
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        return $$4.getBlockState($$3).canBeReplaced(pContext) && $$4.getWorldBorder().isWithinBounds($$3) ? this.defaultBlockState().setValue(FACING, $$1).setValue(WATERLOGGED, flag) : null;
    }

    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack){
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if(!pLevel.isClientSide){
            BlockPos $$5 = pPos.relative(pState.getValue(FACING));
            pLevel.setBlock($$5, pState.setValue(PART, BedPart.HEAD), 3);
            pLevel.blockUpdated(pPos, Blocks.AIR);
            pState.updateNeighbourShapes(pLevel, pPos, 3);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(FACING);
        builder.add(PART);
        builder.add(WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }

    public long getSeed(BlockState pState, BlockPos pPos){
        BlockPos $$2 = pPos.relative(pState.getValue(FACING), pState.getValue(PART) == BedPart.HEAD ? 0 : 1);
        return Mth.getSeed($$2.getX(), pPos.getY(), $$2.getZ());
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag){
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        pTooltip.add(Component.translatable("tooltip.valoria.wip").withStyle(ChatFormatting.GOLD));
    }
}
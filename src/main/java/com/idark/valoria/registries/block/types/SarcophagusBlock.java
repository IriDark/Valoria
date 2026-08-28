package com.idark.valoria.registries.block.types;

import com.idark.valoria.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
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
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import javax.annotation.*;

public class SarcophagusBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock{
    public ArcRandom arcRandom = Tmp.rnd;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<BedPart> PART = BlockStateProperties.BED_PART;
    private static final BooleanProperty OPEN = BooleanProperty.create("open");
    private static final BooleanProperty LOOTED = BooleanProperty.create("looted");

    private static final VoxelShape shape = Block.box(0, 0, 0, 16, 12, 16);
    private static final ResourceLocation mobGear = Valoria.loc("entities/gear/sarcophagus_gear");
    private static final ResourceLocation loot = Valoria.loc("items/sarcophagus");

    public SarcophagusBlock(BlockBehaviour.Properties pProperties){
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, BedPart.FOOT).setValue(OPEN, false).setValue(LOOTED, false).setValue(WATERLOGGED, false));
    }

    private static Direction getNeighbourDirection(BedPart pPart, Direction pDirection){
        return pPart == BedPart.FOOT ? pDirection : pDirection.getOpposite();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
        return shape;
    }


    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit){
        RandomSource rand = pLevel.getRandom();
        BlockPos oppositePos = pPos.relative(pState.getValue(FACING));
        if(pState.getValue(PART) == BedPart.HEAD){
            oppositePos = pPos.relative(pState.getValue(FACING).getOpposite());
        }

        if(!pState.getValue(OPEN)){
            pLevel.setBlockAndUpdate(pPos, pState.setValue(OPEN, true).setValue(LOOTED, false));
            BlockState oppositeState = pLevel.getBlockState(oppositePos);
            if(oppositeState.getBlock() == this){
                pLevel.setBlockAndUpdate(oppositePos, oppositeState.setValue(OPEN, true).setValue(LOOTED, false));
            }

            for(int i = 0; i < 10; i++){
                pLevel.addParticle(ParticleTypes.POOF, pPos.getX() + rand.nextDouble(), pPos.getY() + 1.0f, pPos.getZ() + rand.nextDouble(), 0, 0, 0);
                pLevel.addParticle(ParticleTypes.POOF, oppositePos.getX() + rand.nextDouble(), oppositePos.getY() + 1.0f, oppositePos.getZ() + rand.nextDouble(), 0, 0, 0);
            }

            if(!pLevel.isClientSide && pLevel instanceof ServerLevel serv){
                double posX = (pPos.getCenter().x + oppositePos.getCenter().x) / 2.0;
                double posY = (pPos.above().getCenter().y + oppositePos.above().getCenter().y) / 2.0;
                double posZ = (pPos.getCenter().z + oppositePos.getCenter().z) / 2.0;

                PacketHandler.sendToTracking(serv, pPos, new SmokeParticlePacket(120, posX, posY - 0.135f, posZ, 0.125f, 0, 0.125f, 255, 255, 255));
                for(int i = 0; i < Mth.nextFloat(RandomSource.create(), 1, 4); i++){
                    if(arcRandom.fiftyFifty()){
                        ValoriaUtils.spawnEntities(10, EntityTypeRegistry.DRAUGR.get(), serv, pPos, mobGear);
                    }else{
                        ValoriaUtils.spawnEntities(10, EntityType.SKELETON, serv, pPos, mobGear);
                    }
                }

                pLevel.playSound(null, pPos, SoundsRegistry.SARCOPHAGUS_OPEN.get(), SoundSource.BLOCKS, 0.5f, 1);
                pPlayer.displayClientMessage(Component.translatable("tooltip.valoria.sarcophagus").withStyle(ChatFormatting.GRAY), true);
            }
        }

        if(pState.getValue(OPEN) && !pState.getValue(LOOTED)){
            if(pPlayer instanceof ServerPlayer serverPlayer){
                Vec3 block = new Vec3(pPos.getX() - 0.5f, pPos.getY(), pPos.getZ() - 0.5f);
                Utils.Items.spawnLoot(pLevel, pPos.above(), Utils.Items.createLoot(loot, Utils.Items.getGiftParameters((ServerLevel)pLevel, block, serverPlayer)));
                pLevel.setBlockAndUpdate(pPos, pState.setValue(OPEN, true).setValue(LOOTED, true));
                BlockState oppositeState = pLevel.getBlockState(oppositePos);
                if(oppositeState.getBlock() == this){
                    pLevel.setBlockAndUpdate(oppositePos, oppositeState.setValue(OPEN, true).setValue(LOOTED, true));
                }
            }

            for(int i = 0; i < 3; i++){
                pLevel.addParticle(ParticleTypes.SOUL, pPos.getX() + rand.nextDouble(), pPos.getY() + 1.0f, pPos.getZ() + rand.nextDouble(), 0, 0, 0);
                pLevel.addParticle(ParticleTypes.SOUL, oppositePos.getX() + rand.nextDouble(), oppositePos.getY() + 1.0f, oppositePos.getZ() + rand.nextDouble(), 0, 0, 0);
            }

            return InteractionResult.CONSUME;
        }

        return InteractionResult.SUCCESS;
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

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom){
        super.animateTick(pState, pLevel, pPos, pRandom);
        if(!pState.getValue(LOOTED) && pState.getValue(OPEN)) {
            int i = pPos.getX();
            int j = pPos.getY();
            int k = pPos.getZ();
            double d0 = (double)i + pRandom.nextDouble();
            double d1 = (double)j + 0.7D;
            double d2 = (double)k + pRandom.nextDouble() - 0.2D;

            ParticleBuilder.create(TridotParticles.SPARKLE)
            .setVelocity(0, 0.05f, 0)
            .randomVelocity(0.05f, 0)
            .setScaleData(GenericParticleData.create(0.05f, 0.15f, 0).setEasing(Interp.bounce).build())
            .setTransparencyData(GenericParticleData.create(1.0f, 0.35f, 0f).setEasing(Interp.pow2In).build())
            .setLifetime(12)
            .spawn(pLevel, d0, d1, d2);
        }
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos){
        if(pDirection == getNeighbourDirection(pState.getValue(PART), pState.getValue(FACING))){
            return pNeighborState.is(this) && pNeighborState.getValue(PART) != pState.getValue(PART) ? pState : Blocks.AIR.defaultBlockState();
        } else if(pState.getValue(WATERLOGGED)){
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
        builder.add(OPEN);
        builder.add(LOOTED);
        builder.add(WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }

    public long getSeed(BlockState pState, BlockPos pPos){
        BlockPos $$2 = pPos.relative(pState.getValue(FACING), pState.getValue(PART) == BedPart.HEAD ? 0 : 1);
        return Mth.getSeed($$2.getX(), pPos.getY(), $$2.getZ());
    }
}

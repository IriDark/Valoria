package com.idark.valoria.registries.block.types;

import com.idark.valoria.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.living.*;
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
import net.minecraft.world.entity.monster.*;
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
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import javax.annotation.*;
import java.time.*;

public class SarcophagusBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock{
    public ArcRandom arcRandom = Tmp.rnd;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<BedPart> PART = BlockStateProperties.BED_PART;
    private static final BooleanProperty OPEN = BooleanProperty.create("open");
    private static final BooleanProperty LOOTED = BooleanProperty.create("looted");

    private static final VoxelShape shape = Block.box(0, 0, 0, 16, 12, 16);

    ItemStack[] armor_head = {
            new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.CHAINMAIL_HELMET), new ItemStack(Items.GOLDEN_HELMET), new ItemStack(Items.AIR)
    };
    ItemStack[] armor_chest = {
            new ItemStack(Items.LEATHER_CHESTPLATE), new ItemStack(Items.CHAINMAIL_CHESTPLATE), new ItemStack(Items.GOLDEN_CHESTPLATE), new ItemStack(Items.AIR)
    };
    ItemStack[] armor_legs = {
            new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.CHAINMAIL_LEGGINGS), new ItemStack(Items.GOLDEN_LEGGINGS), new ItemStack(Items.AIR)
    };
    ItemStack[] armor_boots = {
            new ItemStack(Items.LEATHER_BOOTS), new ItemStack(Items.CHAINMAIL_BOOTS), new ItemStack(Items.GOLDEN_BOOTS), new ItemStack(Items.AIR)
    };

    public SarcophagusBlock(BlockBehaviour.Properties pProperties){
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, BedPart.FOOT).setValue(OPEN, false).setValue(LOOTED, false).setValue(WATERLOGGED, false));
    }

    private static boolean isHalloween(){
        LocalDate localdate = LocalDate.now();
        int i = localdate.getDayOfMonth();
        int j = localdate.getMonth().getValue();
        return j == 10 && i >= 20 || j == 11 && i <= 3;
    }

    private static Direction getNeighbourDirection(BedPart pPart, Direction pDirection){
        return pPart == BedPart.FOOT ? pDirection : pDirection.getOpposite();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
        return shape;
    }

    private void spawnSkeletons(Level pLevel, BlockPos pPos, InteractionHand hand){
        RandomSource rand = pLevel.getRandom();
        Skeleton entity = EntityType.SKELETON.create(pLevel);
        for(int tries = 0; tries < 10; tries++){
            double x = (double)pPos.getX() + (rand.nextDouble() - rand.nextDouble()) * 6;
            double y = pPos.getY() + rand.nextInt(1, 2);
            double z = (double)pPos.getZ() + (rand.nextDouble() - rand.nextDouble()) * 6;
            if(entity != null){
                if(pLevel.noCollision(entity, new AABB(x, y, z, x, y, z).inflate(1))){
                    entity.moveTo(x, y, z, 0.0F, 0.0F);
                    if(isHalloween()){
                        ItemStack halloweenEquip = ValoriaUtils.getRandomItemFromTag(rand, TagsRegistry.FROM_SARCOPHAGUS_HALLOWEEN_SPAWNABLE_WITH);
                        entity.setItemSlot(LivingEntity.getEquipmentSlotForItem(halloweenEquip), halloweenEquip);
                    }else{
                        ItemStack equipItem = ValoriaUtils.getRandomItemFromTag(rand, TagsRegistry.FROM_SARCOPHAGUS_SPAWNABLE_WITH);
                        entity.setItemSlot(LivingEntity.getEquipmentSlotForItem(equipItem), equipItem);
                        if(rand.nextFloat() <= 0.4){
                            entity.setItemSlot(EquipmentSlot.HEAD, armor_head[rand.nextInt(armor_head.length)]);
                        }
                    }

                    if(rand.nextFloat() <= 0.4){
                        entity.setItemSlot(EquipmentSlot.CHEST, armor_chest[rand.nextInt(armor_chest.length)]);
                        entity.setItemSlot(EquipmentSlot.LEGS, armor_legs[rand.nextInt(armor_legs.length)]);
                        entity.setItemSlot(EquipmentSlot.FEET, armor_boots[rand.nextInt(armor_boots.length)]);
                    }

                    pLevel.addFreshEntity(entity);
                }
            }
        }
    }

    private void spawnDraugr(Level pLevel, BlockPos pPos, InteractionHand hand){
        RandomSource rand = pLevel.getRandom();
        DraugrEntity entity = EntityTypeRegistry.DRAUGR.get().create(pLevel);
        for(int tries = 0; tries < 10; tries++){
            double x = (double)pPos.getX() + (rand.nextDouble() - rand.nextDouble()) * 6;
            double y = pPos.getY() + rand.nextInt(1, 2);
            double z = (double)pPos.getZ() + (rand.nextDouble() - rand.nextDouble()) * 6;
            if(entity != null){
                if(pLevel.noCollision(entity, new AABB(x, y, z, x, y, z).inflate(1))){
                    entity.moveTo(x, y, z, 0.0F, 0.0F);
                    if(isHalloween()){
                        ItemStack halloweenEquip = ValoriaUtils.getRandomItemFromTag(rand, TagsRegistry.FROM_SARCOPHAGUS_HALLOWEEN_SPAWNABLE_WITH);
                        entity.setItemSlot(LivingEntity.getEquipmentSlotForItem(halloweenEquip), halloweenEquip);
                    } else {
                        ItemStack equipItem = ValoriaUtils.getRandomItemFromTag(rand, TagsRegistry.FROM_SARCOPHAGUS_SPAWNABLE_WITH);
                        entity.setItemSlot(LivingEntity.getEquipmentSlotForItem(equipItem), equipItem);
                    }

                    pLevel.addFreshEntity(entity);
                }
            }
        }
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

            if(!pLevel.isClientSide && pLevel instanceof ServerLevel serv){
                double posX = (pPos.getCenter().x + oppositePos.getCenter().x) / 2.0;
                double posY = (pPos.above().getCenter().y + oppositePos.above().getCenter().y) / 2.0;
                double posZ = (pPos.getCenter().z + oppositePos.getCenter().z) / 2.0;

                PacketHandler.sendToTracking(serv, pPos, new SmokeParticlePacket(120, posX, posY - 0.135f, posZ, 0.125f, 0, 0.125f, 255, 255, 255));
            }

            for(int i = 0; i < 10; i++){
                pLevel.addParticle(ParticleTypes.POOF, pPos.getX() + rand.nextDouble(), pPos.getY() + 1.0f, pPos.getZ() + rand.nextDouble(), 0, 0, 0);
                pLevel.addParticle(ParticleTypes.POOF, oppositePos.getX() + rand.nextDouble(), oppositePos.getY() + 1.0f, oppositePos.getZ() + rand.nextDouble(), 0, 0, 0);
            }

            pPlayer.displayClientMessage(Component.translatable("tooltip.valoria.sarcophagus").withStyle(ChatFormatting.GRAY), true);
            for(int i = 0; i < Mth.nextFloat(RandomSource.create(), 1, 4); i++){
                boolean randomHand = Mth.nextFloat(RandomSource.create(), 0, 1) < 0.5;
                InteractionHand hand = randomHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
                if(arcRandom.fiftyFifty()){
                    spawnSkeletons(pLevel, pPos, hand);
                }else{
                    spawnDraugr(pLevel, pPos, hand);
                }

                pLevel.playSound(null, pPos, SoundsRegistry.SARCOPHAGUS_OPEN.get(), SoundSource.BLOCKS, 0.3f, 1);
            }
        }

        if(pState.getValue(OPEN) && !pState.getValue(LOOTED)){
            if(pPlayer instanceof ServerPlayer serverPlayer){
                Vec3 block = new Vec3(pPos.getX() - 0.5f, pPos.getY(), pPos.getZ() - 0.5f);
                Utils.Items.spawnLoot(pLevel, pPos.above(), Utils.Items.createLoot(new ResourceLocation(Valoria.ID, "items/sarcophagus"), Utils.Items.getGiftParameters((ServerLevel)pLevel, block, serverPlayer)));
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

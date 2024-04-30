package com.idark.valoria.registries.block.types;

import com.idark.valoria.Valoria;
import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.SarcophagusSummonPacket;
import com.idark.valoria.registries.EntityTypeRegistry;
import com.idark.valoria.registries.entity.living.DraugrEntity;
import com.idark.valoria.util.LootUtil;
import com.idark.valoria.util.RandomUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SarcophagusBlock extends HorizontalDirectionalBlock {

    public static final EnumProperty<BedPart> PART = BlockStateProperties.BED_PART;
    private static final BooleanProperty OPEN = BooleanProperty.create("open");
    private static final BooleanProperty LOOTED = BooleanProperty.create("looted");
    public static List<Item> spawnableWith = new ArrayList<>();
    public static List<Item> halloweenSpawnableWith = new ArrayList<>();

    private static final VoxelShape shape = Block.box(0, 0, 0, 16, 12, 16);
    Random rand = new Random();

    public SarcophagusBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, BedPart.FOOT).setValue(OPEN, false).setValue(LOOTED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return shape;
    }

    private static boolean isHalloween() {
        LocalDate localdate = LocalDate.now();
        int i = localdate.getDayOfMonth();
        int j = localdate.getMonth().getValue();
        return j == 10 && i >= 20 || j == 11 && i <= 3;
    }

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

    private void spawnSkeletons(Level pLevel, BlockPos pPos, InteractionHand hand) {
        Skeleton skeleton = EntityType.SKELETON.create(pLevel);
        double d0 = (double)pPos.getX() + (rand.nextDouble() - rand.nextDouble()) * (double)4 + 0.5;
        double d1 = pPos.getY() + rand.nextInt(2);
        double d2 = (double)pPos.getZ() + (rand.nextDouble() - rand.nextDouble()) * (double)4 + 0.5;
        if (skeleton != null) {
            if (pLevel.noCollision(skeleton, new AABB(new BlockPos((int) d0, (int) d1, (int) d2)))) {
                skeleton.moveTo(d0, d1, d2, 0.0F, 0.0F);
                skeleton.setItemInHand(hand, spawnableWith.get(rand.nextInt(spawnableWith.size())).getDefaultInstance());
                if (isHalloween()) {
                    skeleton.setItemSlot(EquipmentSlot.HEAD, halloweenSpawnableWith.get(rand.nextInt(spawnableWith.size())).getDefaultInstance());
                } else {
                    if (rand.nextFloat() <= 0.4) {
                        skeleton.setItemSlot(EquipmentSlot.HEAD, armor_head[rand.nextInt(armor_head.length)]);
                    }
                }

                if (rand.nextFloat() <= 0.4) {
                    skeleton.setItemSlot(EquipmentSlot.CHEST, armor_chest[rand.nextInt(armor_chest.length)]);
                    skeleton.setItemSlot(EquipmentSlot.LEGS, armor_legs[rand.nextInt(armor_legs.length)]);
                    skeleton.setItemSlot(EquipmentSlot.FEET, armor_boots[rand.nextInt(armor_boots.length)]);
                }

                pLevel.addFreshEntity(skeleton);
            }
        }
    }

    private void spawnDraugr(Level pLevel, BlockPos pPos, InteractionHand hand) {
        DraugrEntity entity = EntityTypeRegistry.DRAUGR.get().create(pLevel);
        double d0 = (double)pPos.getX() + (rand.nextDouble() - rand.nextDouble()) * (double)4 + 0.5;
        double d1 = pPos.getY() + rand.nextInt(2);
        double d2 = (double)pPos.getZ() + (rand.nextDouble() - rand.nextDouble()) * (double)4 + 0.5;
        if (entity != null) {
            if (pLevel.noCollision(entity, new AABB(new BlockPos((int) d0, (int) d1, (int) d2)))) {
                entity.moveTo(d0, d1, d2, 0.0F, 0.0F);
                entity.setItemInHand(hand, spawnableWith.get(rand.nextInt(spawnableWith.size())).getDefaultInstance());
                if (isHalloween()) {
                    entity.setItemSlot(EquipmentSlot.HEAD, halloweenSpawnableWith.get(rand.nextInt(spawnableWith.size())).getDefaultInstance());
                }

                pLevel.addFreshEntity(entity);
            }
        }
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockPos oppositePos = pPos.relative(pState.getValue(FACING));
        if (pState.getValue(PART) == BedPart.HEAD) {
            oppositePos = pPos.relative(pState.getValue(FACING).getOpposite());
        }

        if (!pState.getValue(OPEN)) {
            pLevel.setBlockAndUpdate(pPos, pState.setValue(OPEN, true).setValue(LOOTED, false));
            BlockState oppositeState = pLevel.getBlockState(oppositePos);
            if (oppositeState.getBlock() == this) {
                pLevel.setBlockAndUpdate(oppositePos, oppositeState.setValue(OPEN, true).setValue(LOOTED, false));
            }

            if (!pLevel.isClientSide && pLevel instanceof ServerLevel serv) {
                double posX = (pPos.getCenter().x + oppositePos.getCenter().x) / 2.0;
                double posY = (pPos.getCenter().y + oppositePos.getCenter().y) / 2.0;
                double posZ = (pPos.getCenter().z + oppositePos.getCenter().z) / 2.0;

                double offsetX = (new Random().nextDouble() - 0.5) / 16;
                double offsetY = 0;
                double offsetZ = (new Random().nextDouble() - 0.5) / 16;

                PacketHandler.sendToTracking(serv, pPos, new SarcophagusSummonPacket((float) posX, (float) posY, (float) posZ, (float) offsetX, (float) offsetY, (float) offsetZ, 30, 35, 75));
            }

            for (int i = 0; i < 10; i++) {
                pLevel.addParticle(ParticleTypes.POOF, pPos.getX() + rand.nextDouble(), pPos.getY() + 1.0f, pPos.getZ() + rand.nextDouble(), 0, 0, 0);
                pLevel.addParticle(ParticleTypes.POOF, oppositePos.getX() + rand.nextDouble(), oppositePos.getY() + 1.0f, oppositePos.getZ() + rand.nextDouble(), 0, 0, 0);
            }

            pPlayer.displayClientMessage(Component.translatable("tooltip.valoria.sarcophagus").withStyle(ChatFormatting.GRAY), true);
            for (int i = 0; i < Mth.nextFloat(RandomSource.create(), 1, 4); i++) {
                boolean randomHand = Mth.nextFloat(RandomSource.create(), 0, 1) < 0.5;
                InteractionHand hand = randomHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
                if (RandomUtil.fiftyFifty()) {
                    spawnSkeletons(pLevel, pPos, hand);
                } else {
                    spawnDraugr(pLevel, pPos, hand);
                }
            }
        }

        if (pState.getValue(OPEN) && !pState.getValue(LOOTED)) {
            if (pPlayer instanceof ServerPlayer serverPlayer) {
                Vec3 block = new Vec3(pPos.getX() - 0.5f, pPos.getY(), pPos.getZ() - 0.5f);
                LootUtil.SpawnLoot(pLevel, pPos.above(), LootUtil.createLoot(new ResourceLocation(Valoria.ID, "items/sarcophagus"), LootUtil.getGiftParameters((ServerLevel) pLevel, block, serverPlayer)));
                pLevel.setBlockAndUpdate(pPos, pState.setValue(OPEN, true).setValue(LOOTED, true));
                BlockState oppositeState = pLevel.getBlockState(oppositePos);
                if (oppositeState.getBlock() == this) {
                    pLevel.setBlockAndUpdate(oppositePos, oppositeState.setValue(OPEN, true).setValue(LOOTED, true));
                }
            }

            for (int i = 0; i < 3; i++) {
                pLevel.addParticle(ParticleTypes.SOUL, pPos.getX() + rand.nextDouble(), pPos.getY() + 1.0f, pPos.getZ() + rand.nextDouble(), 0, 0, 0);
                pLevel.addParticle(ParticleTypes.SOUL, oppositePos.getX() + rand.nextDouble(), oppositePos.getY() + 1.0f, oppositePos.getZ() + rand.nextDouble(), 0, 0, 0);
            }

            return InteractionResult.CONSUME;
        }

        return InteractionResult.SUCCESS;
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing == getNeighbourDirection(pState.getValue(PART), pState.getValue(FACING))) {
            return pFacingState.is(this) && pFacingState.getValue(PART) != pState.getValue(PART) ? pState : Blocks.AIR.defaultBlockState();
        } else {
            return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }
    }

    private static Direction getNeighbourDirection(BedPart pPart, Direction pDirection) {
        return pPart == BedPart.FOOT ? pDirection : pDirection.getOpposite();
    }

    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pLevel.isClientSide && pPlayer.isCreative()) {
            BedPart $$4 = pState.getValue(PART);
            if ($$4 == BedPart.FOOT) {
                BlockPos $$5 = pPos.relative(getNeighbourDirection($$4, pState.getValue(FACING)));
                BlockState $$6 = pLevel.getBlockState($$5);
                if ($$6.is(this) && $$6.getValue(PART) == BedPart.HEAD) {
                    pLevel.setBlock($$5, Blocks.AIR.defaultBlockState(), 35);
                    pLevel.levelEvent(pPlayer, 2001, $$5, Block.getId($$6));
                }
            }

        }

        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction $$1 = pContext.getHorizontalDirection();
        BlockPos $$2 = pContext.getClickedPos();
        BlockPos $$3 = $$2.relative($$1);
        Level $$4 = pContext.getLevel();
        return $$4.getBlockState($$3).canBeReplaced(pContext) && $$4.getWorldBorder().isWithinBounds($$3) ? this.defaultBlockState().setValue(FACING, $$1) : null;
    }

    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if (!pLevel.isClientSide) {
            BlockPos $$5 = pPos.relative(pState.getValue(FACING));
            pLevel.setBlock($$5, pState.setValue(PART, BedPart.HEAD), 3);
            pLevel.blockUpdated(pPos, Blocks.AIR);
            pState.updateNeighbourShapes(pLevel, pPos, 3);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(PART);
        builder.add(OPEN);
        builder.add(LOOTED);
        super.createBlockStateDefinition(builder);
    }

    public long getSeed(BlockState pState, BlockPos pPos) {
        BlockPos $$2 = pPos.relative(pState.getValue(FACING), pState.getValue(PART) == BedPart.HEAD ? 0 : 1);
        return Mth.getSeed($$2.getX(), pPos.getY(), $$2.getZ());
    }
}

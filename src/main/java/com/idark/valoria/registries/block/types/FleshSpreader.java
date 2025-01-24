package com.idark.valoria.registries.block.types;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.idark.valoria.core.interfaces.FleshSpreaderBehaviour;
import com.idark.valoria.registries.SoundsRegistry;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.*;

public class FleshSpreader{
    final boolean isWorldGeneration;
    private final TagKey<Block> replaceableBlocks;
    private final int growthSpawnCost;
    private final int noGrowthRadius;
    private final int chargeDecayRate;
    private final int additionalDecayRate;
    private List<FleshSpreader.ChargeCursor> cursors = new ArrayList<>();
    private static final Logger LOGGER = LogUtils.getLogger();

    public FleshSpreader(boolean pIsWorldGeneration, TagKey<Block> pReplaceableBlocks, int pGrowthSpawnCoat, int pNoGrowthRadius, int pChargeDecayRate, int pAdditionalDecayRate){
        this.isWorldGeneration = pIsWorldGeneration;
        this.replaceableBlocks = pReplaceableBlocks;
        this.growthSpawnCost = pGrowthSpawnCoat;
        this.noGrowthRadius = pNoGrowthRadius;
        this.chargeDecayRate = pChargeDecayRate;
        this.additionalDecayRate = pAdditionalDecayRate;
    }

    public static FleshSpreader createLevelSpreader(){
        return new FleshSpreader(false, BlockTags.SCULK_REPLACEABLE, 10, 4, 10, 5);
    }

    public static FleshSpreader createWorldGenSpreader(){
        return new FleshSpreader(true, BlockTags.SCULK_REPLACEABLE_WORLD_GEN, 50, 1, 5, 10);
    }

    public TagKey<Block> replaceableBlocks(){
        return this.replaceableBlocks;
    }

    public int growthSpawnCost(){
        return this.growthSpawnCost;
    }

    public int noGrowthRadius(){
        return this.noGrowthRadius;
    }

    public int chargeDecayRate(){
        return this.chargeDecayRate;
    }

    public int additionalDecayRate(){
        return this.additionalDecayRate;
    }

    public boolean isWorldGeneration(){
        return this.isWorldGeneration;
    }

    @VisibleForTesting
    public List<FleshSpreader.ChargeCursor> getCursors(){
        return this.cursors;
    }

    public void clear(){
        this.cursors.clear();
    }

    public void load(CompoundTag pTag){
        if(pTag.contains("cursors", 9)){
            this.cursors.clear();
            List<FleshSpreader.ChargeCursor> list = FleshSpreader.ChargeCursor.CODEC.listOf().parse(new Dynamic<>(NbtOps.INSTANCE, pTag.getList("cursors", 10))).resultOrPartial(LOGGER::error).orElseGet(ArrayList::new);
            int i = Math.min(list.size(), 32);

            for(int j = 0; j < i; ++j){
                this.addCursor(list.get(j));
            }
        }

    }

    public void save(CompoundTag pTag){
        FleshSpreader.ChargeCursor.CODEC.listOf().encodeStart(NbtOps.INSTANCE, this.cursors).resultOrPartial(LOGGER::error).ifPresent((p_222273_) -> {
            pTag.put("cursors", p_222273_);
        });
    }

    public void addCursors(BlockPos pPos, int pCharge){
        while(pCharge > 0){
            int i = Math.min(pCharge, 1000);
            this.addCursor(new FleshSpreader.ChargeCursor(pPos, i));
            pCharge -= i;
        }

    }

    private void addCursor(FleshSpreader.ChargeCursor pCursor){
        if(this.cursors.size() < 32){
            this.cursors.add(pCursor);
        }
    }

    public void updateCursors(LevelAccessor pLevel, BlockPos pPos, RandomSource pRandom, boolean pShouldConvertBlocks){
        if(!this.cursors.isEmpty()){
            List<FleshSpreader.ChargeCursor> list = new ArrayList<>();
            Map<BlockPos, FleshSpreader.ChargeCursor> map = new HashMap<>();
            Object2IntMap<BlockPos> object2intmap = new Object2IntOpenHashMap<>();
            for(FleshSpreader.ChargeCursor spreader : this.cursors){
                spreader.update(pLevel, pPos, pRandom, this, pShouldConvertBlocks);
                if(spreader.charge >= 0){
                    BlockPos blockpos = spreader.getPos();
                    object2intmap.computeInt(blockpos, (p_222264_, p_222265_) -> (p_222265_ == null ? 0 : p_222265_) + spreader.charge);
                    FleshSpreader.ChargeCursor spreader1 = map.get(blockpos);
                    if(spreader1 == null){
                        map.put(blockpos, spreader);
                        list.add(spreader);
                    }else if(!this.isWorldGeneration() && spreader.charge + spreader1.charge <= 1000){
                        spreader1.mergeWith(spreader);
                    }else{
                        list.add(spreader);
                        if(spreader.charge < spreader1.charge){
                            map.put(blockpos, spreader);
                        }
                    }

                }
            }

            this.cursors = list;
        }
    }

    public static class ChargeCursor{
        private static final ObjectArrayList<Vec3i> NON_CORNER_NEIGHBOURS = Util.make(new ObjectArrayList<>(18), (p_222338_) -> {
            BlockPos.betweenClosedStream(new BlockPos(-1, -1, -1), new BlockPos(1, 1, 1)).filter((p_222336_) -> {
                return (p_222336_.getX() == 0 || p_222336_.getY() == 0 || p_222336_.getZ() == 0) && !p_222336_.equals(BlockPos.ZERO);
            }).map(BlockPos::immutable).forEach(p_222338_::add);
        });

        private BlockPos pos;
        int charge;
        private int updateDelay;
        private int decayDelay;
        @Nullable
        private Set<Direction> facings;
        private static final Codec<Set<Direction>> DIRECTION_SET = Direction.CODEC.listOf().xmap((p_222340_) -> {
            return Sets.newEnumSet(p_222340_, Direction.class);
        }, Lists::newArrayList);
        public static final Codec<FleshSpreader.ChargeCursor> CODEC = RecordCodecBuilder.create((p_222330_) -> {
            return p_222330_.group(BlockPos.CODEC.fieldOf("pos").forGetter(FleshSpreader.ChargeCursor::getPos), Codec.intRange(0, 1000).fieldOf("charge").orElse(0).forGetter(FleshSpreader.ChargeCursor::getCharge), Codec.intRange(0, 1).fieldOf("decay_delay").orElse(1).forGetter(FleshSpreader.ChargeCursor::getDecayDelay), Codec.intRange(0, Integer.MAX_VALUE).fieldOf("update_delay").orElse(0).forGetter((p_222346_) -> {
                return p_222346_.updateDelay;
            }), DIRECTION_SET.optionalFieldOf("facings").forGetter((p_222343_) -> {
                return Optional.ofNullable(p_222343_.getFacingData());
            })).apply(p_222330_, FleshSpreader.ChargeCursor::new);
        });

        private ChargeCursor(BlockPos p_222299_, int p_222300_, int p_222301_, int p_222302_, Optional<Set<Direction>> p_222303_){
            this.pos = p_222299_;
            this.charge = p_222300_;
            this.decayDelay = p_222301_;
            this.updateDelay = p_222302_;
            this.facings = p_222303_.orElse(null);
        }

        public ChargeCursor(BlockPos pPos, int pCharge){
            this(pPos, pCharge, 1, 0, Optional.empty());
        }

        public BlockPos getPos(){
            return this.pos;
        }

        public int getCharge(){
            return this.charge;
        }

        public int getDecayDelay(){
            return this.decayDelay;
        }

        @Nullable
        public Set<Direction> getFacingData(){
            return this.facings;
        }

        private boolean shouldUpdate(LevelAccessor pLevel, BlockPos pPos, boolean pIsWorldGeneration){
            if(this.charge <= 0){
                return false;
            }else if(pIsWorldGeneration){
                return true;
            }else if(pLevel instanceof ServerLevel serverlevel){
                return serverlevel.shouldTickBlocksAt(pPos);
            }else{
                return false;
            }
        }

        public void update(LevelAccessor pLevel, BlockPos pPos, RandomSource pRandom, FleshSpreader pSpreader, boolean pShouldConvertBlocks){
            if(this.shouldUpdate(pLevel, pPos, pSpreader.isWorldGeneration)){
                if(this.updateDelay > 0){
                    --this.updateDelay;
                }else{
                    BlockState blockstate = pLevel.getBlockState(this.pos);
                    FleshSpreaderBehaviour fleshBehaviour = getBlockBehaviour(blockstate);
                    if(pShouldConvertBlocks && fleshBehaviour.attemptSpreadVein(pLevel, this.pos, blockstate, this.facings, pSpreader.isWorldGeneration())){
                        if(fleshBehaviour.canChangeBlockStateOnSpread()){
                            blockstate = pLevel.getBlockState(this.pos);
                            fleshBehaviour = getBlockBehaviour(blockstate);
                        }

                        pLevel.playSound(null, this.pos, SoundsRegistry.CYST_SPREAD.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                    }

                    this.charge = fleshBehaviour.attemptUseCharge(this, pLevel, pPos, pRandom, pSpreader, pShouldConvertBlocks);
                    if(this.charge <= 0){
                        fleshBehaviour.onDischarged(pLevel, blockstate, this.pos, pRandom);
                    }else{
                        BlockPos blockpos = getValidMovementPos(pLevel, this.pos, pRandom);
                        if(blockpos != null){
                            fleshBehaviour.onDischarged(pLevel, blockstate, this.pos, pRandom);
                            this.pos = blockpos.immutable();
                            if(pSpreader.isWorldGeneration() && !this.pos.closerThan(new Vec3i(pPos.getX(), this.pos.getY(), pPos.getZ()), 15.0D)){
                                this.charge = 0;
                                return;
                            }

                            blockstate = pLevel.getBlockState(blockpos);
                        }

                        if(blockstate.getBlock() instanceof FleshSpreaderBehaviour){
                            this.facings = MultifaceBlock.availableFaces(blockstate);
                        }

                        this.decayDelay = fleshBehaviour.updateDecayDelay(this.decayDelay);
                        this.updateDelay = fleshBehaviour.getSpreadDelay();
                    }
                }
            }
        }

        void mergeWith(FleshSpreader.ChargeCursor pCursor){
            this.charge += pCursor.charge;
            pCursor.charge = 0;
            this.updateDelay = Math.min(this.updateDelay, pCursor.updateDelay);
        }

        private static FleshSpreaderBehaviour getBlockBehaviour(BlockState pState){
            Block block = pState.getBlock();
            FleshSpreaderBehaviour fleshBehaviour1;
            if(block instanceof FleshSpreaderBehaviour fleshBehaviour){
                fleshBehaviour1 = fleshBehaviour;
            }else{
                fleshBehaviour1 = FleshSpreaderBehaviour.DEFAULT;
            }

            return fleshBehaviour1;
        }

        private static List<Vec3i> getRandomizedNonCornerNeighbourOffsets(RandomSource pRandom){
            return Util.shuffledCopy(NON_CORNER_NEIGHBOURS, pRandom);
        }

        @Nullable
        private static BlockPos getValidMovementPos(LevelAccessor pLevel, BlockPos pPos, RandomSource pRandom){
            BlockPos.MutableBlockPos blockpos$mutableblockpos = pPos.mutable();
            BlockPos.MutableBlockPos blockpos$mutableblockpos1 = pPos.mutable();

            for(Vec3i vec3i : getRandomizedNonCornerNeighbourOffsets(pRandom)){
                blockpos$mutableblockpos1.setWithOffset(pPos, vec3i);
                BlockState blockstate = pLevel.getBlockState(blockpos$mutableblockpos1);
                if(blockstate.getBlock() instanceof FleshSpreaderBehaviour && isMovementUnobstructed(pLevel, pPos, blockpos$mutableblockpos1)){
                    blockpos$mutableblockpos.set(blockpos$mutableblockpos1);
                    if(BloodVeinBlock.hasSubstrateAccess(pLevel, blockstate, blockpos$mutableblockpos1)){
                        break;
                    }
                }
            }

            return blockpos$mutableblockpos.equals(pPos) ? null : blockpos$mutableblockpos;
        }

        private static boolean isMovementUnobstructed(LevelAccessor pLevel, BlockPos pFromPos, BlockPos pToPos){
            if(pFromPos.distManhattan(pToPos) == 1){
                return true;
            }else{
                BlockPos blockpos = pToPos.subtract(pFromPos);
                Direction direction = Direction.fromAxisAndDirection(Direction.Axis.X, blockpos.getX() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
                Direction direction1 = Direction.fromAxisAndDirection(Direction.Axis.Y, blockpos.getY() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
                Direction direction2 = Direction.fromAxisAndDirection(Direction.Axis.Z, blockpos.getZ() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
                if(blockpos.getX() == 0){
                    return isUnobstructed(pLevel, pFromPos, direction1) || isUnobstructed(pLevel, pFromPos, direction2);
                }else if(blockpos.getY() == 0){
                    return isUnobstructed(pLevel, pFromPos, direction) || isUnobstructed(pLevel, pFromPos, direction2);
                }else{
                    return isUnobstructed(pLevel, pFromPos, direction) || isUnobstructed(pLevel, pFromPos, direction1);
                }
            }
        }

        private static boolean isUnobstructed(LevelAccessor pLevel, BlockPos pPos, Direction pDirection){
            BlockPos blockpos = pPos.relative(pDirection);
            return !pLevel.getBlockState(blockpos).isFaceSturdy(pLevel, blockpos, pDirection.getOpposite());
        }
    }
}
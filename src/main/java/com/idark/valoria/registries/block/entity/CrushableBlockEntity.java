package com.idark.valoria.registries.block.entity;

import com.idark.valoria.registries.BlockEntitiesRegistry;
import com.idark.valoria.registries.block.types.CrushableBlock;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class CrushableBlockEntity extends BlockEntity{
    private static final Logger LOGGER = LogUtils.getLogger();
    private int brushCount;
    private long brushCountResetsAtTick;
    private long coolDownEndsAtTick;
    private ItemStack item = ItemStack.EMPTY;
    @Nullable
    private Direction hitDirection;
    @Nullable
    private ResourceLocation lootTable;
    private long lootTableSeed;

    public CrushableBlockEntity(BlockPos pPos, BlockState pBlockState){
        super(BlockEntitiesRegistry.CRUSHABLE_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public boolean crushing(long pStartTick, Player pPlayer, Direction pHitDirection){
        if(this.hitDirection == null){
            this.hitDirection = pHitDirection;
        }

        this.brushCountResetsAtTick = pStartTick + 60L;
        if(pStartTick >= this.coolDownEndsAtTick && this.level instanceof ServerLevel){
            this.coolDownEndsAtTick = pStartTick + 10L;
            this.unpackLootTable(pPlayer);
            int i = this.getCompletionState();
            if(++this.brushCount >= 11){
                this.brushingCompleted(pPlayer);
                return true;
            }else{
                this.level.scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 60);
                int j = this.getCompletionState();
                if(i != j){
                    BlockState blockstate = this.getBlockState();
                    BlockState pState = blockstate.setValue(BlockStateProperties.DUSTED, j);
                    this.level.setBlock(this.getBlockPos(), pState, 3);
                }

                return false;
            }
        }else{
            return false;
        }
    }

    public void unpackLootTable(Player pPlayer){
        if(this.lootTable != null && this.level != null && !this.level.isClientSide() && this.level.getServer() != null){
            LootTable loottable = this.level.getServer().getLootData().getLootTable(this.lootTable);
            if(pPlayer instanceof ServerPlayer serverplayer){
                CriteriaTriggers.GENERATE_LOOT.trigger(serverplayer, this.lootTable);
            }

            unpack(pPlayer, loottable);
        }
    }

    public void unpack(@Nullable Player pPlayer, LootTable loottable){
        LootParams lootparams = (new LootParams.Builder((ServerLevel)this.level)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.worldPosition)).withLuck(pPlayer != null ? pPlayer.getLuck() : 0).create(LootContextParamSets.CHEST);
        ObjectArrayList<ItemStack> objectarraylist = loottable.getRandomItems(lootparams, this.lootTableSeed);
        this.item = switch(objectarraylist.size()){
            case 0 -> ItemStack.EMPTY;
            case 1 -> objectarraylist.get(0);
            default -> {
                LOGGER.warn("Expected max 1 loot from loot table {} got {}", this.lootTable, objectarraylist.size());
                yield objectarraylist.get(0);
            }
        };

        this.lootTable = null;
        this.setChanged();
    }

    private void brushingCompleted(Player pPlayer){
        if(this.level != null && this.level.getServer() != null){
            this.dropContent(pPlayer);
            BlockState blockstate = this.getBlockState();
            this.level.levelEvent(3008, this.getBlockPos(), Block.getId(blockstate));
            Block block = this.getBlockState().getBlock();
            Block block1;
            if(block instanceof CrushableBlock pBlock){
                block1 = pBlock.getTurnsInto();
            }else{
                block1 = Blocks.AIR;
            }

            this.level.setBlock(this.worldPosition, block1.defaultBlockState(), 3);
        }
    }

    private void dropContent(Player pPlayer){
        if(this.level != null && this.level.getServer() != null){
            this.unpackLootTable(pPlayer);
            if(!this.item.isEmpty()){
                double d0 = EntityType.ITEM.getWidth();
                double d1 = 1.0D - d0;
                double d2 = d0 / 2.0D;
                Direction direction = Objects.requireNonNullElse(this.hitDirection, Direction.UP);
                BlockPos blockpos = this.worldPosition.relative(direction, 1);
                double d3 = (double)blockpos.getX() + 0.5D * d1 + d2;
                double d4 = (double)blockpos.getY() + 0.5D + (double)(EntityType.ITEM.getHeight() / 2.0F);
                double d5 = (double)blockpos.getZ() + 0.5D * d1 + d2;
                ItemEntity itementity = new ItemEntity(this.level, d3, d4, d5, this.item.split(this.level.random.nextInt(21) + 10));
                itementity.setDeltaMovement(Vec3.ZERO);
                this.level.addFreshEntity(itementity);
                this.item = ItemStack.EMPTY;
            }

        }
    }

    public void checkReset(){
        if(this.level != null){
            if(this.brushCount != 0 && this.level.getGameTime() >= this.brushCountResetsAtTick){
                int i = this.getCompletionState();
                this.brushCount = Math.max(0, this.brushCount - 2);
                int j = this.getCompletionState();
                if(i != j){
                    this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(BlockStateProperties.DUSTED, Integer.valueOf(j)), 3);
                }

                this.brushCountResetsAtTick = this.level.getGameTime() + 4L;
            }

            if(this.brushCount == 0){
                this.hitDirection = null;
                this.brushCountResetsAtTick = 0L;
                this.coolDownEndsAtTick = 0L;
            }else{
                this.level.scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), (int)(this.brushCountResetsAtTick - this.level.getGameTime()));
            }

        }
    }

    private boolean tryLoadLootTable(CompoundTag pTag){
        if(pTag.contains("LootTable", 8)){
            this.lootTable = new ResourceLocation(pTag.getString("LootTable"));
            this.lootTableSeed = pTag.getLong("LootTableSeed");
            return true;
        }else{
            return false;
        }
    }

    private boolean trySaveLootTable(CompoundTag pTag){
        if(this.lootTable == null){
            return false;
        }else{
            pTag.putString("LootTable", this.lootTable.toString());
            if(this.lootTableSeed != 0L){
                pTag.putLong("LootTableSeed", this.lootTableSeed);
            }

            return true;
        }
    }

    public CompoundTag getUpdateTag(){
        CompoundTag compoundtag = super.getUpdateTag();
        if(this.hitDirection != null){
            compoundtag.putInt("hit_direction", this.hitDirection.ordinal());
        }

        compoundtag.put("item", this.item.save(new CompoundTag()));
        return compoundtag;
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket(){
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void load(CompoundTag pTag){
        if(!this.tryLoadLootTable(pTag) && pTag.contains("item")){
            this.item = ItemStack.of(pTag.getCompound("item"));
        }

        if(pTag.contains("hit_direction")){
            this.hitDirection = Direction.values()[pTag.getInt("hit_direction")];
        }

    }

    protected void saveAdditional(CompoundTag pTag){
        if(!this.trySaveLootTable(pTag)){
            pTag.put("item", this.item.save(new CompoundTag()));
        }

    }

    public static void setLootTable(BlockGetter pLevel, RandomSource pRandom, BlockPos pPos, ResourceLocation pLootTable){
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if(blockentity instanceof CrushableBlockEntity entity){
            entity.setLootTable(pLootTable, pRandom.nextLong());
        }
    }

    public static void setLootTable(RandomSource pRandom, BlockEntity blockentity, ResourceLocation pLootTable){
        if(blockentity instanceof CrushableBlockEntity entity){
            entity.setLootTable(pLootTable, pRandom.nextLong());
        }
    }

    /**
     * Unpacks the LootTable and sets the drop to tile entity
     */
    public static void unpackAndSetItem(ServerLevel pLevel, BlockEntity blockentity, LootTable loottable){
        if(blockentity instanceof CrushableBlockEntity entity){
            LootParams lootparams = (new LootParams.Builder(pLevel).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(entity.worldPosition)).withLuck(0).create(LootContextParamSets.CHEST));
            ObjectArrayList<ItemStack> objectarraylist = loottable.getRandomItems(lootparams, new Random().nextLong());
            entity.setItem(objectarraylist.get(0));
            entity.setChanged();
        }
    }

    /**
     * Sets the item to tile entity
     */
    public static void setItem(BlockGetter pLevel, BlockPos pPos, ItemStack pItem){
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if(blockentity instanceof CrushableBlockEntity entity){
            entity.setItem(pItem);
        }
    }

    public void setLootTable(ResourceLocation pLootTable, long pLootTableSeed){
        this.lootTable = pLootTable;
        this.lootTableSeed = pLootTableSeed;
    }

    public void setItem(ItemStack pItem){
        this.item = pItem;
    }

    private int getCompletionState(){
        if(this.brushCount == 0){
            return 0;
        }else if(this.brushCount < 3){
            return 1;
        }else{
            return this.brushCount < 6 ? 2 : 3;
        }
    }

    @Nullable
    public Direction getHitDirection(){
        return this.hitDirection;
    }

    public ItemStack getItem(){
        return this.item;
    }
}
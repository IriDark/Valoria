package com.idark.valoria.registries.block.entity;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import com.mojang.logging.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.advancements.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.parameters.*;
import net.minecraft.world.phys.*;
import org.slf4j.*;

import javax.annotation.*;
import java.util.*;

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

    public ItemStack unpack(@Nullable Player pPlayer, LootTable loottable) {
        LootParams lootparams = (new LootParams.Builder((ServerLevel)this.level)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.worldPosition)).withLuck(pPlayer != null ? pPlayer.getLuck() : 0).withParameter(LootContextParams.THIS_ENTITY, pPlayer).create(LootContextParamSets.CHEST);
        ObjectArrayList<ItemStack> objectarraylist = loottable.getRandomItems(lootparams, this.lootTableSeed);
        this.item = switch(objectarraylist.size()){
            case 0 -> ItemStack.EMPTY;
            case 1 -> objectarraylist.get(0);
            default -> {
                LOGGER.warn("Expected max 1 loot from loot table " + this.lootTable + " got " + objectarraylist.size());
                yield objectarraylist.get(0);
            }
        };

        this.lootTable = null;
        this.setChanged();
        return objectarraylist.get(0);
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

    public static void setLootTable(BlockGetter pLevel, RandomSource pRandom, BlockPos pPos, ResourceLocation pLootTable) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof CrushableBlockEntity entity) {
            entity.setLootTable(pLootTable, pRandom.nextLong());
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
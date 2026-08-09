package com.idark.valoria.registries.item.types;

import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;

public class BossSummonableItem extends TexturedSpawnEggItem{
    private final Supplier<? extends EntityType<? extends Mob>> typeSupplier;
    private ResourceKey<Level> dimension = Level.OVERWORLD;
    private boolean specificDimension = false;
    private final float expandValue;
    private final VoxelShape AIR = Block.box(5.0D, 5.0D, 5.0D, 11.0D, 11.0D, 11.0D);

    public BossSummonableItem(Supplier<? extends EntityType<? extends Mob>> type, Properties pProperties){
        super(type, pProperties);
        this.typeSupplier = type;
        this.expandValue = 0;
    }

    public BossSummonableItem(float expandValue, Supplier<? extends EntityType<? extends Mob>> type, Properties pProperties){
        super(type, pProperties);
        this.typeSupplier = type;
        this.expandValue = expandValue;
    }

    public BossSummonableItem(float expandValue, ResourceKey<Level> dimension, Supplier<? extends EntityType<? extends Mob>> type, Properties pProperties){
        super(type, pProperties);
        this.typeSupplier = type;
        this.expandValue = expandValue;
        this.dimension = dimension;
        this.specificDimension = true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.boss_summonable", getDefaultType().getDescription()).withStyle(ChatFormatting.GRAY));
        if (specificDimension) {
            ResourceLocation loc = dimension.location();
            String dimKey = "dimension." + loc.getNamespace() + "." + loc.getPath();
            tooltip.add(Component.translatable("tooltip.valoria.boss_summonable.dimension", Component.translatable(dimKey)).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public EntityType<?> getType(@Nullable CompoundTag tag){
        EntityType<?> type = super.getType(tag);
        return type != null ? type : typeSupplier.get();
    }

    @Override
    public EntityType<?> getDefaultType(){
        return this.typeSupplier.get();
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext){
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        BlockPos pos = pContext.getClickedPos().above();
        AABB spawnAABB = getAABB(pos);

        SpawnResult result = canSpawnHere(level, spawnAABB);
        if(result.success()){
            return super.useOn(pContext);
        } else {
            if (level.isClientSide) player.displayClientMessage(result.failMessage(), true);
        }

        return InteractionResult.FAIL;
    }

    @NotNull
    public AABB getAABB(BlockPos targetPos) {
        EntityDimensions dim = getDefaultType().getDimensions();
        double width = dim.width + this.expandValue;
        double height = dim.height;

        double minX = targetPos.getX() + 0.5 - width / 2.0;
        double minY = targetPos.getY();
        double minZ = targetPos.getZ() + 0.5 - width / 2.0;
        return new AABB(minX, minY, minZ, minX + width, minY + height, minZ + width);
    }

    public SpawnResult canSpawnHere(Level world, AABB blockAABB){
        if(specificDimension && world.dimension() != dimension) return SpawnResult.fail();

        BlockPos min = BlockPos.containing(blockAABB.minX, blockAABB.minY, blockAABB.minZ);
        BlockPos max = BlockPos.containing(blockAABB.maxX, blockAABB.maxY, blockAABB.maxZ);
        List<PreventingBlock> preventingBlocks = new ArrayList<>();

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        int baseY = min.getY();
        for(int x = min.getX(); x <= max.getX(); x++){
            for(int z = min.getZ(); z <= max.getZ(); z++){
                mutablePos.set(x, baseY - 1, z);
                BlockState groundState = world.getBlockState(mutablePos);
                VoxelShape shape = groundState.getCollisionShape(world, mutablePos);
                if(groundState.isAir() || !groundState.isSolid() || world.getFluidState(mutablePos).isSource()){
                    preventingBlocks.add(new PreventingBlock(mutablePos.immutable(), shape.isEmpty() ? AIR : shape));
                }
            }
        }

        for(int x = min.getX(); x <= max.getX(); x++){
            for(int y = baseY; y <= max.getY(); y++){
                for(int z = min.getZ(); z <= max.getZ(); z++){
                    mutablePos.set(x, y, z);
                    BlockState state = world.getBlockState(mutablePos);
                    VoxelShape shape = state.getCollisionShape(world, mutablePos);
                    if(!shape.isEmpty() || world.getFluidState(mutablePos).isSource()){
                        if(!state.canBeReplaced() && state.getDestroySpeed(world, mutablePos) >= 0){
                            preventingBlocks.add(new PreventingBlock(mutablePos.immutable(), shape));
                        }
                    }
                }
            }
        }

        return SpawnResult.checkResult(blockAABB, preventingBlocks);
    }

    public record PreventingBlock(BlockPos pos, VoxelShape shape) {}

    public record SpawnResult(boolean success, List<PreventingBlock> preventingBlocks, Component failMessage){
        public static SpawnResult fail(){
            return new SpawnResult(false, List.of(), Component.translatable("tooltip.valoria.boss_summon.dimension_fail").withStyle(ChatFormatting.GRAY));
        }

        public static SpawnResult checkResult(AABB aabb, List<PreventingBlock> preventingBlocks){
            var size = Math.floor(aabb.getSize()) + 3;
            return new SpawnResult(preventingBlocks.isEmpty(), preventingBlocks, Component.translatable("tooltip.valoria.boss_summon.block_fail", size + "x" + size).withStyle(ChatFormatting.GRAY));
        }
    }
}
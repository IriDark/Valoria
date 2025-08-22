package com.idark.valoria.registries.item.types;

import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.util.*;

import java.util.*;
import java.util.function.*;

public class BossSummonableItem extends TexturedSpawnEggItem{
    private final Supplier<? extends EntityType<? extends Mob>> typeSupplier;
    private final float expandValue;

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

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.boss_summonable", getDefaultType().getDescription()).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public EntityType<?> getType(@Nullable CompoundTag tag){
        EntityType<?> type = super.getType(tag);
        return type != null ? type : typeSupplier.get();
    }

    @Override
    protected EntityType<?> getDefaultType(){
        return this.typeSupplier.get();
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext){
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        AABB spawnAABB = getAABB(player, getDefaultType().create(level));
        SpawnResult result = canSpawnHere(level, spawnAABB);
        if(result.success()){
            return super.useOn(pContext);
        }else{
            var size = Math.floor(spawnAABB.getSize()) + 3;
            player.displayClientMessage(Component.translatable("tooltip.valoria.boss_summon_fail", size + "x" + size).withStyle(ChatFormatting.GRAY), true);
            showParticleBox(level, spawnAABB);
            for(BlockPos pos : result.preventingBlocks)
                if(level.isClientSide())
                    showBlockingParticles(level, pos);
        }

        return InteractionResult.FAIL;
    }

    public void showParticleBox(Level level, AABB box){
        if(!(level instanceof ServerLevel server)) return;
        double step = 0.5;
        for(double x = box.minX; x <= box.maxX; x += step){
            for(double y = box.minY; y <= box.maxY; y += step){
                for(double z = box.minZ; z <= box.maxZ; z += step){
                    boolean onEdge =
                    Mth.equal(x, box.minX) || x + step > box.maxX ||
                    Mth.equal(y, box.minY) || y + step > box.maxY ||
                    Mth.equal(z, box.minZ) || z + step > box.maxZ;
                    if(onEdge){
                        server.sendParticles(ParticleTypes.MYCELIUM, x, y, z, 1, 0, 0, 0, 0);
                    }
                }
            }
        }
    }

    public void showBlockingParticles(Level level, BlockPos pos){
        ParticleBuilder.create(TridotParticles.SQUARE)
        .setScaleData(GenericParticleData.create(0.05f).build())
        .setColorData(ColorParticleData.create(Col.red).build())
        .setGravity(0).setLifetime(60).setHasPhysics(false).spawnVoxelShape(level, new Vec3(pos.getX(), pos.getY(), pos.getZ()), level.getBlockState(pos).getShape(level, pos), 15).getParticleOptions();
    }

    @NotNull
    public AABB getAABB(Player player, Entity mob){
        AABB mobBoundingBox = mob.getBoundingBox();
        Vec3 origin = player.getEyePosition();
        Vec3 direction = player.getForward();
        BlockHitResult hitResult = player.level().clip(new ClipContext(origin, origin.add(direction.scale(5)),
        ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, player));
        BlockPos centerPos = hitResult.getBlockPos().above();

        double width = mobBoundingBox.getXsize() + this.expandValue;
        double height = mobBoundingBox.getYsize();
        double depth = mobBoundingBox.getZsize() + this.expandValue;

        double minX = centerPos.getX() - width / 2.0;
        double minY = centerPos.getY();
        double minZ = centerPos.getZ() - depth / 2.0;
        double maxX = centerPos.getX() + width / 2.0;
        double maxY = centerPos.getY() + height;
        double maxZ = centerPos.getZ() + depth / 2.0;

        return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public SpawnResult canSpawnHere(Level world, AABB blockAABB){
        BlockPos min = new BlockPos((int)Math.floor(blockAABB.minX), (int)Math.floor(blockAABB.minY), (int)Math.floor(blockAABB.minZ));
        BlockPos max = new BlockPos((int)Math.floor(blockAABB.maxX), (int)Math.floor(blockAABB.maxY), (int)Math.floor(blockAABB.maxZ));

        List<BlockPos> preventingBlocks = new ArrayList<>();
        int baseY = min.getY();
        for(int x = min.getX(); x <= max.getX(); x++){
            for(int z = min.getZ(); z <= max.getZ(); z++){
                BlockPos groundPos = new BlockPos(x, baseY - 1, z);
                BlockState groundState = world.getBlockState(groundPos);
                if(groundState.isAir() || !groundState.isSolid() || world.getFluidState(groundPos).isSource()){
                    preventingBlocks.add(groundPos);
                }
            }
        }

        for(int x = min.getX(); x <= max.getX(); x++){
            for(int y = baseY; y <= max.getY(); y++){
                for(int z = min.getZ(); z <= max.getZ(); z++){
                    BlockPos checkPos = new BlockPos(x, y, z);
                    BlockState state = world.getBlockState(checkPos);
                    if(!state.getCollisionShape(world, checkPos).isEmpty() || world.getFluidState(checkPos).isSource()){
                        if(!state.canBeReplaced() && state.getDestroySpeed(world, checkPos) >= 0){
                            preventingBlocks.add(checkPos);
                        }
                    }
                }
            }
        }

        return SpawnResult.checkResult(preventingBlocks);
    }

    public record SpawnResult(boolean success, List<BlockPos> preventingBlocks){

        public static SpawnResult checkResult(List<BlockPos> preventingBlocks){
            return new SpawnResult(preventingBlocks.isEmpty(), preventingBlocks);
        }
    }
}
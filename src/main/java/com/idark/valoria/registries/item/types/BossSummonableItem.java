package com.idark.valoria.registries.item.types;

import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;

public class BossSummonableItem extends TexturedSpawnEggItem{
    private final Supplier<? extends EntityType<? extends Mob>> typeSupplier;
    float expandValue;

    public BossSummonableItem(Supplier<? extends EntityType<? extends Mob>> type, Properties pProperties){
        super(type, pProperties);
        this.typeSupplier = type;
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
        if(canSpawnHere(pContext.getLevel(), getDefaultType(), pContext.getPlayer())){
            return super.useOn(pContext);
        } else {
            var size = Math.floor(getAABB(pContext.getPlayer(), getDefaultType().create(pContext.getLevel())).getSize()) + 3;
            pContext.getPlayer().displayClientMessage(Component.translatable("tooltip.valoria.boss_summon_fail", size + "x" + size).withStyle(ChatFormatting.GRAY), true);
            showParticleBox(pContext.getLevel(), getAABB(pContext.getPlayer(), getDefaultType().create(pContext.getLevel())));
            return InteractionResult.FAIL;
        }
    }

    public void showParticleBox(Level level, AABB box) {
        if (!(level instanceof ServerLevel server)) return;
        double step = 0.5;
        for (double x = box.minX; x <= box.maxX; x += step) {
            for (double y = box.minY; y <= box.maxY; y += step) {
                for (double z = box.minZ; z <= box.maxZ; z += step) {
                    boolean onEdge =
                    x == box.minX || x + step > box.maxX ||
                    y == box.minY || y + step > box.maxY ||
                    z == box.minZ || z + step > box.maxZ;
                    if (onEdge) {
                        server.sendParticles(ParticleTypes.SMOKE, x, y, z, 1, 0, 0, 0, 0);
                    }
                }
            }
        }
    }

    @NotNull
    public AABB getAABB(Player player, Entity mob){
        AABB boundingBox = mob.getBoundingBox().expandTowards(expandValue, 0, expandValue);
        double height = 0.1;
        Vec3 origin = player.getEyePosition();
        Vec3 direction = player.getForward();
        BlockHitResult hit = player.level().clip(new ClipContext(origin, origin.add(direction.scale(5)),
        ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, player));
        BlockPos spawnPos = new BlockPos(hit.getBlockPos().getX(), hit.getBlockPos().getY() + 1, hit.getBlockPos().getZ());
        double minX = spawnPos.getX() + boundingBox.minX;
        double minY = spawnPos.getY();
        double minZ = spawnPos.getZ() + boundingBox.minZ;
        double maxX = spawnPos.getX() + boundingBox.maxX;
        double maxY = spawnPos.getY() + boundingBox.maxY - height;
        double maxZ = spawnPos.getZ() + boundingBox.maxZ;
        return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public boolean canSpawnHere(Level world, EntityType<?> entityType, Player player){
        Entity mob = entityType.create(world);
        if(mob == null) return false;

        AABB blockAABB = getAABB(player, mob);
        BlockPos min = new BlockPos((int) blockAABB.minX, (int) blockAABB.minY, (int) blockAABB.minZ);
        BlockPos max = new BlockPos((int) blockAABB.maxX, (int) blockAABB.maxY, (int) blockAABB.maxZ);
        int baseY = min.getY();
        for (int x = min.getX(); x <= max.getX(); x++) {
            for (int z = min.getZ(); z <= max.getZ(); z++) {
                BlockPos groundPos = new BlockPos(x, baseY - 1, z);
                BlockState ground = world.getBlockState(groundPos);
                if (ground.isAir() || !ground.isSolid()) return false;
                for (int y = baseY; y <= max.getY(); y++) {
                    BlockPos checkPos = new BlockPos(x, y, z);
                    BlockState state = world.getBlockState(checkPos);
                    if (!state.getCollisionShape(world, checkPos).isEmpty()) return false;
                }
            }
        }

        return true;
    }
}

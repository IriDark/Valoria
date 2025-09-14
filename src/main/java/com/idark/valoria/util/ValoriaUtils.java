package com.idark.valoria.util;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.ranged.*;
import net.minecraft.client.gui.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.protocol.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.items.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.util.phys.*;
import pro.komaru.tridot.util.struct.func.*;
import top.theillusivec4.curios.api.*;

import javax.annotation.*;
import java.lang.Math;
import java.util.*;
import java.util.function.*;

public class ValoriaUtils{

    public static boolean isVisibleInScissor(GuiGraphics gui, int x, int y, int w, int h, int scissorX, int scissorY, int scissorW, int scissorH) {
        AbsRect s = AbsRect.xywhDef((float)scissorX, (float)scissorY, (float)scissorW, (float)scissorH).pose(gui.pose());
        AbsRect r = AbsRect.xywhDef((float)x, (float)y, (float)w, (float)h).pose(gui.pose());
        return r.x < s.x2 && r.x2 > s.x && r.y < s.y2 && r.y2 > s.y;
    }

    @Nullable
    public static ItemStack getEquippedCurio(Predicate<ItemStack> filter, LivingEntity entity) {
        var curio = CuriosApi.getCuriosHelper().findEquippedCurio(filter, entity);
        return curio.map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(null);
    }

    public static boolean isEquippedCurio(Predicate<ItemStack> filter, LivingEntity entity) {
        var curio = CuriosApi.getCuriosHelper().findEquippedCurio(filter, entity);
        return curio.isPresent();
    }

    public static boolean isEquippedCurio(TagKey<Item> tag, LivingEntity entity) {
        var curio = CuriosApi.getCuriosHelper().findEquippedCurio((item) -> item.is(tag), entity);
        return curio.isPresent();
    }

    public static boolean isEquippedCurio(Item pItem, LivingEntity entity) {
        var curio = CuriosApi.getCuriosHelper().findEquippedCurio((item) -> item.is(pItem), entity);
        return curio.isPresent();
    }

    public static void addHandPlayerItem(Level level, Player player, InteractionHand hand, ItemStack stack, ItemStack addStack) {
        if (player.getInventory().getSlotWithRemainingSpace(addStack) >= 0) {
            addPlayerItem(level, player, addStack);
        } else if (stack.isEmpty()) {
            player.setItemInHand(hand, addStack.copy());
        } else if (ItemHandlerHelper.canItemStacksStack(stack, addStack) && (stack.getCount() + addStack.getCount() <= addStack.getMaxStackSize())) {
            stack.setCount(stack.getCount() + addStack.getCount());
            player.setItemInHand(hand, stack);
        } else {
            addPlayerItem(level, player, addStack);
        }
    }

    public static void addPlayerItem(Level level, Player player, ItemStack addStack) {
        if (player.getInventory().getSlotWithRemainingSpace(addStack) != -1 || player.getInventory().getFreeSlot() > -1) {
            player.getInventory().add(addStack.copy());
        } else {
            level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), addStack.copy()));
        }
    }

    public static void SUpdateTileEntityPacket(BlockEntity tile){
        if(tile.getLevel() instanceof ServerLevel){
            Packet<?> packet = tile.getUpdatePacket();
            if(packet != null){
                BlockPos pos = tile.getBlockPos();
                ((ServerChunkCache)tile.getLevel().getChunkSource()).chunkMap
                .getPlayers(new ChunkPos(pos), false)
                .forEach(e -> e.connection.send(packet));
            }
        }
    }

    public static float enchantmentAccuracy(ItemStack stack) {
        int i = stack.getEnchantmentLevel(EnchantmentsRegistry.ACCURACY.get());
        return i > 0 ? i + 0.5f : 0.0F;
    }

    /**
     * Performs a circled attack near player
     *
     * @param radius      Attack radius
     * @param type        Particle type used to show radius
     * @param hitEntities List for damaged entities
     * @param pos         Position
     */
    public static void radiusHit(Level level, ItemStack stack, Player player, @Nullable ParticleOptions type, List<LivingEntity> hitEntities, Vector3d pos, float pitchRaw, float yawRaw, float radius){
        for(int i = 0; i < 360; i += 10){
            double pitch = ((pitchRaw + 90) * Math.PI) / 180;
            double yaw = ((yawRaw + 90) * Math.PI) / 180;
            float pRadius = radius + Utils.Items.enchantmentRadius(stack);
            double X = Math.sin(pitch) * Math.cos(yaw + i) * pRadius;
            double Y = Math.cos(pitch) * pRadius;
            double Z = Math.sin(pitch) * Math.sin(yaw + i) * pRadius;

            AABB boundingBox = new AABB(pos.x, pos.y - 1 + ((Math.random() - 0.5D) * 0.2F), pos.z, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z);
            List<Entity> entities = level.getEntitiesOfClass(Entity.class, boundingBox);
            for(Entity entity : entities){
                if(entity instanceof LivingEntity livingEntity && !hitEntities.contains(livingEntity) && !livingEntity.equals(player)){
                    if(Utils.Entities.canHitTarget(player, livingEntity)){
                        hitEntities.add(livingEntity);
                    }
                }
            }

            X = Math.sin(pitch) * Math.cos(yaw + i) * pRadius * 0.75F;
            Y = Math.cos(pitch) * pRadius * 0.75F;
            Z = Math.sin(pitch) * Math.sin(yaw + i) * pRadius * 0.75F;
            if(type != null && !level.isClientSide() && level instanceof ServerLevel pServer){
                pServer.sendParticles(type, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z, 1, 0, 0, 0, 0);
            }
        }
    }

    /**
     * Performs a circled attack near player
     *
     * @param radius      Attack radius
     * @param type        Particle type used to show radius
     * @param hitEntities List for damaged entities
     * @param pos         Position
     */
    public static void radiusHit(Level level, Player player, @Nullable ParticleOptions type, List<LivingEntity> hitEntities, Vector3d pos, float pitchRaw, float yawRaw, float radius){
        for(int i = 0; i < 360; i += 10){
            double pitch = ((pitchRaw + 90) * Math.PI) / 180;
            double yaw = ((yawRaw + 90) * Math.PI) / 180;
            double X = Math.sin(pitch) * Math.cos(yaw + i) * radius;
            double Y = Math.cos(pitch) * radius;
            double Z = Math.sin(pitch) * Math.sin(yaw + i) * radius;

            AABB boundingBox = new AABB(pos.x, pos.y - 1 + ((Math.random() - 0.5D) * 0.2F), pos.z, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z);
            List<Entity> entities = level.getEntitiesOfClass(Entity.class, boundingBox);
            for(Entity entity : entities){
                if(entity instanceof LivingEntity livingEntity && !hitEntities.contains(livingEntity) && !livingEntity.equals(player)){
                    if(Utils.Entities.canHitTarget(player, livingEntity)){
                        hitEntities.add(livingEntity);
                    }
                }
            }

            X = Math.sin(pitch) * Math.cos(yaw + i) * radius * 0.75F;
            Y = Math.cos(pitch) * radius * 0.75F;
            Z = Math.sin(pitch) * Math.sin(yaw + i) * radius * 0.75F;
            if(type != null && !level.isClientSide() && level instanceof ServerLevel pServer){
                pServer.sendParticles(type, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z, 1, 0, 0, 0, 0);
            }
        }
    }

    public static void stunNearby(Level level, LivingEntity caster, Vector3d pos, float pitchRaw, float yawRaw, float radius){
        for(int i = 0; i < 360; i += 10){
            double pitch = ((pitchRaw + 90) * Math.PI) / 180;
            double yaw = ((yawRaw + 90) * Math.PI) / 180;
            double X = Math.sin(pitch) * Math.cos(yaw + i) * radius;
            double Y = Math.cos(pitch) * radius;
            double Z = Math.sin(pitch) * Math.sin(yaw + i) * radius;

            AABB boundingBox = new AABB(pos.x, pos.y - 1 + ((Math.random() - 0.5D) * 0.2F), pos.z, pos.x + X, pos.y + Y + ((Math.random() - 0.5D) * 0.2F), pos.z + Z);
            List<Entity> entities = level.getEntitiesOfClass(Entity.class, boundingBox);
            for(Entity entity : entities){
                if(entity instanceof LivingEntity livingEntity && !livingEntity.equals(caster)){
                    if(Utils.Entities.canHitTarget(caster, livingEntity)){
                        livingEntity.addEffect(new MobEffectInstance(EffectsRegistry.STUN.get(), 30, 0));
                    }
                }
            }
        }
    }

    /**
     * Searches items in player inventory that equals an instance of GunpowderCharge
     */
    public static ItemStack getProjectile(Player player, ItemStack pShootable){
        Boolf<ItemStack> predicate = (stack) -> stack.getItem() instanceof GunpowderCharge;
        return Utils.Items.getProjectile(player, pShootable, predicate);
    }

    /**
     * Determines whether the given effect can be cured using curative items.
     * <p>
     * Returns true if the effect is negative and non-instantaneous (is harmful but wider).
     */
    public static boolean isCurable(MobEffectInstance e) {
        var effect = e.getEffect();
        return !effect.isBeneficial() && !effect.isInstantenous();
    }

    @SuppressWarnings({"removal", "UnstableApiUsage", "deprecation"})
    public static boolean onePerTypeEquip(SlotContext slotContext, ItemStack stack){
        List<ItemStack> items = new ArrayList<>();
        List<SlotResult> curioSlots = CuriosApi.getCuriosHelper().findCurios(slotContext.getWearer(), stack.getItem());
        for(SlotResult slot : curioSlots){
            items.add(slot.stack());
        }

        return items.isEmpty() || slotContext.cosmetic();
    }

    public static void addList(List<Item> list, Item... T){
        Collections.addAll(list, T);
    }
}
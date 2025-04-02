package com.idark.valoria.util;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.ranged.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.protocol.*;
import net.minecraft.server.level.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.phys.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.util.struct.func.*;
import top.theillusivec4.curios.api.*;

import javax.annotation.*;
import java.lang.Math;
import java.util.*;

public class ValoriaUtils{
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
                    hitEntities.add(livingEntity);
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
                    hitEntities.add(livingEntity);
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
                    livingEntity.addEffect(new MobEffectInstance(EffectsRegistry.STUN.get(), 30, 0));
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

    @SuppressWarnings("removal")
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
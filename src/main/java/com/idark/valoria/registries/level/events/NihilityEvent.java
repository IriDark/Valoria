package com.idark.valoria.registries.level.events;

import com.idark.valoria.core.capability.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.level.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.server.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.TickEvent.*;
import pro.komaru.tridot.util.*;

import java.util.*;

public class NihilityEvent{
    public static float damagingLevel = 0.5f;
    public static float criticalLevel = 0.75f;

    public static void tick(PlayerTickEvent event, INihilityLevel nihilityLevel, ServerPlayer player){
        Level level = player.level();
        Difficulty difficulty = level.getDifficulty();
        float max = nihilityLevel.getMaxAmount(player);
        float amount = nihilityLevel.getAmount();

        if(level.dimension() == LevelGen.VALORIA_KEY){
            if (difficulty == Difficulty.PEACEFUL) {
                return;
            }

            if(player.tickCount % (int)(player.getAttributeValue(AttributeReg.NIHILITY_RESILIENCE.get()) * 20) == 0){
                double resistance = player.getAttributeValue(AttributeReg.NIHILITY_RESISTANCE.get());
                float baseFactor = (float)Math.max(0.05, 1.0 - (resistance * 0.05));
                float difficultyMul = difficulty.getId() * 0.5f;
                float finalAmount = baseFactor * difficultyMul;
                nihilityLevel.modifyAmount(player, finalAmount);
            }
        }else{
            if(player.tickCount % ServerConfig.NIHILITY_DECAY_INTERVAL.get() * 40 == 0 && amount > 0){
                int baseDecay = Tmp.rnd.nextInt(1, 5);
                int decayMultiplier = (difficulty == Difficulty.EASY) ? 2 : 1;
                nihilityLevel.decrease(player, baseDecay * decayMultiplier);
            }
        }

        if(isDamagingLevel(player, amount, max)){
            float ratio = amount / max;
            boolean flag = ratio >= damagingLevel;
            int segments = (int)((ratio - damagingLevel) / 0.1f);
            float damage = (float)(1 + segments * ServerConfig.NIHILITY_DAMAGE_MULTIPLIER.get());
            if(ratio >= 0.95f) {
                onMaxAction(nihilityLevel, player, damage);
            } else if(flag){
                player.hurt(DamageSourceRegistry.voidHarm(player.level()), damage);
            }

            boolean criticalFlag = amount > max * criticalLevel;
            if(criticalFlag){
                if(Tmp.rnd.chance(0.05f)){
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.WARDEN_HEARTBEAT, net.minecraft.sounds.SoundSource.PLAYERS,
                    1.0f, 0.8f);
                }

                if(ServerConfig.CRITICAL_NIHILITY_BLINDNESS.get()){
                    if(!player.hasEffect(MobEffects.BLINDNESS)){
                        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 120, 0));
                    }
                }
            }
        }
    }

    private static void onMaxAction(INihilityLevel nihilityLevel, ServerPlayer player, float damage){
        switch(ServerConfig.MAX_NIHILITY_ACTION.get()) {
            case DAMAGE -> {
                player.hurt(DamageSourceRegistry.voidHarm(player.level()), damage);
                break;
            }
            case TELEPORT -> {
                MinecraftServer server = player.getServer();
                if(server != null){
                    ResourceKey<Level> respawnDim = player.getRespawnDimension();
                    ServerLevel targetLevel = server.getLevel(respawnDim);
                    if(targetLevel != null){
                        Optional<Vec3> respawnPos = Optional.empty();
                        if(player.getRespawnPosition() != null){
                            respawnPos = Player.findRespawnPositionAndUseSpawnBlock(
                            targetLevel, player.getRespawnPosition(), player.getRespawnAngle(), player.isRespawnForced(), true);
                        }

                        Vec3 target = respawnPos.orElseGet(() -> {
                            BlockPos worldSpawn = targetLevel.getSharedSpawnPos();
                            return new Vec3(worldSpawn.getX(), worldSpawn.getY(), worldSpawn.getZ());
                        });

                        player.teleportTo(targetLevel, target.x, target.y, target.z, player.getYRot(), player.getXRot());
                        nihilityLevel.setAmount(0);
                    }
                }
                break;
            }

            default -> player.kill();
        }
    }

    public static void clientTick(INihilityLevel nihilityLevel, Player player) {

    }

    private static boolean isDamagingLevel(Player player, float amountClient, float maxClient){
        return player.tickCount % (amountClient < maxClient * criticalLevel ? 40 : 20) == 0;
    }
}
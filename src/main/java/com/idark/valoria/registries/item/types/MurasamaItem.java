package com.idark.valoria.registries.item.types;

import com.idark.valoria.client.gui.overlay.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.interfaces.*;
import com.idark.valoria.util.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.*;
import org.joml.*;
import team.lodestar.lodestone.handlers.screenparticle.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.screen.*;

import java.lang.Math;
import java.util.*;

public class MurasamaItem extends KatanaItem implements IParticleItemEntity, ParticleEmitterHandler.ItemParticleSupplier{

    public MurasamaItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn){
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    public static int getCharge(ItemStack stack){
        CompoundTag nbt = stack.getTag();
        if(nbt == null){
            nbt = new CompoundTag();
            stack.setTag(nbt);
        }
        if(!nbt.contains("charge")){
            nbt.putInt("charge", 0);
            stack.setTag(nbt);
            return 0;
        }else{
            return nbt.getInt("charge");
        }
    }

    public static void setCharge(ItemStack stack, int charge){
        CompoundTag nbt = stack.getTag();
        if(nbt == null){
            nbt = new CompoundTag();
            stack.setTag(nbt);
        }
        nbt.putInt("charge", charge);
        stack.setTag(nbt);
    }

    public static void addCharge(ItemStack stack, int charge){
        setCharge(stack, getCharge(stack) + charge);
    }

    public void onUseTick(@NotNull Level worldIn, @NotNull LivingEntity livingEntityIn, @NotNull ItemStack stack, int count){
        addCharge(stack, 1);
        Player player = (Player)livingEntityIn;
        if(worldIn instanceof ServerLevel srv){
            for(int ii = 0; ii < 1 + Mth.nextInt(RandomSource.create(), 0, 2); ii += 1){
                PacketHandler.sendToTracking(srv, player.getOnPos(), new MurasamaParticlePacket(3F, player.getX(), (player.getY() + (player.getEyeHeight() / 2)), player.getZ(), 235, 0, 25));
            }
        }

        if(getCharge(stack) == 20){
            player.playNotifySound(SoundsRegistry.RECHARGE.get(), SoundSource.PLAYERS, 0.6f, 1);
        }
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(!playerIn.isShiftKeyDown()){
            playerIn.startUsingItem(InteractionHand.MAIN_HAND);
            if(!playerIn.isFallFlying() && playerIn.isUsingItem() && getCharge(itemstack) == 20){
                applyCooldown(playerIn);
            }

            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged){
        if(!slotChanged){
            return false;
        }

        return super.shouldCauseReequipAnimation(oldStack, newStack, true);
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entityLiving, int timeLeft){
        RandomSource rand = level.getRandom();
        Player player = (Player)entityLiving;
        player.awardStat(Stats.ITEM_USED.get(this));
        double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
        double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;
        float dashDistance = 5f;
        if(getCharge(stack) >= 20){
            Vec3 dir = (player.getViewVector(0.0f).scale(2.0d));
            if(dir.x < dashDistance){
                player.push(dir.x, dir.y * 0.25, dir.z);
            }

            for(Item item : ForgeRegistries.ITEMS){
                if(item instanceof KatanaItem){
                    player.getCooldowns().addCooldown(item, 125);
                }
            }

            Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
            List<LivingEntity> hitEntities = new ArrayList<>();
            double maxDistance = distance(dashDistance, level, player);
            for(int i = 0; i < 25; i += 1){
                double locDistance = i * 0.5D;
                double X = Math.sin(pitch) * Math.cos(yaw) * locDistance;
                double Y = Math.cos(pitch) * locDistance;
                double Z = Math.sin(pitch) * Math.sin(yaw) * locDistance;

                level.addParticle(ParticleTypes.WAX_OFF, pos.x + X + (rand.nextDouble() - 0.5D), pos.y + Y, pos.z + Z + (rand.nextDouble() - 0.5D), 0d, 0.05d, 0d);
                if(level instanceof ServerLevel srv){
                    for(int ii = 0; ii < 1 + Mth.nextInt(RandomSource.create(), 0, 2); ii += 1){
                        PacketHandler.sendToTracking(srv, player.getOnPos(), new MurasamaParticlePacket(3F, (pos.x + X), (pos.y + Y), (pos.z + Z), 255, 0, 0));
                    }
                }

                List<Entity> entities = level.getEntitiesOfClass(Entity.class, new AABB(pos.x + X - 0.5D, pos.y + Y - 0.5D, pos.z + Z - 0.5D, pos.x + X + 0.5D, pos.y + Y + 0.5D, pos.z + Z + 0.5D));
                for(Entity entity : entities){
                    if(entity instanceof LivingEntity enemy){
                        if(!hitEntities.contains(enemy) && (!enemy.equals(player))){
                            hitEntities.add(enemy);
                        }
                    }
                }

                if(locDistance >= maxDistance){
                    break;
                }
            }

            float ii = 1F;
            for(LivingEntity entity : hitEntities){
                entity.hurt(level.damageSources().playerAttack(player), (float)((player.getAttributeValue(Attributes.ATTACK_DAMAGE) * (double)ii) + EnchantmentHelper.getSweepingDamageRatio(player) + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
                entity.knockback(0.4F, player.getX() - entity.getX(), player.getZ() - entity.getZ());
                if(EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) > 0){
                    int i = EnchantmentHelper.getFireAspect(player);
                    entity.setSecondsOnFire(i * 4);
                }

                ii = ii - (1F / (hitEntities.size() * 2));
            }

            if(!player.isCreative()){
                stack.hurtAndBreak(hitEntities.size(), player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }

            for(int i = 0; i < 4; i++){
                level.addParticle(ParticleTypes.POOF, player.getX() + (rand.nextDouble() - 0.5D), player.getY(), player.getZ() + (rand.nextDouble() - 0.5D), 0d, 0.05d, 0d);
            }

            hitEntities.clear();
            double locYaw = 0;
            double locPitch = 0;
            double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * maxDistance;
            double Y = Math.cos(locPitch + pitch) * maxDistance;
            double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * maxDistance;

            List<Entity> entities = level.getEntitiesOfClass(Entity.class, new AABB(pos.x + X - 3D, pos.y + Y - 3D, pos.z + Z - 2.5D, pos.x + X + 3D, pos.y + Y + 3D, pos.z + Z + 3D));
            for(Entity entity : entities){
                if(entity instanceof LivingEntity enemy){
                    if(!hitEntities.contains(enemy) && (!enemy.equals(player))){
                        hitEntities.add(enemy);
                    }
                }
            }

            for(LivingEntity entity : hitEntities){
                entity.hurt(level.damageSources().generic(), (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE) * ii) + EnchantmentHelper.getSweepingDamageRatio(player));
                entity.knockback(0.4F, player.getX() - entity.getX(), player.getZ() - entity.getZ());
                if(EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, entity) > 0){
                    int i = EnchantmentHelper.getFireAspect(player);
                    entity.setSecondsOnFire(i * 4);
                }
            }

            level.playSound(null, player.getOnPos(), SoundsRegistry.SWIFTSLICE.get(), SoundSource.PLAYERS, 1.0F, 1F);
            if(level.isClientSide) DashOverlayRender.showDashOverlay();
        }

        setCharge(stack, 0);
    }

    @Override
    public void spawnParticles(Level level, ItemEntity entity){
        RandomSource rand = level.getRandom();
        double X = ((rand.nextDouble() - 0.5D) * 0.3f);
        double Y = ((rand.nextDouble() - 0.5D) + 0.3f);
        double Z = ((rand.nextDouble() - 0.5D) * 0.3f);

        double dX = -X;
        double dY = -Y;
        double dZ = -Z;
        int count = Mth.nextInt(rand, 0, 1);
        for(int ii = 0; ii < count; ii += 1){
            double yaw = Math.atan2(dZ, dX);
            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;
            double XX = Math.sin(pitch) * Math.cos(yaw) * (float)(rand.nextDouble() * 0.025F) / (ii + 1), YY = Math.sin(pitch) * Math.sin(yaw) * (float)(rand.nextDouble() * 0.025F) / (ii + 1), ZZ = Math.cos(pitch) * (float)(rand.nextDouble() * 0.025F) / (ii + 1);
            Vec3 pos = new Vec3(entity.getX() + X, entity.getY() + Y, entity.getZ() + Z);

            ParticleEffects.itemParticles(level, pos, ColorParticleData.create(Pal.strongRed, Pal.moderateViolet).build()).getBuilder().setMotion(XX, YY, ZZ).spawn(level, pos.x,  pos.y, pos.z);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
        ScreenParticleRegistry.spawnCoreParticles(target, ColorParticleData.create(Pal.strongRed, Pal.moderateViolet).build());
    }
}
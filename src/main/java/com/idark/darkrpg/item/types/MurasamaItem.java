package com.idark.darkrpg.item.types;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.idark.darkrpg.block.ModBlocks;
import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.util.ModSoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;

import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

@SuppressWarnings("ALL")
public class MurasamaItem extends SwordItem {

    static Random rand = new Random();

    public MurasamaItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    /**
    * Some sounds taken from the CalamityMod (Terraria) in a https://calamitymod.fandom.com/wiki/Category:Sound_effects
    */
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    public void onUseTick(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
        Player player = (Player)livingEntityIn;
        addCharge(stack, 1);
        //for (int ii = 0; ii < 1 + MathHelper.nextInt(rand, 0,2); ii += 1) {
        //    bloodParticle(new Vector3d(player.getX(), player.getY() + (player.getEyeHeight() / 2), player.getZ()), 5F, (float) (rand.nextDouble() * 0.1F), worldIn);
        //}
        if (getCharge(stack) == 20) {
            player.playNotifySound(ModSoundRegistry.RECHARGE.get(), SoundSource.PLAYERS,1,1);
        }
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    public int getUseDuration(ItemStack stack) {
        return 78000;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        if (!slotChanged) {
            return false;
        }
        return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }

    /**
     *     Sounds taken from the CalamityMod (Terraria) in a https://calamitymod.fandom.com/wiki/Category:Sound_effects
     */
    /*public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        PlayerEntity player = (PlayerEntity)entityLiving;

        if (getCharge(stack) >= 20) {
            Vector3d dir = MathUtils.direction(player).scale(1.5F);
            player.push(dir.x,dir.y*0.25,dir.z);
            player.getCooldowns().addCooldown(this, 100);
            player.awardStat(Stats.ITEM_USED.get(this));

            Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
            List<LivingEntity> hitEntities = new ArrayList<LivingEntity>();
            double maxDistance = distance(pos, worldIn, player);

            for (int i = 0; i < 20; i += 1) {
                double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
                double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;

                double locYaw = 0;
                double locPitch = 0;
                double locDistance = i * 0.5D;

                double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * locDistance;
                double Y = Math.cos(locPitch + pitch) * locDistance;
                double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * locDistance;

                worldIn.addParticle(ParticleTypes.POOF, pos.x + X + (rand.nextDouble() - 0.5D), pos.y + Y, pos.z + Z + (rand.nextDouble() - 0.5D), 0d, 0.05d, 0d);
                for (int ii = 0; ii < 1 + MathHelper.nextInt(rand, 0,2); ii += 1) {
                    bloodParticle(new Vector3d(pos.x + X, pos.y + Y, pos.z + Z), 3F, (float) (rand.nextDouble() * 0.05F), worldIn);
                }
                List<Entity> entities = worldIn.getEntitiesOfClass(Entity.class,  new AxisAlignedBB(pos.x + X - 0.5D,pos.y + Y - 0.5D,pos.z + Z - 0.5D,pos.x + X + 0.5D,pos.y + Y + 0.5D,pos.z + Z + 0.5D));
                for (Entity entity : entities) {
                    if (entity instanceof  LivingEntity) {
                        LivingEntity enemy = (LivingEntity)entity;
                        if (!hitEntities.contains(enemy) && (!enemy.equals(player))) {
                            hitEntities.add(enemy);
                        }
                    }
                }

                if (locDistance >= maxDistance) {
                    break;
                }
            }

            int hits = hitEntities.size();
            float ii = 1F;

            for (LivingEntity entity : hitEntities) {
                entity.hurt(DamageSource.GENERIC, (float) (player.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * ii) + EnchantmentHelper.getSweepingDamageRatio(player));
                entity.knockback(0.4F, player.getX() - entity.getX(), player.getZ() - entity.getZ());
                if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) > 0) {
                    int i = EnchantmentHelper.getFireAspect(player);
                    entity.setSecondsOnFire(i * 4);
                }
                ii = ii - (1F / (hits * 2));
            }

            if (!player.isCreative()) {
                stack.hurtAndBreak(hits, player, (p_220045_0_) -> {p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);});
            }

            for (int i = 0;i<4;i++) {
                worldIn.addParticle(ParticleTypes.POOF, player.getX() + (rand.nextDouble() - 0.5D), player.getY(), player.getZ() + (rand.nextDouble() - 0.5D), 0d, 0.05d, 0d);
            }
            worldIn.playSound(player, player.blockPosition(), ModSoundRegistry.SWIFTSLICE.get(), SoundCategory.AMBIENT, 10f, 1f);
            DashOverlayRender.isDash = true;

            hitEntities.clear();

            double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
            double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;

            double locYaw = 0;
            double locPitch = 0;
            double locDistance = maxDistance;

            double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * locDistance;
            double Y = Math.cos(locPitch + pitch) * locDistance;
            double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * locDistance;

            for (int iii = 0; iii < 25; iii += 1) {
                bloodParticle(new Vector3d(pos.x + X, pos.y + Y, pos.z + Z), 5F, (float) (rand.nextDouble() * 0.05F), worldIn);
                worldIn.addParticle(ParticleTypes.POOF, pos.x + X + ((rand.nextDouble() - 0.5D) * 3), pos.y + Y + ((rand.nextDouble() - 0.5D) * 3), pos.z + Z + ((rand.nextDouble() - 0.5D) * 3), 0d, 0.05d, 0d);
            }
            for (int iii = 0; iii < 10; iii += 1) {
                worldIn.addParticle(ParticleTypes.SWEEP_ATTACK, pos.x + X + ((rand.nextDouble() - 0.5D) * 2.5F), pos.y + Y + ((rand.nextDouble() - 0.5D) * 2.5F), pos.z + Z + ((rand.nextDouble() - 0.5D) * 2.5F), 0d, 0d, 0d);
            }
            List<Entity> entities = worldIn.getEntitiesOfClass(Entity.class,  new AxisAlignedBB(pos.x + X - 3D,pos.y + Y - 3D,pos.z + Z - 2.5D,pos.x + X + 3D,pos.y + Y + 3D,pos.z + Z + 3D));
            for (Entity entity : entities) {
                if (entity instanceof  LivingEntity) {
                    LivingEntity enemy = (LivingEntity)entity;
                    if (!hitEntities.contains(enemy) && (!enemy.equals(player))) {
                        hitEntities.add(enemy);
                    }
                }
            }

            for (LivingEntity entity : hitEntities) {
                entity.hurt(DamageSource.GENERIC, (float) (player.getAttribute(Attributes.ATTACK_DAMAGE).getValue()) + EnchantmentHelper.getSweepingDamageRatio(player));
                entity.knockback(0.6F, player.getX() - entity.getX(), player.getZ() - entity.getZ());
                if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) > 0) {
                    int i = EnchantmentHelper.getFireAspect(player);
                    entity.setSecondsOnFire(i * 4);
                }
            }
        }
        setCharge(stack, 0);
    }

    public static double distance(Vector3d pos1, World worldIn, PlayerEntity player) {
        double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
        double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;

        double locYaw = 0;
        double locPitch = 0;
        double locDistance = 10D;

        double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * locDistance;
        double Y = Math.cos(locPitch + pitch) * locDistance;
        double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * locDistance;

        BlockRayTraceResult ray = worldIn.clip(new RayTraceContext(pos1, new Vector3d(pos1.x + X, pos1.y + Y, pos1.z + Z), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
        X = ray.getLocation().x();
        Y = ray.getLocation().y();
        Z = ray.getLocation().z();

        return Math.sqrt((X - pos1.x) * (X - pos1.x) + (Y - pos1.y)*(Y - pos1.y) + (Z - pos1.z)*(Z - pos1.z));
    }

    public static void bloodParticle(Vector3d pos, float distance, float speed, World worldIn) {
        double X = ((rand.nextDouble() - 0.5D) * distance);
        double Y = ((rand.nextDouble() - 0.5D) * distance);
        double Z = ((rand.nextDouble() - 0.5D) * distance);

        double dX = -X;
        double dY = -Y;
        double dZ = -Z;

        int count = 1 + MathHelper.nextInt(rand, 0,2);

        for (int ii = 0; ii < count; ii += 1) {
            double yaw = Math.atan2(dZ, dX);
            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

            double XX = Math.sin(pitch) * Math.cos(yaw) * speed / (ii + 1);
            double YY = Math.sin(pitch) * Math.sin(yaw) * speed / (ii + 1);
            double ZZ = Math.cos(pitch) * speed / (ii + 1);

            worldIn.addParticle(new RedstoneParticleData(1F, 0F, 0F, 1F), pos.x + X, pos.y + Y, pos.z + Z, XX, YY, ZZ);
        }
    }*/

    public static int getCharge(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt==null) {
            nbt = new CompoundTag();
            stack.setTag(nbt);
        }
        if (!nbt.contains("charge")) {
            nbt.putInt("charge", 0);
            stack.setTag(nbt);
            return 0;
        } else {
            return nbt.getInt("charge");
        }
    }

    public static void setCharge(ItemStack stack, int charge) {
        CompoundTag nbt = stack.getTag();
        if (nbt==null) {
            nbt = new CompoundTag();
            stack.setTag(nbt);
        }
        nbt.putInt("charge", charge);
        stack.setTag(nbt);
    }

    public static void addCharge(ItemStack stack, int charge) {
        setCharge(stack, getCharge(stack) + charge);
    }
}
package com.idark.darkrpg.item.types;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.idark.darkrpg.block.ModBlocks;
import com.idark.darkrpg.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
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
public class PhantomItem extends SwordItem {
    Random rand = new Random();
	private Minecraft client;
	//private ClientWorld world;
    public PhantomItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    public int getUseDuration(ItemStack stack) {
        return 30;
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a https://calamitymod.fandom.com/wiki/Category:Sound_effects
     */
    /*public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        PlayerEntity player = (PlayerEntity)entityLiving;
        player.getCooldowns().addCooldown(this, 10);
        player.awardStat(Stats.ITEM_USED.get(this));

        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        List<LivingEntity> hitEntities = new ArrayList<LivingEntity>();
        for (int i = 0; i < 360; i += 10) {
            float yawDouble = 0;
            if (i <= 180) {
                yawDouble = ((float) i) / 180F;
            } else {
                yawDouble = 1F - ((((float) i) - 180F) / 180F);
            }
            hitDirection(worldIn, player, hitEntities, pos, 0, player.getRotationVector().y + i, 4);
        }

        worldIn.addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() + ((rand.nextDouble() - 0.7D) * 1), player.getY() + ((rand.nextDouble() - 1D) * 1), player.getZ() + ((rand.nextDouble() - 0.5D) * 1), 0.05d * ((rand.nextDouble() - 0.5D) * 1), 0.05d * ((rand.nextDouble() - 0.5D) * 1), 0.05d * ((rand.nextDouble() - 0.5D) * 1));
        worldIn.addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() + ((rand.nextDouble() - 0.5D) * 2), player.getY() + ((rand.nextDouble() - 0.5D) * 2), player.getZ() + ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2));
        worldIn.playSound(player, player.blockPosition(), SoundEvents.TOTEM_USE, SoundCategory.AMBIENT, 1.0F, 1.0F);
        Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(ModItems.ETERNITY.get()));
        if (!player.isCreative()) {
            stack.hurtAndBreak(35, player, (p_220045_0_) -> {p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);});

        worldIn.playSound(player, player.blockPosition(), ModSoundRegistry.BLAZECHARGE.get(), SoundCategory.AMBIENT, 10f, 1f);
		}
	}

    public void hitDirection(World worldIn, PlayerEntity player, List<LivingEntity> hitEntities, Vector3d pos, float pitchRaw, float yawRaw, float distance) {
        double pitch = ((pitchRaw + 90) * Math.PI) / 180;
        double yaw = ((yawRaw + 90) * Math.PI) / 180;

        double locYaw = 0;
        double locPitch = 0;
        double locDistance = distance;

        double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * locDistance;
        double Y = Math.cos(locPitch + pitch) * locDistance;
        double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * locDistance;
        BlockRayTraceResult ray = worldIn.clip(new RayTraceContext(pos, new Vector3d(pos.x + X, pos.y + Y, pos.z + Z), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
        X = ray.getLocation().x();
        Y = ray.getLocation().y();
        Z = ray.getLocation().z();
		
		List<Entity> entities = worldIn.getEntitiesOfClass(Entity.class,  new AxisAlignedBB(X - 2D,Y - 2D,Z - 2D,X + 2D,Y + 2D,Z + 2D));
        for (Entity entity : entities) {
            if (entity instanceof  LivingEntity) {
                LivingEntity enemy = (LivingEntity)entity;
                if (!hitEntities.contains(enemy) && (!enemy.equals(player))) {
                    hitEntities.add(enemy);
                }
            }
        }

        locDistance = distance(pos, new Vector3d(X, Y, Z));

        X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * locDistance * 0.75F;
        Y = Math.cos(locPitch + pitch) * locDistance * 0.75F;
        Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * locDistance * 0.75F;
        worldIn.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.x + X + ((rand.nextDouble() - 0.5D) * 0.2F), pos.y + Y + ((rand.nextDouble() - 0.5D) * 0.2F), pos.z + Z + ((rand.nextDouble() - 0.5D) * 0.2F), 0d, 0d, 0d);
    }

    public static double distance(Vector3d pos1, Vector3d pos2){
        return Math.sqrt((pos2.x - pos1.x) * (pos2.x - pos1.x) + (pos2.y - pos1.y)*(pos2.y - pos1.y) + (pos2.z - pos1.z)*(pos2.z - pos1.z));
    }*/
}
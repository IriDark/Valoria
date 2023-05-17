package com.idark.darkrpg.item.types;

import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.util.ModSoundRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.*;
import net.minecraft.client.world.ClientWorld;;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PhantomItem extends SwordItem {
    Random rand = new Random();
	private Minecraft client;
	private ClientWorld world;
    public PhantomItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

	private static ItemStack getEternityItem(PlayerEntity player, Hand handIn) {
		ItemStack stack = player.getHeldItem(handIn);
		if (stack.getItem() == ModItems.ETERNITY.get()) {
			return stack;
		}

		return new ItemStack(ModItems.ETERNITY.get());
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (itemstack.getDamage() >= itemstack.getMaxDamage() - 1) {
            return ActionResult.resultFail(itemstack);
        } else {
            playerIn.setActiveHand(handIn);
            return ActionResult.resultConsume(itemstack);
        }
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    public int getUseDuration(ItemStack stack) {
        return 10;
    }

    //Sounds taken from the CalamityMod (Terraria) in a https://calamitymod.fandom.com/wiki/Category:Sound_effects
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft, Hand handIn) {
        PlayerEntity player = (PlayerEntity)entityLiving;
        player.getCooldownTracker().setCooldown(this, 625);
        player.addStat(Stats.ITEM_USED.get(this));

        Vector3d pos = new Vector3d(player.getPosX(), player.getPosY() + player.getEyeHeight(), player.getPosZ());
        List<LivingEntity> hitEntities = new ArrayList<LivingEntity>();

        for (int i = 0; i < 360; i += 10) {
            float yawDouble = 0;
            if (i <= 180) {
                yawDouble = ((float) i) / 180F;
            } else {
                yawDouble = 1F - ((((float) i) - 180F) / 180F);
            }
            hitDirection(worldIn, player, hitEntities, pos, 0, player.getPitchYaw().y + i, 4);
        }

        float damage = (float) (player.getAttribute(Attributes.ATTACK_DAMAGE).getValue()) + EnchantmentHelper.getSweepingDamageRatio(player);

        worldIn.addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getPosX() + ((rand.nextDouble() - 0.5D) * 2), player.getPosY() + ((rand.nextDouble() - 0.5D) * 2), player.getPosZ() + ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2));
        Minecraft.getInstance().gameRenderer.displayItemActivation(getEternityItem(player, handIn));
        for (LivingEntity entity : hitEntities) {
            entity.attackEntityFrom(DamageSource.GENERIC, damage);
            entity.applyKnockback(0.4F, player.getPosX() - entity.getPosX(), player.getPosZ() - entity.getPosZ());
            if (EnchantmentHelper.getFireAspectModifier(player) > 0) {
                int i = EnchantmentHelper.getFireAspectModifier(player);
                entity.setFire(i * 4);
            }
            if (!player.isCreative()) {
                stack.damageItem(hitEntities.size(), player, (p_220045_0_) -> {p_220045_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);});
            }
        }

        worldIn.playSound(player, player.getPosition(), ModSoundRegistry.BLAZECHARGE.get(), SoundCategory.AMBIENT, 10f, 1f);
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
        BlockRayTraceResult ray = worldIn.rayTraceBlocks(new RayTraceContext(pos, new Vector3d(pos.x + X, pos.y + Y, pos.z + Z), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
        X = ray.getHitVec().getX();
        Y = ray.getHitVec().getY();
        Z = ray.getHitVec().getZ();
		
		List<Entity> entities = worldIn.getEntitiesWithinAABB(Entity.class,  new AxisAlignedBB(X - 2D,Y - 2D,Z - 2D,X + 2D,Y + 2D,Z + 2D));
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
    }
}
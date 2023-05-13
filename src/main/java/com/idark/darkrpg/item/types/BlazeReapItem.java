package com.idark.darkrpg.item.types;

import com.idark.darkrpg.util.ModSoundRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlazeReapItem extends PickaxeItem {

    Random rand = new Random();

    public BlazeReapItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant){
        return enchant.type != EnchantmentType.BREAKABLE && enchant.type == EnchantmentType.WEAPON || enchant.type == EnchantmentType.DIGGER;
    }

    //Sounds taken from the CalamityMod (Terraria) in a https://calamitymod.fandom.com/wiki/Category:Sound_effects
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (isCharged(itemstack) == 0) {
            List<ItemStack> items = playerIn.container.getInventory();
            int gunpowder = 0;
            boolean canCharge = false;

            if (!playerIn.isCreative()) {
                for (ItemStack item : items) {
                    if (item.getItem().equals(Items.GUNPOWDER)) {
                        gunpowder = gunpowder + item.getCount();
                        if (gunpowder >= 5) {
                            canCharge = true;
                            break;
                        }
                    }
                }
            } else {
                canCharge = true;
            }

            if (canCharge) {
                gunpowder = 5;

                if (!playerIn.isCreative()) {
                    for (ItemStack item : items) {
                        if (item.getItem().equals(Items.GUNPOWDER)) {
                            if (gunpowder - item.getCount() >= 0) {
                                gunpowder = gunpowder - item.getCount();
                                playerIn.inventory.deleteStack(item);
                            } else {
                                item.setCount(item.getCount() - gunpowder);
                                gunpowder = 0;
                            }

                            if (gunpowder <= 0) {
                                break;
                            }
                        }
                    }
                }

                setCharge(itemstack, 1);
                playerIn.getCooldownTracker().setCooldown(this, 20);
                worldIn.playSound(playerIn, playerIn.getPosition(), ModSoundRegistry.BLAZECHARGE.get(), SoundCategory.AMBIENT, 10f, 1f);
                playerIn.addStat(Stats.ITEM_USED.get(this));
            }
            return new ActionResult<ItemStack>(ActionResultType.SUCCESS, itemstack);
        } else if (isCharged(itemstack) == 1) {
            setCharge(itemstack, 0);
            playerIn.getCooldownTracker().setCooldown(this, 50);
            playerIn.addStat(Stats.ITEM_USED.get(this));

            Vector3d pos = new Vector3d(playerIn.getPosX(), playerIn.getPosY() + playerIn.getEyeHeight(), playerIn.getPosZ());

            double pitch = ((playerIn.getPitchYaw().x + 90) * Math.PI) / 180;
            double yaw = ((playerIn.getPitchYaw().y + 90) * Math.PI) / 180;

            double locYaw = 0;
            double locPitch = 0;
            double locDistance = 5D;

            double X = Math.sin(locPitch + pitch) * Math.cos(locYaw + yaw) * locDistance;
            double Y = Math.cos(locPitch + pitch) * locDistance;
            double Z = Math.sin(locPitch + pitch) * Math.sin(locYaw + yaw) * locDistance;

            BlockRayTraceResult ray = worldIn.rayTraceBlocks(new RayTraceContext(pos, new Vector3d(pos.x + X, pos.y + Y, pos.z + Z), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, playerIn));
            X = ray.getHitVec().getX() - pos.x;
            Y = ray.getHitVec().getY() - pos.y;
            Z = ray.getHitVec().getZ() - pos.z;

            List<Entity> entities = worldIn.getEntitiesWithinAABB(Entity.class,  new AxisAlignedBB(pos.x + X - 3D,pos.y + Y - 3D,pos.z + Z - 3D,pos.x + X + 3D,pos.y + Y + 3D,pos.z + Z + 3D));
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity) {
                    LivingEntity enemy = (LivingEntity)entity;
                    if (!enemy.equals(playerIn)) {
                        enemy.attackEntityFrom(DamageSource.GENERIC.setExplosion(), 10F);
                        enemy.applyKnockback(0.6F, playerIn.getPosX() + X - entity.getPosX(), playerIn.getPosZ() + Z - entity.getPosZ());
                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, itemstack) > 0) {
                            int i = EnchantmentHelper.getFireAspectModifier(playerIn);
                            enemy.setFire(i * 4);
                        }
                    }
                }
            }

            playerIn.applyKnockback(1.2F, X, Z);
            if (!playerIn.isCreative()) {
                itemstack.damageItem(10, playerIn, (p_220045_0_) -> {p_220045_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);});
            }

            for (int i = 0; i < 10; i++) {
                worldIn.addParticle(ParticleTypes.LARGE_SMOKE, pos.x + X + ((rand.nextDouble() - 0.5D) * 3), pos.y + Y + ((rand.nextDouble() - 0.5D) * 3), pos.z + Z + ((rand.nextDouble() - 0.5D) * 3), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2));
            }
            for (int i = 0; i < 25; i++) {
                worldIn.addParticle(ParticleTypes.FLAME, pos.x + X + ((rand.nextDouble() - 0.5D) * 3), pos.y+ Y + ((rand.nextDouble() - 0.5D) * 3), pos.z + Z + ((rand.nextDouble() - 0.5D) * 3), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2));
            }
            worldIn.addParticle(ParticleTypes.EXPLOSION_EMITTER, pos.x + X, pos.y + Y, playerIn.getPosZ() + Z, 0d, 0d, 0d);
            worldIn.playSound(playerIn, playerIn.getPosition().add(X, Y + playerIn.getEyeHeight(), Z), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 10f, 1f);


            return new ActionResult<ItemStack>(ActionResultType.SUCCESS, itemstack);
        }

        return new ActionResult<ItemStack>(ActionResultType.PASS, itemstack);
    }

    public static int isCharged(ItemStack stack) {
        CompoundNBT nbt = stack.getTag();
        if (nbt==null) {
            nbt = new CompoundNBT();
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
        CompoundNBT nbt = stack.getTag();
        if (nbt==null) {
            nbt = new CompoundNBT();
            stack.setTag(nbt);
        }
        nbt.putInt("charge", charge);
        stack.setTag(nbt);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
        super.addInformation(stack, world, tooltip, flags);
        tooltip.add(1, new TranslationTextComponent("tooltip.darkrpg.familiar").mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
        tooltip.add(2, new StringTextComponent("                "));
        tooltip.add(3, new TranslationTextComponent("tooltip.darkrpg.blazereap").mergeStyle(TextFormatting.GRAY));
		tooltip.add(4, new TranslationTextComponent("tooltip.darkrpg.rmb").mergeStyle(TextFormatting.GREEN));
	}
}
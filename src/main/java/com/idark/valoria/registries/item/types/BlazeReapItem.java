package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;

import java.util.*;

//TODO: Probably rework
public class BlazeReapItem extends PickaxeItem implements Vanishable{
    Random rand = new Random();

    public BlazeReapItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder){
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    public static int isCharged(ItemStack stack){
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

    public static String getModeString(ItemStack stack){
        if(isCharged(stack) == 1){
            return "tooltip.valoria.rmb";
        }else{
            return "tooltip.valoria.rmb_shift";
        }
    }

    public int getUseDuration(ItemStack stack){
        return 72000;
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant){
        return enchant.category == EnchantmentCategory.WEAPON || enchant.category == EnchantmentCategory.DIGGER || enchant.category == EnchantmentsRegistry.BLAZE;
    }

    /**
     * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
     */
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand){
        ItemStack itemstack = player.getItemInHand(hand);
        if(player.isShiftKeyDown()){
            if(isCharged(itemstack) == 0){
                List<ItemStack> items = player.inventoryMenu.getItems().stream().filter(item -> item.is(Items.GUNPOWDER)).toList();
                int gunpowder = 0;
                boolean canCharge = false;
                if(!player.isCreative()){
                    for(ItemStack item : items){
                        gunpowder = gunpowder + item.getCount();
                        if(gunpowder >= 5){
                            canCharge = true;
                        }
                    }
                }else{
                    canCharge = true;
                }

                if(canCharge){
                    gunpowder = 5;
                    if(!player.isCreative()){
                        for(ItemStack item : items){
                            if(gunpowder - item.getCount() >= 0){
                                gunpowder = gunpowder - item.getCount();
                                player.getInventory().removeItem(item);
                            }else{
                                item.setCount(item.getCount() - gunpowder);
                                gunpowder = 0;
                            }

                            if(gunpowder <= 0){
                                break;
                            }
                        }
                    }

                    setCharge(itemstack, 1);
                    player.getCooldowns().addCooldown(this, 20);
                    level.playSound(null, player.blockPosition(), SoundsRegistry.BLAZECHARGE.get(), SoundSource.AMBIENT, 1f, 1f);
                    player.awardStat(Stats.ITEM_USED.get(this));
                }else{
                    player.displayClientMessage(Component.translatable("tooltip.valoria.recharge").withStyle(ChatFormatting.GRAY), true);
                }

                for(int i = 0; i < 6; ++i){
                    double d0 = rand.nextGaussian() * 0.02D;
                    double d1 = rand.nextGaussian() * 0.02D;
                    double d2 = rand.nextGaussian() * 0.02D;
                    level.addParticle(ParticleTypes.FLAME, player.getRandomX(1.0D), player.getRandomY() - 0.5D, player.getRandomZ(1.0D), d0, d1, d2);
                }
            }

            return InteractionResultHolder.pass(itemstack);
        }else if(isCharged(itemstack) == 1){
            setCharge(itemstack, 0);
            player.getCooldowns().addCooldown(this, 50);
            player.awardStat(Stats.ITEM_USED.get(this));
            Vec3 pos = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());

            double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
            double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;
            double X = Math.sin(pitch) * Math.cos(yaw) * 15;
            double Y = Math.cos(pitch) * 15;
            double Z = Math.sin(pitch) * Math.sin(yaw) * 15;
            Vec3 playerPos = player.getEyePosition();
            Vec3 EndPos = (player.getViewVector(0.0f).scale(20.0d));
            if(ProjectileUtil.getEntityHitResult(player, playerPos, EndPos, new AABB(pos.x + X - 3D, pos.y + Y - 3D, pos.z + Z - 3D, pos.x + X + 3D, pos.y + Y + 3D, pos.z + Z + 3D), (e) -> true, 15) == null){
                HitResult hitresult = ValoriaUtils.getHitResult(playerPos, player, (e) -> true, EndPos, level);
                if(hitresult != null){
                    switch(hitresult.getType()){
                        case BLOCK:
                            X = hitresult.getLocation().x() - pos.x;
                            Y = hitresult.getLocation().y() - pos.y;
                            Z = hitresult.getLocation().z() - pos.z;
                            break;
                        case ENTITY:
                            Entity entity = ((EntityHitResult)hitresult).getEntity();
                            X = entity.getX() - pos.x;
                            Y = entity.getY() - pos.y;
                            Z = entity.getZ() - pos.z;
                            break;
                        case MISS:
                            break;
                    }
                }
            }

            if(!level.isClientSide){
                if(EnchantmentHelper.getTagEnchantmentLevel(EnchantmentsRegistry.EXPLOSIVE_FLAME.get(), itemstack) > 0){
                    level.explode(player, pos.x + X, pos.y + Y, pos.z + Z, 4F, Level.ExplosionInteraction.TNT);
                }else{
                    level.explode(player, pos.x + X, pos.y + Y, pos.z + Z, 4F, Level.ExplosionInteraction.NONE);
                }
            }

            for(int i = 0; i < 12; i++){
                level.addParticle(ParticleTypes.LARGE_SMOKE, pos.x + X + ((rand.nextDouble() - 0.5D) * 3), pos.y + Y + ((rand.nextDouble() - 0.5D) * 3), pos.z + Z + ((rand.nextDouble() - 0.5D) * 3), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2));
                level.addParticle(ParticleTypes.FLAME, pos.x + X + ((rand.nextDouble() - 0.5D) * 3), pos.y + Y + ((rand.nextDouble() - 0.5D) * 3), pos.z + Z + ((rand.nextDouble() - 0.5D) * 3), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2), 0.05d * ((rand.nextDouble() - 0.5D) * 2));
            }

            return InteractionResultHolder.success(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(1, Component.translatable("tooltip.valoria.familiar").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        tooltip.add(2, Component.empty());
        tooltip.add(3, Component.translatable("tooltip.valoria.blazereap").withStyle(ChatFormatting.GRAY));
        tooltip.add(4, Component.translatable(getModeString(stack)).withStyle(ChatFormatting.GREEN));
    }
}
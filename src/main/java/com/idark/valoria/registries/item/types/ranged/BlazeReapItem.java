package com.idark.valoria.registries.item.types.ranged;

import com.idark.valoria.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.client.render.screenshake.*;
import pro.komaru.tridot.util.math.*;

import java.util.*;

public class BlazeReapItem extends ValoriaPickaxe implements Vanishable, OverlayRenderItem{
    private static final ResourceLocation BAR = new ResourceLocation(Valoria.ID, "textures/gui/overlay/blazecharge_bar.png");

    public BlazeReapItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder){
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    public static String getModeString(ItemStack stack){
        CompoundTag nbt = stack.getOrCreateTag();
        if(nbt.contains("charge")){
            if(nbt.getInt("charge") == 1){
                return "tooltip.valoria.rmb";
            }
        }

        return "tooltip.valoria.rmb_shift";
    }

    public int getUseDuration(ItemStack stack){
        return 72000;
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant){
        return enchant.category == EnchantmentCategory.WEAPON || enchant.category == EnchantmentCategory.DIGGER || enchant.category == EnchantmentsRegistry.BLAZE;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand){
        ItemStack weapon = player.getItemInHand(hand);
        ItemStack ammo = ValoriaUtils.getProjectile(player, weapon);
        RandomSource rand = level.getRandom();
        CompoundTag nbt = weapon.getOrCreateTag();
        boolean hasAmmo = !ammo.isEmpty();
        boolean flag = ammo.getItem() instanceof GunpowderCharge;
        if(level instanceof ServerLevel serverLevel){
            if(player.isShiftKeyDown()){
                recharge(player, serverLevel, nbt, hasAmmo, ammo, rand);
                return InteractionResultHolder.pass(weapon);
            }else if(nbt.getInt("charge") == 1){
                nbt.putInt("charge", 0);
                player.getCooldowns().addCooldown(this, 40);
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
                    HitResult hitresult = Utils.Hit.hitResult(playerPos, player, (e) -> true, EndPos, level);
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

                float radius = flag ? ((GunpowderCharge)ammo.getItem()).getRadius() : 3f;
                float damage = flag ? ((GunpowderCharge)ammo.getItem()).getDamage() : 25f;
                float knockback = flag ? ((GunpowderCharge)ammo.getItem()).getKnockback() : 0.5f;
                if(EnchantmentHelper.getTagEnchantmentLevel(EnchantmentsRegistry.EXPLOSIVE_FLAME.get(), weapon) > 0){
                    level.explode(player, pos.x + X, pos.y + Y, pos.z + Z, radius, Level.ExplosionInteraction.TNT);
                }else{
                    Utils.Hit.explosion(player, weapon, pos, new Vec3(X, Y, Z), radius, damage, knockback);
                }

                ScreenshakeHandler.add(new PositionedScreenshakeInstance(5, pro.komaru.tridot.util.phys.Vec3.from(player.getEyePosition()), 15, 30).intensity(radius * 0.85f).interp(Interp.bounce));
                double offset = ((rand.nextDouble() - 0.5D) * radius);
                double speed = 0.05d * offset;
                serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, pos.x + X, pos.y + Y, pos.z + Z, 12, offset, offset,offset, speed);
                serverLevel.sendParticles(ParticleTypes.FLAME, pos.x + X, pos.y + Y, pos.z + Z, 12, offset, offset,offset, speed);
                return InteractionResultHolder.success(weapon);
            }
        }

        return InteractionResultHolder.pass(weapon);
    }

    private void recharge(Player player, ServerLevel serverLevel, CompoundTag nbt, boolean hasAmmo, ItemStack ammo, RandomSource rand){
        if(nbt.getInt("charge") == 0){
            if(hasAmmo){
                if(!player.isCreative()){
                    ammo.shrink(1);
                }

                nbt.putInt("charge", 1);
                player.getCooldowns().addCooldown(this, 20);
                serverLevel.playSound(null, player.blockPosition(), SoundsRegistry.BLAZECHARGE.get(), SoundSource.AMBIENT, 1f, 1f);
                player.awardStat(Stats.ITEM_USED.get(this));
            }else{
                player.displayClientMessage(Component.translatable("tooltip.valoria.recharge").withStyle(ChatFormatting.GRAY), true);
            }

            double d0 = rand.nextGaussian() * 0.02D;
            double d2 = rand.nextGaussian() * 0.02D;
            serverLevel.sendParticles(ParticleTypes.FLAME, player.getRandomX(1.0D), player.getRandomY() - 0.5D, player.getRandomZ(1.0D), 6, d0, 0, d2, 0.025);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static List<ItemStack> getAmmunition(){
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        List<ItemStack> items = player.getInventory().items;
        ArrayList<ItemStack> ammoItems = new ArrayList<>();
        for(ItemStack stack : items){
            if(stack.getItem() instanceof GunpowderCharge){
                ammoItems.add(stack);
            }
        }

        return ammoItems;
    }

    @Override
    public ResourceLocation getTexture(){
        return BAR;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(CompoundTag tag, GuiGraphics gui, int offsetX, int offsetY){
        gui.pose().pushPose();
        Player plr = Minecraft.getInstance().player;
        if(plr != null){
            boolean flag = plr.getOffhandItem().getItem() instanceof OverlayRenderItem;
            int xCord = ClientConfig.MAGMA_CHARGE_BAR_X.get();
            int yCord = flag ? ClientConfig.MAGMA_CHARGE_BAR_Y.get() + 35 : ClientConfig.MAGMA_CHARGE_BAR_Y.get();
            gui.blit(BAR, xCord, yCord, 0, 0, 73, 19, 128, 64);
            float y = yCord + 10;
            List<ItemStack> ammunition = getAmmunition();
            int itemCount = Math.min(ammunition.size(), 3);
            for(int i = 0; i < itemCount; i++){
                ItemStack stack = ammunition.get(i);
                Utils.Render.renderItemModelInGui(stack, xCord + (16 * i), y, 16, 16, 16);
            }
        }

        gui.pose().popPose();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.valoria.familiar").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        tooltip.add(Component.translatable("tooltip.valoria.blazereap").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(getModeString(stack)).withStyle(ChatFormatting.GREEN));
    }
}
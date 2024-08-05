package com.idark.valoria.registries.item.types.ranged;

import com.idark.valoria.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
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
import net.minecraftforge.client.event.*;
import org.lwjgl.opengl.*;

import java.util.*;


// some day it will be done, but not in this life: FIX FUCKING NBT TAGS SYNCING, I HATE YOU MOJANG
// tags should be sent by packet
public class BlazeReapItem extends PickaxeItem implements Vanishable{
    private static final ResourceLocation BAR = new ResourceLocation(Valoria.ID, "textures/gui/overlay/blazecharge_bar.png");
    public BlazeReapItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder){
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }
    public static String getModeString(ItemStack stack){
        if(NbtUtils.readNbt(stack, "charge") == 1){
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
        ItemStack weapon = player.getItemInHand(hand);
        ItemStack ammo = ValoriaUtils.getProjectile(player, weapon);
        RandomSource rand = level.getRandom();
        boolean hasAmmo = !ammo.isEmpty();
        boolean flag = ammo.getItem() instanceof GunpowderCharge;
        if(player.isShiftKeyDown()){
            if(NbtUtils.readNbt(weapon, "charge") == 0){
                if(hasAmmo){
                    if(!player.isCreative()){
                        ammo.shrink(1);
                    }

                    NbtUtils.writeIntNbt(weapon, "charge", 1);
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

            return InteractionResultHolder.pass(weapon);
        }else if(NbtUtils.readNbt(weapon, "charge") == 1){
            NbtUtils.writeIntNbt(weapon, "charge", 0);
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

            // I hate you mojang, where the fucking explosion damage configuration... why it's hardcoded... just WHY
            float radius = flag ? ((GunpowderCharge)ammo.getItem()).getRadius() : 3f;
            float damage = flag ? ((GunpowderCharge)ammo.getItem()).getDamage() : 25f;
            float knockback = flag ? ((GunpowderCharge)ammo.getItem()).getKnockback() : 0.5f;
            if(!level.isClientSide){
                if(EnchantmentHelper.getTagEnchantmentLevel(EnchantmentsRegistry.EXPLOSIVE_FLAME.get(), weapon) > 0){
                    level.explode(player, pos.x + X, pos.y + Y, pos.z + Z, radius, Level.ExplosionInteraction.TNT);
                }else{
                    ValoriaUtils.configExplode(player, weapon, pos, new Vec3(X, Y, Z), radius, damage, knockback);
                }
            }

            for(int i = 0; i < 12; i++){
                level.addParticle(ParticleTypes.LARGE_SMOKE, pos.x + X + ((rand.nextDouble() - 0.5D) * radius), pos.y + Y + ((rand.nextDouble() - 0.5D) * radius), pos.z + Z + ((rand.nextDouble() - 0.5D) * radius), 0.05d * ((rand.nextDouble() - 0.5D) * radius), 0.05d * ((rand.nextDouble() - 0.5D) * radius), 0.05d * ((rand.nextDouble() - 0.5D) * radius));
                level.addParticle(ParticleTypes.FLAME, pos.x + X + ((rand.nextDouble() - 0.5D) * radius), pos.y + Y + ((rand.nextDouble() - 0.5D) * radius), pos.z + Z + ((rand.nextDouble() - 0.5D) * radius), 0.05d * ((rand.nextDouble() - 0.5D) * radius), 0.05d * ((rand.nextDouble() - 0.5D) * radius), 0.05d * ((rand.nextDouble() - 0.5D) * radius));
            }

            return InteractionResultHolder.success(weapon);
        }

        return InteractionResultHolder.pass(weapon);
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

    @OnlyIn(Dist.CLIENT)
    public static void onDrawScreenPost(RenderGuiOverlayEvent.Post event){
        Minecraft mc = Minecraft.getInstance();
        for(ItemStack itemStack : mc.player.getHandSlots()){
            boolean renderBar = itemStack.getItem() instanceof BlazeReapItem && !mc.player.isSpectator() && !mc.player.isCreative();
            if(renderBar){
                GuiGraphics gui = event.getGuiGraphics();
                gui.pose().pushPose();
                gui.pose().translate(0, 0, -200);
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                int xCord = ClientConfig.MAGMA_CHARGE_BAR_X.get();
                int yCord = ClientConfig.MAGMA_CHARGE_BAR_Y.get();
                gui.blit(BAR, xCord, yCord, 0, 0, 73, 19, 128, 64);

                float x = 6;
                float y = 5.5f;
                List<ItemStack> ammunition = getAmmunition();
                int itemCount = Math.min(ammunition.size(), 3);
                for (int i = 0; i < itemCount; i++) {
                    ItemStack stack = ammunition.get(i);
                    RenderUtils.renderItemModelInGui(stack, x + (16 * i), y, 16, 16, 16);
                }

                RenderSystem.disableBlend();
                gui.pose().popPose();
            }
        }
    }

//    @OnlyIn(Dist.CLIENT)
//    @Override
//    public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
//        if (NbtUtils.readNbt(stack, "charge") == 1){
//            ScreenParticleRegistry.spawnFireParticles(target, ColorParticleData.create(Color.white, Pal.strongRed).build());
//        }
//    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(1, Component.translatable("tooltip.valoria.familiar").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        tooltip.add(2, Component.empty());
        tooltip.add(3, Component.translatable("tooltip.valoria.blazereap").withStyle(ChatFormatting.GRAY));
        tooltip.add(4, Component.translatable(getModeString(stack)).withStyle(ChatFormatting.GREEN));
    }
}
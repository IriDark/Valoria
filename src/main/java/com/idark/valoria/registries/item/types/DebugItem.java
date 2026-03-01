package com.idark.valoria.registries.item.types;

import com.idark.valoria.client.ui.screen.book.codex.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.projectile.*;
import com.idark.valoria.registries.item.ability.*;
import com.idark.valoria.registries.item.ability.components.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.gameevent.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;

/**
 * Used to debug some particle packets and other things
 */
public class DebugItem extends Item{

    public DebugItem(Properties pProperties){
        super(pProperties);
    }

    public int getUseDuration(@NotNull ItemStack stack){
        return 72000;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (!level.isClientSide && !stack.getOrCreateTag().contains("valoria_abilities")) {
            DashAbility dashAbility = new DashAbility();
            dashAbility.maxUsages = 3;
            dashAbility.itemCooldown = 20;

            var scythe = new ScytheAbility();
            scythe.cancelVanillaBehaviour=true;
            scythe.usages=3;
            scythe.itemCooldown=60;

            AbilityHelper.setAbility(stack, CastType.RIGHT_CLICK, dashAbility);
            AbilityHelper.setAbility(stack, CastType.SHIFT_RIGHT_CLICK, dashAbility);
            AbilityHelper.setAbility(stack, CastType.RIGHT_CLICK, dashAbility);
            AbilityHelper.setAbility(stack, CastType.LEFT_CLICK, scythe);
            AbilityHelper.setAbility(stack, CastType.SHIFT_LEFT_CLICK, dashAbility);
            AbilityHelper.setAbility(stack, new DescriptionAbility().addLine(Component.translatable("tooltip.valoria.katana").withStyle(ChatFormatting.GRAY)));
            AbilityHelper.setAbility(stack, new DescriptionAbility().addLine(Component.literal("ВЫЕБИТЕ КЕРДО НАХУй").withStyle(ChatFormatting.GRAY)));
        }
    }

    private void spawn(ServerLevel level, Player entity){
        LaserEntity laser = new LaserEntity(level, entity);
        laser.setDamage(6);
        Vec3 vector3d = entity.getViewVector(1.0F);
        entity.gameEvent(GameEvent.ITEM_INTERACT_START);
        entity.playSound(SoundsRegistry.MAGIC_SHOOT.get());
        laser.shoot(vector3d.x(), vector3d.y(), vector3d.z(), 1F, 3);
        level.addFreshEntity(laser);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration){
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
        var pos = pLivingEntity.blockPosition();
//        if(!pLevel.isClientSide()) {
//            PacketHandler.sendToTracking(pLevel, pos, new AlchemyUpgradeParticlePacket(4, pos.getX(), pos.getY() + 2, pos.getZ()));
//        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        playerIn.startUsingItem(handIn);
        var pos = playerIn.blockPosition();
//        if(!worldIn.isClientSide()) {
//            PacketHandler.sendToTracking(worldIn, pos, new AlchemyUpgradeParticlePacket(1, pos.getX(), pos.getY() + 2, pos.getZ()));
//        }

        return InteractionResultHolder.consume(itemstack);
    }

    private static void scorpionSpit(Level worldIn, Player playerIn){
        AcidSpit spit = new AcidSpit(playerIn, worldIn);
        spit.setBaseDamage(6);
        spit.addEffect(new MobEffectInstance(MobEffects.POISON, 50, 0));
        Vec3 vector3d = playerIn.getViewVector(1.0F);
        spit.shoot(vector3d.x(), vector3d.y(), vector3d.z(), 3, 1);
        playerIn.playSound(SoundEvents.LLAMA_SPIT);
        worldIn.addFreshEntity(spit);
    }

    @OnlyIn(Dist.CLIENT)
    public void openGui(){
        Minecraft.getInstance().setScreen(new Codex());
    }

    private static void acorn(Level worldIn, Player playerIn){
        AcornProjectile spell = new AcornProjectile(playerIn, worldIn);
        Vec3 vector3d = playerIn.getViewVector(1.0F);
        spell.shoot(vector3d.x(), vector3d.y(), vector3d.z(), 1F, 3);
        worldIn.addFreshEntity(spell);
    }

    private static void spell(Level worldIn, Player playerIn){
        SpellProjectile spell = new SpellProjectile(worldIn, playerIn, 6);
        Vec3 vector3d = playerIn.getViewVector(1.0F);
        spell.setColor(Pal.nature);
        spell.shoot(vector3d.x(), vector3d.y(), vector3d.z(), 1F, 3);
        worldIn.addFreshEntity(spell);
    }
}
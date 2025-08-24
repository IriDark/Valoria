package com.idark.valoria.registries.item.types;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.component.*;
import com.idark.valoria.registries.item.types.builders.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.client.render.gui.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;
import pro.komaru.tridot.util.struct.data.*;

import java.lang.Math;
import java.util.*;

import static com.idark.valoria.Valoria.BASE_DASH_DISTANCE_UUID;

public class KatanaItem extends SwordItem implements CooldownNotifyItem, DashItem, CooldownReductionItem, TooltipComponentItem{
    public AbstractKatanaBuilder<? extends KatanaItem> builder;
    public Multimap<Attribute, AttributeModifier> defaultModifiers;
    public ArcRandom arcRandom = Tmp.rnd;

    public KatanaItem(AbstractKatanaBuilder<? extends KatanaItem> builderIn){
        super(builderIn.tier, (int)builderIn.attackDamageIn, builderIn.attackSpeedIn, builderIn.itemProperties);
        this.builder = builderIn;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", builderIn.attackDamageIn + builderIn.tier.getAttackDamageBonus(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", builderIn.attackSpeedIn, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeReg.DASH_DISTANCE.get(), new AttributeModifier(BASE_DASH_DISTANCE_UUID, "Tool modifier", builderIn.dashDist, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    public KatanaItem(Tier tier, float attackDamageIn, float attackSpeedIn, Item.Properties builderIn){
        this(new Builder(attackDamageIn, attackSpeedIn, builderIn).setTier(tier));
    }

    public KatanaItem(Tier tier, float attackDamageIn, float attackSpeedIn, float dashDistance, Item.Properties builderIn){
        this(new Builder(attackDamageIn, attackSpeedIn, builderIn).setTier(tier).setDashDistance(dashDistance));
    }

    public void onUseTick(@NotNull Level worldIn, @NotNull LivingEntity livingEntityIn, @NotNull ItemStack stack, int count){
        Player player = (Player)livingEntityIn;
        if(player.getTicksUsingItem() == builder.chargeTime){
            if(builder.chargedSound != null){
                player.playNotifySound(builder.chargedSound, SoundSource.PLAYERS, 0.25f, 1);
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged){
        if(!slotChanged){
            return false;
        }

        return super.shouldCauseReequipAnimation(oldStack, newStack, true);
    }

    @Override
    public SoundEvent getSoundEvent(){
        return builder.cooldownSound;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot){
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    public static double distance(double distance, Level level, Player player){
        double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
        double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;
        double X = Math.sin(pitch) * Math.cos(yaw) * distance;
        double Y = Math.cos(pitch) * distance;
        double Z = Math.sin(pitch) * Math.sin(yaw) * distance;

        Vec3 pos = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        Vec3 EndPos = (player.getViewVector(0.0f).scale(2.0d));
        HitResult hitresult = Utils.Hit.hitResult(player.getEyePosition(), player, (e) -> true, EndPos, level);
        if(hitresult != null){
            switch(hitresult.getType()){
                case BLOCK, MISS:
                    X = hitresult.getLocation().x();
                    Y = hitresult.getLocation().y();
                    Z = hitresult.getLocation().z();
                    break;
                case ENTITY:
                    Entity entity = ((EntityHitResult)hitresult).getEntity();
                    X = entity.getX();
                    Y = entity.getY();
                    Z = entity.getZ();
                    break;
            }
        }

        return Math.sqrt((X - pos.x) * (X - pos.x) + (Y - pos.y) * (Y - pos.y) + (Z - pos.z) * (Z - pos.z));
    }

    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level worldIn, BlockState state, @NotNull BlockPos pos, LivingEntity entityLiving){
        if(state.getDestroySpeed(worldIn, pos) != 0.0F){
            stack.hurtAndBreak(5, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        return true;
    }

    public void applyCooldown(Player playerIn){
        for(Item item : ForgeRegistries.ITEMS){
            if(item instanceof KatanaItem){
                playerIn.getCooldowns().addCooldown(item, getCooldownReduction(builder.cooldownTime, playerIn.getUseItem()));
            }
        }
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(!playerIn.isShiftKeyDown() && handIn != InteractionHand.OFF_HAND){
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public int getUseDuration(@NotNull ItemStack stack){
        return 72000;
    }

    public double getDashDistance(Player player){
        return player.getAttributeValue(AttributeReg.DASH_DISTANCE.get());
    }

    public int getHurtAmount(List<LivingEntity> detectedEntities){
        return 5 + detectedEntities.size();
    }

    public void performEffects(LivingEntity targets, Player player){
        targets.knockback(0.4F, player.getX() - targets.getX(), player.getZ() - targets.getZ());
        if(EnchantmentHelper.getFireAspect(player) > 0){
            int i = EnchantmentHelper.getFireAspect(player);
            targets.setSecondsOnFire(i * 4);
        }
    }

    public void performDash(@NotNull ItemStack stack, @NotNull Level level, @NotNull Player player, Vector3d pos, RandomSource rand){
        double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
        double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;
        double dashDistance = getDashDistance(player);
        performDash(player, stack, dashDistance);
        double ii = 1D;
        if(level instanceof ServerLevel srv){
            if(!builder.usePacket){
                for(int i = 0; i < 10; i += 1){
                    double locDistance = i * 0.5D;
                    double X = Math.sin(pitch) * Math.cos(yaw) * locDistance;
                    double Y = Math.cos(pitch) * 2;
                    double Z = Math.sin(pitch) * Math.sin(yaw) * locDistance;

                    srv.sendParticles(builder.particleOptions, pos.x + X + (rand.nextDouble() - 0.5D), pos.y + Y, pos.z + Z + (rand.nextDouble() - 0.5D), 1, 0, 0.5, 0, 0);
                    List<LivingEntity> detectedEntities = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos.x + X - 0.5D, pos.y + Y - 0.5D, pos.z + Z - 0.5D, pos.x + X + 0.5D, pos.y + Y + 0.5D, pos.z + Z + 0.5D));
                    for(LivingEntity entity : detectedEntities){
                        if(!entity.equals(player)){
                            entity.hurt(level.damageSources().playerAttack(player), (float)((player.getAttributeValue(Attributes.ATTACK_DAMAGE) * ii) + EnchantmentHelper.getSweepingDamageRatio(player) + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
                            performEffects(entity, player);
                            Utils.Entities.applyWithChance(entity, builder.effects, builder.chance, arcRandom);
                            if(!player.isCreative()){
                                stack.hurtAndBreak(getHurtAmount(detectedEntities), player, (plr) -> plr.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                            }
                        }

                        ii = ii - (1D / (detectedEntities.size() * 2));
                    }

                    if(locDistance >= distance(dashDistance, level, player)){
                        break;
                    }
                }
            }else{
                PacketHandler.sendToTracking(srv, player.getOnPos(), new DashParticlePacket(player.getUUID(), 1, 0, 0, 0, builder.dashColor));
            }
        }
    }

    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entityLiving, int timeLeft){
        RandomSource rand = level.getRandom();
        Player player = (Player)entityLiving;
        Vector3d pos = new Vector3d(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        if(!player.isFallFlying() && player.getTicksUsingItem() >= builder.chargeTime){
            player.awardStat(Stats.ITEM_USED.get(this));
            applyCooldown(player);
            performDash(stack, level, player, pos, rand);
            level.playSound(null, player.getOnPos(), builder.dashSound, SoundSource.PLAYERS, 1F, 1F);
            if(level.isClientSide){
                OverlayRender.setOverlayTexture(builder.texture);
                OverlayRender.showOverlay(builder.overlayTime);
            }
        }
    }

    public Seq<TooltipComponent> getTooltips(ItemStack pStack) {
        return Seq.with(
        new AbilitiesComponent(),
        new AbilityComponent(Component.translatable("tooltip.valoria.katana").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/gui/tooltips/dash.png")),
        new ClientTextComponent(Component.translatable("tooltip.valoria.rmb").withStyle(style -> style.withFont(Valoria.FONT)))
        );
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        if(builder.chargeTime > 0 && flags.isAdvanced()){
            tooltip.add(Component.translatable("tooltip.valoria.katana_charge", builder.chargeTime).withStyle(ChatFormatting.GRAY));
        }
    }

    public static class Builder extends AbstractKatanaBuilder<KatanaItem>{
        public Builder(float attackDamageIn, float attackSpeedIn, Properties itemProperties){
            super(attackDamageIn, attackSpeedIn, itemProperties);
        }

        /**
         * @return Build of KatanaItem with all the configurations you set :p
         */
        public KatanaItem build(){
            return new KatanaItem(this);
        }
    }
}
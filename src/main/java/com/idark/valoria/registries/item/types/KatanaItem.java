package com.idark.valoria.registries.item.types;

import com.idark.valoria.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.builders.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.client.resources.language.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
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
import net.minecraft.world.item.component.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.client.render.gui.overlay.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.common.registry.item.components.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;
import pro.komaru.tridot.util.struct.data.*;

import java.lang.Math;
import java.util.*;

import static com.idark.valoria.Valoria.BASE_DASH_DISTANCE_ID;

public class KatanaItem extends SwordItem implements CooldownNotifyItem, DashItem, CooldownReductionItem, TooltipComponentItem{
    public AbstractKatanaBuilder<? extends KatanaItem> builder;
    public ItemAttributeModifiers defaultModifiers;
    public ArcRandom arcRandom = Tmp.rnd;

    public KatanaItem(AbstractKatanaBuilder<? extends KatanaItem> builderIn){
        super(builderIn.tier, builderIn.itemProperties);
        this.builder = builderIn;
        this.defaultModifiers = ItemAttributeModifiers.builder()
            .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, builderIn.attackDamageIn + builderIn.tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, builderIn.attackSpeedIn, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(AttributeReg.DASH_DISTANCE, new AttributeModifier(BASE_DASH_DISTANCE_ID, builderIn.dashDist, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .build();
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

    @Override
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers(){
        return this.defaultModifiers;
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

    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level worldIn, BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entityLiving){
        if(state.getDestroySpeed(worldIn, pos) != 0.0F){
            stack.hurtAndBreak(5, entityLiving, EquipmentSlot.MAINHAND);
        }

        return true;
    }

    public void applyCooldown(Player playerIn){
        for(Item item : BuiltInRegistries.ITEM){
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

    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity){
        return 72000;
    }

    public double getDashDistance(Player player){
        return player.getAttributeValue(AttributeReg.DASH_DISTANCE);
    }

    public int getHurtAmount(List<LivingEntity> detectedEntities){
        return detectedEntities.size();
    }

    public void performEffects(LivingEntity targets, Player player){
        targets.knockback(0.4F, player.getX() - targets.getX(), player.getZ() - targets.getZ());
        int i = CombatCompat.fireAspect(player);
        if(i > 0){
            targets.igniteForSeconds(i * 4);
        }
    }

    public void performDash(@NotNull ItemStack stack, @NotNull Level level, @NotNull Player player, Vector3d pos, RandomSource rand){
        double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
        double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;
        double dashDistance = getDashDistance(player);
        performDash(player, stack, dashDistance);
        if(level instanceof ServerLevel srv){
            for(int i = 0; i < 10; i += 1){
                double locDistance = i * 0.5D;
                double X = Math.sin(pitch) * Math.cos(yaw) * locDistance;
                double Y = Math.cos(pitch) * 2;
                double Z = Math.sin(pitch) * Math.sin(yaw) * locDistance;
                List<LivingEntity> detectedEntities = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos.x + X - 0.5D, pos.y + Y - 0.5D, pos.z + Z - 0.5D, pos.x + X + 0.5D, pos.y + Y + 0.5D, pos.z + Z + 0.5D));
                for(LivingEntity entity : detectedEntities){
                    if(!entity.equals(player)){
                        entity.hurt(level.damageSources().playerAttack(player), (float)(((player.getAttributeValue(Attributes.ATTACK_DAMAGE) / 2) + getHurtAmount(detectedEntities)) + CombatCompat.sweepingRatio(player) + CombatCompat.damageBonus(player, stack, entity)) * 1.35f);
                        performEffects(entity, player);
                        Utils.Entities.applyWithChance(entity, builder.effects, builder.chance, arcRandom);
                        if(!player.isCreative()){
                            stack.hurtAndBreak(5 + getHurtAmount(detectedEntities), player, EquipmentSlot.MAINHAND);
                        }
                    }
                }

                if(locDistance >= distance(dashDistance, level, player)){
                    break;
                }
            }

            float X = (float)(Math.sin(pitch) * Math.cos(yaw));
            float Y = (float)(Math.cos(pitch) * 2);
            float Z = (float)(Math.sin(pitch) * Math.sin(yaw));

            PacketHandler.sendToTracking(srv, player.getOnPos(), new DashParticlePacket(player.getUUID(), X, Y, Z));
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
                OverlayHandler.addInstance(new TimedOverlayInstance().setTexture(builder.texture).setShowTime(builder.overlayTime).setFadeIn(0));
            }
        }
    }

    public Seq<TooltipComponent> getTooltips(ItemStack pStack) {
        Seq<TooltipComponent> seq = Seq.with(
            new SeparatorComponent(Component.translatable("tooltip.tridot.abilities")),
            new AbilityComponent(Component.translatable("tooltip.valoria.katana").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/gui/tooltips/dash.png"))
        );

        seq.add(new TextComponent(Component.translatable("tooltip.tridot.crossbow.speed", builder.chargeTime > 0 ? Utils.Items.formatTickDuration(builder.chargeTime) : I18n.get("tooltip.valoria.timed.instant")).withStyle(style -> style.withColor(ChatFormatting.GRAY).withFont(Valoria.FONT))));
        seq.add(new TextComponent(Component.translatable("tooltip.valoria.rmb").withStyle(style -> style.withFont(Valoria.FONT))));
        return seq;
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

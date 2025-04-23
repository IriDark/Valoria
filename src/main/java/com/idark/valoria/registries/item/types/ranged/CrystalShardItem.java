package com.idark.valoria.registries.item.types.ranged;

import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.*;

import static pro.komaru.tridot.TridotLib.BASE_PROJECTILE_DAMAGE_UUID;

public class CrystalShardItem extends Item{
    private final Multimap<Attribute, AttributeModifier> tridentAttributes;
    public final ImmutableList<MobEffectInstance> effects;
    public ArcRandom arc = Tmp.rnd;

    public CrystalShardItem(double damage, Properties builderIn, MobEffectInstance... pEffects){
        super(builderIn);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(AttributeRegistry.PROJECTILE_DAMAGE.get(), new AttributeModifier(BASE_PROJECTILE_DAMAGE_UUID, "Tool modifier", damage * 2, AttributeModifier.Operation.ADDITION));
        this.tridentAttributes = builder.build();
        this.effects = ImmutableList.copyOf(pEffects);
    }

    public UseAnim getUseAnimation(ItemStack stack){
        return UseAnim.SPEAR;
    }

    public int getUseDuration(ItemStack stack){
        return 72000;
    }

    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft){
        if(entityLiving instanceof Player playerEntity){
            int i = this.getUseDuration(stack) - timeLeft;
            if(i >= 6){
                if(!worldIn.isClientSide){
                    stack.hurtAndBreak(1, playerEntity, (player) -> player.broadcastBreakEvent(entityLiving.getUsedItemHand()));
                    CrystalShard shard = shootProjectile(worldIn, playerEntity);
                    worldIn.addFreshEntity(shard);
                    worldIn.playSound(playerEntity, shard, SoundsRegistry.CRYSTAL_FROST_PREPARE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    if(!playerEntity.getAbilities().instabuild){
                        stack.shrink(1);
                    }
                }

                playerEntity.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    private @NotNull CrystalShard shootProjectile(Level worldIn, Player playerEntity){
        CrystalShard shard = new CrystalShard(worldIn, playerEntity, playerEntity.getAttributeValue(AttributeRegistry.PROJECTILE_DAMAGE.get()));
        shard.setEffectsFromList(effects);
        shard.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0F, 2.5F + (float)0 * 0.5F, 1.0F);
        if(playerEntity.getAbilities().instabuild){
            shard.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }

        return shard;
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(!playerIn.isShiftKeyDown()){
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker){
        Utils.Entities.applyWithChance(target, effects, 1, arc);
        return super.hurtEnemy(stack, target, attacker);
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot){
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.tridentAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        Utils.Items.effectTooltip(effects, tooltip, 1, 1);
    }
}
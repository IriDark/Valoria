package com.idark.valoria.registries.item.types.ranged;

import com.google.common.collect.*;
import com.idark.valoria.core.enums.*;
import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.*;
import net.minecraft.core.*;
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
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.core.math.*;
import pro.komaru.tridot.registry.item.*;
import pro.komaru.tridot.utilities.*;

import java.util.*;

import static pro.komaru.tridot.TridotLib.BASE_PROJECTILE_DAMAGE_UUID;

public class KunaiItem extends SwordItem{
    private final Multimap<Attribute, AttributeModifier> tridentAttributes;
    public final ImmutableList<MobEffectInstance> effects;
    public float chance = 1;
    public ArcRandom arc = new ArcRandom();

    public KunaiItem(int damage, float speed, Item.Properties builderIn, float chance, MobEffectInstance... pEffects){
        super(ModItemTier.NONE, damage, speed, builderIn);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 3, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.PROJECTILE_DAMAGE.get(), new AttributeModifier(BASE_PROJECTILE_DAMAGE_UUID, "Tool modifier", damage * 2, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -1.9F, AttributeModifier.Operation.ADDITION));
        this.tridentAttributes = builder.build();
        this.chance = chance;
        this.effects = ImmutableList.copyOf(pEffects);
    }

    public KunaiItem(int damage, float speed, Item.Properties builderIn, MobEffectInstance... pEffects){
        super(ModItemTier.NONE, damage, speed, builderIn);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 3, AttributeModifier.Operation.ADDITION));
        builder.put(AttributeRegistry.PROJECTILE_DAMAGE.get(), new AttributeModifier(BASE_PROJECTILE_DAMAGE_UUID, "Tool modifier", damage * 2, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -1.9F, AttributeModifier.Operation.ADDITION));
        this.tridentAttributes = builder.build();
        this.effects = ImmutableList.copyOf(pEffects);
    }

    public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player){
        return !player.isCreative();
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant){
        return enchant.category.canEnchant(stack.getItem()) || enchant == Enchantments.PIERCING || enchant == Enchantments.LOYALTY;
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
                    KunaiEntity kunaiEntity = shootProjectile(stack, worldIn, playerEntity);
                    worldIn.addFreshEntity(kunaiEntity);
                    worldIn.playSound(playerEntity, kunaiEntity, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                    if(!playerEntity.getAbilities().instabuild){
                        playerEntity.getInventory().removeItem(stack);
                    }
                }

                playerEntity.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    private @NotNull KunaiEntity shootProjectile(ItemStack stack, Level worldIn, Player playerEntity){
        KunaiEntity kunaiEntity = new KunaiEntity(playerEntity, worldIn, stack);
        kunaiEntity.setItem(stack);
        int pierceLevel = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PIERCING, stack);
        if(pierceLevel > 0){
            kunaiEntity.setPierceLevel((byte)pierceLevel);
        }

        if(EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) > 0){
            kunaiEntity.setSecondsOnFire(100);
        }

        kunaiEntity.setEffectsFromList(effects);
        kunaiEntity.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0F, 2.5F + (float)0 * 0.5F, 1.0F);
        if(playerEntity.getAbilities().instabuild){
            kunaiEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }

        return kunaiEntity;
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
        Utils.Entities.applyWithChance(target, effects, chance, arc);
        return super.hurtEnemy(stack, target, attacker);
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot){
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.tridentAttributes : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.kunai").withStyle(ChatFormatting.GRAY));
        Utils.Items.effectTooltip(effects, tooltip, 1, chance);
    }
}
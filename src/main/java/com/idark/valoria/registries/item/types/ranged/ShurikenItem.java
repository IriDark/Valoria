package com.idark.valoria.registries.item.types.ranged;

import com.google.common.collect.*;
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
import net.minecraft.world.item.component.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.*;

import static pro.komaru.tridot.Tridot.BASE_PROJECTILE_DAMAGE_ID;

public class ShurikenItem extends Item{
    private final ItemAttributeModifiers tridentAttributes;
    public final ImmutableList<MobEffectInstance> effects;
    public float chance = 1;
    public ArcRandom arc = Tmp.rnd;

    public ShurikenItem(int damage, Properties builderIn, float chance, MobEffectInstance... pEffects){
        super(builderIn);
        this.tridentAttributes = projectileAttributes(damage);
        this.chance = chance;
        this.effects = ImmutableList.copyOf(pEffects);
    }

    public ShurikenItem(int damage, Properties builderIn, MobEffectInstance... pEffects){
        super(builderIn);
        this.tridentAttributes = projectileAttributes(damage);
        this.effects = ImmutableList.copyOf(pEffects);
    }

    static ItemAttributeModifiers projectileAttributes(double damage){
        return ItemAttributeModifiers.builder()
            .add(AttributeRegistry.PROJECTILE_DAMAGE, new AttributeModifier(BASE_PROJECTILE_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .build();
    }

    public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player){
        return !player.isCreative();
    }

    public UseAnim getUseAnimation(ItemStack stack){
        return UseAnim.SPEAR;
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity){
        return 72000;
    }

    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft){
        if(entityLiving instanceof Player playerEntity){
            int i = this.getUseDuration(stack, entityLiving) - timeLeft;
            if(i >= 4){
                if(!worldIn.isClientSide){
                    ShurikenProjectile shuriken = shootProjectile(stack, worldIn, playerEntity);
                    worldIn.addFreshEntity(shuriken);
                    worldIn.playSound(playerEntity, shuriken, SoundEvents.TRIDENT_THROW.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    if(!playerEntity.getAbilities().instabuild){
                        stack.shrink(1);
                    }
                }

                playerEntity.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    private @NotNull ShurikenProjectile shootProjectile(ItemStack stack, Level worldIn, Player playerEntity){
        ShurikenProjectile shuriken = new ShurikenProjectile(playerEntity, worldIn, stack);
        shuriken.setItem(stack);
        shuriken.setEffectsFromList(effects);
        shuriken.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0F, 2.5F + (float)0 * 0.5F, 3.0F);
        if(playerEntity.getAbilities().instabuild){
            shuriken.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }

        return shuriken;
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

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(){
        return this.tridentAttributes;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.shuriken").withStyle(ChatFormatting.GRAY));
        Utils.Items.effectTooltip(effects, tooltip, 1, chance);
    }
}

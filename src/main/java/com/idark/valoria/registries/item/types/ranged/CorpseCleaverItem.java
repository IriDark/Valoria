package com.idark.valoria.registries.item.types.ranged;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.client.render.gui.overlay.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.common.registry.item.components.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.*;

import static pro.komaru.tridot.Tridot.BASE_PROJECTILE_DAMAGE_ID;

public class CorpseCleaverItem extends SwordItem implements TooltipComponentItem{
    private final ItemAttributeModifiers pAttributes;

    public CorpseCleaverItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Item.Properties pProperties){
        super(pTier, pProperties);
        float attackDamage = (float)pAttackDamageModifier + pTier.getAttackDamageBonus();
        this.pAttributes = ItemAttributeModifiers.builder()
            .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(AttributeRegistry.PROJECTILE_DAMAGE, new AttributeModifier(BASE_PROJECTILE_DAMAGE_ID, attackDamage + 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, pAttackSpeedModifier, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .build();
    }

    public UseAnim getUseAnimation(ItemStack stack){
        return UseAnim.SPEAR;
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity){
        return 72000;
    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft){
        if(entityLiving instanceof Player playerEntity){
            if(this.getUseDuration(stack, entityLiving) - timeLeft >= 6){
                if(!level.isClientSide){
                    stack.hurtAndBreak(1, playerEntity, LivingEntity.getSlotForHand(entityLiving.getUsedItemHand()));
                    MeatBlockEntity meat = new MeatBlockEntity(level, playerEntity, stack);
                    meat.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0F, 2.5F + (float)0 * 0.5F, 1.0F);
                    level.addFreshEntity(meat);
                    level.playSound(playerEntity, meat, SoundEvents.LLAMA_SWAG.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    if(!playerEntity.getAbilities().instabuild){
                        stack.hurtAndBreak(1, playerEntity, LivingEntity.getSlotForHand(entityLiving.getUsedItemHand()));
                        playerEntity.hurt(DamageSourceRegistry.bleeding(level), 2.0F);
                        playerEntity.getCooldowns().addCooldown(this, 40);
                    }
                } else {
                    TimedOverlayInstance instance = new TimedOverlayInstance();
                    instance.setTexture(Valoria.loc("textures/gui/overlay/blood.png"));
                    instance.setShowTime(30);

                    OverlayHandler.addInstance(instance);
                }

                playerEntity.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if(!playerIn.isShiftKeyDown()){
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.consume(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(){
        return this.pAttributes;
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced){
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        Utils.Items.effectTargetTooltip(ImmutableList.of(new MobEffectInstance(EffectsRegistry.BLEEDING, 120, 1)), pTooltipComponents, 1, 1);
    }

    @Override
    public Seq<TooltipComponent> getTooltips(ItemStack pStack){
        return Seq.with(
        new SeparatorComponent(Component.translatable("tooltip.tridot.abilities")),
        new AbilityComponent(Component.translatable("tooltip.valoria.corpsecleaver").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/gui/tooltips/corpsecleaver.png")),
        new TextComponent(Component.translatable("tooltip.valoria.hold_rmb").withStyle(style -> style.withFont(Valoria.FONT)))
        );
    }
}

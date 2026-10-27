package com.idark.valoria.registries.item.types;

import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.skins.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.common.registry.item.skins.*;
import pro.komaru.tridot.util.*;

import java.util.*;

public class ValoriaSword extends SwordItem{
    public ValoriaSword(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties){
        super(pTier, pProperties.attributes(createAttributes(pAttackDamageModifier + pTier.getAttackDamageBonus(), pAttackSpeedModifier)));
    }

    public ValoriaSword(Tier pTier, Properties pProperties){
        super(pTier, pProperties);
    }

    public static ItemAttributeModifiers createAttributes(float attackDamage, float attackSpeed) {
        var builder = ItemAttributeModifiers.builder()
        .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
        .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        return builder.build();
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if(!(pAttacker instanceof Player player)) return true;
        pStack.hurtAndBreak(2, pAttacker, EquipmentSlot.MAINHAND);
        if(Utils.Items.getAttackStrengthScale(player, 0.9f)){
            if(Tmp.rnd.chance(0.25f)){
                if(pStack.is(ItemsRegistry.crimtaneSword.get())){
                    pAttacker.addEffect(new MobEffectInstance(EffectsRegistry.RENEWAL, 120));
                }
            }
        }

        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        ItemSkin skin = ItemSkin.itemSkin(stack);
        if(skin != null && skin == SkinsRegistry.DEATH_OF_CRABS){
            tooltip.add(Component.translatable("item_skin.valoria.death_of_crabs.desc").withStyle(ChatFormatting.GRAY));
        }

        if(stack.is(ItemsRegistry.crimtaneSword.get())) {
            Utils.Items.effectTooltip(ImmutableList.of(new MobEffectInstance(EffectsRegistry.RENEWAL, 120)), tooltip, 1, 0.25f);
        }
    }
}

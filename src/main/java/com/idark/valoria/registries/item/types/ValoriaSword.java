package com.idark.valoria.registries.item.types;

import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.skins.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.common.registry.item.skins.*;
import pro.komaru.tridot.util.*;

import java.util.*;

public class ValoriaSword extends SwordItem{
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public ValoriaSword(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties){
        super(pTier, (int)pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.attackDamage = pAttackDamageModifier + pTier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", pAttackSpeedModifier, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if(Tmp.rnd.chance(0.25f)){
            if(pStack.is(ItemsRegistry.crimtaneSword.get())){
                pAttacker.addEffect(new MobEffectInstance(EffectsRegistry.RENEWAL.get(), 120));
            }
        }

        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        Utils.Items.addContributorTooltip(stack, tooltip);
        ItemSkin skin = ItemSkin.itemSkin(stack);
        if(skin != null && skin == SkinsRegistry.DEATH_OF_CRABS){
            tooltip.add(Component.translatable("item_skin.valoria.death_of_crabs.desc").withStyle(ChatFormatting.GRAY));
        }

        if(stack.is(ItemsRegistry.crimtaneSword.get())) {
            Utils.Items.effectTooltip(ImmutableList.of(new MobEffectInstance(EffectsRegistry.RENEWAL.get(), 120)), tooltip, 1, 0.25f);
        }
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }
}

package com.idark.valoria.registries.item.types.shield;

import com.google.common.collect.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.common.registry.item.builders.*;
import pro.komaru.tridot.common.registry.item.types.*;

import java.util.*;

public class DraugrShieldItem extends ConfiguredShield{
    public final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public DraugrShieldItem(AbstractShieldBuilder<? extends ConfiguredShield> builder){
        super(builder);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attrBuilder = ImmutableMultimap.builder();
        attrBuilder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("0340e1c5-92f3-4c99-80e1-c592f3ec99a8"), "Tool modifier", 0.1, Operation.MULTIPLY_TOTAL));
        this.defaultModifiers = attrBuilder.build();
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot){
        return pEquipmentSlot == EquipmentSlot.OFFHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag){
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        pTooltip.add(Component.translatable("tooltip.valoria.shield.speed").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void onShieldBlock(DamageSource source, float pAmount, ItemStack itemStack, LivingEntity entity){
        super.onShieldBlock(source, pAmount, itemStack, entity);
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 0));
        if(entity instanceof Player player) {
            player.getCooldowns().addCooldown(itemStack.getItem(), 60);
        }
    }
}
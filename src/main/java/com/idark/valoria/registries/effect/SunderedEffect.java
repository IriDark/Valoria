package com.idark.valoria.registries.effect;

import com.google.common.collect.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.*;
import pro.komaru.tridot.common.config.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.util.*;

import java.util.*;

public class SunderedEffect extends MobEffect {
    private final Map<Attribute, AttributeModifier> percentModifiers = Maps.newHashMap();

    public SunderedEffect() {
        super(MobEffectCategory.HARMFUL, Col.hexToDecimal("8A9EA7"));
        addPercent(AttributeRegistry.PERCENT_ARMOR.get(), "d8f3b259-86f3-4f91-a1e6-42d4a77914bb", -15.0D, Operation.ADDITION);
        addAttributeModifier(Attributes.ARMOR, "b1a47321-45cd-4e89-9a21-72f4185d9b12", -0.15D, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public void addPercent(Attribute pAttribute, String pUuid, double pAmount, Operation pOperation) {
        AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(pUuid), this::getDescriptionId, pAmount, pOperation);
        this.percentModifiers.put(pAttribute, attributemodifier);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        for (Map.Entry<Attribute, AttributeModifier> entry : this.getAttributeModifiers().entrySet()) {
            AttributeInstance attributeinstance = pAttributeMap.getInstance(entry.getKey());
            if (attributeinstance != null) {
                attributeinstance.removeModifier(entry.getValue());
            }
        }
    }

    @Override
    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        for (Map.Entry<Attribute, AttributeModifier> entry : this.getAttributeModifiers().entrySet()) {
            AttributeInstance attributeinstance = pAttributeMap.getInstance(entry.getKey());
            if (attributeinstance != null) {
                AttributeModifier attributemodifier = entry.getValue();
                attributeinstance.removeModifier(attributemodifier);
                attributeinstance.addPermanentModifier(new AttributeModifier(attributemodifier.getId(), this.getDescriptionId() + " " + pAmplifier, this.getAttributeModifierValue(pAmplifier, attributemodifier), attributemodifier.getOperation()));
            }
        }
    }

    @Override
    public Map<Attribute, AttributeModifier> getAttributeModifiers() {
        if (CommonConfig.PERCENT_ARMOR.get()) {
            return percentModifiers;
        }
        return super.getAttributeModifiers();
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}

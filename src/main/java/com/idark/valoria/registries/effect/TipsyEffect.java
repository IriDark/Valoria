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

public class TipsyEffect extends MobEffect{
    Map<Attribute, AttributeModifier> attr = Maps.newHashMap();

    public TipsyEffect(){
        super(MobEffectCategory.NEUTRAL, Col.hexToDecimal("ecc597"));
        addPercent(AttributeRegistry.PERCENT_ARMOR.get(), "3db84224-bf20-4333-b842-24bf20433360", -10F, Operation.ADDITION);
        addPercent(Attributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.ARMOR, "74841448-7BD1-4C3F-924D-EED3F7A6E439", -0.10F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public void addPercent(Attribute pAttribute, String pUuid, double pAmount, Operation pOperation) {
        AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(pUuid), this::getDescriptionId, pAmount, pOperation);
        this.attr.put(pAttribute, attributemodifier);
    }

    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        for(Map.Entry<Attribute, AttributeModifier> entry : this.getAttributeModifiers().entrySet()) {
            AttributeInstance attributeinstance = pAttributeMap.getInstance(entry.getKey());
            if (attributeinstance != null) {
                attributeinstance.removeModifier(entry.getValue());
            }
        }
    }

    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        for(Map.Entry<Attribute, AttributeModifier> entry : this.getAttributeModifiers().entrySet()) {
            AttributeInstance attributeinstance = pAttributeMap.getInstance(entry.getKey());
            if (attributeinstance != null) {
                AttributeModifier attributemodifier = entry.getValue();
                attributeinstance.removeModifier(attributemodifier);
                attributeinstance.addPermanentModifier(new AttributeModifier(attributemodifier.getId(), this.getDescriptionId() + " " + pAmplifier, this.getAttributeModifierValue(pAmplifier, attributemodifier), attributemodifier.getOperation()));
            }
        }

    }

    public Map<Attribute, AttributeModifier> getAttributeModifiers() {
        if(CommonConfig.PERCENT_ARMOR.get()){
            return attr;
        }

        return super.getAttributeModifiers();
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier){
        return true;
    }
}
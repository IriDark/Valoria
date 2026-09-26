package com.idark.valoria.registries.effect;

import com.idark.valoria.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.*;
import pro.komaru.tridot.common.config.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.util.*;

import java.util.*;
import java.util.function.*;

public class SunderedEffect extends MobEffect {
    private final Map<Holder<Attribute>, AttributeTemplate> percentModifiers = new LinkedHashMap<>();

    public SunderedEffect() {
        super(MobEffectCategory.HARMFUL, Col.hexToDecimal("8A9EA7"));
        addPercent(AttributeRegistry.PERCENT_ARMOR, Valoria.loc("sundered_percent_armor"), -15.0D, Operation.ADD_VALUE);
        addAttributeModifier(Attributes.ARMOR, Valoria.loc("sundered_armor"), -0.10F, Operation.ADD_MULTIPLIED_TOTAL);
    }

    public void addPercent(Holder<Attribute> pAttribute, ResourceLocation id, double pAmount, Operation pOperation) {
        this.percentModifiers.put(pAttribute, new AttributeTemplate(id, pAmount, pOperation));
    }

    private boolean usePercentArmor(){
        return CommonConfig.PERCENT_ARMOR.get();
    }

    @Override
    public void createModifiers(int amplifier, BiConsumer<Holder<Attribute>, AttributeModifier> output){
        if(usePercentArmor()){
            this.percentModifiers.forEach((attribute, template) -> output.accept(attribute, template.create(amplifier)));
        }else{
            super.createModifiers(amplifier, output);
        }
    }

    @Override
    public void removeAttributeModifiers(AttributeMap pAttributeMap) {
        super.removeAttributeModifiers(pAttributeMap);
        for(Map.Entry<Holder<Attribute>, AttributeTemplate> entry : this.percentModifiers.entrySet()) {
            AttributeInstance attributeinstance = pAttributeMap.getInstance(entry.getKey());
            if (attributeinstance != null) {
                attributeinstance.removeModifier(entry.getValue().id());
            }
        }
    }

    @Override
    public void addAttributeModifiers(AttributeMap pAttributeMap, int pAmplifier) {
        createModifiers(pAmplifier, (attribute, modifier) -> {
            AttributeInstance attributeinstance = pAttributeMap.getInstance(attribute);
            if (attributeinstance != null) {
                attributeinstance.removeModifier(modifier.id());
                attributeinstance.addPermanentModifier(modifier);
            }
        });
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier){
        return true;
    }
}

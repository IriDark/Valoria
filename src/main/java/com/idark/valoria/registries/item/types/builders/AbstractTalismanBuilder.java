package com.idark.valoria.registries.item.types.builders;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.idark.valoria.registries.item.types.curio.TalismanItem;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item.Properties;

import java.util.UUID;

public abstract class AbstractTalismanBuilder<T extends TalismanItem>{
    public Properties properties;
    public Multimap<Attribute, AttributeModifier> attributes = LinkedHashMultimap.create();

    public AbstractTalismanBuilder(Properties pProperties){
        this.properties = pProperties;
    }

    public AbstractTalismanBuilder<T> put(Attribute attribute, double value){
        this.attributes.put(attribute, new AttributeModifier(UUID.randomUUID(), "Accessory modifier", value, AttributeModifier.Operation.ADDITION));
        return this;
    }

    public AbstractTalismanBuilder<T> put(Attribute attribute, AttributeModifier.Operation operation, double value){
        this.attributes.put(attribute, new AttributeModifier(UUID.randomUUID(), "Accessory modifier", value, operation));
        return this;
    }

    public abstract T build();
}

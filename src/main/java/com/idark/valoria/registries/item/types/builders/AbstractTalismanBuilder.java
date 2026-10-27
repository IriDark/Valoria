package com.idark.valoria.registries.item.types.builders;

import com.google.common.collect.*;
import com.idark.valoria.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.*;
import net.minecraft.world.item.component.*;

public abstract class AbstractTalismanBuilder<T extends Item>{
    public Properties properties;
    public Multimap<Holder<Attribute>, AttributeModifier> attributes = LinkedHashMultimap.create();

    public AbstractTalismanBuilder(Properties pProperties){
        this.properties = pProperties;
    }

    public Properties buildProperties(){
        return this.properties.attributes(createAttributeModifiers(this));
    }

    public static ItemAttributeModifiers createAttributeModifiers(AbstractTalismanBuilder<?> builderIn){
        var builder = ItemAttributeModifiers.builder();
        builderIn.attributes.forEach((attribute, modifier) -> builder.add(attribute, modifier, EquipmentSlotGroup.OFFHAND));
        return builder.build();
    }

    public AbstractTalismanBuilder<T> put(Holder<Attribute> attribute, double value){
        return put(attribute, AttributeModifier.Operation.ADD_VALUE, value);
    }

    public AbstractTalismanBuilder<T> put(Holder<Attribute> attribute, AttributeModifier.Operation operation, double value){
        String name = attribute.getRegisteredName().replace(':', '/').replace('.', '_');
        this.attributes.put(attribute, new AttributeModifier(Valoria.loc("talisman/" + name + "/" + attributes.size()), value, operation));
        return this;
    }

    public abstract T build();
}

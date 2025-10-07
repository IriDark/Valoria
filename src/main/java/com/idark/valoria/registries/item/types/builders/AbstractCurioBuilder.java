package com.idark.valoria.registries.item.types.builders;

import com.google.common.collect.*;
import com.idark.valoria.registries.item.types.curio.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.Item.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.common.registry.item.builders.AbstractArmorBuilder.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.function.*;

public abstract class AbstractCurioBuilder<T extends CurioAccessoryItem, B extends AbstractCurioBuilder<T, B>> {
    public Tier tier;
    public Properties itemProperties;
    public Multimap<Supplier<Attribute>, AttributeData> attributeMap = HashMultimap.create();
    public Multimap<String, AttributeData> slotModifiers = HashMultimap.create();
    public Seq<MobEffectInstance> effects = Seq.with();

    public AbstractCurioBuilder(Tier tier, Properties properties){
        this.tier = tier;
        this.itemProperties = properties;
    }

    @SuppressWarnings("unchecked")
    protected B self() {
        return (B) this;
    }

    public B addEffects(MobEffectInstance... effects){
        this.effects.addAll(effects);
        return self();
    }

    public B setEffects(Seq<MobEffectInstance> effects){
        this.effects = effects;
        return self();
    }

    public B addEffect(MobEffectInstance effect){
        this.effects.add(effect);
        return self();
    }

    public B addSlots(Multimap<String, AttributeData> map){
        slotModifiers.putAll(map);
        return self();
    }

    public B setSlots(Multimap<String, AttributeData> map){
        slotModifiers = map;
        return self();
    }

    public B addSlot(String attribute, AttributeData mod){
        slotModifiers.put(attribute, mod);
        return self();
    }

    public B addAttrs(Multimap<Supplier<Attribute>, AttributeData> map){
        attributeMap.putAll(map);
        return self();
    }

    public B setAttrs(Multimap<Supplier<Attribute>, AttributeData> map){
        attributeMap = map;
        return self();
    }

    public B addAttr(Supplier<Attribute> attribute, AttributeData mod){
        attributeMap.put(attribute, mod);
        return self();
    }

    public abstract T build();
}
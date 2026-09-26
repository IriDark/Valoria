package com.idark.valoria.registries.item.types.builders;

import com.google.common.collect.*;
import com.idark.valoria.registries.item.types.curio.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.Item.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.common.registry.item.builders.AbstractArmorBuilder.*;
import pro.komaru.tridot.util.struct.data.*;

public abstract class AbstractCurioBuilder<T extends CurioAccessoryItem, B extends AbstractCurioBuilder<T, B>> {
    public Tier tier;
    public Properties itemProperties;
    public ResourceLocation texPath;
    public boolean dependsOnStack = true;

    public Multimap<Holder<Attribute>, AttributeData> attributeMap = HashMultimap.create();
    public Multimap<String, AttributeData> slotModifiers = HashMultimap.create();
    public Seq<MobEffectInstance> effects = Seq.with();
    public SoundEvent equipSound;
    public float volume = 1.0f;
    public float pitch = 1.0f;

    public AbstractCurioBuilder(Tier tier, Properties properties){
        this.tier = tier;
        this.itemProperties = properties;
    }

    @SuppressWarnings("unchecked")
    public B self() {
        return (B) this;
    }

    public B equipSound(SoundEvent soundEvent, float vol, float pitch) {
        this.equipSound = soundEvent;
        this.volume = vol;
        this.pitch = pitch;
        return self();
    }

    public B equipSound(SoundEvent soundEvent) {
        this.equipSound = soundEvent;
        return self();
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

    public B addAttrs(Multimap<Holder<Attribute>, AttributeData> map){
        attributeMap.putAll(map);
        return self();
    }

    public B setAttrs(Multimap<Holder<Attribute>, AttributeData> map){
        attributeMap = map;
        return self();
    }

    public B addAttr(Holder<Attribute> attribute, AttributeData mod){ // PORT NOTE: attributes are Holders in 1.21
        attributeMap.put(attribute, mod);
        return self();
    }


    public B setTexPath(ResourceLocation texPath){
        this.texPath = texPath;
        return self();
    }

    public B setDependsOnStack(boolean dependsOnStack){
        this.dependsOnStack = dependsOnStack;
        return self();
    }

    public abstract T build();
}
package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.common.registry.item.builders.AbstractArmorBuilder.*;
import top.theillusivec4.curios.api.*;

import java.util.*;
import java.util.function.*;

public class EyeNecklaceItem extends CurioAccessoryItem{

    public EyeNecklaceItem(NecklaceBuilder builder){
        super(builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack){
        Multimap<Attribute, AttributeModifier> m = LinkedHashMultimap.create();
        LivingEntity entity = slotContext.getWearer();
        if(entity == null) return m;

        Level level = entity.level();
        float time = level.getTimeOfDay(0) % 24000;
        boolean isDarkEnough = time > 12000 && time < 24000;
        if(isDarkEnough) {
            return super.getAttributeModifiers(slotContext, uuid, stack);
        } else if(!isDarkEnough && builder instanceof NecklaceBuilder neckBuilder) {
            neckBuilder.negativeAttributeMap.forEach((attrSupplier, data) -> {
                AttributeModifier modifier1 = new AttributeModifier(uuid, "Attribute Modifier", data.value(), data.operation());
                m.put(attrSupplier.get(), modifier1);
            });
        }

        return m;
    }

    public static class NecklaceBuilder extends Builder<NecklaceBuilder>{
        public Multimap<Supplier<Attribute>, AttributeData> negativeAttributeMap = HashMultimap.create();

        public NecklaceBuilder(Tier tier, Properties properties){
            super(tier, properties);
        }

        public NecklaceBuilder addNegativeAttrs(Multimap<Supplier<Attribute>, AttributeData> map){
            negativeAttributeMap.putAll(map);
            return this;
        }

        public NecklaceBuilder setNegativeAttrs(Multimap<Supplier<Attribute>, AttributeData> map){
            negativeAttributeMap = map;
            return this;
        }

        public NecklaceBuilder addNegativeAttr(Supplier<Attribute> attribute, AttributeData mod){
            negativeAttributeMap.put(attribute, mod);
            return this;
        }

        @Override
        public EyeNecklaceItem build(){
            return new EyeNecklaceItem(this);
        }
    }
}
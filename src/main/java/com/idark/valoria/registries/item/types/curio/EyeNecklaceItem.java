package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.*;
import com.idark.valoria.registries.item.types.builders.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.common.registry.item.builders.AbstractArmorBuilder.*;
import top.theillusivec4.curios.api.*;

import java.util.*;
import java.util.function.*;

public class EyeNecklaceItem extends CurioAccessoryItem {

    public EyeNecklaceItem(NecklaceBuilder builder) {
        super(builder);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        Level level = slotContext.entity().level();
        if (level.isClientSide()) return;

        boolean isNightNow = !level.isDay();
        boolean wasNightBefore = stack.getOrCreateTag().getBoolean("IsNightActive");
        if (isNightNow != wasNightBefore) {
            stack.getOrCreateTag().putBoolean("IsNightActive", isNightNow);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> m = LinkedHashMultimap.create();
        boolean isNightActive = stack.getOrCreateTag().getBoolean("IsNightActive");
        if (!isNightActive) {
            m.putAll(super.getAttributeModifiers(slotContext, uuid, stack));
        } else {
            if (builder instanceof NecklaceBuilder neckBuilder) {
                neckBuilder.negativeAttributeMap.forEach((attrSupplier, data) -> {
                    AttributeModifier modifier = new AttributeModifier(uuid, "Night Debuff", data.value(), data.operation());
                    m.put(attrSupplier.get(), modifier);
                });
            }
        }

        return m;
    }

    public static class NecklaceBuilder extends AbstractCurioBuilder<EyeNecklaceItem, NecklaceBuilder>{
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
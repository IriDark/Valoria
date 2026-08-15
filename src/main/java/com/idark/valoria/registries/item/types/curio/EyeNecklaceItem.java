package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.registries.item.types.builders.*;
import net.minecraft.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.common.registry.item.builders.AbstractArmorBuilder.*;
import pro.komaru.tridot.common.registry.item.components.*;
import pro.komaru.tridot.util.struct.data.*;
import top.theillusivec4.curios.api.*;

import java.util.*;
import java.util.function.*;

public class EyeNecklaceItem extends CurioAccessoryItem implements TooltipComponentItem{
    public EyeNecklaceItem(NecklaceBuilder builder) {
        super(builder);
    }
    private static final String DARK_ACTIVE_TAG = "IsDarkActive";

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected){
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (pLevel.isClientSide()) return;

        CompoundTag tag = pStack.getOrCreateTag();
        if(pEntity.tickCount % 100 == 0){
            boolean isDarkNow = pLevel.getMaxLocalRawBrightness(pEntity.blockPosition()) < 8;
            if(isDarkNow != tag.getBoolean(DARK_ACTIVE_TAG)){
                tag.putBoolean(DARK_ACTIVE_TAG, isDarkNow);
            }
        }
        
        if (pEntity.tickCount % 2 == 0) {
            int currentState = tag.getInt("EyeState");
            boolean isDark = tag.getBoolean(DARK_ACTIVE_TAG);
            if (isDark && currentState < 3) {
                tag.putInt("EyeState", currentState + 1);
            } else if (!isDark && currentState > 0) {
                tag.putInt("EyeState", currentState - 1);
            }
        }
    }

    public Seq<TooltipComponent> getTooltips(ItemStack pStack) {
        return Seq.with(new AbilityComponent(Component.translatable("tooltip.valoria.eye_necklace").withStyle(ChatFormatting.GRAY), Valoria.loc("textures/gui/tooltips/eye.png")));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (!stack.getOrCreateTag().getBoolean(DARK_ACTIVE_TAG)) {
            return super.getAttributeModifiers(slotContext, uuid, stack);
        }

        Multimap<Attribute, AttributeModifier> m = LinkedHashMultimap.create();
        if (builder instanceof NecklaceBuilder neckBuilder){
            neckBuilder.negativeAttributeMap.forEach((attrSupplier, data) -> {
                m.put(attrSupplier.get(), new AttributeModifier(uuid, "Darkness Debuff", data.value(), data.operation()));
            });
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
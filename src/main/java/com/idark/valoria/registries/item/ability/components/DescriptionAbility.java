package com.idark.valoria.registries.item.ability.components;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.ability.*;
import com.idark.valoria.registries.item.ability.AbilityComponent;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.common.registry.item.components.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.*;

public class DescriptionAbility extends AbilityComponent {
    public static final AbilityType<DescriptionAbility> TYPE = new AbilityType<>(new ResourceLocation(Valoria.ID, "description")) {
        @Override
        public DescriptionAbility createInstance() {
            return new DescriptionAbility();
        }
    };

    private final List<Component> lines = new ArrayList<>();
    public DescriptionAbility(){
        super(TYPE);
    }

    public DescriptionAbility addLine(List<Component> component) {
        this.lines.addAll(component);
        return this;
    }

    public DescriptionAbility addLine(Component component) {
        this.lines.add(component);
        return this;
    }

    @Override
    protected void writeCustomNBT(CompoundTag tag) {
        ListTag listTag = new ListTag();
        for (Component comp : lines) {
            listTag.add(StringTag.valueOf(Component.Serializer.toJson(comp)));
        }

        tag.put("desc_lines", listTag);
    }

    @Override
    protected void readCustomNBT(CompoundTag tag) {
        this.lines.clear();
        if (tag.contains("desc_lines", Tag.TAG_LIST)) {
            ListTag listTag = tag.getList("desc_lines", Tag.TAG_STRING);
            for (int i = 0; i < listTag.size(); i++) {
                Component comp = Component.Serializer.fromJson(listTag.getString(i));
                if (comp != null) this.lines.add(comp);
            }
        }
    }

    @Override
    public Seq<TooltipComponent> getTooltips(ItemStack pStack, CastType castType){
        Seq<TooltipComponent> seq = Seq.with();
        for (Component comp : lines) {
            seq.add(new TextComponent(comp.copy()));
        }

        return seq;
    }

    @Override
    public int execute(ServerPlayer player, Level level, ItemStack stack){
        return 0;
    }
}
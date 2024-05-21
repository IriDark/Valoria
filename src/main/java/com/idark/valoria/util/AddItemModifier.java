package com.idark.valoria.util;

import com.google.common.base.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraftforge.common.loot.*;
import net.minecraftforge.registries.*;

import javax.annotation.*;
import java.util.function.Supplier;

public class AddItemModifier extends LootModifier{

    public static final Supplier<Codec<AddItemModifier>> CODEC = Suppliers.memoize(() ->
    RecordCodecBuilder.create(inst -> codecStart(inst).and(
    inst.group(
    ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter((m) -> m.item),
    Codec.INT.optionalFieldOf("count", 1).forGetter((m) -> m.count),
    Codec.FLOAT.optionalFieldOf("chance", 1.0F).forGetter((m) -> m.chance)
    )
    ).apply(inst, AddItemModifier::new)));

    private final Item item;
    private final int count;
    private final float chance;

    public AddItemModifier(LootItemCondition[] conditionsIn, Item item, int count, float chance){
        super(conditionsIn);
        this.item = item;
        this.count = count;
        this.chance = chance;
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context){
        if(context.getRandom().nextFloat() <= chance){
            ItemStack addedStack = new ItemStack(item, count);

            if(addedStack.getCount() < addedStack.getMaxStackSize()){
                generatedLoot.add(addedStack);
            }else{
                int i = addedStack.getCount();

                while(i > 0){
                    ItemStack subStack = addedStack.copy();
                    subStack.setCount(Math.min(addedStack.getMaxStackSize(), i));
                    i -= subStack.getCount();
                    generatedLoot.add(subStack);
                }
            }
        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec(){
        return CODEC.get();
    }
}
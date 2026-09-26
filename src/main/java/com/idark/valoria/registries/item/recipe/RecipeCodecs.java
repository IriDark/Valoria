package com.idark.valoria.registries.item.recipe;

import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

import java.util.*;

/**
 * PORT NOTE: recipe serializers are (Map)Codec + StreamCodec pairs in 1.21 instead of fromJson/fromNetwork/toNetwork.
 * Shared pieces for Valoria's serializers. {@link #ITEM_STACK} accepts both the 1.21 {@code {"id","count"}} result
 * object and the 1.20.1 {@code {"item","count"}} form so existing recipe JSONs keep loading; the datagen writes the
 * new form.
 */
public final class RecipeCodecs{
    private RecipeCodecs(){}

    private static final Codec<ItemStack> LEGACY_ITEM_STACK = RecordCodecBuilder.create(i -> i.group(
        BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(ItemStack::getItem),
        Codec.INT.optionalFieldOf("count", 1).forGetter(ItemStack::getCount)
    ).apply(i, ItemStack::new));

    public static final Codec<ItemStack> ITEM_STACK = Codec.withAlternative(ItemStack.CODEC, LEGACY_ITEM_STACK);

    /** {@code {"ingredient": ..., "count": n}} entries used by the alchemy/workbench recipes. */
    public static final Codec<Pair<Ingredient, RecipeData>> COUNTED_INGREDIENT = RecordCodecBuilder.create(i -> i.group(
        Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(Pair::getFirst),
        Codec.INT.optionalFieldOf("count", 1).forGetter(p -> p.getSecond().count)
    ).apply(i, (ing, count) -> Pair.of(ing, new RecipeData(count))));

    public static final StreamCodec<RegistryFriendlyByteBuf, Pair<Ingredient, RecipeData>> COUNTED_INGREDIENT_STREAM = StreamCodec.composite(
        Ingredient.CONTENTS_STREAM_CODEC, Pair::getFirst,
        ByteBufCodecs.VAR_INT, p -> p.getSecond().count,
        (ing, count) -> Pair.of(ing, new RecipeData(count))
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, List<Pair<Ingredient, RecipeData>>> COUNTED_INGREDIENT_LIST_STREAM = COUNTED_INGREDIENT_STREAM.apply(ByteBufCodecs.list());

    public static final StreamCodec<RegistryFriendlyByteBuf, List<Ingredient>> INGREDIENT_LIST_STREAM = Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list());
}

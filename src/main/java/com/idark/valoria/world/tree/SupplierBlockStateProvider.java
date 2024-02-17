package com.idark.valoria.world.tree;

import com.idark.valoria.Valoria;
import com.idark.valoria.world.ModBlockStateProviderType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

public class SupplierBlockStateProvider extends AbstractSupplierBlockStateProvider {
    public SupplierBlockStateProvider(String path) {
        this(new ResourceLocation(Valoria.MOD_ID, path));
    }

    public SupplierBlockStateProvider(ResourceLocation path) {
        super(path);
    }

    public static final Codec<SupplierBlockStateProvider> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("key").forGetter(d -> d.key.getPath()))
            .apply(instance, SupplierBlockStateProvider::new));

    @Override
    protected BlockStateProviderType<?> type() {
        return ModBlockStateProviderType.AN_STATEPROVIDER.get();
    }
}
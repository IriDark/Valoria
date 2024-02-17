package com.idark.valoria.world;

import com.idark.valoria.Valoria;
import com.idark.valoria.world.tree.SupplierBlockStateProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProviderType {

    public static final DeferredRegister<BlockStateProviderType<?>> BLOCK_STATE_PROVIDER_TYPE = DeferredRegister.createOptional(Registries.BLOCK_STATE_PROVIDER_TYPE, Valoria.MOD_ID);

    public static final RegistryObject<BlockStateProviderType<?>> AN_STATEPROVIDER = BLOCK_STATE_PROVIDER_TYPE.register("an_stateprovider", () -> new BlockStateProviderType<>(SupplierBlockStateProvider.CODEC));

    public static void register(IEventBus eventBus) {
        BLOCK_STATE_PROVIDER_TYPE.register(eventBus);
    }
}
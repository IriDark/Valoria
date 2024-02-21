package com.idark.valoria.registries.world.block.types;

import com.idark.valoria.Valoria;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodTypes {
    public static final WoodType SHADEWOOD = WoodType.register(new WoodType(new ResourceLocation(Valoria.MOD_ID, "shadewood").toString(), BlockSetType.OAK));
}
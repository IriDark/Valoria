package com.idark.valoria.registries.block.types;

import com.idark.valoria.Valoria;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodTypes{
    public static final WoodType SHADEWOOD = WoodType.register(new WoodType(new ResourceLocation(Valoria.ID, "shadewood").toString(), BlockSetType.OAK));
    public static final WoodType ELDRITCH = WoodType.register(new WoodType(new ResourceLocation(Valoria.ID, "eldritch").toString(), BlockSetType.OAK));
}
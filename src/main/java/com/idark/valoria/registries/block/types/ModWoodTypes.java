package com.idark.valoria.registries.block.types;

import com.idark.valoria.*;
import net.minecraft.world.level.block.state.properties.*;

public class ModWoodTypes{
    public static final WoodType SHADEWOOD = WoodType.register(new WoodType(Valoria.loc("shade").toString(), BlockSetType.OAK));
    public static final WoodType ELDRITCH = WoodType.register(new WoodType(Valoria.loc("eldritch").toString(), BlockSetType.OAK));
    public static final WoodType DREADWOOD = WoodType.register(new WoodType(Valoria.loc("dread").toString(), BlockSetType.OAK));
}
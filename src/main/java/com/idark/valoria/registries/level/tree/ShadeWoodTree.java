package com.idark.valoria.registries.level.tree;

import com.idark.valoria.registries.level.*;
import net.minecraft.world.level.block.grower.*;

import java.util.*;

public final class ShadeWoodTree{
    public static final TreeGrower INSTANCE = new TreeGrower("valoria:shadewood", Optional.of(LevelGen.FANCY_SHADEWOOD_TREE), Optional.of(LevelGen.SHADEWOOD_TREE), Optional.empty());

    private ShadeWoodTree(){}
}

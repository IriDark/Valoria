package com.idark.valoria.registries.level.tree;

import com.idark.valoria.registries.level.*;
import net.minecraft.world.level.block.grower.*;

import java.util.*;

public final class EldritchTree{
    public static final TreeGrower INSTANCE = new TreeGrower("valoria:eldritch", Optional.of(LevelGen.FANCY_ELDRITCH_TREE), Optional.of(LevelGen.ELDRITCH_TREE), Optional.empty());

    private EldritchTree(){}
}

package com.idark.darkrpg.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.Random;

public class Elemental_manipulator extends Block {

    public Elemental_manipulator() {
        super(
            Properties.create(Material.IRON)
                .hardnessAndResistance(3f)
        );
    }
}
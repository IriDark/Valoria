package com.idark.valoria.client;

import com.idark.valoria.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.resources.model.*;

import static pro.komaru.tridot.client.model.TridotModels.addLayer;

public class ValoriaLayers{
    public static final ModelLayerLocation INFERNAL_ARMOR_INNER = new ModelLayerLocation(Valoria.loc("infernal_armor"), "inner");
    public static final ModelLayerLocation INFERNAL_ARMOR_OUTER = new ModelLayerLocation(Valoria.loc("infernal_armor"), "outer");
    public static final ModelLayerLocation VOID_ARMOR_INNER = new ModelLayerLocation(Valoria.loc("void_armor"), "inner");
    public static final ModelLayerLocation VOID_ARMOR_OUTER = new ModelLayerLocation(Valoria.loc("void_armor"), "outer");
    public static final ModelLayerLocation PHANTASM_ARMOR_INNER = new ModelLayerLocation(Valoria.loc("phantasm_armor"), "inner");
    public static final ModelLayerLocation PHANTASM_ARMOR_OUTER = new ModelLayerLocation(Valoria.loc("phantasm_armor"), "outer");

    public static ModelLayerLocation GAS_MASK_LAYER = new ModelLayerLocation(Valoria.loc("gas_mask"), "inner");
    public static ModelLayerLocation RESPIRATOR_LAYER = new ModelLayerLocation(Valoria.loc("respirator"), "main");
    public static ModelLayerLocation CROWN_LAYER = new ModelLayerLocation(Valoria.loc("crown"), "main");
    public static ModelLayerLocation MONOCLE_LAYER = new ModelLayerLocation(Valoria.loc("monocle"), "main");

    public static ModelLayerLocation NECKLACE_LAYER = new ModelLayerLocation(Valoria.loc("necklace"), "main");
    public static ModelLayerLocation HANDS_LAYER = new ModelLayerLocation(Valoria.loc("hands"), "main");
    public static ModelLayerLocation HANDS_LAYER_SLIM = new ModelLayerLocation(Valoria.loc("hands_slim"), "main");
    public static ModelLayerLocation BELT_LAYER = new ModelLayerLocation(Valoria.loc("belt"), "main");
    public static ModelLayerLocation BAG_LAYER = new ModelLayerLocation(Valoria.loc("jewelry_bag"), "main");
    public static ModelResourceLocation KEG_MODEL = new ModelResourceLocation(Valoria.ID, "keg_barrel", "");
    public static ModelResourceLocation SPHERE = new ModelResourceLocation(Valoria.ID, "elemental_sphere", "");
    public static ModelResourceLocation CYST = new ModelResourceLocation(Valoria.ID, "cyst", "");
    public static ModelLayerLocation THE_FALLEN_COLLECTOR_ARMOR_LAYER = addLayer(Valoria.ID, "the_fallen_collector_armor_layer");
}

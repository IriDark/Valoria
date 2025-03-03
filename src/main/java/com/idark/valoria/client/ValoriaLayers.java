package com.idark.valoria.client;

import com.idark.valoria.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.*;

import static pro.komaru.tridot.client.TridotModels.addLayer;

public class ValoriaLayers{
    public static final ModelLayerLocation INFERNAL_ARMOR_INNER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "infernal_armor"), "inner");
    public static final ModelLayerLocation INFERNAL_ARMOR_OUTER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "infernal_armor"), "outer");
    public static final ModelLayerLocation VOID_ARMOR_INNER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "void_armor"), "inner");
    public static final ModelLayerLocation VOID_ARMOR_OUTER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "void_armor"), "outer");
    public static final ModelLayerLocation PHANTASM_ARMOR_INNER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "phantasm_armor"), "inner");
    public static final ModelLayerLocation PHANTASM_ARMOR_OUTER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "phantasm_armor"), "outer");

    public static ModelLayerLocation NECKLACE_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "necklace"), "main");
    public static ModelLayerLocation HANDS_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "hands"), "main");
    public static ModelLayerLocation HANDS_LAYER_SLIM = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "hands_slim"), "main");
    public static ModelLayerLocation BELT_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "belt"), "main");
    public static ModelLayerLocation BAG_LAYER = new ModelLayerLocation(new ResourceLocation(Valoria.ID, "jewelry_bag"), "main");
    public static ModelResourceLocation KEG_MODEL = new ModelResourceLocation(Valoria.ID, "keg_barrel", "");
    public static ModelResourceLocation SPHERE = new ModelResourceLocation(Valoria.ID, "elemental_sphere", "");
    public static ModelResourceLocation CYST = new ModelResourceLocation(Valoria.ID, "cyst", "");
    public static ModelLayerLocation THE_FALLEN_COLLECTOR_ARMOR_LAYER = addLayer(Valoria.ID, "the_fallen_collector_armor_layer");
}

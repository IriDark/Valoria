package com.idark.valoria.registries.entity;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.AttributeReg.*;

import java.util.*;

public class ElementalTypes {
    public static final List<ElementalType> ELEMENTALS = List.of(
        new ElementalType(AttributeReg.INFERNAL_DAMAGE, AttributeReg.INFERNAL_RESISTANCE),
        new ElementalType(AttributeReg.DEPTH_DAMAGE, AttributeReg.DEPTH_RESISTANCE),
        new ElementalType(AttributeReg.NATURE_DAMAGE, AttributeReg.NATURE_RESISTANCE),
        new ElementalType(AttributeReg.VOID_DAMAGE, AttributeReg.VOID_RESISTANCE)
    );
}
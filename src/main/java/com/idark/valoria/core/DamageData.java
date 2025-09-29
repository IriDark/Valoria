package com.idark.valoria.core;

import com.idark.valoria.util.*;
import net.minecraft.network.chat.*;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.function.*;

public class DamageData{
    public static final Seq<DamageData> dataTypes = Seq.with();
    private final Predicate<DamageSource> predicate;
    private final Col color;
    private final Component text;

    static {
        DamageData FIRE = register(new DamageData(d -> d.is(DamageTypeTags.IS_FIRE), Pal.infernal));
        DamageData FREEZE = register(new DamageData(d -> d.is(DamageTypeTags.IS_FREEZING), Pal.oceanic));
        DamageData DROWNING = register(new DamageData(d -> d.is(DamageTypeTags.IS_DROWNING), Pal.oceanic));
    }

    public DamageData(Predicate<DamageSource> predicate, Component text, Col color){
        this.predicate = predicate;
        this.color = color;
        this.text = text;
    }

    public DamageData(Predicate<DamageSource> predicate, Col color){
        this.predicate = predicate;
        this.color = color;
        this.text = null;
    }

    public static DamageData register(DamageData data) {
        if (dataTypes.contains(data)) {
            throw new IllegalArgumentException(data + " is already registered!");
        }

        dataTypes.add(data);
        return data;
    }

    public Component getText(){
        return text;
    }

    public Col getColor() {
        return color;
    }

    public Predicate<DamageSource> predicate(){
        return predicate;
    }
}
package com.idark.valoria.registries;

import com.idark.valoria.Valoria;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AttackRegistry{
    private static final Map<String, AttackRegistry> REGISTRY = new HashMap<>();
    private final String namespace;
    private final String id;
    private final int[] color;

    public AttackRegistry(String pNamespace, String id, Color color){
        this.namespace = pNamespace;
        this.id = id;
        this.color = new int[]{color.getRed(), color.getGreen(), color.getBlue()};
        register(this);
    }

    public AttackRegistry(String namespace, String id){
        this(namespace, id, new Color(255, 255, 255));
    }

    public AttackRegistry(String namespace, String id, int red, int green, int blue){
        this(namespace, id, new Color(red, green, blue));
    }

    public String toString(){
        return this.namespace + ":" + this.id;
    }

    private static void register(AttackRegistry attack){
        String key = attack.toString();
        if(REGISTRY.containsKey(key)){
            throw new IllegalArgumentException("Attack ID " + key + " is already registered!");
        }

        REGISTRY.put(attack.toString(), attack);
    }

    public static AttackRegistry byId(String id){
        return REGISTRY.getOrDefault(id, NONE);
    }

    public String getNamespace(){
        return namespace;
    }

    public String getId(){
        return id;
    }

    public int[] getColor(){
        return color;
    }

    public static final AttackRegistry NONE = new AttackRegistry(Valoria.ID, "none");
}

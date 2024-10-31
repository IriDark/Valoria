package com.idark.valoria.client.ui.bossbars;

import com.idark.valoria.*;
import net.minecraft.resources.*;

import java.util.*;

//todo move to lib
public class Bossbar{
    public static Map<String, Bossbar> bossbars = new HashMap<>();
    static{
        bossbars.put("Necromancer", new Bossbar(new ResourceLocation(Valoria.ID, "textures/gui/bossbars/necromancer.png")));
    }

    private final ResourceLocation texture;
    public Bossbar(ResourceLocation tex) {
        this.texture = tex;
    }

    public ResourceLocation getTexture(){
        return texture;
    }
}
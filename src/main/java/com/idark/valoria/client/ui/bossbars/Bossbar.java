package com.idark.valoria.client.ui.bossbars;

import com.idark.valoria.Valoria;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

//todo delete
@OnlyIn(Dist.CLIENT)
public class Bossbar{
    public static Map<String, Bossbar> bossbars = new HashMap<>();

    static{
        bossbars.put("Necromancer", new Bossbar(new ResourceLocation(Valoria.ID, "textures/gui/bossbars/necromancer.png")));
    }

    public boolean rainbow = false;
    private final Color color;
    private final ResourceLocation texture;

    public Bossbar(ResourceLocation tex){
        this.texture = tex;
        this.color = new Color(1, 1, 1);
    }

    public Bossbar(Color color){
        this.texture = new ResourceLocation(Valoria.ID, "textures/gui/bossbars/base.png");
        this.color = color;
    }

    public Bossbar(boolean rainbow){
        this.texture = new ResourceLocation(Valoria.ID, "textures/gui/bossbars/base.png");
        this.color = new Color(1, 1, 1);
        this.rainbow = rainbow;
    }

    public Color getColor(){
        return color;
    }

    public ResourceLocation getTexture(){
        return texture;
    }
}
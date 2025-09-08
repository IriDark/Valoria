package com.idark.valoria.client;

import com.idark.valoria.*;
import net.minecraft.resources.*;

public enum Cloaks{
    IriDark_("IriDark_", cloakTex("calamitas_cloak")),
    IriDark("IriDark", cloakTex("calamitas_cloak")),
    Dev("Dev", cloakTex("calamitas_cloak")),
    Auriny("Auriny", cloakTex("calamitas_cloak")),
    Kerdo("_Kerdo_", cloakTex("calamitas_cloak")),
    Tanc57("TANC57", cloakTex("tanc57_cloak")),

    ;

    public final String name;
    public final ResourceLocation texture;
    Cloaks(String name, ResourceLocation texture) {
        this.name = name;
        this.texture = texture;
    }

    public static ResourceLocation cloakTex(String name) {
        return Valoria.loc("textures/entity/cloak/" + name + ".png");
    }

    public String getName(){
        return name;
    }

    public ResourceLocation getTexture(){
        return texture;
    }
}
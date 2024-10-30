package com.idark.valoria.core.enums;

import com.idark.valoria.*;
import com.idark.valoria.core.interfaces.*;
import net.minecraft.resources.*;

public enum Bosses implements Bossbar{
    NECROMANCER("Necromancer", new ResourceLocation(Valoria.ID, "textures/gui/bossbars/necromancer.png")),
    ENDER_DRAGON("Ender Dragon", new ResourceLocation(Valoria.ID, "textures/gui/bossbars/necromancer.png")) //test
    ;

    public final String name;
    public final ResourceLocation texture;
    Bosses(String name, ResourceLocation texture) {
        this.name = name;
        this.texture = texture;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public ResourceLocation getTexture(){
        return texture;
    }
}

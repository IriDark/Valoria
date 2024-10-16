package com.idark.valoria.client.render;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ValoriaEffects extends DimensionSpecialEffects {
    public ValoriaEffects() {
        super(192, true, SkyType.END, false, false);
    }

    @NotNull
    public Vec3 getBrightnessDependentFogColor(Vec3 p_108908_, float p_108909_) {
        return p_108908_.multiply(p_108909_ * 0.94F + 0.06F, p_108909_ * 0.94F + 0.06F, p_108909_ * 0.91F + 0.09F);
    }

    public boolean isFoggyAt(int p_108905_, int p_108906_) {
        return false;
    }
}

package com.idark.valoria.registries.item.types;

import com.idark.valoria.registries.item.types.curio.charm.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.client.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.render.gui.particle.ParticleEmitterHandler.*;
import pro.komaru.tridot.client.render.gui.particle.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

public class ElementalCharmItem extends ImmunityItem implements IGUIParticleItem{
    public ElementalCharmItem(Properties pProperties){
        super(pProperties);
    }

    @Override
    public void spawnParticlesLate(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y){
        ScreenParticleBuilder.create(TridotScreenParticles.WISP, target)
        .setTransparencyData(GenericParticleData.create(0.125f, 0f).setEasing(Interp.bounce).build())
        .setScaleData(GenericParticleData.create(0.5f, 0).setEasing(Interp.bounceIn).build())
        .setColorData(ColorParticleData.create(Col.rainbow(ClientTick.getTotal())).build())
        .randomOffset(5, 5)
        .randomVelocity(1.5f, 1.5f)
        .setVelocity(0, -0.5f, 0)
        .setLifetime(10)
        .spawnOnStack(0, 2);
    }
}
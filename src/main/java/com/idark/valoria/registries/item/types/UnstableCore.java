package com.idark.valoria.registries.item.types;

import net.minecraft.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.client.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.render.gui.particle.ParticleEmitterHandler.*;
import pro.komaru.tridot.client.render.gui.particle.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.*;

public class UnstableCore extends CoreItem implements ParticleItemEntity, IGuiRenderItem, IGUIParticleItem{
    public UnstableCore(@NotNull ParticleType<?> pType, Properties pProperties, String pCoreID){
        super(pType, pProperties, pCoreID);
    }

    @Override
    public ColorParticleData getColor(){
        return ColorParticleData.create().setRandomColor().build();
    }

    @Override
    public int getGivenCores(){
        return Tmp.rnd.nextInt(1, 4);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced){
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("tooltip.valoria.unstable_core").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void spawnParticlesLate(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y){
        ScreenParticleBuilder.create(TridotScreenParticles.WISP, target)
        .setTransparencyData(GenericParticleData.create(0.125f, 0f).setEasing(Interp.bounce).build())
        .setScaleData(GenericParticleData.create(0.5f, 0).setEasing(Interp.bounce).build())
        .setColorData(ColorParticleData.create(Col.rainbow(ClientTick.getTotal())).build())
        .randomOffset(5, 5)
        .randomVelocity(1.5f, 1.5f)
        .setVelocity(0, -0.8f, 0)
        .setLifetime(10)
        .spawnOnStack(0, 3);
    }
}

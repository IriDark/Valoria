package com.idark.valoria.registries.item.types;

import net.minecraft.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.util.*;

import java.util.*;

public class UnstableCore extends CoreItem{
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
}

package com.idark.valoria.registries.item.types;

import com.idark.valoria.core.interfaces.*;
import net.minecraft.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.client.graphics.particle.data.*;

import java.awt.*;
import java.util.List;

import static com.idark.valoria.client.particle.ParticleEffects.spawnItemParticles;

//todo
public class CoreItem extends Item implements ParticleItemEntity{
    private final String coreName;
    public ParticleType<?> particle;
    public ColorParticleData color;
    private final int givenCores;

    public CoreItem(@NotNull ParticleType<?> pType, Properties pProperties, int pGivenCores, Color pColor, Color pColorTo, String pCoreID){
        super(pProperties);
        particle = pType;
        givenCores = pGivenCores;
        color = ColorParticleData.create(pColor, pColorTo).build();
        coreName = pCoreID;
    }

    public CoreItem(@NotNull ParticleType<?> pType, Properties pProperties, int pGivenCores, Color pColor, Color pColorTo, RegistryObject<Item> item){
        super(pProperties);
        particle = pType;
        givenCores = pGivenCores;
        color = ColorParticleData.create(pColor, pColorTo).build();
        coreName = item.getId().getPath();
    }

    public String getCoreName(){
        return coreName;
    }

    public Color getCoreColor(){
        return new Color(color.r1, color.g1, color.b1).darker();
    }

    public int getGivenCores(){
        return givenCores;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced){
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("tooltip.valoria.core").withStyle(ChatFormatting.GRAY));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnParticles(Level level, ItemEntity entity){
        spawnItemParticles(level, entity, particle, color);
    }

//    @OnlyIn(Dist.CLIENT)
//    @Override
//    public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
//        ScreenParticleRegistry.spawnCoreParticles(target, color);
//    }
}

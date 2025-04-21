package com.idark.valoria.registries.item.types;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.*;
import net.minecraft.core.particles.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.client.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.client.render.gui.particle.ParticleEmitterHandler.*;
import pro.komaru.tridot.client.render.gui.particle.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import static com.idark.valoria.client.particle.ParticleEffects.spawnItemParticles;

//todo
public class ParticleMaterialItem extends Item implements ParticleItemEntity, IGuiRenderItem, IGUIParticleItem{
    public ParticleType<?> particle;
    public ColorParticleData color;
    public float alpha;

    public ParticleMaterialItem(Properties pProperties, float alpha, ColorParticleData color, ParticleType<?> particle){
        super(pProperties);
        this.alpha = alpha;
        this.color = color;
        this.particle = particle;
    }

    public ParticleMaterialItem(Properties pProperties, ColorParticleData color){
        super(pProperties);
        this.alpha = 0.35f;
        this.color = color;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnParticles(Level level, ItemEntity entity){
        spawnItemParticles(level, entity, particle, color);
    }

    @Override
    public void spawnParticlesLate(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y){
        if(stack.is(ItemsRegistry.elementalCrystal.get())){
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

    @Override
    public void onGuiRender(GuiGraphics gfx, LivingEntity livingEntity, Level level, ItemStack itemStack, int x, int y, int seed, int guiOffset){
        if(!itemStack.is(ItemsRegistry.elementalCrystal.get())){
            PoseStack poseStack = gfx.pose();
            poseStack.pushPose();

            poseStack.translate(x + 8, y + 9, 100);
            RenderBuilder.create().setRenderType(TridotRenderTypes.ADDITIVE_TEXTURE)
            .setUV(Utils.Render.getSprite(Valoria.ID, "particle/smoke"))
            .setColor(color.r1, color.g1, color.b1).setAlpha(1f)
            .renderCenteredQuad(poseStack, 10f)
            .endBatch();

            poseStack.popPose();
        }
    }
}
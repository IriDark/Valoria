package com.idark.valoria.registries.item.types;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.*;
import net.minecraft.client.gui.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.util.*;

import java.util.*;

import static com.idark.valoria.client.particle.ParticleEffects.spawnItemParticles;

//todo
public class CoreItem extends Item implements ParticleItemEntity, IGuiRenderItem{
    private final String coreName;
    public ParticleType<?> particle;
    public ColorParticleData color;
    private final int givenCores;

    public CoreItem(@NotNull ParticleType<?> pType, Properties pProperties, int pGivenCores, Col pColor, Col pColorTo, String pCoreID){
        super(pProperties);
        particle = pType;
        givenCores = pGivenCores;
        color = ColorParticleData.create(pColor, pColorTo).build();
        coreName = pCoreID;
    }

    public CoreItem(@NotNull ParticleType<?> pType, Properties pProperties, int pGivenCores, Col pColor, Col pColorTo, RegistryObject<Item> item){
        super(pProperties);
        particle = pType;
        givenCores = pGivenCores;
        color = ColorParticleData.create(pColor, pColorTo).build();
        coreName = item.getId().getPath();
    }

    public String getCoreName(){
        return coreName;
    }

    public Col getCoreColor(){
        return new Col(color.r1, color.g1, color.b1).darker();
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

    @Override
    public void onGuiRender(GuiGraphics gfx, LivingEntity livingEntity, Level level, ItemStack itemStack, int x, int y, int seed, int guiOffset){
        PoseStack poseStack = gfx.pose();
        if(itemStack.is(ItemsRegistry.voidCore.get())) {
            poseStack.pushPose();
            poseStack.translate(x + 8, y + 9, 100);
            RenderBuilder.create().setRenderType(TridotRenderTypes.TRANSLUCENT_TEXTURE)
            .setUV(Utils.Render.getSprite(Valoria.ID, "particle/smoke"))
            .setColor(Col.fromHex("562a8a")).setAlpha(1f)
            .renderCenteredQuad(poseStack, 15f)
            .endBatch();
            poseStack.popPose();
        } else {
            poseStack.pushPose();
            poseStack.translate(x + 8, y + 9, 100);
            RenderBuilder.create().setRenderType(TridotRenderTypes.ADDITIVE_TEXTURE)
            .setUV(Utils.Render.getSprite(Valoria.ID, "particle/smoke"))
            .setColor(color.r1, color.g1, color.b1).setAlpha(1f)
            .renderCenteredQuad(poseStack, 14f)
            .endBatch();
            poseStack.popPose();
        }
    }
}
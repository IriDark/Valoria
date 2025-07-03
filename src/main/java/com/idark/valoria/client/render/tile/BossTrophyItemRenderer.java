package com.idark.valoria.client.render.tile;

import com.idark.valoria.registries.block.types.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.nbt.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.*;
import pro.komaru.tridot.client.*;

import java.util.function.*;

public class BossTrophyItemRenderer extends BlockEntityWithoutLevelRenderer{
    public BossTrophyItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay){
        Item item = stack.getItem();
        Minecraft mc = Minecraft.getInstance();
        Entity displayed = null;
        if(item instanceof BlockItem blockItem){
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(blockItem.getBlock().defaultBlockState(), poseStack, buffer, light, overlay);
            if(blockItem.getBlock() instanceof BossTrophyBlock trophy) {
                if(trophy.getEntity() != null){
                    var tag = new CompoundTag();
                    tag.putString("id", ForgeRegistries.ENTITY_TYPES.getKey(trophy.getEntity().get()).toString());
                    displayed = EntityType.loadEntityRecursive(tag, mc.level, Function.identity());
                }
            }
        }

        if (displayed != null) {
            poseStack.pushPose();

            poseStack.translate(0.5, 0.5, 0.5);
            float ticks = (ClientTick.ticksInGame + Minecraft.getInstance().getPartialTick()) * 2 % 360;
            poseStack.translate(0, Math.sin(Math.toRadians(ticks)) * 0.05, 0);
            poseStack.mulPose(Axis.YP.rotationDegrees(ticks));

            float scale = 1.25f;
            float max = Math.max(displayed.getBbWidth(), displayed.getBbHeight());
            if (max > 1.0f) scale /= max;
            poseStack.scale(scale, scale, scale);

            Minecraft.getInstance().getEntityRenderDispatcher().render(displayed, 0, 0, 0, 0, Minecraft.getInstance().getPartialTick(),
            poseStack, buffer, light);

            poseStack.popPose();
        }
    }
}
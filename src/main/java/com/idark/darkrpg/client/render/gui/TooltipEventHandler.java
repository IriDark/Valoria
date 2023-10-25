package com.idark.darkrpg.client.render.gui;

import com.idark.darkrpg.DarkRPG;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TooltipEventHandler {
	public static final ResourceLocation TRASH = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/trash.png");
	public static final ResourceLocation DEFAULT = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/default.png");
	public static final ResourceLocation COMMON = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/common.png");
	public static final ResourceLocation UNCOMMON = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/uncommon.png");
	public static final ResourceLocation RARE = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/rare.png");
	public static final ResourceLocation EPIC = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/epic.png");
	public static final ResourceLocation MYTHIC = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/mythic.png");
	public static final ResourceLocation MASTERY = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/mastery.png");
	public static final ResourceLocation RELIC = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/relic.png");
	public static final ResourceLocation ETERNAL = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/eternal.png");
	
	@SubscribeEvent
	public static void onPostTooltipEvent(RenderTooltipEvent.Color event) {
        /*ItemStack stack = event.getStack();

        int x = event.getX();
        int y = event.getY();
        int width = event.getWidth();
        int height = event.getHeight();
		Minecraft mc = Minecraft.getInstance();
		MatrixStack matrix = event.getMatrixStack();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderSystem.translatef(0, 0, 900);		
		matrix.pushPose();		

		if (stack.getItem() instanceof BlazeReapItem) {
            mc.textureManager.bind(ETERNAL);
            Screen.blit(matrix, x, y + 11, 0, 0, 80, 16, 80, 16);
		}
		matrix.popPose();
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (!stack.isEmpty()) {
            List<ITextComponent> tooltip = event.getToolTip();
			if (stack.getItem() instanceof BlazeReapItem) {
				tooltip.add(1, new StringTextComponent("                "));
				tooltip.add(1, new StringTextComponent("                "));
			}
		}
		
    RenderSystem.disableBlend();		*/
	}
}
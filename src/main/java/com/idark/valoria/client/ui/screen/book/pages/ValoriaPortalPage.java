package com.idark.valoria.client.ui.screen.book.pages;

import com.idark.valoria.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;

public class ValoriaPortalPage extends TextPage{
    public static final ResourceLocation PORTAL = new ResourceLocation(Valoria.ID, "textures/gui/book/valoria_portal.png");
    public ValoriaPortalPage(String textKey){
        super(textKey);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics gui, int x, int y, int mouseX, int mouseY){
        super.render(gui, x, y, mouseX, mouseY);
        gui.blit(PORTAL, x + 135, y + 15, 0, 0, 112, 112, 112, 112);
    }
}

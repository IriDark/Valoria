package com.idark.valoria.client.ui.screen.book;

import com.idark.valoria.*;
import com.idark.valoria.api.unlockable.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;

import javax.annotation.*;

@OnlyIn(Dist.CLIENT)
public class Bookmark{

    public static final ResourceLocation BACKGROUND = new ResourceLocation(Valoria.ID, "textures/gui/book/lexicon.png");
    public final Item item;
    public final int x = 267;
    private final int y;
    private final int index;
    public final Chapter chapter;
    public final MutableComponent translate;
    public Unlockable unlockable;

    public Bookmark(int index, Item item, @Nullable MutableComponent pTranslate, Chapter chapter){
        this.index = index;
        this.item = item;
        this.translate = pTranslate;
        this.chapter = chapter;
        this.y = getColumn();
        LexiconChapters.categories.add(this);
    }

    public Bookmark(int index, Item item, @Nullable MutableComponent pTranslate, Chapter chapter, Unlockable pUnlockablePage){
        this.index = index;
        this.item = item;
        this.translate = pTranslate;
        this.chapter = chapter;
        this.y = getColumn();
        unlockable = pUnlockablePage;
        LexiconChapters.categories.add(this);
    }

    public void render(GuiGraphics gui, int guiLeft, int guiTop, int mouseX, int mouseY){
        if(isUnlocked()){
            if(mouseX >= guiLeft + x + 2 && mouseX < guiLeft + x + 35 && mouseY >= guiTop + y + 4 && mouseY < guiTop + y + 25){
                gui.blit(BACKGROUND, guiLeft + x, guiTop + y, 0, 218, 35, 25, 512, 512);
                gui.renderItem(item.getDefaultInstance(), guiLeft + x + 7, guiTop + y + 4);
                if(translate != null){
                    renderTooltip(gui, translate, mouseX + 4, mouseY + 10);
                }
            }else{
                gui.blit(BACKGROUND, guiLeft + x, guiTop + y, 287, 15, 18, 18, 512, 512);
                gui.blit(BACKGROUND, guiLeft + x, guiTop + y, 0, 193, 35, 25, 512, 512);
                gui.renderItem(item.getDefaultInstance(), guiLeft + x + 2, guiTop + y + 4);
            }
        }
    }

    public int getColumn(){
        return 10 + (index * 28);
    }

    public Chapter getChapter(){
        return chapter;
    }

    public boolean onClick(double mouseX, double mouseY, int guiLeft, int guiTop){
        return mouseX >= guiLeft + x + 2 && mouseX < guiLeft + x + 35 && mouseY >= guiTop + y + 4 && mouseY < guiTop + y + 25;
    }

    public void renderTooltip(GuiGraphics gui, MutableComponent component, int x, int y){
        gui.renderTooltip(Minecraft.getInstance().font, component, x, y);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isUnlocked(){
        if(unlockable == null){
            return true;
        }else{
            return (UnlockUtils.isUnlocked(Minecraft.getInstance().player, unlockable));
        }
    }
}

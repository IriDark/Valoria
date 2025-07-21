package com.idark.valoria.client.ui.screen.book.pages;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.screen.book.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.language.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.loading.*;
import pro.komaru.tridot.client.*;

import java.util.*;

public class TextPage extends Page{
    public String text, title;
    public boolean hasTitle = true;
    public static final ResourceLocation BACKGROUND = new ResourceLocation(Valoria.ID, "textures/gui/book/codex.png");
    public ItemStack result;
    public Ingredient[] inputs;
    private boolean hasRecipe = false;

    public TextPage(String textKey){
        this.text = textKey;
        this.title = textKey + ".name";
    }

    public TextPage hideTitle(){
        hasTitle = false;
        return this;
    }

    public TextPage withCustomTitle(String title){
        this.title = title;
        return this;
    }

    public TextPage withCraftEntry(ItemStack result){
        this.result = result;
        if(FMLEnvironment.dist.isClient()){
            Minecraft mc = Minecraft.getInstance();

            // null on resource load, initialized on first open
            if(mc.level != null){
                RecipeManager manager = mc.level.getRecipeManager();
                Optional<? extends Recipe<?>> optional = manager.getRecipes().stream()
                .filter(r -> ItemStack.isSameItemSameTags(r.getResultItem(mc.level.registryAccess()), result))
                .findFirst();

                if(optional.isPresent()){
                    Recipe<?> recipe = optional.get();
                    NonNullList<Ingredient> ingredients = recipe.getIngredients();

                    this.inputs = ingredients.toArray(Ingredient[]::new);
                    this.hasRecipe = true;
                }
            }
        }

        return this;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics gui, int x, int y, int mouseX, int mouseY){
        int yOffset = 13;
        if(hasTitle){
            drawText(gui, this.title, x + (120 - Minecraft.getInstance().font.width(I18n.get(this.title))) / 2, y + 22 - Minecraft.getInstance().font.lineHeight, false);
            yOffset += 16;
        }

        drawWrappingText(gui, I18n.get(text), x + 3, y + yOffset, 120, false);
        if(hasRecipe){
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    int index = i * 3 + j;
                    if (index < inputs.length) {
                        Ingredient ingredient = inputs[index];
                        ItemStack[] matchingStacks = ingredient.getItems();
                        if (matchingStacks.length > 0) {
                            int cycle = (ClientTick.ticksInGame / 40) % matchingStacks.length;
                            ItemStack display = matchingStacks[cycle];

                            BookGui.drawItemWithTooltip(display, x + 22 + j * 18, y + 35 + i * 18 + 50, gui, mouseX, mouseY, true);
                        }
                    }

                    gui.blit(BACKGROUND, x + 21 + j * 18, y + 34 + i * 18 + 50, 287, 15, 18, 18, 512, 512);
                }
            }

            gui.blit(BACKGROUND, x + 88, y + 56 + 46, 287, 15, 18, 18, 512, 512);
            BookGui.drawItemWithTooltip(result, x + 89, y + 57 + 46, gui, mouseX, mouseY, true);
            resultArrow(gui, x, y);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void resultArrow(GuiGraphics gui, int x, int y){
        gui.blit(BACKGROUND, x + 77, y + 107, 306, 15, 9, 7, 512, 512);
    }
}
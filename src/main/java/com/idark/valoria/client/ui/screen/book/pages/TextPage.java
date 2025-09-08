package com.idark.valoria.client.ui.screen.book.pages;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.screen.book.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.client.resources.language.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.loading.*;
import org.joml.*;
import pro.komaru.tridot.client.*;

import javax.annotation.*;
import java.lang.Math;
import java.util.*;

public class TextPage extends Page{
    public String text, title;
    public boolean hasTitle = true;
    public static final ResourceLocation BACKGROUND = new ResourceLocation(Valoria.ID, "textures/gui/book/codex.png");
    public ItemStack result;
    public Ingredient[] inputs;
    private boolean hasRecipe = false;

    @Nullable public LivingEntity entity;
    @Nullable public EntityType<? extends LivingEntity> type;

    public int entityX, entityY = 0;
    public int recipeX = 21, recipeY = 34;
    public int entityScale = 25;
    public boolean isDark = false;
    public static final Quaternionf ENTITY_ANGLE = (new Quaternionf()).rotationXYZ(0.43633232F, 0.0F, (float)Math.PI);

    public TextPage(String textKey){
        this.text = textKey;
        this.title = textKey + ".name";
    }

    public TextPage withEntity(EntityType<? extends LivingEntity> type) {
        this.type = type;
        return this;
    }

    public TextPage setCraftData(int x, int y) {
        this.recipeX = x;
        this.recipeY = y;
        return this;
    }

    public TextPage setEntityData(int x, int y, int scale, boolean dark) {
        this.entityX = x;
        this.entityY = y;
        this.entityScale = scale;
        this.isDark = dark;
        return this;
    }

    public TextPage setEntityData(int x, int y, int scale) {
        this.entityX = x;
        this.entityY = y;
        this.entityScale = scale;
        return this;
    }

    public TextPage hideTitle(){
        hasTitle = false;
        return this;
    }

    public TextPage withCustomTitle(String title){
        this.title = title;
        return this;
    }

    @Override
    public void init(){
        super.init();
        if(FMLEnvironment.dist.isClient()){
            if(result != null && result.getItem() != Items.AIR){
                Minecraft mc = Minecraft.getInstance();
                if(mc.level != null){
                    RecipeManager manager = mc.level.getRecipeManager();
                    Optional<? extends Recipe<?>> optional = manager.getRecipes().stream()
                    .filter(r -> ItemStack.isSameItem(r.getResultItem(mc.level.registryAccess()), result))
                    .findFirst();
                    if(optional.isPresent()){
                        Recipe<?> recipe = optional.get();
                        NonNullList<Ingredient> ingredients = recipe.getIngredients();

                        this.inputs = ingredients.toArray(Ingredient[]::new);
                        this.hasRecipe = true;
                    }
                }
            }
        }
    }

    public TextPage withCraftEntry(ItemStack result){
        this.result = result;
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

                    gui.blit(BACKGROUND, x + recipeX + j * 18, y + recipeY + i * 18 + 50, 287, 15, 18, 18, 512, 512);
                }
            }

            gui.blit(BACKGROUND, x + 88, y + 56 + 46, 287, 15, 18, 18, 512, 512);
            BookGui.drawItemWithTooltip(result, x + 89, y + 57 + 46, gui, mouseX, mouseY, true);
            resultArrow(gui, x, y);
        }

        Minecraft mc = Minecraft.getInstance();
        if(type != null) renderEntity(isDark, gui, type, x, y, entityScale, mc, yOffset);
    }

    @OnlyIn(Dist.CLIENT)
    public void renderEntity(boolean isDark, GuiGraphics gui, EntityType<? extends LivingEntity> type, int x, int y, int scale, Minecraft mc, int yOffset){
        this.entity = type.create(mc.level);
        if(entity != null) {
            this.entity.setYBodyRot(210.0F);
            this.entity.setYHeadRot(210.0F);
            if(isDark){
                gui.setColor(0, 0, 0, 1);
                InventoryScreen.renderEntityInInventory(gui, x + entityX, y + entityY + yOffset, scale, ENTITY_ANGLE, null, this.entity);
                gui.setColor(1, 1, 1, 1);
            } else {
                InventoryScreen.renderEntityInInventory(gui, x + entityX, y + entityY + yOffset, scale, ENTITY_ANGLE, null, this.entity);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void resultArrow(GuiGraphics gui, int x, int y){
        gui.blit(BACKGROUND, x + 77, y + 107, 306, 15, 9, 7, 512, 512);
    }
}
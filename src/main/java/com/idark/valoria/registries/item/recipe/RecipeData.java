package com.idark.valoria.registries.item.recipe;

public class RecipeData{
    public final int count;
    public int current;
    public boolean isEnough;

    public RecipeData(int count, int current, boolean isEnough){
        this.count = count;
        this.current = current;
        this.isEnough = isEnough;
    }

    public RecipeData(int count){
        this.count = count;
        this.current = 0;
        this.isEnough = false;
    }

    public void setCurrent(int value){
        current = value;
    }

    public void setEnough(boolean value){
        isEnough = value;
    }
}
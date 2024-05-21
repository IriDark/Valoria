package com.idark.valoria.api.unlockable;

import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;

public class Unlockable{
    public String id;

    public Unlockable(String id){
        this.id = id;
    }

    public boolean canReceived(){
        return false;
    }

    public String getId(){
        return id;
    }

    public boolean hasAllAward(){
        return true;
    }

    public void award(Player player){
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getIcon(){
        return ItemStack.EMPTY;
    }
}
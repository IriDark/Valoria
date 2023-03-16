package com.idark.darkrpg.item;

import com.idark.darkrpg.item.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

    public class ModItemGroup {

    public static final ItemGroup DARKRPG_GROUP = new ItemGroup("DarkRPGModTab") {
   
    @Override
    public ItemStack createIcon() {
    return new ItemStack(ModItems.NATURE_PICKAXE.get());
    }
};
    public static final ItemGroup DARKRPG_BLOCKS_GROUP = new ItemGroup("DarkRPGBlocksModTab") {
    
	@Override
    public ItemStack createIcon() {
    return new ItemStack(ModItems.VOID_STONE.get());
     }
   };
 }
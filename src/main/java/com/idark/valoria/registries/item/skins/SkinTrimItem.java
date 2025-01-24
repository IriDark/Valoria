package com.idark.valoria.registries.item.skins;

import com.idark.valoria.Valoria;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class SkinTrimItem extends Item{
    public ItemSkin skin;
    private static final ResourceLocation EMPTY_SLOT_HELMET = new ResourceLocation("item/empty_armor_slot_helmet");
    private static final ResourceLocation EMPTY_SLOT_CHESTPLATE = new ResourceLocation("item/empty_armor_slot_chestplate");
    private static final ResourceLocation EMPTY_SLOT_LEGGINGS = new ResourceLocation("item/empty_armor_slot_leggings");
    private static final ResourceLocation EMPTY_SLOT_BOOTS = new ResourceLocation("item/empty_armor_slot_boots");
    private static final ResourceLocation EMPTY_SLOT_HOE = new ResourceLocation("item/empty_slot_hoe");
    private static final ResourceLocation EMPTY_SLOT_AXE = new ResourceLocation("item/empty_slot_axe");
    private static final ResourceLocation EMPTY_SLOT_SWORD = new ResourceLocation("item/empty_slot_sword");
    private static final ResourceLocation EMPTY_SLOT_SHOVEL = new ResourceLocation("item/empty_slot_shovel");
    private static final ResourceLocation EMPTY_SLOT_PICKAXE = new ResourceLocation("item/empty_slot_pickaxe");

    public SkinTrimItem(ItemSkin skin, Item.Properties properties){
        super(properties);
        this.skin = skin;
    }

    public List<ResourceLocation> createBaseEmptyIcons(){
        return List.of(EMPTY_SLOT_HELMET, EMPTY_SLOT_CHESTPLATE, EMPTY_SLOT_LEGGINGS, EMPTY_SLOT_BOOTS, EMPTY_SLOT_HOE, EMPTY_SLOT_AXE, EMPTY_SLOT_SWORD, EMPTY_SLOT_SHOVEL, EMPTY_SLOT_PICKAXE);
    }

    public boolean canApply(ItemStack stack){
        return skin.canApplyOnItem(stack);
    }

    public ItemSkin getSkin(){
        return skin;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, level, tooltip, flags);
        tooltip.add(getSkin().getSkinComponent());
    }

    public String getDescriptionId(){
        return Util.makeDescriptionId("item", new ResourceLocation(Valoria.ID, "skin_trim"));
    }
}

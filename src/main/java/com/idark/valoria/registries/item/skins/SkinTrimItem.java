package com.idark.valoria.registries.item.skins;

import com.idark.valoria.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.*;
import pro.komaru.tridot.common.registry.item.skins.*;

import java.util.*;

public class SkinTrimItem extends Item{
    public ItemSkin skin;
    private static final ResourceLocation EMPTY_SLOT_HELMET = ResourceLocation.parse("item/empty_armor_slot_helmet");
    private static final ResourceLocation EMPTY_SLOT_CHESTPLATE = ResourceLocation.parse("item/empty_armor_slot_chestplate");
    private static final ResourceLocation EMPTY_SLOT_LEGGINGS = ResourceLocation.parse("item/empty_armor_slot_leggings");
    private static final ResourceLocation EMPTY_SLOT_BOOTS = ResourceLocation.parse("item/empty_armor_slot_boots");
    private static final ResourceLocation EMPTY_SLOT_HOE = ResourceLocation.parse("item/empty_slot_hoe");
    private static final ResourceLocation EMPTY_SLOT_AXE = ResourceLocation.parse("item/empty_slot_axe");
    private static final ResourceLocation EMPTY_SLOT_SWORD = ResourceLocation.parse("item/empty_slot_sword");
    private static final ResourceLocation EMPTY_SLOT_SHOVEL = ResourceLocation.parse("item/empty_slot_shovel");
    private static final ResourceLocation EMPTY_SLOT_PICKAXE = ResourceLocation.parse("item/empty_slot_pickaxe");

    public SkinTrimItem(ItemSkin skin, Item.Properties properties){
        super(properties);
        this.skin = skin;
    }

    public List<ResourceLocation> createBaseEmptyIcons(){
        return List.of(EMPTY_SLOT_HELMET, EMPTY_SLOT_CHESTPLATE, EMPTY_SLOT_LEGGINGS, EMPTY_SLOT_BOOTS, EMPTY_SLOT_HOE, EMPTY_SLOT_AXE, EMPTY_SLOT_SWORD, EMPTY_SLOT_SHOVEL, EMPTY_SLOT_PICKAXE);
    }

    public boolean canApply(ItemStack stack){
        return skin.appliesOn(stack);
    }

    public ItemSkin getSkin(){
        return skin;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext level, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, level, tooltip, flags);
        tooltip.add(getSkin().skinComponent());
        var player = Minecraft.getInstance().player;
        if(player != null){
            if(Screen.hasShiftDown()){
                tooltip.add(Component.translatable("tooltip.valoria.skin").withStyle(ChatFormatting.GRAY));
                for(var reg : BuiltInRegistries.ITEM.entrySet()) {
                    for(SkinEntry skinEntry : skin.skinEntries()) {
                        ItemStack item = reg.getValue().getDefaultInstance();
                        if(skinEntry.appliesOn(item)){
                            tooltip.add(Component.literal("  • ").append(item.getHoverName()).withStyle(item.getRarity().getStyleModifier()));
                        }
                    }
                }
            } else {
                tooltip.add(Component.translatable("tooltip.tridot.shift_for_details", Component.translatable("key.keyboard.left.shift").getString()).withStyle(ChatFormatting.GRAY));
            }
        }
    }

    public String getDescriptionId(){
        return Util.makeDescriptionId("item", Valoria.loc("skin_trim"));
    }
}

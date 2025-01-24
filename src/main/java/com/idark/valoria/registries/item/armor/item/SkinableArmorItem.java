package com.idark.valoria.registries.item.armor.item;

import mod.maxbogomol.fluffy_fur.client.model.armor.ArmorModel;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.extensions.IForgeItem;

import java.util.List;

import static com.idark.valoria.util.ValoriaUtils.addContributorTooltip;

public class SkinableArmorItem extends PercentageArmorItem implements IForgeItem{
    public SkinableArmorItem(ArmorMaterial pMaterial, Type pType, Properties pProperties){
        super(pMaterial, pType, pProperties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type){
        ItemSkin skin = ItemSkin.getSkinFromItem(stack);
        if(skin == null) return super.getArmorTexture(stack, entity, slot, type);
        return skin.getArmorTexture(stack, entity, slot, type);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        addContributorTooltip(stack, tooltip);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.extensions.common.IClientItemExtensions> consumer){
        consumer.accept(new IClientItemExtensions(){
            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel original){
                float partialTicks = Minecraft.getInstance().getFrameTime();
                float f = Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
                float f1 = Mth.rotLerp(partialTicks, entity.yHeadRotO, entity.yHeadRot);
                float netHeadYaw = f1 - f;
                float netHeadPitch = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());

                ArmorModel model;
                ItemSkin skin = ItemSkin.getSkinFromItem(itemStack);
                if(skin != null){
                    model = skin.getArmorModel(entity, itemStack, armorSlot, original);
                    model.slot = type.getSlot();
                    model.copyFromDefault(original);
                    model.setupAnim(entity, entity.walkAnimation.position(partialTicks), entity.walkAnimation.speed(partialTicks), entity.tickCount + partialTicks, netHeadYaw, netHeadPitch);
                    return model;
                }

                return original;
            }
        });
    }
}

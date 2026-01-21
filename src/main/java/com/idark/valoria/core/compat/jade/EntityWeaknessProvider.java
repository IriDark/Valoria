package com.idark.valoria.core.compat.jade;

import com.google.common.cache.*;
import com.google.common.collect.*;
import com.idark.valoria.core.interfaces.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.packs.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.phys.*;
import snownee.jade.api.*;
import snownee.jade.api.config.*;
import snownee.jade.api.theme.*;
import snownee.jade.api.ui.*;
import snownee.jade.api.ui.IElement.*;
import snownee.jade.impl.ui.*;

import java.util.*;
import java.util.concurrent.*;

public enum EntityWeaknessProvider implements IEntityComponentProvider, ResourceManagerReloadListener{
    INSTANCE;

    public static final Cache<IEffectiveWeaponEntity, ImmutableList<ItemStack>> resultCache = CacheBuilder.newBuilder().expireAfterAccess(5L, TimeUnit.MINUTES).build();
    private static final Vec2 ITEM_SIZE = new Vec2(10.0F, 0.0F);
    private static final Component CHECK = Component.literal("✔");
    private static final Component X = Component.literal("✕");

    @Override
    public ResourceLocation getUid(){
        return ModPlugin.ENTITY_WEAKNESS;
    }

    public static ImmutableList<ItemStack> getTool(IEffectiveWeaponEntity entity) {
        ImmutableList.Builder<ItemStack> tools = ImmutableList.builder();
        ItemStack tool = new EffectiveWeaponHandler("weapons", () -> getEffectiveWeapons(entity)).test(entity);
        if (!tool.isEmpty()) {
            tools.add(tool);
        }

        return tools.build();
    }

    public static List<ItemStack> getEffectiveWeapons(IEffectiveWeaponEntity entity) {
        var tagKey = entity.getEffective();

        Optional<HolderSet.Named<Item>> tag = BuiltInRegistries.ITEM.getTag(tagKey);

        if (tag.isPresent()) {
            return tag.get().stream()
            .findFirst()
            .map(holder -> new ItemStack(holder.value()))
            .stream().toList();
        }

        return List.of();
    }

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor entityAccessor, IPluginConfig config){
        if(entityAccessor.getEntity() instanceof IEffectiveWeaponEntity eff){
            boolean newLine = config.get(Identifiers.MC_HARVEST_TOOL_NEW_LINE);
            List<IElement> elements = this.getText(entityAccessor, eff, config);
            if (!elements.isEmpty()) {
                elements.forEach((e) -> e.message(null));
                if (newLine) {
                    tooltip.add(elements);
                } else {
                    elements.forEach((e) -> e.align(Align.RIGHT));
                    tooltip.append(0, elements);
                }
            }
        }
    }

    public List<IElement> getText(EntityAccessor entityAccessor, IEffectiveWeaponEntity eff, IPluginConfig config){
        List<ItemStack> tools = List.of();
        int offsetY = 0;
        if (!config.get(Identifiers.MC_HARVEST_TOOL_NEW_LINE)) {
            offsetY = -3;
        }

        try{
            tools = resultCache.get(eff, () -> getTool(eff));
        }catch(ExecutionException e){
            e.printStackTrace();
        }

        if(!tools.isEmpty()){
            List<IElement> elements = Lists.newArrayList();
            for(ItemStack tool : tools) {
                elements.add(IElementHelper.get().item(tool, 0.75F).translate(new Vec2(-1.0F, (float)offsetY)).size(ITEM_SIZE).message((String)null));
            }

            elements.add(0, IElementHelper.get().spacer(5, 0));
            ItemStack held = entityAccessor.getPlayer().getMainHandItem();
            boolean isEffective = held.is(eff.getEffective());
            IThemeHelper t = IThemeHelper.get();
            Component text = isEffective ? t.success(CHECK) : t.danger(X);
            elements.add((new SubTextElement(text)).translate(new Vec2(-3.0F, (float)(7 + offsetY))));
            return elements;
        } else {
            return List.of();
        }
    }

    public void onResourceManagerReload(ResourceManager resourceManager) {
        resultCache.invalidateAll();
    }
}
package com.idark.valoria.registries.item.types;

import com.idark.valoria.*;
import com.idark.valoria.client.render.tile.*;
import net.minecraft.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.extensions.common.*;

import java.util.function.*;

public class BossTrophyBlockItem extends BlockItem{
    public BossTrophyBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public String getDescriptionId(){
        return Util.makeDescriptionId("block", Valoria.loc("boss_trophy"));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer){
        consumer.accept(new IClientItemExtensions(){
            private final BlockEntityWithoutLevelRenderer renderer = new BossTrophyItemRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer(){
                return renderer;
            }
        });
    }
}
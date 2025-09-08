package com.idark.valoria.client.render.item;

import com.idark.valoria.client.model.item.*;
import com.idark.valoria.registries.item.types.*;
import net.minecraft.world.item.*;
import software.bernie.geckolib.renderer.*;

public class FlameSwordRenderer extends GeoItemRenderer<FlameSwordItem>{
    public ItemDisplayContext transformType;

    public FlameSwordRenderer(){
        super(new FlameSwordModel());
    }


}
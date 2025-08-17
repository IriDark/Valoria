package com.idark.valoria.registries.item.types.curio.hands;

import com.idark.valoria.registries.item.types.builders.*;
import com.idark.valoria.registries.item.types.curio.*;

public class TalismanItem extends AbstractTalismanItem{
    public TalismanItem(Builder builder){
        super(builder);
    }

    public static class Builder extends AbstractTalismanBuilder<TalismanItem>{
        public Builder(Properties pProperties){
            super(pProperties);
        }

        public TalismanItem build(){
            return new TalismanItem(this);
        }
    }
}
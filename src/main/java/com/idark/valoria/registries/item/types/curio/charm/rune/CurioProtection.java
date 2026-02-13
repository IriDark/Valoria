package com.idark.valoria.registries.item.types.curio.charm.rune;

public class CurioProtection extends AbstractRuneItem{
    public CurioProtection(Properties properties){
        super(properties);
    }

    @Override
    public RuneType runeType(){
        return RuneType.PROTECTION;
    }
}
package com.idark.valoria.registries.item.types.curio.charm.rune;

public class CurioNihilityProtection extends AbstractRuneItem{
    public CurioNihilityProtection(Properties properties){
        super(properties);
    }

    @Override
    public RuneType runeType(){
        return RuneType.PROTECTION;
    }
}
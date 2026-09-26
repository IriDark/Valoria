package com.idark.valoria.util;

import net.minecraft.world.*;

public final class BlockInteraction{
    private BlockInteraction(){}

    public static ItemInteractionResult toItemResult(InteractionResult result){
        return switch(result){
            case SUCCESS, SUCCESS_NO_ITEM_USED -> ItemInteractionResult.SUCCESS;
            case CONSUME -> ItemInteractionResult.CONSUME;
            case CONSUME_PARTIAL -> ItemInteractionResult.CONSUME_PARTIAL;
            case FAIL -> ItemInteractionResult.FAIL;
            case PASS -> ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        };
    }
}
